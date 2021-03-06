package TypeWriting.gui.inputing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.WordPanel;

/**
 * 用于展示打字速度的子窗口
 */
@SuppressWarnings("serial")
@Component("Displayboard")
public class Displayboard extends JPanel {

	private JScrollPane scrollPane;

	// WordPanel队列
	private List<WordPanel> wordQueue = new ArrayList<WordPanel>();

	// 用于记录需要移除的Word的变量，以及保护它的锁
	private Object removeLock = new Object();
	private WordPanel needToRemove = null;

	// 记录设置需要移除的Word时的系统时间
	private long removeTime = 0;

	public Displayboard() {
		new RemoveThread().start();
	}

	public void clear() {
		synchronized (removeLock) {
			needToRemove = null;
			wordQueue.clear();
			removeTime = 0;
			removeAll();
		}
	}

	class RemoveThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {

					// 每隔一定时间才执行，减小系统消耗
					Thread.sleep(Config.DisplayBoardRemoveTime);

					synchronized (removeLock) {

						// 如果超过了阈值，那么就移除掉需要移除的Word
						if (needToRemove != null
								&& System.currentTimeMillis() - removeTime > Config.DisplayBoardRemoveCeil) {
							clearRemoveWord();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 收到了一个信息，调整展示内容并刷新显示。
	 */
	public void receiveWord(long time, String type, String text, int offset,
			int length) {
		WordPanel word = new WordPanel(time, type, text, offset, length);
		word.init();

		if ("INSERT".equals(type)) {
			insertWord(word);
		} else if ("REMOVE".equals(type)) {
			removeWord(word);
		}

		showWords();

		if (wordQueue.size() > 0
				&& wordQueue.get(wordQueue.size() - 1).getOffset() == offset) {
			JScrollBar bar = scrollPane.getVerticalScrollBar();
			bar.setValue(bar.getMaximum());
		}
	}

	/**
	 * 找出涉及到offset的最前一个WordPanel的下标
	 */
	private int findFirstWordIndex(int offset) {
		for (int i = 0; i < wordQueue.size(); i++) {
			WordPanel word = wordQueue.get(i);
			if ("INSERT".equals(word.getType())) {
				if (word.getOffset() + word.getLength() > offset) {
					return i;
				}
			}
		}
		return wordQueue.size();
	}

	/**
	 * 接收到新增一段文字的信息时调用此方法。 如果插入这段文字不需要拆分已有的WordPanel，那么就只新增一个WordPanel。
	 * 否则需要拆分原有的某个WordPanel，然后再新增。
	 */
	private void insertWord(WordPanel word) {

		synchronized (removeLock) {
			if (needToRemove != null
					&& needToRemove.getOffset() == word.getOffset()) {
				needToRemove.setText(word.getText());
				needToRemove.setTime(word.getTime());
				needToRemove.setType(word.getType());
				needToRemove.setLength(word.getLength());
				needToRemove.refresh();
				needToRemove = null;
				return;
			}
		}

		int index = findFirstWordIndex(word.getOffset());

		if (index >= wordQueue.size()) { // 在最后插入
			wordQueue.add(word);
			add(word);
		} else if (index < 0) { // 在最前插入
			wordQueue.add(0, word);
			add(word);
		} else { // 在中间插入

			WordPanel old = wordQueue.get(index);

			if ("REMOVE".equals(old.getType())) { // 如果是REMOVE，那就放到这一个后面
				wordQueue.add(index + 1, word);
				add(word);
			} else if ("INSERT".equals(old.getType())) { // 如果是INSERT
				if (old.getOffset() == word.getOffset()) { // 正好offset相等，那就放到这一个前面
					wordQueue.add(index, word);
					add(word);
				} else { // 不相等（那么一定是大于），需要把这一个拆为两个，然后放到中间

					String oldText = old.getText(); // 需要拆分的text
					String leftText = oldText.substring(0, word.getOffset() // 拆分后的前面部分
							- old.getOffset());
					String rightText = oldText.substring(word.getOffset() // 拆分后的后面部分
							- old.getOffset());

					// 删掉被拆分的
					wordQueue.remove(old);
					remove(old);

					// 添加拆分后的后面部分
					if (!"".equals(rightText)) {
						WordPanel right = new WordPanel(old.getTime(),
								"INSERT", rightText, old.getOffset()
										+ leftText.length() + word.getLength(),
								rightText.length());
						right.init();
						wordQueue.add(index, right);
						add(right);
					}

					// 添加新的
					wordQueue.add(index, word);
					add(word);

					// 添加拆分后的前面部分
					if (!"".equals(leftText)) {
						WordPanel left = new WordPanel(old.getTime(), "INSERT",
								leftText, old.getOffset() + leftText.length(),
								leftText.length());
						left.init();
						wordQueue.add(index, left);
						add(left);
					}
				}
			}
		}
	}

	private void clearRemoveWord() {
		synchronized (removeLock) {
			if (needToRemove != null) {
				wordQueue.remove(needToRemove);
				remove(needToRemove);
				needToRemove = null;
			}
		}
		showWords();
	}

	/**
	 * 接收到删除一段文字的信息时调用此方法。 把需要删除的文字的跨度范围内的所有WordPanel都删除的。
	 * 在两端的WordPanel有时候需要拆分开，因为被删除的文字可能只涉及到它们的Text的一部分。
	 */
	private void removeWord(WordPanel word) {

		clearRemoveWord();

		int index = findFirstWordIndex(word.getOffset());
		if (index < 0 || index > wordQueue.size()) {
			return;
		}

		// 找到最开始受影响的一个
		WordPanel start = wordQueue.get(index);

		// 如果是删除掉这一个
		if (start.getOffset() == word.getOffset()
				&& start.getLength() == word.getLength()) {
			synchronized (removeLock) {
				needToRemove = start;
				removeTime = System.currentTimeMillis();
			}

			return;
		}

		// 最开始受影响的剩余部分
		String leftText = start.getText().substring(0,
				word.getOffset() - start.getOffset());

		// 用于记录最后受影响的剩余部分
		String rightText = "";

		// 用于记录最后受影响的一个
		WordPanel end = null;

		while (index < wordQueue.size()) {

			end = wordQueue.get(index);

			if (!"INSERT".equals(end.getType())) {
				index++;
				continue;
			}

			// 删除这一个
			wordQueue.remove(end);
			remove(end);

			// 如果这一个是最后一个受影响的，那么剩余部分的长度一定>=0
			int rightLength = end.getOffset() + end.getLength()
					- word.getOffset() - word.getLength();
			if (rightLength >= 0) {

				// 最后受影响的剩余部分
				rightText = end.getText().substring(
						end.getLength() - rightLength);
				break;
			}

			// PS：这里index没有自增，因为有wordQueue.remove(end)语句，每次循环都会把size减1
		}

		// 添加最后受影响的剩余部分
		if (!"".equals(rightText)) {
			WordPanel right = new WordPanel(end.getTime(), "INSERT", rightText,
					end.getOffset() + leftText.length() + word.getLength(),
					rightText.length());
			right.init();
			wordQueue.add(index, right);
			add(right);
		}

		// 添加最开始受影响的剩余部分
		if (!"".equals(leftText)) {
			WordPanel left = new WordPanel(start.getTime(), "INSERT", leftText,
					start.getOffset() + leftText.length(), leftText.length());
			left.init();
			wordQueue.add(index, left);
			add(left);
		}
	}

	public void reshowWords() {
		showWords();
	}

	private void showWords() {

		int paddingLeft = Config.DisplayBoardPaddingLeft;
		int paddingTop = Config.DisplayBoardPaddingTop;
		int wordHeight = Config.DisplayBoardWordHeight;
		int wordGap = Config.DisplayBoardWordGap;
		boolean wordBorder = Config.DisplayBoardWordBorder;
		int width = getWidth();
		int top = paddingTop;
		int left = paddingLeft;
		int offset = 0;
		for (int i = 0; i < wordQueue.size(); i++) {
			WordPanel word = wordQueue.get(i);
			if ("INSERT".equals(word.getType())) {
				word.setOffset(offset);
				word.setLength(word.getText().length());
				offset += word.getLength();

				// 如果是换行
				if ("\r".equals(word.getText())
						|| "\r\n".equals(word.getText())
						|| "\n".equals(word.getText())) {
					word.setLocation(-1000, -1000);
					left = paddingLeft;
					top += wordHeight;
					continue;
				}
			}
			int wordWidth = word.getWidth();
			if (wordWidth + left > width && left > paddingLeft) {
				left = paddingLeft;
				top += wordHeight;
			}
			word.setLocation(left, top);
			left += wordWidth + wordGap;
			if (wordBorder) {
				left -= 1;
			}
		}

		int height = scrollPane.getHeight();
		setSize(width, Math.max(height - 30, top + wordHeight));
		setPreferredSize(getSize());

		repaint();
	}

	public void init() {
		createScrollPane();
		initStyle();
		setLayout(null);
	}

	public void initStyle() {
		this.setBackground(Color.white);

	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	private void createScrollPane() {
		scrollPane = new JScrollPane(this);
		scrollPane.setBackground(Config.BoardColor);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		createPadding();
	}

	private void createPadding() {
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(null, Config.DisplayboardBoarderTitle,
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						Config.BoardBorderFont), BorderFactory
				.createEmptyBorder(1, 1, 1, 1)));
	}
}

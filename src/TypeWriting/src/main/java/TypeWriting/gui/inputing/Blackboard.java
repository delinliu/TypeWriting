package TypeWriting.gui.inputing;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.Article;
import TypeWriting.listener.BlackboardSelectedListener;

/**
 * 用于展示文章。
 */
@SuppressWarnings("serial")
@Component("Blackboard")
public class Blackboard extends JTextPane {

	private JScrollPane scrollPane;

	@Resource(name = "BlackboardSelectedListener")
	BlackboardSelectedListener blackboardSelectedListener;

	private String formatText(String text) {
		text.replaceAll("\r\n", "\n");
		text.replaceAll("\r", "\n");
		String[] paragraphs = text.split("\n");
		StringBuilder all = new StringBuilder();
		for (String paragraph : paragraphs) {
			paragraph = paragraph.trim();
			int start = 0;
			while (start < paragraph.length() && paragraph.charAt(start) == '　') {
				start++;
			}
			paragraph = paragraph.substring(start);

			int end = paragraph.length() - 1;
			while (end >= 0 && paragraph.charAt(end) == '　') {
				end--;
			}
			paragraph = paragraph.substring(0, end + 1);

			all.append("    " + paragraph + "\n");
		}
		return all.toString();
	}

	public void display(Article article) {
		try {
			String text = formatText(new String(article.getArticleContent()));
			getDocument().insertString(0, text, Config.PlainAttrSet);
			setCaretPosition(0);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		setText("");
	}

	public void init() {

		createScrollPane();
		initStyle();
		addMouseListener(blackboardSelectedListener);
	}

	public void initStyle() {
		setFont(Config.BoardFont);
		setEditable(false);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	private void createScrollPane() {
		scrollPane = new JScrollPane(this);
		scrollPane.setBackground(Config.BoardColor);
		createPadding();
	}

	private void createPadding() {
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(null, Config.BlackboardBoarderTitle,
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						Config.BoardBorderFont), BorderFactory
				.createEmptyBorder(1, 1, 1, 1)));
	}
}

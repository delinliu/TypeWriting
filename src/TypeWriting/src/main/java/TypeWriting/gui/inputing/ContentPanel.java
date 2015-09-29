package TypeWriting.gui.inputing;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.annotation.Resource;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;
import TypeWriting.config.Config;

/**
 * 进行打字练习的时候的主内容面板。 负责Blackboard、Inputboard、Hintboard、Displayboard的排版。
 */
@SuppressWarnings("serial")
@Component("ContentPanel")
public class ContentPanel extends JPanel implements ComponentListener {

	@Resource(name = "Blackboard")
	private Blackboard blackboard;
	@Resource(name = "Inputboard")
	private Inputboard inputboard;
	@Resource(name = "Hintboard")
	private Hintboard hintboard;
	@Resource(name = "Displayboard")
	private Displayboard displayboard;

	public void init() {

		setBackground(Config.ContentBackground);
		setLayout(null);
		setVisible(true);

		blackboard.init();
		add(blackboard.getScrollPane());

		inputboard.init();
		add(inputboard.getScrollPane());

		hintboard.init();
		add(hintboard);

		displayboard.init();
		add(displayboard.getScrollPane());

		addComponentListener(this);
	}

	/**
	 * 大小被改变了，重新调整子部件的位置和大小。
	 */
	@Override
	public void componentResized(ComponentEvent e) {

		// 得到面板的size
		int width = this.getWidth();
		int height = this.getHeight();

		int padding = Config.ContentPadding;
		int vGap = Config.ContentVGap;
		int hGap = Config.ContentHGap;
		int hintH = Config.HintHeight;

		// 调整Blackboard
		int boardWidth = width / 2 - padding;
		int boardHeight = (height - padding * 2 - vGap * 2 - hintH) / 2;
		blackboard.getScrollPane().setLocation(padding, padding);
		blackboard.getScrollPane().setSize(boardWidth, boardHeight);
		blackboard.setSize(boardWidth, boardHeight);
		blackboard.repaint();

		// 调整Inputboard
		inputboard.getScrollPane().setLocation(padding,
				padding + boardHeight + vGap * 2 + hintH);
		inputboard.getScrollPane().setSize(boardWidth, boardHeight);
		inputboard.setSize(boardWidth, boardHeight);
		inputboard.repaint();

		// 调整Hintboard
		hintboard.setLocation(padding, padding + boardHeight + vGap);
		hintboard.setSize(boardWidth, hintH);

		// 调整Displayboard
		displayboard.getScrollPane().setLocation(padding + boardWidth + hGap,
				padding);
		displayboard.getScrollPane().setSize(
				width - padding * 2 - boardWidth - hGap, height - padding * 2);
		displayboard.setSize(width - padding * 2 - boardWidth - hGap - 15,
				displayboard.getHeight());

		// 刷新展示面板
		displayboard.reshowWords();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}

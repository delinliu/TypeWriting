package TypeWriting.gui.articlelist;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.annotation.Resource;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component("ArticleListPanel")
public class ArticleListPanel extends JPanel implements ComponentListener {

	@Resource(name = "ArticleListBoard")
	private ArticleListBoard articleListBoard;

	public void init() {
		setBackground(Color.WHITE);
		setLayout(null);

		articleListBoard.init();
		add(articleListBoard.getScrollPane());

		addComponentListener(this);
	}

	@Override
	public void componentResized(ComponentEvent e) {

		int paddingTop = 10;
		int paddingBottom = 20;
		int paddingLeft = 30;
		int articleListBoardWidth = 400;
		int width = this.getWidth();
		int height = this.getHeight();

		articleListBoard.getScrollPane().setLocation(paddingLeft, paddingTop);
		articleListBoard.getScrollPane().setSize(articleListBoardWidth,
				height - paddingTop - paddingBottom);
		articleListBoard.setSize(articleListBoardWidth, height - paddingTop
				- paddingBottom);
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

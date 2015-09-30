package TypeWriting.gui.articlemanager;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.annotation.Resource;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import TypeWriting.entity.Article;

@SuppressWarnings("serial")
@Component("ArticleManagerPanel")
public class ArticleManagerPanel extends JPanel implements ComponentListener {

	@Resource(name = "ArticleManagerListBoard")
	private ArticleManagerListBoard articleManagerListBoard;

	@Resource(name = "EditContentBoard")
	private EditContentBoard editContentBoard;

	@Resource(name = "EditTitleBoard")
	private EditTitleBoard editTitleBoard;

	private StyleButton editButton = new StyleButton("确认编辑文章");
	private StyleButton deleteButton = new StyleButton("删除这篇文章");

	public void display() {
		articleManagerListBoard.display();
		editContentBoard.clear();
		editTitleBoard.clear();
		editButton.clear();
		deleteButton.clear();
	}
	
	public void displayArticle(Article article){
		editContentBoard.display(article);
		editTitleBoard.display(article);
		editButton.display();
		deleteButton.display();
	}

	public void init() {
		setBackground(Color.WHITE);
		setLayout(null);

		articleManagerListBoard.init();
		add(articleManagerListBoard.getScrollPane());

		editContentBoard.init();
		add(editContentBoard.getScrollPane());

		editTitleBoard.init();
		add(editTitleBoard);

		add(editButton);
		add(deleteButton);

		addComponentListener(this);
	}

	@Override
	public void componentResized(ComponentEvent e) {

		int paddingTop = 10;
		int paddingBottom = 20;
		int paddingRight = 30;
		int paddingLeft = 30;
		int gapH = 20;
		int gapV = 10;
		int articleManagerListBoardWidth = 400;
		int width = this.getWidth();
		int height = this.getHeight();
		int editTitleBoardHeight = 50;
		int buttonHeight = editButton.getHeight();
		int buttonWidth = editButton.getWidth();
		int buttonGap = 20;

		articleManagerListBoard.getScrollPane()
				.setLocation(
						width - paddingRight - articleManagerListBoardWidth,
						paddingTop);
		articleManagerListBoard.getScrollPane().setSize(
				articleManagerListBoardWidth,
				height - paddingTop - paddingBottom);
		articleManagerListBoard.setSize(articleManagerListBoardWidth, height
				- paddingTop - paddingBottom);

		editContentBoard.getScrollPane().setLocation(paddingLeft,
				paddingTop + editTitleBoardHeight + gapV);
		editContentBoard.getScrollPane().setSize(
				width - paddingLeft - paddingRight
						- articleManagerListBoardWidth - gapH,
				height - paddingTop - editTitleBoardHeight - gapV * 2
						- buttonHeight - paddingBottom);
		editContentBoard.setSize(width - paddingLeft - paddingRight
				- articleManagerListBoardWidth - gapH, height - paddingTop
				- editTitleBoardHeight - gapV * 2 - buttonHeight
				- paddingBottom);

		editTitleBoard.setLocation(paddingLeft, paddingTop);
		editTitleBoard.setSize(width - paddingLeft - paddingRight
				- articleManagerListBoardWidth - gapH, editTitleBoardHeight);

		editButton.setLocation(paddingLeft, height - paddingBottom
				- buttonHeight);
		deleteButton.setLocation(paddingLeft + buttonWidth + buttonGap, height
				- paddingBottom - buttonHeight);
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

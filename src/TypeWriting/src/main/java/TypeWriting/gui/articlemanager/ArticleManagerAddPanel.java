package TypeWriting.gui.articlemanager;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.annotation.Resource;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import TypeWriting.entity.Article;
import TypeWriting.entity._Exception;
import TypeWriting.service.impl.ArticleServiceImpl;

@SuppressWarnings("serial")
@Component("ArticleManagerAddPanel")
public class ArticleManagerAddPanel extends JPanel implements
		ComponentListener, ActionListener {

	@Resource(name = "EditContentBoard")
	private EditContentBoard editContentBoard;

	@Resource(name = "EditTitleBoard")
	private EditTitleBoard editTitleBoard;

	@Resource(name = "ResultHintBoard")
	private ResultHintBoard resultHintBoard;

	@Resource(name = "ArticleServiceImpl")
	private ArticleServiceImpl articleService;

	private StyleButton addButton = new StyleButton("添加这篇文章");

	public void display() {
		editContentBoard.display(null);
		editTitleBoard.display(null);
		addButton.display();
		resultHintBoard.clear();
		add(editContentBoard.getScrollPane());
		add(editTitleBoard);
		add(addButton);
		add(resultHintBoard);
		resized();
	}

	public void init() {
		setBackground(Color.WHITE);
		setLayout(null);

		editContentBoard.init();

		editTitleBoard.init();

		resultHintBoard.init();

		addComponentListener(this);
		addButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
			try {
				resultHintBoard.display("操作中...", 0);
				Article article = new Article();
				article.setArticleTitle(editTitleBoard.getText());
				article.setArticleContentString(editContentBoard.getText());
				articleService.addArticle(article);
				resultHintBoard.display("新增文章成功！", 1);
				editTitleBoard.display(null);
				editContentBoard.display(null);
			} catch (_Exception e1) {
				// e1.printStackTrace();
				resultHintBoard.display(e1.getMessage(), 2);
			} catch (Exception e1) {
				e1.printStackTrace();
				resultHintBoard.display("新增文章失败！", 2);
			}
		}
	}
	
	public void resized(){
		int paddingTop = 10;
		int paddingBottom = 20;
		int paddingRight = 30;
		int paddingLeft = 30;
		int gapH = 20;
		int gapV = 10;
		int articleManagerListBoardWidth = 0;
		int width = getWidth();
		int height = getHeight();
		int editTitleBoardHeight = 50;
		int buttonHeight = addButton.getHeight();
		int buttonWidth = addButton.getWidth();
		int buttonGap = 20;

		int leftBoardWidth = width - paddingLeft - paddingRight
				- articleManagerListBoardWidth - gapH;
		int editContentHeight = height - paddingTop - editTitleBoardHeight
				- gapV * 2 - buttonHeight - paddingBottom;
		int editContentBoardTop = paddingTop + editTitleBoardHeight + gapV;

		editContentBoard.getScrollPane().setLocation(paddingLeft,
				editContentBoardTop);
		editContentBoard.getScrollPane().setSize(leftBoardWidth,
				editContentHeight);
		editContentBoard.setSize(leftBoardWidth, editContentHeight);

		editTitleBoard.setLocation(paddingLeft, paddingTop);
		editTitleBoard.setSize(leftBoardWidth, editTitleBoardHeight);

		addButton.setLocation(paddingLeft, height - paddingBottom
				- buttonHeight);

		resultHintBoard.setLocation(paddingLeft + buttonWidth + buttonGap,
				height - paddingBottom - buttonHeight);
		resultHintBoard.setSize(leftBoardWidth - buttonWidth - buttonGap,
				buttonHeight);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		resized();
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

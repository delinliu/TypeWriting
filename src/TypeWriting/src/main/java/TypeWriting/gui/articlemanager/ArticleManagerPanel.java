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
@Component("ArticleManagerPanel")
public class ArticleManagerPanel extends JPanel implements ComponentListener,
		ActionListener {

	@Resource(name = "ArticleManagerListBoard")
	private ArticleManagerListBoard articleManagerListBoard;

	@Resource(name = "EditContentBoard")
	private EditContentBoard editContentBoard;

	@Resource(name = "EditTitleBoard")
	private EditTitleBoard editTitleBoard;

	@Resource(name = "ResultHintBoard")
	private ResultHintBoard resultHintBoard;

	@Resource(name = "ArticleServiceImpl")
	private ArticleServiceImpl articleService;

	private StyleButton editButton = new StyleButton("更新这篇文章");
	private StyleButton deleteButton = new StyleButton("删除这篇文章");
	private Article article = null;

	public void display() {
		article = null;
		articleManagerListBoard.display();
		editContentBoard.clear();
		editTitleBoard.clear();
		editButton.clear();
		deleteButton.clear();
		resultHintBoard.clear();
		add(articleManagerListBoard.getScrollPane());
		add(editContentBoard.getScrollPane());
		add(editTitleBoard);
		add(deleteButton);
		add(editButton);
		add(resultHintBoard);
		resized();
	}

	public void displayArticle(Article article) {
		this.article = article;
		editContentBoard.display(article);
		editTitleBoard.display(article);
		editButton.display();
		deleteButton.display();
		resultHintBoard.clear();
	}

	public void init() {
		setBackground(Color.WHITE);
		setLayout(null);
		articleManagerListBoard.init();
		editContentBoard.init();
		editTitleBoard.init();
		resultHintBoard.init();
		addComponentListener(this);
		editButton.addActionListener(this);
		deleteButton.addActionListener(this);
	}

	public void resized() {
		int paddingTop = 10;
		int paddingBottom = 20;
		int paddingRight = 30;
		int paddingLeft = 30;
		int gapH = 20;
		int gapV = 10;
		int articleManagerListBoardWidth = 400;
		int width = getWidth();
		int height = getHeight();
		int editTitleBoardHeight = 70;
		int buttonHeight = editButton.getHeight();
		int buttonWidth = editButton.getWidth();
		int buttonGap = 20;

		int leftBoardWidth = width - paddingLeft - paddingRight
				- articleManagerListBoardWidth - gapH;
		int editContentHeight = height - paddingTop - editTitleBoardHeight
				- gapV * 2 - buttonHeight - paddingBottom;
		int editContentBoardTop = paddingTop + editTitleBoardHeight + gapV;

		int listBoardLeft = width - paddingRight - articleManagerListBoardWidth;
		int listBoardHeight = height - paddingTop - paddingBottom;

		articleManagerListBoard.getScrollPane().setLocation(listBoardLeft,
				paddingTop);
		articleManagerListBoard.getScrollPane().setSize(
				articleManagerListBoardWidth, listBoardHeight);
		articleManagerListBoard.setSize(articleManagerListBoardWidth,
				listBoardHeight);

		editContentBoard.getScrollPane().setLocation(paddingLeft,
				editContentBoardTop);
		editContentBoard.getScrollPane().setSize(leftBoardWidth,
				editContentHeight);
		editContentBoard.setSize(leftBoardWidth, editContentHeight);

		editTitleBoard.setLocation(paddingLeft, paddingTop);
		editTitleBoard.setSize(leftBoardWidth, editTitleBoardHeight);

		editButton.setLocation(paddingLeft, height - paddingBottom
				- buttonHeight);
		deleteButton.setLocation(paddingLeft + buttonWidth + buttonGap, height
				- paddingBottom - buttonHeight);

		resultHintBoard.setLocation(paddingLeft + buttonWidth * 2 + buttonGap
				* 2, height - paddingBottom - buttonHeight);
		resultHintBoard.setSize(leftBoardWidth - buttonWidth * 2 - buttonGap
				* 2, buttonHeight);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (article == null) {
			resultHintBoard.display("该文章已经被删除了！", 2);
			return;
		}
		if (e.getSource() == deleteButton) {
			try {
				resultHintBoard.display("操作中...", 0);
				articleService.deleteArticle(article);
				resultHintBoard.display("删除文章成功！", 1);
				articleManagerListBoard.removeArticle(article);
				article = null;
			} catch (_Exception e1) {
				// e1.printStackTrace();
				resultHintBoard.display(e1.getMessage(), 2);
			} catch (Exception e1) {
				e1.printStackTrace();
				resultHintBoard.display("删除文章失败！", 2);
			}
		} else if (e.getSource() == editButton) {
			try {
				resultHintBoard.display("操作中...", 0);
				Article newArticle = new Article();
				newArticle.setArticleId(article.getArticleId());
				newArticle.setArticleTitle(editTitleBoard.getText());
				newArticle.setArticleContentString(editContentBoard.getText());
				articleService.updateArticle(newArticle);
				resultHintBoard.display("更新文章成功！", 1);
				article.setArticleTitle(newArticle.getArticleTitle());
				article.setArticleContentString(newArticle
						.getArticleContentString());
				articleManagerListBoard.refreshContent();
			} catch (_Exception e1) {
				// e1.printStackTrace();
				resultHintBoard.display(e1.getMessage(), 2);
			} catch (Exception e1) {
				e1.printStackTrace();
				resultHintBoard.display("更新文章失败！", 2);
			}
		}
	}
}

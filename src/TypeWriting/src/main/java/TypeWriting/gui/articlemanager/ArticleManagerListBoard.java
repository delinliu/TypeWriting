package TypeWriting.gui.articlemanager;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.Article;
import TypeWriting.gui.inputing.App;
import TypeWriting.service.impl.ArticleServiceImpl;

@SuppressWarnings({ "serial", "rawtypes" })
@Component("ArticleManagerListBoard")
public class ArticleManagerListBoard extends JList implements
		ListSelectionListener {

	private JScrollPane scrollPane = new JScrollPane(this);

	@Resource(name = "ArticleServiceImpl")
	private ArticleServiceImpl articleService;

	@Resource(name = "App")
	private App app;

	@Resource(name = "ArticleManagerPanel")
	private ArticleManagerPanel articleManagerPanel;

	private List<Article> articles = null;

	@SuppressWarnings("unchecked")
	public void initContent() {

		List<Map<String, Object>> maps;
		try {
			maps = articleService.findArticleList();
			articles = new ArrayList<Article>();
			if (maps != null) {
				int seq = 1;
				for (Map<String, Object> map : maps) {
					Article article = new Article();
					article.setAddtime((Date) map.get("addtime"));
					article.setUpdatetime((Date) map.get("updatetime"));
					article.setArticleId((long) map.get("articleId"));
					article.setArticleTitle((String) map.get("articleTitle"));
					article.setArticleContent((byte[]) map
							.get("articleContent"));
					article.setDisplayName("第" + seq++ + "课："
							+ article.getArticleTitle());
					articles.add(article);
				}
			}
			setListData(articles.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void display() {
		initContent();
	}

	public void init() {
		setBackground(Color.WHITE);
		setFont(new Font("幼圆", Font.PLAIN, 15));
		setBorder(null);
		createScrollPane();
		initContent();

		addListSelectionListener(this);
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
		scrollPane
				.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory
								.createTitledBorder(
										null,
										"",
										javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
										javax.swing.border.TitledBorder.DEFAULT_POSITION,
										null), BorderFactory.createEmptyBorder(
								1, 1, 1, 1)));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int seq = getSelectedIndex();
		if (seq >= 0 && articles != null && articles.size() > seq) {
			articleManagerPanel.displayArticle(articles.get(seq));
		}
	}
}

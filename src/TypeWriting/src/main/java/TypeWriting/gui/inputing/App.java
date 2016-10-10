package TypeWriting.gui.inputing;

import javax.annotation.Resource;
import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import TypeWriting.config.Config;
import TypeWriting.entity.Article;
import TypeWriting.gui.articlelist.ArticleListPanel;
import TypeWriting.gui.articlemanager.ArticleManagerAddPanel;
import TypeWriting.gui.articlemanager.ArticleManagerPanel;
import TypeWriting.gui.menu.MainMenuBar;

@Service("App")
public class App {
	private JFrame frame = new JFrame();

	@Resource(name = "ContentPanel")
	private ContentPanel contentPanel;

	@Resource(name = "ArticleListPanel")
	private ArticleListPanel articleListPanel;

	@Resource(name = "ArticleManagerPanel")
	private ArticleManagerPanel articleManagerPanel;

	@Resource(name = "ArticleManagerAddPanel")
	private ArticleManagerAddPanel articleManagerAddPanel;

	@Resource(name = "MainMenuBar")
	private MainMenuBar mainMenuBar;

	public void switchContent(String name) {
		if (frame.getContentPane() == contentPanel) {
			contentPanel.clear();
		}
		if ("ArticleListPanel".equals(name)) {
			frame.setTitle(Config.FrameTitle);
			frame.setContentPane(articleListPanel);
			articleListPanel.display();
		} else if ("ArticleManagerPanel".equals(name)) {
			frame.setTitle("管理文章");
			frame.setContentPane(articleManagerPanel);
			articleManagerPanel.display();
		} else if ("ArticleManagerAddPanel".equals(name)) {
			frame.setTitle("新增文章");
			frame.setContentPane(articleManagerAddPanel);
			articleManagerAddPanel.display();
		}
		frame.validate();
	}

	public void displayInputingPanel(Article article) {
		frame.setTitle(article.toString());
		frame.setContentPane(contentPanel);
		frame.validate();
		contentPanel.display(article);
	}

	public void init() {

		// 初始化菜单
		mainMenuBar.init();
		frame.setJMenuBar(mainMenuBar);

		// 初始化内容面板
		contentPanel.init();

		// 初始化文章列表面板
		articleListPanel.init();

		// 初始化文章管理面板
		articleManagerPanel.init();

		// 初始化新增文章面板
		articleManagerAddPanel.init();

		switchContent("ArticleListPanel");
		// frame.setContentPane(articleListPanel);
		// frame.setContentPane(contentPanel);
		// frame.setContentPane(articleManagerPanel);

		// 初始化主窗口
		frame.setTitle(Config.FrameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Config.FrameWidth, Config.FrameHeight);
		if (Config.FrameFullScreen) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			frame.setSize(Config.FrameWidth, Config.FrameHeight);
		}
		frame.setVisible(true);
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		ApplicationContext factory = new ClassPathXmlApplicationContext(
				"context.xml");

		App app = (App) factory.getBean("App");

		app.init();
	}
}

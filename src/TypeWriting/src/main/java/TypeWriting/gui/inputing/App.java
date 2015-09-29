package TypeWriting.gui.inputing;

import javax.annotation.Resource;
import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import TypeWriting.config.Config;
import TypeWriting.gui.articlelist.ArticleListPanel;
import TypeWriting.gui.menu.MainMenuBar;
import TypeWriting.service.impl.TestServiceImpl;

@Service("App")
public class App {
	private JFrame frame = new JFrame();
	
	@Resource(name = "TestServiceImpl")
	private TestServiceImpl testServiceImpl;

	@Resource(name = "ContentPanel")
	private ContentPanel contentPanel;

	@Resource(name = "ArticleListPanel")
	private ArticleListPanel articleListPanel;

	@Resource(name = "MainMenuBar")
	private MainMenuBar mainMenuBar;

	public void switchContent(String name) {
		if("ArticleListPanel".equals(name)){
			frame.setContentPane(articleListPanel);
		}
		frame.validate();
	}

	public void init() {

//		testServiceImpl.testMethod();
		
		// 初始化菜单
		mainMenuBar.init();
		frame.setJMenuBar(mainMenuBar);

		// 初始化内容面板
		contentPanel.init();

		// 初始化文章列表面板
		articleListPanel.init();

		frame.setContentPane(articleListPanel);
//		frame.setContentPane(contentPanel);

		// 初始化主窗口
		frame.setTitle(Config.FrameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

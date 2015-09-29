package TypeWriting.gui.inputing;

import javax.annotation.Resource;
import javax.swing.JFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import TypeWriting.config.Config;
import TypeWriting.menu.MainMenuBar;

@Service("App")
public class App {
	private JFrame frame = new JFrame();

	@Resource(name = "ContentPanel")
	private ContentPanel contentPanel;

	@Resource(name = "MainMenuBar")
	private MainMenuBar mainMenuBar;

	public void init() {

		// 初始化菜单
		mainMenuBar.init();
		frame.setJMenuBar(mainMenuBar);

		// 初始化内容面板
		contentPanel.init();
		frame.setContentPane(contentPanel);

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

package TypeWriting.TypeWriting;

import java.awt.Dimension;

import javax.annotation.Resource;
import javax.swing.JFrame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import TypeWriting.config.Config;

@Service("App")
public class App {
	private JFrame frame;

	@Resource(name = "ContentPanel")
	private ContentPanel contentPanel;

	public void init() {

		// 初始化主窗口
		frame = new JFrame(Config.FrameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Config.FrameWidth, Config.FrameHeight);
		frame.setVisible(true);
		if (Config.FrameFullScreen) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		

		// 初始化内容面板
		contentPanel.init();
		Dimension sz = frame.getContentPane().getSize();
		contentPanel.setSize(sz);
		frame.setContentPane(contentPanel);
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		ApplicationContext factory = new ClassPathXmlApplicationContext(
				"context.xml");

		App app = (App) factory.getBean("App");

		app.init();
	}
}

package TypeWriting.TypeWriting;

import java.awt.Dimension;
import java.awt.Toolkit;

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

		// initiate frame
		frame = new JFrame(Config.FrameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Config.FrameWidth, Config.FrameHeight);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		// initiate frame's content pane
		contentPanel.init();
		Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
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

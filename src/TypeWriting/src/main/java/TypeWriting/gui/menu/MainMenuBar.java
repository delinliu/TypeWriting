package TypeWriting.gui.menu;

import javax.annotation.Resource;
import javax.swing.JMenuBar;
import org.springframework.stereotype.Component;
import TypeWriting.config.Config;

@SuppressWarnings("serial")
@Component("MainMenuBar")
public class MainMenuBar extends JMenuBar {

	@Resource(name = "StartMenu")
	private StartMenu startMenu;

	public void init() {

		// 初始化菜单条
		setBackground(Config.MenuBackgroundColor);
		setOpaque(true);
		setBorder(null);

		// 初始化开始菜单
		startMenu.init();
		add(startMenu);
	}
}

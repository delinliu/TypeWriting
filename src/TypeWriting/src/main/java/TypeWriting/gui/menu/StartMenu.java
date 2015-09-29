package TypeWriting.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.Resource;
import javax.swing.JMenu;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.gui.inputing.App;

/**
 * 开始菜单
 */
@SuppressWarnings("serial")
@Component("StartMenu")
public class StartMenu extends JMenu {

	private StyleMenuItem linkIndexPage = new StyleMenuItem(
			Config.MenuStartItem1);
	private StyleMenuItem linkManageArticle = new StyleMenuItem(
			Config.MenuStartItem2);

	@Resource(name = "App")
	private App app;

	public void init() {

		// 初始化开始菜单
		setText(Config.MenuStartTitle);
		setFont(Config.MenuFont);
		setBackground(Config.MenuBackgroundColor);
		setOpaque(true);
		setBorder(null);

		// 添加子项
		add(linkIndexPage);
		add(linkManageArticle);

		linkIndexPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.switchContent("ArticleListPanel");
			}
		});
	}
}
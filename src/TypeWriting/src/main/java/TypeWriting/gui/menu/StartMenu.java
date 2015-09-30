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
public class StartMenu extends JMenu implements ActionListener {

	private StyleMenuItem linkIndexPage = new StyleMenuItem(
			Config.MenuStartItem1);
	private StyleMenuItem linkManageArticle = new StyleMenuItem(
			Config.MenuStartItem2);
	private StyleMenuItem linkAddArticle = new StyleMenuItem(
			Config.MenuStartItem3);

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
		add(linkAddArticle);

		linkIndexPage.addActionListener(this);
		linkManageArticle.addActionListener(this);
		linkAddArticle.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == linkIndexPage) {
			app.switchContent("ArticleListPanel");
		} else if (e.getSource() == linkManageArticle) {
			app.switchContent("ArticleManagerPanel");
		} else if (e.getSource() == linkAddArticle) {
			app.switchContent("ArticleManagerAddPanel");
		}
	}
}

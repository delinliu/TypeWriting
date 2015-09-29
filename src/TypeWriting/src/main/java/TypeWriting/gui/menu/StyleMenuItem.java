package TypeWriting.gui.menu;

import javax.swing.JMenuItem;
import TypeWriting.config.Config;

/**
 * 为JMenuItem包装背景和字体
 */
@SuppressWarnings("serial")
public class StyleMenuItem extends JMenuItem {

	public StyleMenuItem() {
		super();
		style();
	}

	public StyleMenuItem(String title) {
		super(title);
		style();
	}

	private void style() {
		setBackground(Config.MenuBackgroundColor);
		setFont(Config.MenuFont);
		setOpaque(true);
		setBorder(null);
	}
}

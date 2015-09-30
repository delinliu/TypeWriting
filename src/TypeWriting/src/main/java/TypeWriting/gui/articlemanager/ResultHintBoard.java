package TypeWriting.gui.articlemanager;

import java.awt.Color;

import javax.swing.JTextPane;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;

@SuppressWarnings("serial")
@Component("ResultHintBoard")
public class ResultHintBoard extends JTextPane {

	public void display(String msg, int mode) {

		setText("");
		setVisible(true);
		try {
			switch (mode) {
			case 0:
				getStyledDocument().insertString(0, msg, Config.PlainAttrSet);
				break;
			case 1:
				getStyledDocument().insertString(0, msg, Config.FineAttrSet);
				break;
			case 2:
				getStyledDocument().insertString(0, msg, Config.FailAttrSet);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		setText("");
		setVisible(false);
	}

	public void init() {
		setBackground(Color.WHITE);
		setFont(Config.BoardFont);
		setEditable(false);
		clear();
	}
}

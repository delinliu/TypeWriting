package TypeWriting.TypeWriting;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import org.springframework.stereotype.Component;
import TypeWriting.config.Config;

/**
 * 用于展示拼音提示信息。
 */
@SuppressWarnings("serial")
@Component("Hintboard")
public class Hintboard extends JTextArea {

	public void init() {
		this.setText(Config.HintDefaultText);
		initStyle();
		setBorder(BorderFactory.createEmptyBorder(4, 10, 1, 1));
	}

	public void initStyle() {
		setFont(Config.BoardFont);
		setEditable(false);
		setLineWrap(false);
	}
}

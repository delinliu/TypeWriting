package TypeWriting.TypeWriting;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;

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
		this.setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
	}
}

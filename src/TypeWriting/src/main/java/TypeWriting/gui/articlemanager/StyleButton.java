package TypeWriting.gui.articlemanager;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class StyleButton extends JButton {

	public void display(){
		setEnabled(true);
		setVisible(true);
	}
	
	public void clear(){
		setEnabled(false);
		setVisible(false);
	}
	
	public StyleButton() {
		super();
		style();
		clear();
	}

	public StyleButton(String text) {
		super(text);
		style();
		clear();
	}

	public void style() {
		setFont(new Font("幼圆", Font.PLAIN, 16));
		setBackground(Color.WHITE);
		setSize(150,30);
		setFocusPainted(false);
	}
}

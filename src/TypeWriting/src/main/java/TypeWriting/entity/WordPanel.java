package TypeWriting.entity;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import TypeWriting.config.Config;

@SuppressWarnings("serial")
public class WordPanel extends JPanel {

	// 用于显示文字信息的Label
	private JLabel textLabel;

	// 文字信息生成的时间长度
	private long time;
	
	// 类型，INSERT或REMOVE
	private String type;
	
	// 文字信息
	private String text;
	
	// 文字信息在全文中的offset
	private int offset;
	
	// 文字信息的长度
	private int length;

	public WordPanel() {
		textLabel = new JLabel();
		textLabel.setFont(Config.BoardFont);
		textLabel.setOpaque(true);
		textLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		add(textLabel);
		setVisible(true);
		setLayout(null);
	}

	public WordPanel(long time, String type, String text, int offset, int length) {
		this();
		this.time = time;
		this.type = type;
		this.text = text;
		this.offset = offset;
		this.length = length;
	}

	public void init() {
		textLabel.setText(text);
		textLabel.setLocation(0, 0);
		textLabel.setSize(textLabel.getPreferredSize());
		setSize(textLabel.getPreferredSize());
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}

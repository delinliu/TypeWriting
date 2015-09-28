package TypeWriting.entity;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import TypeWriting.config.Config;

@SuppressWarnings("serial")
public class WordPanel extends JPanel {

	// 用于显示文字信息的Label
	private JLabel textLabel;

	// 用于显示输入花费的时间的Label
	private JLabel timeLabel;

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
		setVisible(true);
		setLayout(null);
		setBackground(Color.WHITE);
		if (Config.DisplayBoardWordBorder) {
			setBorder(BorderFactory
					.createCompoundBorder(
							BorderFactory
									.createTitledBorder(
											null,
											"",
											javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
											javax.swing.border.TitledBorder.DEFAULT_POSITION,
											Config.BoardBorderFont),
							BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		}
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

		textLabel = new JLabel();
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setFont(Config.BoardFont);
		textLabel.setOpaque(true);
		textLabel.setBackground(getTextColor(time));
		textLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		textLabel.setText(text);
		textLabel.setLocation(0, 0);
		int textWidth = textLabel.getPreferredSize().width;
		int textHeight = textLabel.getPreferredSize().height;
		add(textLabel);

		if ("INSERT".equals(type)) {
			timeLabel = new JLabel();
			timeLabel.setFont(new Font("幼圆", Font.PLAIN, 13));
			timeLabel.setHorizontalAlignment(JLabel.CENTER);
			timeLabel.setForeground(new Color(150, 150, 150));
			timeLabel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			timeLabel.setText(getTimeText(time));
			timeLabel.setLocation(0, textHeight);
			int timeWidth = timeLabel.getPreferredSize().width;
			int timeHeight = timeLabel.getPreferredSize().height;
			int width = Math.max(textWidth, timeWidth);
			timeLabel.setSize(width, timeHeight);
			add(timeLabel);

			textLabel.setSize(width, textHeight);
			setSize(Math.max(textWidth, timeWidth), textHeight + timeHeight);
		} else {

			textLabel.setSize(textWidth, textHeight);
			setSize(textWidth, textHeight);
		}
	}

	/**
	 * 不同的时间显示不同的时间提示
	 */
	private String getTimeText(long time) {
		if (time < 800) {
			return "0";
		} else if (time < 1000 * 60) {
			return (time + 500) / 1000 + "s";
		} else {
			return (time) / 1000 / 60 + "min";
		}
	}

	/**
	 * 不同的时间显示不同的颜色
	 */
	private Color getTextColor(long time) {
		if (time < 2500) {
			return new Color(31, 176, 69);
		} else if (time < 4500) {
			return new Color(41, 178, 242);
		} else if (time < 9500) {
			return new Color(234, 221, 84);
		} else if (time < 29500) {
			return new Color(255, 199, 36);
		} else {
			return new Color(173, 113, 194);
		}
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

package TypeWriting.config;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Config {

	public final static String FrameTitle = "打字练习";
	public final static int FrameWidth = 1400;
	public final static int FrameHeight = 900;

	public final static Color ContentBackground = Color.WHITE;
	public final static int ContentPadding = 2;
	public final static int ContentVGap = 0;
	public final static int ContentHGap = 2;
	public final static int HintHeight = 30;

	public final static Font BoardFont = new Font("宋体", Font.PLAIN, 18);
	public final static Color BoardColor = Color.WHITE;

	public final static Font BoardBorderFont = new Font("幼圆", Font.PLAIN, 13);
	public final static String BlackboardBoarderTitle = "对照框";
	public final static String InputboardBoarderTitle = "输入框";
	public final static String DisplayboardBoarderTitle = "打字速度记录框";

	public final static String HintDefaultText = "用鼠标选中对照框中的汉字能够显示拼音。";

	public final static SimpleAttributeSet FineAttrSet;
	public final static SimpleAttributeSet FailAttrSet;
	public final static SimpleAttributeSet PlainAttrSet;

	static {
		FineAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(FineAttrSet, new Color(0, 240, 0));
		FailAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(FailAttrSet, new Color(250, 0, 0));
		PlainAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(PlainAttrSet, new Color(10, 10, 10));
	}

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}
}

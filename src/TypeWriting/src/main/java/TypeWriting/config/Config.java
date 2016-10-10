package TypeWriting.config;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Config {

	private final static Properties properties;
	static {
		EncodedResource resource = null;
		Properties props = null;
		resource = new EncodedResource(new ClassPathResource("style.properties"), "UTF-8");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
		}
		properties = props;
	}

	// 主窗口的标题
	public final static String FrameTitle = "选择文章";

	// 主窗口是否全屏
	public final static boolean FrameFullScreen = true;

	// 主窗口的默认宽高
	public final static int FrameWidth = 1400;
	public final static int FrameHeight = 900;

	// 打字主面板的背景色
	public final static Color ContentBackground = Color.WHITE;

	// 打字主面板四周的空隙
	public final static int ContentPadding = 2;

	// 打字主面板里子部件y轴方向的间隔
	public final static int ContentVGap = 0;

	// 打字主面板里子部件x轴方向的间隔
	public final static int ContentHGap = 2;

	// 提示面包板的高度
	public final static int HintHeight = 30;

	// 各个Board的字体
	public final static Font BoardFont;

	// 各个Board的颜色
	public final static Color BoardColor = Color.WHITE;

	// 各个Board标题的字体
	public final static Font BoardBorderFont;

	// 各个Board的标题
	public final static String BlackboardBoarderTitle = "对照框";
	public final static String InputboardBoarderTitle = "输入框";
	public final static String DisplayboardBoarderTitle = "打字速度记录框";

	// HintBoard的默认文本
	public final static String HintDefaultText = "用鼠标选中对照框中的汉字能够显示拼音。";

	// 多个INSERT的信息连在一起的时候，如果在这个时间里面，则会绑在一起显示
	public final static long SendTimeGap = 100;

	// DisplayBoard内容的PaddingLeft、PaddingTop
	public final static int DisplayBoardPaddingLeft = 10;
	public final static int DisplayBoardPaddingTop = 10;

	// DisplayBoard每行的高度，每两个词之间的间隙
	public final static int DisplayBoardWordHeight = 50;
	public final static int DisplayBoardWordGap = 0;

	// DisplayBoard中词的边框宽度
	public final static boolean DisplayBoardWordBorder = false;

	// DisplayBoard中Remove线程的循环间隔
	public final static long DisplayBoardRemoveTime = 50;

	// DisplayBoard中Remove线程执行删除操作的阈值
	public final static long DisplayBoardRemoveCeil = 200;

	// 文章列表字体
	public final static Font ArticleListFont;

	// 菜单的字体、背景色
	public final static Font MenuFont;
	public final static Color MenuBackgroundColor = Color.WHITE;

	// 开始菜单及其子项的标题
	public final static String MenuStartTitle = "开始";
	public final static String MenuStartItem1 = "选择文章";
	public final static String MenuStartItem2 = "管理文章";
	public final static String MenuStartItem3 = "添加文章";

	// Blackboard中正确、错误、普通，三种样式
	public final static SimpleAttributeSet FineAttrSet;
	public final static SimpleAttributeSet FailAttrSet;
	public final static SimpleAttributeSet PlainAttrSet;

	public final static SimpleAttributeSet ManagerFineAttrSet;
	public final static SimpleAttributeSet ManagerFailAttrSet;
	public final static SimpleAttributeSet ManagerPlainAttrSet;

	public final static SimpleAttributeSet RecordAttrSet1;
	public final static SimpleAttributeSet RecordAttrSet2;
	public final static SimpleAttributeSet RecordAttrSet3;
	public final static SimpleAttributeSet RecordAttrSet4;

	static {
		String defaultFont = "宋体";
		int defaultSize = 20;
		if (properties != null) {
			BoardFont = new Font(properties.getProperty("BoardFont", defaultFont), Font.PLAIN,
					Integer.parseInt(properties.getProperty("BoardFontSize", String.valueOf(defaultSize))));
			BoardBorderFont = new Font(properties.getProperty("BoardTitleFont", defaultFont), Font.PLAIN,
					Integer.parseInt(properties.getProperty("BoardTitleFontSize", String.valueOf(defaultSize))));
			ArticleListFont = new Font(properties.getProperty("ArticleListFont", defaultFont), Font.PLAIN,
					Integer.parseInt(properties.getProperty("ArticleListFontSize", String.valueOf(defaultSize))));
			MenuFont = new Font(properties.getProperty("MenuFont", defaultFont), Font.PLAIN,
					Integer.parseInt(properties.getProperty("MenuFontSize", String.valueOf(defaultSize))));
		} else {
			BoardFont = new Font(defaultFont, Font.PLAIN, defaultSize);
			BoardBorderFont = new Font(defaultFont, Font.PLAIN, defaultSize);
			ArticleListFont = new Font(defaultFont, Font.PLAIN, defaultSize);
			MenuFont = new Font(defaultFont, Font.PLAIN, defaultSize);
		}
	}

	static {
		FineAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(FineAttrSet, new Color(0, 240, 0));
		FailAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(FailAttrSet, new Color(250, 0, 0));
		PlainAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(PlainAttrSet, new Color(10, 10, 10));
		ManagerFineAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(ManagerFineAttrSet, new Color(0, 130, 0));
		ManagerFailAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(ManagerFailAttrSet, new Color(150, 0, 0));
		ManagerPlainAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(ManagerPlainAttrSet, new Color(10, 10, 10));

		RecordAttrSet1 = new SimpleAttributeSet();
		StyleConstants.setForeground(RecordAttrSet1, new Color(10, 10, 10));
		RecordAttrSet2 = new SimpleAttributeSet();
		StyleConstants.setForeground(RecordAttrSet2, new Color(0xff6600));
		RecordAttrSet3 = new SimpleAttributeSet();
		StyleConstants.setForeground(RecordAttrSet3, new Color(0x337ab7));
		RecordAttrSet4 = new SimpleAttributeSet();
		StyleConstants.setForeground(RecordAttrSet4, new Color(10, 10, 10));
	}

	static {
		try {
			// for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			// {
			// if ("Nimbus".equals(info.getName())) {
			// UIManager.setLookAndFeel(info.getClassName());
			// break;
			// }
			// }
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}
}

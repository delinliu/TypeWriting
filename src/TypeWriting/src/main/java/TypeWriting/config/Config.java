package TypeWriting.config;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Config {

	// 主窗口的标题
	public final static String FrameTitle = "打字练习";
	
	// 主窗口是否全屏
	public final static boolean FrameFullScreen = false;
	
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
	public final static Font BoardFont = new Font("宋体", Font.PLAIN, 18);
	
	// 各个Board的颜色
	public final static Color BoardColor = Color.WHITE;

	// 各个Board标题的字体
	public final static Font BoardBorderFont = new Font("幼圆", Font.PLAIN, 13);
	
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
	
	// 菜单的字体、背景色
	public final static Font MenuFont = new Font("幼圆", Font.PLAIN, 14);
	public final static Color MenuBackgroundColor = Color.WHITE;
	
	// 开始菜单及其子项的标题
	public final static String MenuStartTitle = "开始";
	public final static String MenuStartItem1 = "选择文章";
	public final static String MenuStartItem2 = "管理文章";
	
	// Blackboard中正确、错误、普通，三种样式
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
//			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}
}

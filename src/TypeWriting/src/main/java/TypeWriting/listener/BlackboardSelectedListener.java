package TypeWriting.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.annotation.Resource;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Component;
import TypeWriting.TypeWriting.Blackboard;
import TypeWriting.TypeWriting.Hintboard;
import TypeWriting.config.Config;

/**
 * 在Blackboard中选中一段文字后，把其中的中文转成拼音，展示在Hintboard中。
 */
@Component("BlackboardSelectedListener")
public class BlackboardSelectedListener extends MouseAdapter {

	@Resource(name = "Blackboard")
	private Blackboard blackboard;
	@Resource(name = "Hintboard")
	private Hintboard hintboard;

	final static private HanyuPinyinOutputFormat defaultFormat;
	
	static {
		
		defaultFormat = new HanyuPinyinOutputFormat();
		
		// 生成的拼音格式为小写
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		
		// 生成的拼音格式为不需要有声调
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	/**
	 * 鼠标Release事件，表明已经选中了一段文字
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

		// 获取选中的文字
		String text = blackboard.getSelectedText();
		
		String chineseText = "";

		if (text != null && !"".equals(text)) {

			// 一个字符一个字符的获取拼音，并拼接需要展示的内容。
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (isChinese(c)) {
					try {
						@SuppressWarnings("deprecation")
						String pinyin = PinyinHelper.toHanyuPinyinString(
								"" + c, defaultFormat, "");
						chineseText += c + "(" + pinyin + ")";
					} catch (BadHanyuPinyinOutputFormatCombination e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		// 把内容展示到Hintboard上
		if ("".equals(chineseText)) {
			hintboard.setText(Config.HintDefaultText);
		} else {
			hintboard.setText(chineseText);
		}
	}

	/**
	 * 判断是否为中文文字
	 */
	private boolean isChinese(char c) {
		return Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
	}
}

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

@Component("BlackboardSelectedListener")
public class BlackboardSelectedListener extends MouseAdapter {

	@Resource(name = "Blackboard")
	private Blackboard blackboard;
	@Resource(name = "Hintboard")
	private Hintboard hintboard;

	final static private HanyuPinyinOutputFormat defaultFormat;
	static {
		defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		// get the selected text and initiate result
		String text = blackboard.getSelectedText();
		String chineseText = "";
		
		if (text != null && !"".equals(text)) {
			
			// convert selected text to pinyin one character by one
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

		// set the hint text
		if ("".equals(chineseText)) {
			hintboard.setText(Config.HintDefaultText);
		} else {
			hintboard.setText(chineseText);
		}
	}

	/**
	 * is Chinese character
	 */
	private boolean isChinese(char c) {
		return Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
	}
}

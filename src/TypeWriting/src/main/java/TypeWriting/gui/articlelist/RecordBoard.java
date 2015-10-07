package TypeWriting.gui.articlelist;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.Article;

@SuppressWarnings("serial")
@Component("RecordBoard")
public class RecordBoard extends JTextPane {

	private JScrollPane scrollPane;

	public void clear() {
		setText("");
		try {
			StyledDocument doc = getStyledDocument();
			doc.insertString(doc.getLength(), "选中文章查看历史记录。双击文章开始打字练习。",
					Config.RecordAttrSet1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void display(Article article, List<Map<String, Object>> records) {
		setText("");
		try {
			StyledDocument doc = getStyledDocument();
			doc.insertString(doc.getLength(), "《" + article.getArticleTitle()
					+ "》", Config.RecordAttrSet2);
			doc.insertString(doc.getLength(), "共有", Config.RecordAttrSet1);
			doc.insertString(doc.getLength(), records.size() + "",
					Config.RecordAttrSet2);
			doc.insertString(doc.getLength(), "次记录。\n\n", Config.RecordAttrSet1);

			for (Map<String, Object> record : records) {
				doc.insertString(doc.getLength(),
						((Date) record.get("addtime")).toLocaleString() + "，",
						Config.RecordAttrSet3);
				doc.insertString(doc.getLength(), "练习了", Config.RecordAttrSet1);
				doc.insertString(doc.getLength(),
						"" + ((long) record.get("time") / 1000 / 60),
						Config.RecordAttrSet2);
				doc.insertString(doc.getLength(), "分钟",
						Config.RecordAttrSet1);
				doc.insertString(doc.getLength(),
						"" + ((long) record.get("time") / 1000 % 60),
						Config.RecordAttrSet2);
				doc.insertString(doc.getLength(), "秒，完成率",
						Config.RecordAttrSet1);
				doc.insertString(doc.getLength(), record.get("finish") + "%",
						Config.RecordAttrSet2);
				doc.insertString(doc.getLength(), "，正确率", Config.RecordAttrSet1);
				doc.insertString(doc.getLength(), record.get("ratio") + "%",
						Config.RecordAttrSet2);
				doc.insertString(doc.getLength(), "\n", Config.RecordAttrSet1);
			}
			// doc.insertString(doc.getLength(), "文章", Config.RecordAttrSet1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {
		createScrollPane();
		initStyle();
	}

	public void initStyle() {
		setFont(Config.BoardFont);
		setEditable(false);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	private void createScrollPane() {
		scrollPane = new JScrollPane(this);
		scrollPane.setBackground(Config.BoardColor);
		createPadding();
	}

	private void createPadding() {
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(null, "",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						Config.BoardBorderFont), BorderFactory
				.createEmptyBorder(1, 1, 1, 1)));
	}
}

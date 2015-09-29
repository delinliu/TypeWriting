package TypeWriting.gui.inputing;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.Article;
import TypeWriting.listener.BlackboardSelectedListener;

/**
 * 用于展示文章。
 */
@SuppressWarnings("serial")
@Component("Blackboard")
public class Blackboard extends JTextPane {

	private JScrollPane scrollPane;

	@Resource(name = "BlackboardSelectedListener")
	BlackboardSelectedListener blackboardSelectedListener;

	public void display(Article article) {
		try {
			getDocument().insertString(0, new String(article.getArticleContent()), Config.PlainAttrSet);
			setCaretPosition(0);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		setText("");
	}

	public void init() {

		createScrollPane();
		initStyle();
		addMouseListener(blackboardSelectedListener);
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
				.createTitledBorder(null, Config.BlackboardBoarderTitle,
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						Config.BoardBorderFont), BorderFactory
				.createEmptyBorder(1, 1, 1, 1)));
	}
}

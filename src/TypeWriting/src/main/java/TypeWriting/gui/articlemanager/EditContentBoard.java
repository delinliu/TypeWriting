package TypeWriting.gui.articlemanager;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.Article;

@SuppressWarnings("serial")
@Component("EditContentBoard")
public class EditContentBoard extends JTextArea {

	private JScrollPane scrollPane;

	public void clear() {
		setText("");
		setEditable(false);
		scrollPane.setVisible(false);
	}

	public void display(Article article) {
		setText(new String(article.getArticleContent()));
		setEditable(true);
		scrollPane.setVisible(true);
	}

	public void init() {
		createScrollPane();
		initStyle();
		clear();
	}

	public void initStyle() {
		setFont(Config.BoardFont);
		setEditable(true);
		setLineWrap(true);
		setWrapStyleWord(true);
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
				.createTitledBorder(null, "文章内容",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						Config.BoardBorderFont), BorderFactory
				.createEmptyBorder(1, 1, 1, 1)));
	}
}

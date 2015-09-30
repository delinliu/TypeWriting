package TypeWriting.gui.articlemanager;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.Article;

@SuppressWarnings("serial")
@Component("EditTitleBoard")
public class EditTitleBoard extends JTextField {

	public void clear() {
		setText("");
		setEditable(false);
		setVisible(false);
	}

	public void display(Article article) {
		if (article != null) {
			setText(article.getArticleTitle());
		} else {
			setText("");
		}
		setEditable(true);
		setVisible(true);
	}

	public void init() {
		setBackground(Color.WHITE);
		setFont(Config.BoardFont);
		createPadding();
		clear();
	}

	private void createPadding() {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(null, "文章标题",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						Config.BoardBorderFont), BorderFactory
				.createEmptyBorder(1, 1, 1, 1)));
	}
}

package TypeWriting.gui.articlelist;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;

@SuppressWarnings({ "serial", "rawtypes" })
@Component("ArticleListBoard")
public class ArticleListBoard extends JList implements ListSelectionListener {

	private JScrollPane scrollPane = new JScrollPane(this);

	public void init() {
		setBackground(Color.WHITE);
		setBorder(null);
		createScrollPane();

		this.setFont(new Font("幼圆", Font.PLAIN, 15));
		this.setListData(new String[] { "1", "哈" });

		addListSelectionListener(this);

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println(getSelectedIndex());
				}
			}
		});
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
		scrollPane
				.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory
								.createTitledBorder(
										null,
										"",
										javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
										javax.swing.border.TitledBorder.DEFAULT_POSITION,
										null), BorderFactory.createEmptyBorder(
								1, 1, 1, 1)));
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		System.out.println("Change");
	}
}

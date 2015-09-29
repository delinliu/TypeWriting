package TypeWriting.gui.inputing;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.springframework.stereotype.Component;
import TypeWriting.config.Config;
import TypeWriting.listener.InputDocumentListener;

/**
 * 文字输入面板
 */
@SuppressWarnings("serial")
@Component("Inputboard")
public class Inputboard extends JTextArea {

	private JScrollPane scrollPane;

	@Resource(name = "InputDocumentListener")
	private InputDocumentListener inputDocumentListener;

	public void init() {
		createScrollPane();
		initStyle();
		getDocument().addDocumentListener(inputDocumentListener);
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
				.createTitledBorder(null, Config.InputboardBoarderTitle,
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						Config.BoardBorderFont), BorderFactory
				.createEmptyBorder(1, 1, 1, 1)));
	}
}
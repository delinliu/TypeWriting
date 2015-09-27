package TypeWriting.TypeWriting;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.listener.BlackboardSelectedListener;

@SuppressWarnings("serial")
@Component("Blackboard")
public class Blackboard extends JTextPane {

	private JScrollPane scrollPane;
	
	@Resource(name = "BlackboardSelectedListener")
	BlackboardSelectedListener blackboardSelectedListener;

	public void init() {
		
		setText("你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错你好不错。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。\r\n你好。");
		
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

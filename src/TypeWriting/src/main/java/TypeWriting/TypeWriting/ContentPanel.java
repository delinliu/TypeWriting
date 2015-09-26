package TypeWriting.TypeWriting;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.annotation.Resource;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;

@SuppressWarnings("serial")
@Component("ContentPanel")
public class ContentPanel extends JPanel implements ComponentListener {

	@Resource(name = "Blackboard")
	private Blackboard blackboard;
	@Resource(name = "Inputboard")
	private Inputboard inputboard;
	@Resource(name = "Hintboard")
	private Hintboard hintboard;

	public void init() {

		// initiate content panel
		setBackground(Config.ContentBackground);
		setLayout(null);
		setVisible(true);

		// initiate Blackboard
		blackboard.init();
		add(blackboard.getScrollPane());

		// initiate Inputboard
		inputboard.init();
		add(inputboard.getScrollPane());
		
		hintboard.init();
		add(hintboard);
		
		addComponentListener(this);
	}

	@Override
	public void componentResized(ComponentEvent e) {

		// get ContentPanel's width and height
		int width = this.getWidth();
		int height = this.getHeight();

		int padding = Config.ContentPadding;
		int gap = Config.ContentVGap;
		int hintH = Config.HintHeight;

		// set location and size of Blackboard and apply
		int boardWidth = width / 2 - padding;
		int boardHeight = (height - padding * 2 - gap * 2 - hintH) / 2;
		blackboard.getScrollPane().setLocation(padding, padding);
		blackboard.getScrollPane().setSize(boardWidth, boardHeight);
		blackboard.setSize(boardWidth, boardHeight);
		blackboard.repaint();

		// set location and size of Inputboard and apply
		inputboard.getScrollPane().setLocation(padding, padding + boardHeight + gap * 2
				+ hintH);
		inputboard.getScrollPane().setSize(boardWidth, boardHeight);
		inputboard.setSize(boardWidth, boardHeight);
		inputboard.repaint();
		
		// set location and size of Hintboard and apply 
		hintboard.setLocation(padding, padding + boardHeight + gap);
		hintboard.setSize(boardWidth, hintH);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}

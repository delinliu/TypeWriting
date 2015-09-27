package TypeWriting.listener;

import javax.annotation.Resource;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import org.springframework.stereotype.Component;

import TypeWriting.TypeWriting.Blackboard;
import TypeWriting.TypeWriting.Inputboard;
import TypeWriting.config.Config;
import TypeWriting.entity.DocumentMessage;
import TypeWriting.entity.DocumentMessageManager;

@Component("InputDocumentListener")
public class InputDocumentListener implements DocumentListener {

	@Resource(name = "Blackboard")
	private Blackboard blackboard;
	@Resource(name = "Inputboard")
	private Inputboard inputboard;
	@Resource(name = "DocumentMessageManager")
	private DocumentMessageManager documentMessageManager;

	private JTextArea copyOfInputboard = new JTextArea();
	private Document copiedDoc = copyOfInputboard.getDocument();

	@Override
	public void insertUpdate(DocumentEvent e) {
		sendMessge(e);
		match();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		sendMessge(e);
		match();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	private void sendMessge(DocumentEvent e) {
		int offset = e.getOffset();
		int length = e.getLength();
		String type = e.getType().toString();
		try {
			String text = "";
			if ("INSERT".equals(type)) {
				text = inputboard.getDocument().getText(offset, length);
				copiedDoc.insertString(offset, text, null);
			} else if ("REMOVE".equals(type)) {
				text = copiedDoc.getText(offset, length);
				copiedDoc.remove(offset, length);
			}
			documentMessageManager.receiveMessage(new DocumentMessage(type,
					offset, length, text, System.currentTimeMillis()));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	private void match() {
		try {
			String inputText = inputboard.getText();
			int iSz = inputText.length();
			int i = 0;

			StyledDocument doc = blackboard.getStyledDocument();
			doc.setCharacterAttributes(0, doc.getLength(), Config.PlainAttrSet,
					false);
			int oSz = doc.getLength();
			String originText = doc.getText(0, oSz);
			int o = 0;

			while (o < oSz && i < iSz) {
				char oC = originText.charAt(o);
				char iC = inputText.charAt(i);
				if (oC == '\r' || oC == '\n') {
					o++;
				} else if (iC == '\r' || iC == '\n') {
					i++;
				} else if (oC == iC) {
					doc.setCharacterAttributes(o, 1, Config.FineAttrSet, false);
					o++;
					i++;
				} else {
					doc.setCharacterAttributes(o, 1, Config.FailAttrSet, false);
					o++;
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

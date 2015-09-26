package TypeWriting.listener;

import javax.annotation.Resource;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

import org.springframework.stereotype.Component;

import TypeWriting.TypeWriting.Blackboard;
import TypeWriting.TypeWriting.Inputboard;
import TypeWriting.config.Config;

@Component("InputDocumentListener")
public class InputDocumentListener implements DocumentListener {

	@Resource(name = "Blackboard")
	private Blackboard blackboard;
	@Resource(name = "Inputboard")
	private Inputboard inputboard;

	@Override
	public void insertUpdate(DocumentEvent e) {
		match();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		match();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
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

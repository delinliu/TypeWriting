package TypeWriting.listener;

import javax.annotation.Resource;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.entity.DocumentMessage;
import TypeWriting.entity.DocumentMessageManager;
import TypeWriting.gui.inputing.Blackboard;
import TypeWriting.gui.inputing.Inputboard;

/**
 * 监听Inputboard的文本变化，并进行相应的处理（更新Blackboard中文字样式、发送文字信息给DocumentMessageManager）
 */
@Component("InputDocumentListener")
public class InputDocumentListener implements DocumentListener {

	@Resource(name = "Blackboard")
	private Blackboard blackboard;
	@Resource(name = "Inputboard")
	private Inputboard inputboard;
	@Resource(name = "DocumentMessageManager")
	private DocumentMessageManager documentMessageManager;

	// 一个JTextArea副本，用于模拟Inputboard。
	// 因为文字删除事件是在文字已经被删除后才触发的，所以光凭Inputboard是无法获取被删除的文本的。
	// 用一个副本则可以在获取被删除的文本后再模拟删除操作。
	private JTextArea copyOfInputboard = new JTextArea();
	private Document copiedDoc = copyOfInputboard.getDocument();

	public void clear() {
		documentMessageManager.clear();
	}

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

	/**
	 * 发送消息给DocumentMessageManager
	 */
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

	/**
	 * 把Inputboard中的文本与Blackboard中的文本进行逐字比对，在Blackboard中把匹配上的文字标记为绿色，错误的标记为红色，】。
	 */
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

			int start = 0;
			boolean fine = true;
			boolean oLine = true;
			boolean iLine = true;
			while (o < oSz && i < iSz) {
				char oC = originText.charAt(o);
				char iC = inputText.charAt(i);
				if (oC == '\r' || oC == '\n') {
					o++;
					oLine = true;
				} else if (iC == '\r' || iC == '\n') {
					i++;
					iLine = true;
				} else if (oC == ' ' && oLine) {
					o++;
				} else if (iC == ' ' && iLine) {
					i++;
				} else if (oC == iC) {
					if (!fine) {
						doc.setCharacterAttributes(start, o - start,
								Config.FailAttrSet, false);
						fine = !fine;
						start = o;
					}
					o++;
					i++;
					oLine = false;
					iLine = false;
				} else {
					if (fine) {
						doc.setCharacterAttributes(start, o - start,
								Config.FineAttrSet, false);
						fine = !fine;
						start = o;
					}
					o++;
					i++;
					oLine = false;
					iLine = false;
				}
			}
			if (fine) {
				doc.setCharacterAttributes(start, o - start,
						Config.FineAttrSet, false);
			} else {
				doc.setCharacterAttributes(start, o - start,
						Config.FailAttrSet, false);
			}
			blackboard.setCaretPosition(Math.min(blackboard.getDocument()
					.getLength(), o + 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

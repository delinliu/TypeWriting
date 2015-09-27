package TypeWriting.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import TypeWriting.TypeWriting.Displayboard;

@Component("DocumentMessageManager")
public class DocumentMessageManager {

	@Resource(name = "Displayboard")
	private Displayboard displayboard;

	private List<DocumentMessage> messageQueue = new ArrayList<DocumentMessage>();
	private int sequence = 0;
	private Object messageQueueLock = new Object();
	private MessageHandler messageHandler = new MessageHandler();

	public DocumentMessageManager() {
		messageHandler.start();
	}

	public void receiveMessage(DocumentMessage message) {
		synchronized (messageQueueLock) {
			messageQueue.add(message);
			messageQueueLock.notify();
		}
	}

	class MessageHandler extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					DocumentMessage message = null;
					boolean isOK = false;
					synchronized (messageQueueLock) {
						if (sequence < messageQueue.size()) {
							message = messageQueue.get(sequence);
							if (!" ".equals(message.getText())) {
								System.out.println(message);
								isOK = true;
							}
							sequence++;
						}

					}
					if (isOK) {
						long time = sequence > 0 ? message.getTimestamp()
								- messageQueue.get(sequence - 1).getTimestamp()
								: 0;
						displayboard.receiveWord(time, message.getType(),
								message.getText(), message.getOffset(),
								message.getLength());
					}
					synchronized (messageQueueLock) {
						if (sequence == messageQueue.size()) {
							messageQueueLock.wait();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

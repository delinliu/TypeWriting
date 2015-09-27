package TypeWriting.entity;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import TypeWriting.TypeWriting.Displayboard;

/**
 * 开设一个线程专门接收文字信息，然后经过稍微处理再把它传给Displayboard。
 */
@Component("DocumentMessageManager")
public class DocumentMessageManager {

	@Resource(name = "Displayboard")
	private Displayboard displayboard;

	// 接收文字信息的队列
	private List<DocumentMessage> messageQueue = new ArrayList<DocumentMessage>();
	
	// 处理完毕的队列中的信息的个数
	private int sequence = 0;
	
	// 操作队列的锁
	private Object messageQueueLock = new Object();
	
	// 专门处理信息的线程
	private MessageHandler messageHandler = new MessageHandler();

	public DocumentMessageManager() {
		
		// 启动线程，开始工作
		messageHandler.start();
	}

	/**
	 * 接收到一个信息，把它加到队列中，唤醒工作线程
	 */
	public void receiveMessage(DocumentMessage message) {
		synchronized (messageQueueLock) {
			messageQueue.add(message);
			messageQueueLock.notify();
		}
	}

	/**
	 * 专门处理信息的线程
	 */
	class MessageHandler extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					DocumentMessage message = null;
					long time = 0;
					boolean isOK = false;

					// 读取队列信息，自增sequence
					synchronized (messageQueueLock) {
						if (sequence < messageQueue.size()) {
							message = messageQueue.get(sequence);
							if (!" ".equals(message.getText())) {
								System.out.println(message);
								time = sequence > 0 ? message.getTimestamp()
										- messageQueue.get(sequence - 1)
												.getTimestamp() : 0;
								isOK = true;
							}
							sequence++;
						}
					}

					// 如果信息OK，那么调用显示面板，更新显示
					if (isOK) {
						displayboard.receiveWord(time, message.getType(),
								message.getText(), message.getOffset(),
								message.getLength());
					}

					// 如果读完队列了，那么开始等待
					synchronized (messageQueueLock) {
						if (sequence == messageQueue.size()) {
							messageQueueLock.wait();
						}
					}

					// PS：这里要以上代码分成3段是因为调用显示面板可能会花费很多时间，所以不能占着锁

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

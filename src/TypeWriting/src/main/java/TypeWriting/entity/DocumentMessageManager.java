package TypeWriting.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import TypeWriting.config.Config;
import TypeWriting.gui.inputing.Displayboard;

/**
 * 开设一个线程专门接收文字信息，然后经过稍微处理再把它传给Displayboard。
 */
@Component("DocumentMessageManager")
public class DocumentMessageManager {

	@Resource(name = "Displayboard")
	private Displayboard displayboard;

	// 接收文字信息的队列
	private List<DocumentMessage> messageQueue = new ArrayList<DocumentMessage>();

	// 处理完毕的队列中的信息的序号
	private int sequence = 0;

	// 处理完毕并且已经发送出去的信息的序号
	private int sendSequence = 0;

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

		/**
		 * 发送一个消息给Displayboard
		 */
		private void sendOne(int seq) {
			DocumentMessage msg = messageQueue.get(seq);
			displayboard.receiveWord(0, msg.getType(), msg.getText(),
					msg.getOffset(), msg.getLength());
		}

		/**
		 * 把下标为min~max之间的事件拼凑起来，发送给Displayboard。
		 * 注意：这些事件一定是INSERT事件，否则是代码逻辑错误，强制退出程序。
		 */
		private void sendRange(int min, int max) {

			if (min > max) {
				return;
			}

			// type一定是INSERT
			String type = "INSERT";

			// offset以min为准
			int offset = messageQueue.get(min).getOffset();

			// 计算time，最后的时间戳为max，最早的时间戳是在min之前
			long time = 0;
			long end = messageQueue.get(max).getTimestamp();
			long start = end;
			for (int i = min - 1; i >= 0;) {
				DocumentMessage pre = messageQueue.get(i);

				// 如果pre为REMOVE、prepre为INSERT，而且它们删除和新增的内容一模一样，那么就忽略它们
				if ("REMOVE".equals(pre.getType()) && i > 0) {
					DocumentMessage prepre = messageQueue.get(i - 1);
					if ("INSERT".equals(prepre.getType())
							&& pre.getOffset() == prepre.getOffset()
							&& pre.getLength() == prepre.getLength()) {
						start = prepre.getTimestamp();
						i -= 2;
						continue;
					}
				}
				start = pre.getTimestamp();
				break;
			}
			time = end - start;

			// 拼凑text
			String text = "";
			while (min <= max) {
				DocumentMessage msg = messageQueue.get(min);

				if (!type.equals(msg.getType())) { // 这是不可能的，否则逻辑错误
					System.out.println("ERROR!");
					System.exit(-1);
				}
				text += msg.getText();
				min++;
			}

			// 发送消息
			displayboard.receiveWord(time, type, text, offset, text.length());
		}

		@Override
		public void run() {
			while (true) {
				try {
					DocumentMessage message = null;
					boolean isOK = false;
					long timeGap = Config.SendTimeGap;
					int mode = 0;

					// 循环直到OK为止，OK意味着需要发送信息给Displayboard了
					while (!isOK) {

						// 记录着是否需要sleep来等一下
						// 因为如果队列的最后一个信息是INSERT，那么等timeGap时间后，如果还没有新的信息过来，那么就需要把队列中现有的信息发送给Displayboard
						boolean isNeedSleep = false;

						synchronized (messageQueueLock) {
							if (sequence < messageQueue.size()) {
								message = messageQueue.get(sequence);

								// 如果是删除操作，那么OK，可以发送信息了
								if ("REMOVE".equals(message.getType())) {
									isOK = true;
									mode = 1;

									// 如果是插入操作，而且是换行，那么之后操作与REMOVE相同
								} else if ("INSERT".equals(message.getType())
										&& ("\r".equals(message.getText())
												|| "\r\n".equals(message
														.getText()) || "\n"
													.equals(message.getText()))) {
									isOK = true;
									mode = 1;

									// 如果是插入操作，而且在此之前还有信息未发送
								} else if ("INSERT".equals(message.getType())
										&& sequence > 0
										&& sequence > sendSequence) {

									DocumentMessage previous = messageQueue
											.get(sequence - 1);

									// 前一个信息如果也是插入操作在文本中是前一个或者
									if ("INSERT".equals(previous.getType()) // 前一个也是INSERT（逻辑上这是一定的）
											&& (message.getTimestamp()
													- previous.getTimestamp() > timeGap // 这一个与前一个时间间隔超过了阈值（这种情况是有可能发生的，没拿锁的时候这个线程阻塞了，然后队列中就会有许多信息）
											|| previous.getOffset()
													+ previous.getLength() != message // 这一个与前一个在文本位置中不是前后关系
													.getOffset())) {
										isOK = true;
										mode = 2;
									}
								}

								sequence++;
							}

							// 如果不OK（那么一定不是REMOVE），而且队列中没信息了，那么需要sleep一小段时间来等待后来的信息
							if (!isOK && sequence == messageQueue.size()) {
								isNeedSleep = true;
							}
						}

						// 等待后来的信息
						if (isNeedSleep) {
							Thread.sleep(timeGap);
							synchronized (messageQueueLock) {
								if (sequence == messageQueue.size()) { // 如果等了一段时间之后，还没有新的信息过来，那么可以发送信息了
									isOK = true;
									mode = 3;
								}
							}
						}
					}

					switch (mode) {
					case 1: // 最后处理的信息是REMOVE，那么需要把这个信息之前所有的信息组合起来发送，然后再发送这个信息
						sendRange(sendSequence, sequence - 2);
						sendOne(sequence - 1);
						sendSequence = sequence;
						break;
					case 2: // 最后处理的信息是INSERT，那么需要把这个信息之前所有的信息组合起来发送，但是不发送这个信息
						sendRange(sendSequence, sequence - 2);
						sendSequence = sequence - 1;
						break;
					case 3: // 最后处理的信息是INSERT，而且队列中没有其他未处理的信息了，而且等了一段时间也没有新信息过来，那么把所有未发送的信息组合起来发送
						sendRange(sendSequence, sequence - 1);
						sendSequence = sequence;
						break;
					}

					// 如果读完队列了（而且不是mode=2，因为如果是mode=2，那么如果没有新的信息过来，多一段时间最后的那个INSERT信息也需要发送出去的），那么开始等待
					synchronized (messageQueueLock) {
						if (mode != 2 && sequence == messageQueue.size()) {
							messageQueueLock.wait();
						}
					}

					// PS：这里要以上代码分成几段拿锁是因为Sleep、调用显示面板可能会花费很多时间，所以不能占着锁

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

package TypeWriting.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import TypeWriting.service.RecordService;

@Component("RecordUpdateManager")
public class RecordUpdateManager {

	@Resource(name = "RecordServiceImpl")
	private RecordService recordService;

	private List<Map<String, Object>> recordQueue = new ArrayList<Map<String, Object>>();

	private int sequence = 0;

	private Object recordQueueLock = new Object();

	private RecordUpdateThread recordUpdateThread = new RecordUpdateThread();

	public RecordUpdateManager() {
		recordUpdateThread.start();
	}

	public void addRecord(int recordId, int time, int finish, int ratio) {
		Map<String, Object> record = new HashMap<String, Object>();
		record.put("recordId", recordId);
		record.put("time", time);
		record.put("finish", finish);
		record.put("ratio", ratio);
		synchronized (recordQueueLock) {
			recordQueue.add(record);
			recordQueueLock.notify();
		}
	}

	class RecordUpdateThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					synchronized (recordQueueLock) {
						while (sequence < recordQueue.size()) {
							Map<String, Object> record = recordQueue
									.get(sequence);
							sequence++;
							recordService.updateRecord(
									(int) record.get("recordId"),
									(int) record.get("time"),
									(int) record.get("finish"),
									(int) record.get("ratio"));
						}
						recordQueueLock.wait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

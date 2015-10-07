package TypeWriting.service;

import java.util.List;
import java.util.Map;

import TypeWriting.entity._Exception;

public interface RecordService {

	public int addRecord(long articleId) throws _Exception;

	public void updateRecord(long recordId, int time, int finish, int ratio)
			throws _Exception;

	public List<Map<String, Object>> findRecords(long articleId)
			throws _Exception;
}

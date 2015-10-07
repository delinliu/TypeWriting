package TypeWriting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import TypeWriting.entity._Exception;
import TypeWriting.mapper.RecordMapper;
import TypeWriting.service.RecordService;

@Service("RecordServiceImpl")
public class ReordServiceImpl implements RecordService {

	@Resource(name = "recordMapper")
	private RecordMapper recordMapper;

	@Override
	public int addRecord(long articleId) throws _Exception {

		if (articleId <= 0) {
			throw new _Exception("新增记录失败。");
		}

		Map<String, Object> record = new HashMap<String, Object>();
		record.put("articleId", articleId);

		Integer row = recordMapper.addRecord(record);
		if (row == null || row != 1 || record.get("recordId") == null) {
			throw new _Exception("新增记录失败。");
		}

		return ((Long) record.get("recordId")).intValue();
	}

	@Override
	public void updateRecord(long recordId, int time, int finish, int ratio)
			throws _Exception {
		Map<String, Object> record = new HashMap<String, Object>();
		record.put("recordId", recordId);
		record.put("time", time);
		record.put("finish", finish);
		record.put("ratio", ratio);

		Integer row = recordMapper.updateRecord(record);
		if (row == null || row != 1) {
			throw new _Exception("更新记录失败");
		}

	}

	@Override
	public List<Map<String, Object>> findRecords(long articleId)
			throws _Exception {
		Map<String, Object> article = new HashMap<String, Object>();
		article.put("articleId", articleId);

		return recordMapper.findRecord(article);
	}

}

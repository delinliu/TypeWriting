package TypeWriting.mapper;

import java.util.List;
import java.util.Map;

public interface RecordMapper {

	public Integer addRecord(Map<String, Object> record);

	public Integer updateRecord(Map<String, Object> record);

	public List<Map<String, Object>> findRecord(Map<String, Object> article);
}

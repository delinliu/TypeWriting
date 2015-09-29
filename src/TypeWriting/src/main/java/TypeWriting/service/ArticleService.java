package TypeWriting.service;

import java.util.List;
import java.util.Map;

public interface ArticleService {

	public List<Map<String, Object>> findArticleList() throws Exception;
}

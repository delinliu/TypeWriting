package TypeWriting.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import TypeWriting.mapper.ArticleMapper;
import TypeWriting.service.ArticleService;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements ArticleService {

	@Resource(name = "articleMapper")
	private ArticleMapper articleMapper;
	
	@Override
	public List<Map<String, Object>> findArticleList() throws Exception {
		return articleMapper.findArticleList();
	}

}

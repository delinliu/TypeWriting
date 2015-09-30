package TypeWriting.mapper;

import java.util.List;
import java.util.Map;

import TypeWriting.entity.Article;

public interface ArticleMapper {

	public List<Map<String, Object>> findArticleList();

	public Integer updateArticle(Article article);

	public Integer deleteArticle(Article article);

	public Integer addArticle(Article article);

}

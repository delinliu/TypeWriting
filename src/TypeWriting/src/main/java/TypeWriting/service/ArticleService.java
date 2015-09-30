package TypeWriting.service;

import java.util.List;
import java.util.Map;

import TypeWriting.entity.Article;
import TypeWriting.entity._Exception;

public interface ArticleService {

	public List<Map<String, Object>> findArticleList() throws _Exception;

	public void updateArticle(Article article) throws _Exception;

	public void deleteArticle(Article article) throws _Exception;

	public void addArticle(Article article) throws _Exception;
}

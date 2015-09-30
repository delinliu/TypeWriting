package TypeWriting.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import TypeWriting.entity.Article;
import TypeWriting.entity._Exception;
import TypeWriting.mapper.ArticleMapper;
import TypeWriting.service.ArticleService;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements ArticleService {

	@Resource(name = "articleMapper")
	private ArticleMapper articleMapper;

	@Override
	public List<Map<String, Object>> findArticleList() throws _Exception {
		List<Map<String, Object>> articles = articleMapper.findArticleList();
		for (Map<String, Object> article : articles) {
			try {
				article.put("articleContentString",
						new String((byte[]) article.get("articleContent"),
								"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return articles;
	}

	int TITLE_MAX_LENGTH = 100;
	int CONTENT_MAX_LENGTH = 10000;

	private void checkTitle(Article article) throws _Exception {
		if (article.getArticleTitle() == null
				|| article.getArticleTitle().length() < 1) {
			throw new _Exception("请输入文章标题！");
		} else if (article.getArticleTitle().length() > TITLE_MAX_LENGTH) {
			throw new _Exception("文章标题最多为" + TITLE_MAX_LENGTH + "个字！");
		}
	}

	private void checkContent(Article article) throws _Exception {
		if (article.getArticleContentString() == null
				|| article.getArticleContentString().length() < 1) {
			throw new _Exception("请输入文章内容！");
		} else if (article.getArticleContentString().length() > CONTENT_MAX_LENGTH) {
			throw new _Exception("文章内容最多为" + CONTENT_MAX_LENGTH + "个字！");
		}
	}

	@Override
	public void updateArticle(Article article) throws _Exception {
		if (article == null || article.getArticleId() < 1) {
			throw new _Exception("保存文章失败！");
		}

		checkTitle(article);

		checkContent(article);
		
		try {
			article.setArticleContent(article.getArticleContentString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Integer row = articleMapper.updateArticle(article);

		if (row == null || row != 1) {
			throw new _Exception("保存文章失败！");
		}
	}

	@Override
	public void deleteArticle(Article article) throws _Exception {
		if (article == null || article.getArticleId() < 1) {
			throw new _Exception("删除文章失败！");
		}

		Integer row = articleMapper.deleteArticle(article);

		if (row == null || row != 1) {
			throw new _Exception("删除文章失败！");
		}
	}

	@Override
	public void addArticle(Article article) throws _Exception {

		checkTitle(article);

		checkContent(article);
		
		try {
			article.setArticleContent(article.getArticleContentString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Integer row = articleMapper.addArticle(article);

		if (row == null || row != 1) {
			throw new _Exception("新增文章失败！");
		}
	}
}

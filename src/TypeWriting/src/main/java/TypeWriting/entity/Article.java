package TypeWriting.entity;

import java.util.Date;

public class Article {

	private long articleId;
	private String articleTitle;
	private byte[] articleContent;
	private Date addtime;
	private Date updatetime;
	private String status;

	private int lessonSequence;

	public int getLessonSequence() {
		return lessonSequence;
	}

	public void setLessonSequence(int lessonSequence) {
		this.lessonSequence = lessonSequence;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public byte[] getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(byte[] articleContent) {
		this.articleContent = articleContent;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "第" + lessonSequence + "课：" + articleTitle;
	}
}

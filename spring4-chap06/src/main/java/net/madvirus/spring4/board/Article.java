package net.madvirus.spring4.board;

import java.util.Date;

public class Article {
	
	
	private Integer id;
	private String writerName;
	private String title;
	private String content;
	private Date creationTime;
	
	public Article(String writerName, String title, String content) {
		this.writerName = writerName;
		this.title = title;
		this.content = content;
		this.creationTime = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWriterName() {
		return writerName;
	}
	
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	
	
	
}

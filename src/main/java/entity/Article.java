package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Article {

	@Column(name = "article_id")
	private String id;
	
	@Column(name = "title")
	private String title;

	public Article() {
		// TODO Auto-generated constructor stub
	}

	public Article(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

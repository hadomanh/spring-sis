package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lecturer")
public class Lecturer {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "username")
	private String username;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {
			CascadeType.DETACH, 
			CascadeType.MERGE, 
			CascadeType.PERSIST,
			CascadeType.REFRESH
			})
	@JoinColumn(name = "school_id")
	private School school;
	
	@ManyToMany(mappedBy = "lecturers")
	private List<Subject> subjects;

	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "lecturer_article",
			joinColumns = @JoinColumn(name = "lecturer_id")
	)
	private List<Article> articles;

	public Lecturer(String id, String firstName, String lastName, String username) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}

	public Lecturer(String id, String firstName, String lastName, String username, List<Article> articles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.articles = articles;
	}
	

}

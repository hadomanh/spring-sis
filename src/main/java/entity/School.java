package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "school")
public class School {
	
	@Id
	@Column
	private String id;
	
	@Column
	private String name;
	
	@Embedded
	private Address address;
	
	@Embeddable
	@Data
	@NoArgsConstructor
	public static class Address {
		@Column
		private String building;
		
		@Column
		private String room;
	}
	
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, 
			fetch = FetchType.EAGER,
			mappedBy = "school")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Student> students;
	
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,
			mappedBy = "school")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Lecturer> lecturers;
	
}

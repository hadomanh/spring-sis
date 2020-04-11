package dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {
	
	private String id, firstName, lastName, username;
	
	private School school;
	
	private List<Subject> subjects;

	@Data
	@NoArgsConstructor
	public static class School {
		private String id, name;
		
	}
	
	@Data
	@NoArgsConstructor
	public static class Subject {
		private String id, name;
	}

}

package dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LecturerDTO {
	
	private String id, firstName, lastName, username;
	
	private School school;
	
	private List<Subject> subjects;
	
	private List<String> articles;

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

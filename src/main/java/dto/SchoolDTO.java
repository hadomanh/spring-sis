package dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchoolDTO {
	
	private String id;
	
	private String name;
	
	private List<Student> students;
	
	@Data
	@NoArgsConstructor
	public static class Student {
		
		private String id;
		
		private String firstName;
		
		private String lastName;
		
		private String username;

		
	}


}

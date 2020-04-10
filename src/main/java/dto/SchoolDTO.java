package dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchoolDTO {
	
	private String id;
	
	private String name;
	
	private String addressBuilding;
	
	private String addressRoom;
	
	private List<Student> students;
	
	private List<Lecturer> lecturers;
	
	@Data
	@NoArgsConstructor
	public static class Student {
		
		private String id;
		
		private String firstName;
		
		private String lastName;
		
		private String username;

		
	}
	
	@Data
	@NoArgsConstructor
	public static class Lecturer {
		
		private String id;
		
		private String firstName;
		
		private String lastName;
		
		private String username;

		
	}


}

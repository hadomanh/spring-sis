package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {
	
	private String id, firstName, lastName, username;
	
	private School school;

	@Data
	@NoArgsConstructor
	public static class School {
		private String id, name;
		
	}

}

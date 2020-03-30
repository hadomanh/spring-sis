package rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.StudentDTO;
import entity.Student;
import exception.NotFoundException;
import exception.WrongSyntaxException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/student")
public class StudentRestController {

	@Autowired
	private MainService<Student> studentService;
	
	@Autowired
	private AdapterService<Student, StudentDTO> adapterService;

	@GetMapping("/{id}")
	public StudentDTO getStudent(@PathVariable String id) {
		
		if(!id.matches("^[0-9]*$")) {
			throw new WrongSyntaxException("Student ID contains alphabetic character - " + id);
		}

		Student result = studentService.get(Student.class, id);

		if (result == null) {
			throw new NotFoundException("Student ID not found - " + id);
		}

		return adapterService.getJSON(result);
	}

	@GetMapping("/")
	public List<StudentDTO> getAll() {
		
		List<Student> results = studentService.getAll(Student.class);
		
		return adapterService.getJSON(results);
	}

	@PostMapping("/")
	public Student addStudent(@RequestBody Student newStudent) {

		studentService.save(newStudent);

		return newStudent;
	}

	@PutMapping("/")
	public Student updateStudent(@RequestBody Student newStudent) {
		
		if(!newStudent.getId().matches("^[0-9]*$")) {
			throw new WrongSyntaxException("Student ID contains alphabetic character - " + newStudent.getId());
		}

		Student result = studentService.get(Student.class, newStudent.getId());

		if (result == null) {
			throw new NotFoundException("Student ID not found - " + newStudent.getId());
		}

		studentService.save(newStudent);

		return newStudent;
	}

	@DeleteMapping("/{id}")
	public String deleteStudent(@PathVariable String id) {
		
		if(!id.matches("^[0-9]*$")) {
			throw new WrongSyntaxException("Student ID contains alphabetic character - " + id);
		}

		Student result = studentService.get(Student.class, id);

		if (result == null) {
			throw new NotFoundException("Student ID not found - " + id);
		}

		studentService.delete(Student.class, id);

		return "Deleted student ID - " + id;
	}

}

package rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.transaction.Transactional;

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
import entity.School;
import entity.Student;
import exception.NotFoundException;
import exception.WrongSyntaxException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/student")
@Transactional
public class StudentRestController {

	@Autowired
	private MainService<Student> studentService;

	@Autowired
	private MainService<School> schoolService;

	@Autowired
	private AdapterService<Student, StudentDTO> adapterService;

	@GetMapping("/{id}")
	public StudentDTO get(@PathVariable String id) {

		if (!id.matches("^[0-9]*$")) {
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

	@PostMapping("/{schoolId}")
	public StudentDTO add(@RequestBody Student newStudent, @PathVariable String schoolId) {

		School school = schoolService.get(School.class, schoolId);

		if (school == null)
			throw new NotFoundException("School ID not found - " + schoolId);

		newStudent.setSchool(school);

		studentService.save(newStudent);

		return adapterService.getJSON(newStudent);
	}

	@PutMapping("/{id}/{schoolId}")
	public StudentDTO update(@RequestBody Student newStudent, 
								@PathVariable String id, 
								@PathVariable String schoolId) {

		Student toUpdate = studentService.get(Student.class, id);

		if (toUpdate == null) {
			throw new NotFoundException("Student ID not found - " + id);
		}

		School school = schoolService.get(School.class, schoolId);

		if (school == null) {
			throw new NotFoundException("School ID not found - " + schoolId);
		}

		for (Field field : newStudent.getClass().getDeclaredFields()) {
			
			try {
				
				field.setAccessible(true);
				Object newValue = field.get(newStudent);
				
				if (newValue != null) {
					field.set(toUpdate, newValue);
				}
			
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		toUpdate.setId(id);

		toUpdate.setSchool(school);

		studentService.save(toUpdate);

		return adapterService.getJSON(toUpdate);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {

		if (!id.matches("^[0-9]*$")) {
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

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
import entity.Subject;
import exception.NotFoundException;
import exception.WrongSyntaxException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/student")
@Transactional
public class StudentRestController {

	@Autowired
	private MainService<Student> mainService;
	
	@Autowired
	private MainService<School> schoolService;

	@Autowired
	private MainService<Subject> subjectService;

	@Autowired
	private AdapterService<Student, StudentDTO> adapterService;

	@GetMapping("/{id}")
	public StudentDTO get(@PathVariable String id) {

		if (!id.matches("^[0-9]*$")) {
			throw new WrongSyntaxException("Student ID contains alphabetic character - " + id);
		}

		Student result = mainService.get(Student.class, id);

		if (result == null) {
			throw new NotFoundException("Student ID not found - " + id);
		}

		return adapterService.getJSON(result);
	}

	@GetMapping("/")
	public List<StudentDTO> getAll() {

		List<Student> results = mainService.getAll(Student.class);

		return adapterService.getJSON(results);
	}

	@PostMapping("/{schoolId}")
	public StudentDTO add(@RequestBody Student newStudent, @PathVariable String schoolId) {

		School school = schoolService.get(School.class, schoolId);

		if (school == null)
			throw new NotFoundException("School ID not found - " + schoolId);

		newStudent.setSchool(school);

		mainService.save(newStudent);

		return adapterService.getJSON(newStudent);
	}

	@PutMapping("/{id}")
	public StudentDTO update(@RequestBody Student newStudent, 
								@PathVariable String id) {

		Student toUpdate = mainService.get(Student.class, id);

		if (toUpdate == null) {
			throw new NotFoundException("Student ID not found - " + id);
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

		mainService.save(toUpdate);

		return adapterService.getJSON(toUpdate);
	}

	@PutMapping("/{id}/{subjectId}")
	public StudentDTO update(@PathVariable String id, 
								@PathVariable String subjectId) {

		Student toUpdate = mainService.get(Student.class, id);

		if (toUpdate == null) {
			throw new NotFoundException("Student ID not found - " + id);
		}
		
		Subject newSubject = subjectService.get(Subject.class, subjectId);
		
		if (newSubject == null) {
			throw new NotFoundException("Subject ID not found - " + subjectId);
		}

		toUpdate.addSubject(newSubject);

		mainService.save(toUpdate);

		return adapterService.getJSON(toUpdate);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {

		if (!id.matches("^[0-9]*$")) {
			throw new WrongSyntaxException("Student ID contains alphabetic character - " + id);
		}

		Student toRemove = mainService.get(Student.class, id);

		if (toRemove == null) {
			throw new NotFoundException("Student ID not found - " + id);
		}

		mainService.delete(Student.class, id);

		return "Deleted student ID - " + id;
	}
	
	@DeleteMapping("/{id}/{subjectId}")
	public StudentDTO delete(@PathVariable String id, 
								@PathVariable String subjectId) {
		
		Student toUpdate = mainService.get(Student.class, id);
		
		if (toUpdate == null) {
			throw new NotFoundException("Student ID not found - " + id);
		}
		
		Subject toRemove = subjectService.get(Subject.class, subjectId);
		
		if (toRemove == null) {
			throw new NotFoundException("Subject ID not found - " + subjectId);
		}
		
		toUpdate.removeSubject(toRemove);
		
		mainService.save(toUpdate);
		
		return adapterService.getJSON(toUpdate);
	}

}

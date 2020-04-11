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

import dto.LecturerDTO;
import entity.Lecturer;
import entity.School;
import entity.Subject;
import exception.NotFoundException;
import exception.WrongSyntaxException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/lecturer")
@Transactional
public class LecturerRestController {

	@Autowired
	private MainService<Lecturer> mainService;

	@Autowired
	private MainService<School> schoolService;

	@Autowired
	private MainService<Subject> subjectService;

	@Autowired
	private AdapterService<Lecturer, LecturerDTO> adapterService;

	@GetMapping("/{id}")
	public LecturerDTO get(@PathVariable String id) {

		if (!id.matches("^[0-9]*$")) {
			throw new WrongSyntaxException("Student ID contains alphabetic character - " + id);
		}

		Lecturer result = mainService.get(Lecturer.class, id);

		if (result == null) {
			throw new NotFoundException("Lecturer ID not found - " + id);
		}

		return adapterService.getJSON(result);
	}

	@GetMapping("/")
	public List<LecturerDTO> getAll() {
		return adapterService.getJSON(mainService.getAll(Lecturer.class));
	}

	@PostMapping("/{schoolId}")
	public LecturerDTO add(@RequestBody Lecturer lecturer,
							@PathVariable String schoolId) {
		School school = schoolService.get(School.class, schoolId);

		if (school == null)
			throw new NotFoundException("School ID not found - " + schoolId);

		lecturer.setSchool(school);
		mainService.save(lecturer);
		return adapterService.getJSON(lecturer);
	}

	@PutMapping("/{id}")
	public LecturerDTO update(@RequestBody Lecturer newLecturer, @PathVariable String id) {
		Lecturer toUpdate = mainService.get(Lecturer.class, id);

		if (toUpdate == null) {
			throw new NotFoundException("Lecturer ID not found - " + id);
		}

		for (Field field : newLecturer.getClass().getDeclaredFields()) {

			field.setAccessible(true);

			try {
				Object newValue = field.get(newLecturer);
				if (newValue != null) {
					field.set(toUpdate, newValue);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		mainService.save(toUpdate);

		return adapterService.getJSON(toUpdate);
	}

	@PutMapping("/{id}/{subjectId}")
	public LecturerDTO update(@RequestBody Lecturer newLecturer, @PathVariable String id,
			@PathVariable String subjectId) {
		Lecturer toUpdate = mainService.get(Lecturer.class, id);

		if (toUpdate == null) {
			throw new NotFoundException("Lecturer ID not found - " + id);
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
		Lecturer lecturer = mainService.get(Lecturer.class, id);

		if (lecturer == null)
			throw new NotFoundException("Lecturer ID not found - " + id);

		mainService.delete(Lecturer.class, id);

		return "Deleted lecturer ID " + id;
	}

	@DeleteMapping("/{id}/{subjectId}")
	public LecturerDTO delete(@PathVariable String id, @PathVariable String subjectId) {
		Lecturer toUpdate = mainService.get(Lecturer.class, id);

		if (toUpdate == null) {
			throw new NotFoundException("Lecturer ID not found - " + id);
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

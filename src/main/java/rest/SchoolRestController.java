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

import dto.SchoolDTO;
import entity.Lecturer;
import entity.School;
import entity.Student;
import exception.EntityExistsException;
import exception.NotFoundException;
import exception.WrongSyntaxException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/school")
@Transactional
public class SchoolRestController {

	@Autowired
	private MainService<School> mainService;
	
	@Autowired
	private MainService<Student> studentService;
	
	@Autowired
	private MainService<Lecturer> lecturerService;
	
	@Autowired
	private AdapterService<School, SchoolDTO> adapterService;
	
	@GetMapping("/")
	public List<SchoolDTO> getAll() {
		return adapterService.getJSON(mainService.getAll(School.class));
	}
	
	@GetMapping("/{id}")
	public SchoolDTO get(@PathVariable String id) {
		
		School result = mainService.get(School.class, id);
		
		if (result == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		return adapterService.getJSON(result);
	}

	@PostMapping("/")
	public SchoolDTO add(@RequestBody School toAdd) {
		
		School toCheck = mainService.get(School.class, toAdd.getId());
		
		if (toCheck != null) {
			throw new EntityExistsException("School ID exists - " + toAdd.getId());
		}
		
		mainService.save(toAdd);
		
		return adapterService.getJSON(toAdd);
	}

	@PutMapping("/{id}")
	public SchoolDTO update(@RequestBody School newSchool,
							@PathVariable String id) {
		
		School toUpdate = mainService.get(School.class, id);
		
		if (toUpdate == null) {
			throw new NotFoundException("School ID not found - " + newSchool.getId());
		}
		
		for (Field field : newSchool.getClass().getDeclaredFields()) {
			
			field.setAccessible(true);
			
			try {
				Object newValue = field.get(newSchool);
				
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
	
	@PutMapping("/{id}/{entity}/{entityId}")
	public SchoolDTO update(@PathVariable String id,
							@PathVariable String entity,
							@PathVariable String entityId) {
		
		School toUpdate = mainService.get(School.class, id);
		
		if (toUpdate == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		if ("student".equals(entity.toLowerCase())) {
			Student toAddStudent = studentService.get(Student.class, entityId);
			
			if (toAddStudent == null) {
				throw new NotFoundException("Student ID not found - " + entityId);
			}
			
			toAddStudent.setSchool(toUpdate);
			toUpdate.addStudent(toAddStudent);
			
		} else if ("lecturer".equals(entity.toLowerCase())) {
			Lecturer toAddLecturer = lecturerService.get(Lecturer.class, entityId);
			
			if (toAddLecturer == null) {
				throw new NotFoundException("Lecturer ID not found - " + entityId);
			}
			
			toAddLecturer.setSchool(toUpdate);
			toUpdate.addLecturer(toAddLecturer);
			
		} else throw new WrongSyntaxException("Only lecturer or student entity accepted!");
		
		mainService.save(toUpdate);
		
		return adapterService.getJSON(toUpdate);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		
		School result = mainService.get(School.class, id);
		
		if (result == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		mainService.delete(School.class, id);
		
		return "Deleted school ID - " + id;
	}
	
	@DeleteMapping("/{id}/{entity}/{entityId}")
	public SchoolDTO delete(@PathVariable String id,
							@PathVariable String entity,
							@PathVariable String entityId) {
		
		School toUpdate = mainService.get(School.class, id);
		
		if (toUpdate == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		if ("student".equals(entity.toLowerCase())) {
			Student toRemoveStudent = studentService.get(Student.class, entityId);
			
			if (toRemoveStudent == null) {
				throw new NotFoundException("Student ID not found - " + entityId);
			}
			
			toRemoveStudent.setSchool(null);
			toUpdate.removeStudent(toRemoveStudent);
			
		} else if ("lecturer".equals(entity.toLowerCase())) {
			Lecturer toRemoveLecturer = lecturerService.get(Lecturer.class, entityId);
			
			if (toRemoveLecturer == null) {
				throw new NotFoundException("Lecturer ID not found - " + entityId);
			}
			
			toRemoveLecturer.setSchool(null);
			toUpdate.removeLecturer(toRemoveLecturer);
			
		} else throw new WrongSyntaxException("Only lecturer or student entity accepted!");
		
		mainService.save(toUpdate);
		
		return adapterService.getJSON(toUpdate);
	}

}

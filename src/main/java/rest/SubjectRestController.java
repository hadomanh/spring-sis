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

import dto.SubjectDTO;
import entity.Subject;
import exception.EntityExistsException;
import exception.NotFoundException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/subject")
@Transactional
public class SubjectRestController {
	
	@Autowired
	private MainService<Subject> mainService;
	
	@Autowired
	private AdapterService<Subject, SubjectDTO> adapterService;
	
	@GetMapping("/{id}")
	public SubjectDTO get(@PathVariable String id) {
		Subject subject = mainService.get(Subject.class, id);
		if (subject == null) {
			throw new NotFoundException("Subject ID not found - " + id);
		}
		
		return adapterService.getJSON(subject);
	}
	
	@GetMapping("/")
	public List<SubjectDTO> getAll() {
		return adapterService.getJSON(mainService.getAll(Subject.class));
	}
	
	@PostMapping("/")
	public SubjectDTO add(@RequestBody Subject toAdd) {
		
		Subject toCheck = mainService.get(Subject.class, toAdd.getId());
		
		if (toCheck != null) {
			throw new EntityExistsException("Subject ID exists - " + toAdd.getId());
		}
		
		mainService.save(toAdd);
		
		return adapterService.getJSON(toAdd);
	}
	
	@PutMapping("/{id}")
	public SubjectDTO update(@PathVariable String id, 
								@RequestBody Subject newSubject) {
		
		Subject toUpdate = mainService.get(Subject.class, id);
		
		if (toUpdate == null) {
			throw new NotFoundException("Subject ID not found - " + id);
		}
		
		for (Field field : newSubject.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object newValue = field.get(newSubject);
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
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		
		Subject toDelete = mainService.get(Subject.class, id);
		
		if (toDelete == null) {
			throw new NotFoundException("Subject ID not found - " + id);
		}
		mainService.delete(Subject.class, id);
		
		return "Deleted subject ID - " + id;
	}

}

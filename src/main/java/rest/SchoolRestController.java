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
import entity.School;
import exception.NotFoundException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/school")
@Transactional
public class SchoolRestController {

	@Autowired
	private MainService<School> schoolService;
	
	@Autowired
	private AdapterService<School, SchoolDTO> adapterService;
	
	@GetMapping("/")
	public List<SchoolDTO> getAll() {
		return adapterService.getJSON(schoolService.getAll(School.class));
	}
	
	@GetMapping("/{id}")
	public SchoolDTO get(@PathVariable String id) {
		
		School result = schoolService.get(School.class, id);
		
		if (result == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		return adapterService.getJSON(result);
	}

	@PostMapping("/")
	public School add(@RequestBody School newSchool) {
		
		schoolService.save(newSchool);
		
		return newSchool;
	}

	@PutMapping("/{id}")
	public SchoolDTO update(@RequestBody School newSchool,
							@PathVariable String id) {
		
		School toUpdate = schoolService.get(School.class, id);
		
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
		
		schoolService.save(toUpdate);
		
		return adapterService.getJSON(toUpdate);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		
		School result = schoolService.get(School.class, id);
		
		if (result == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		schoolService.delete(School.class, id);
		
		return "Deleted school ID - " + id;
	}
	
	

}

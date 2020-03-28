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

import dto.SchoolDTO;
import entity.School;
import exception.NotFoundException;
import service.AdapterService;
import service.MainService;

@RestController
@RequestMapping("/school")
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
	public SchoolDTO getSchool(@PathVariable String id) {
		
		School result = schoolService.get(School.class, id);
		
		if (result == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		return adapterService.getJSON(result);
	}

	@PostMapping("/")
	public School addSchool(@RequestBody School newSchool) {
		
		schoolService.save(newSchool);
		
		return newSchool;
	}

	@PutMapping("/")
	public School updateSchool(@RequestBody School newSchool) {
		
		School result = schoolService.get(School.class, newSchool.getId());
		
		if (result == null) {
			throw new NotFoundException("School ID not found - " + newSchool.getId());
		}
		
		schoolService.save(newSchool);
		
		return newSchool;
	}
	
	@DeleteMapping("/{id}")
	public String deleteSchool(@PathVariable String id) {
		
		School result = schoolService.get(School.class, id);
		
		if (result == null) {
			throw new NotFoundException("School ID not found - " + id);
		}
		
		schoolService.delete(School.class, id);
		
		return "Deleted school ID - " + id;
	}
	
	

}

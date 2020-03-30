package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Lecturer;
import service.MainService;

@RestController
@RequestMapping("/lecturer")
public class LecturerRestController {
	
	@Autowired
	private MainService<Lecturer> mainService;

	@PostMapping("/test")
	public Lecturer test(@RequestBody Lecturer lecturer) {
		mainService.save(lecturer);
		return lecturer;
	}

}

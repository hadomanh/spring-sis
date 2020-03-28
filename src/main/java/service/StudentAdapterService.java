package service;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.StudentDTO;
import entity.Student;

@Service
public class StudentAdapterService implements AdapterService<Student, StudentDTO> {

	@Override
	public StudentDTO getJSON(Student source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDTO> getJSON(List<Student> sourceList) {
		// TODO Auto-generated method stub
		return null;
	}


}

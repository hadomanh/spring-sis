package service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.Beta;

import dto.StudentDTO;
import entity.Student;

@Service
@Beta
public class StudentAdapterService implements AdapterService<Student, StudentDTO> {
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public StudentDTO getJSON(Student source) {
		return modelMapper.map(source, StudentDTO.class);
	}

	@Override
	public List<StudentDTO> getJSON(List<Student> sourceList) {
		List<StudentDTO> results = new ArrayList<StudentDTO>();
		
		for (int i = 0; i < sourceList.size(); i++) {
			results.add(getJSON(sourceList.get(i)));
		}
		
		return results;
	}


}

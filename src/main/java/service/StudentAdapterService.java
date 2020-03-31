package service;

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


}

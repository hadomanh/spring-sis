package service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.Beta;
import com.google.common.reflect.TypeToken;

import dto.StudentDTO;
import entity.Student;
import entity.Subject;

@Service
@Beta
public class StudentAdapterService implements AdapterService<Student, StudentDTO> {
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public StudentDTO getJSON(Student source) {
		StudentDTO destination = modelMapper.map(source, StudentDTO.class);
		
		Type listType = new TypeToken<List<dto.StudentDTO.Subject>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
		
		List<Subject> subjects = source.getSubjects();
		
		if (subjects != null) {
			destination.setSubjects(modelMapper.map(subjects, listType));
		}
		
		return destination;
	}


}

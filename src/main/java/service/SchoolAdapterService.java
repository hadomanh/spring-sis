package service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.Beta;
import com.google.common.reflect.TypeToken;

import dto.SchoolDTO;
import entity.Lecturer;
import entity.School;
import entity.Student;

@Service
@Beta
public class SchoolAdapterService implements AdapterService<School, SchoolDTO> {
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public SchoolDTO getJSON(School source) {
		SchoolDTO destination = modelMapper.map(source, SchoolDTO.class);

		Type listType = new TypeToken<List<dto.SchoolDTO.Student>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
		
		List<Student> students = source.getStudents();
		
		if (students != null) {
			destination.setStudents(modelMapper.map(students, listType));
		}

		listType = new TypeToken<List<dto.SchoolDTO.Lecturer>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
		
		List<Lecturer> lecturers = source.getLecturers();
		
		if (lecturers != null) {
			destination.setLecturers(modelMapper.map(lecturers, listType));
		}

		return destination;
	}

}

package service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.Beta;
import com.google.common.reflect.TypeToken;

import dto.SchoolDTO;
import entity.School;

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

		destination.setStudents(modelMapper.map(source.getStudents(), listType));

		listType = new TypeToken<List<dto.SchoolDTO.Lecturer>>() {
			private static final long serialVersionUID = 1L;
		}.getType();

		destination.setStudents(modelMapper.map(source.getLecturers(), listType));

		return destination;
	}

}

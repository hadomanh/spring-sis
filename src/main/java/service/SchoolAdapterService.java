package service;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

		return destination;
	}

	@Override
	public List<SchoolDTO> getJSON(List<School> sourceList) {
		List<SchoolDTO> result = new ArrayList<SchoolDTO>();
		
		for (int i = 0; i < sourceList.size(); i++) {
			result.add(getJSON(sourceList.get(i)));
		}
		
		return result;
	}

}

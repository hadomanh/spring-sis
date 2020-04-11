package service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.Beta;
import com.google.common.reflect.TypeToken;

import dto.LecturerDTO;
import entity.Lecturer;
import entity.Subject;

@Service
@Beta
public class LecturerAdapterService implements AdapterService<Lecturer, LecturerDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public LecturerDTO getJSON(Lecturer source) {
		LecturerDTO destination = modelMapper.map(source, LecturerDTO.class);

		Type listType = new TypeToken<List<dto.LecturerDTO.Subject>>() {
			private static final long serialVersionUID = 1L;
		}.getType();
		
		List<Subject> subjects = source.getSubjects();

		if (subjects != null) {
			destination.setSubjects(modelMapper.map(subjects, listType));
		}

		return destination;
	}

}

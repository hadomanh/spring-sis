package service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.Beta;

import dto.LecturerDTO;
import entity.Lecturer;

@Service
@Beta
public class LecturerAdapterService implements AdapterService<Lecturer, LecturerDTO> {
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public LecturerDTO getJSON(Lecturer source) {
		return modelMapper.map(source, LecturerDTO.class);
	}


}

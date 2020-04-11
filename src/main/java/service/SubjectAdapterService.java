package service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.Beta;

import dto.SubjectDTO;
import entity.Subject;

@Service
@Beta
public class SubjectAdapterService implements AdapterService<Subject, SubjectDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public SubjectDTO getJSON(Subject source) {
		return modelMapper.map(source, SubjectDTO.class);
	}

}

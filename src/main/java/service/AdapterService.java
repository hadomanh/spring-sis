package service;

import java.util.List;

public interface AdapterService<S, D> {
	
	D getJSON(S source);

	List<D> getJSON(List<S> sourceList);

}

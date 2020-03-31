package service;

import java.util.ArrayList;
import java.util.List;

public interface AdapterService<S, D> {

	D getJSON(S source);

	default List<D> getJSON(List<S> sourceList) {
		List<D> results = new ArrayList<D>();
		sourceList.forEach(item -> results.add(getJSON(item)));
		return results;
	}

}

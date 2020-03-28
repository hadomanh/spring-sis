package service;

import java.util.List;

public interface MainService<T> {
	
	T save(T entity);
	
	List<T> getAll(Class<T> genericType);
	
	T get(Class<T> genericType, Object id);
	
	T delete(Class<T> genericType, Object id);

}

package dao;

import java.util.List;

public interface MainDAO<T> {

	T save(T entity);

	List<T> getAll(Class<T> genericType);

	T get(Class<T> genericType, Object id);
	
	T delete(Class<T> genericType, Object id);

}

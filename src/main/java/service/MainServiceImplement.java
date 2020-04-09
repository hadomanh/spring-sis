package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.MainDAO;

@Service
public class MainServiceImplement<T> implements MainService<T> {

	@Autowired
	private MainDAO<T> mainDAO;

	@Override
	public T save(T entity) {
		return mainDAO.save(entity);
	}

	@Override
	public List<T> getAll(Class<T> genericType) {
		return mainDAO.getAll(genericType);
	}

	@Override
	public T get(Class<T> genericType, Object id) {
		return mainDAO.get(genericType, id);
	}

	@Override
	public T delete(Class<T> genericType, Object id) {

		mainDAO.delete(genericType, id);

		return null;
	}

}

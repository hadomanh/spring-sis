package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MainDAOImplement<T> implements MainDAO<T> {
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public T save(T entity) {

		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(entity);

		return entity;
	}

	@Override
	public List<T> getAll(Class<T> genericType) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.createQuery("from " + genericType.getName(), genericType).getResultList();
	}

	@Override
	public T get(Class<T> genericType, Object id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		T result = session.get(genericType, id.toString());

		return result;
	}

	@Override
	public T delete(Class<T> genericType, Object id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.delete(session.get(genericType, id.toString()));
		
		return null;
	}

}

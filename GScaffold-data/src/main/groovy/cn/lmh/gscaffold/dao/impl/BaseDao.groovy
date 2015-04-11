package cn.lmh.gscaffold.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.lmh.gscaffold.dao.IDao;

public abstract class BaseDao<T> implements IDao<T>{
	private Class<T> clazz = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@Resource(name="sessionFactory")
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		
		return this.sessionFactory.getCurrentSession();
	}

	public Serializable add(T o) {
		return this.sessionFactory.getCurrentSession().save(o);
	}

	public void update(T o) {
		this.sessionFactory.getCurrentSession().update(o);
	}

	public T get(Serializable id) {
		return (T) this.sessionFactory.getCurrentSession().get(clazz, id);
	}

	public void delete(Serializable id) {
		this.sessionFactory.getCurrentSession().delete(this.get(id));		
	}
}

package cn.lmh.gscaffold.dao;

import java.io.Serializable;
/**
 * Dao interface
 * @author Liu Menghan
 *
 * @param <T>
 */
public interface Dao<T> {
	public Serializable add(T o);
	
	public void update(T o);
	
	public T get(Serializable id);
	
	public void delete(Serializable id);
}

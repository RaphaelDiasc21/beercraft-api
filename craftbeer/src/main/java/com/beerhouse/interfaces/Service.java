package com.beerhouse.interfaces;

import java.util.List;

public interface Service {
	public <T> int create(T entity);
	public <T> List<T> getAll();
	public <T> T getById(int id);
	public <T> void  update(T entity);
	public <T> void deleteById(T entity);
}

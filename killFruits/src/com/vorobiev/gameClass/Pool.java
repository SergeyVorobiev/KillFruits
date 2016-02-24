package com.vorobiev.gameClass;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> 
{
	private final PoolObjectFactory<T> factory;
	private final int maxSize;
	private final List<T> freeObject;
	public interface PoolObjectFactory<T>
	{
		public T CreatObject();
	}
	public Pool(PoolObjectFactory<T> factory, int maxSize)
	{
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObject = new ArrayList<T>(maxSize);
	}
	public T newObject()
	{	
		if(freeObject.size() > 0)
			return freeObject.remove(freeObject.size() - 1);
		else
			return factory.CreatObject();
	}
	public void free(T object)
	{
		if(freeObject.size() < maxSize)
			freeObject.add(object);
	}
}

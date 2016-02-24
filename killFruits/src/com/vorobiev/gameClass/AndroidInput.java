package com.vorobiev.gameClass;

import java.util.ArrayList;
import java.util.List;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.vorobiev.gameInterface.Input;

public class AndroidInput implements Input, OnTouchListener
{
	private boolean[] isTouch = new boolean[20];
	private List<TouchEvent> bufferTouch = new ArrayList<TouchEvent>();
	private List<TouchEvent> returnTouch= new ArrayList<TouchEvent>();
	private Pool<TouchEvent> pool;
	public AndroidInput(GLSurfaceView surface)
	{
		surface.setOnTouchListener(this);
		Pool.PoolObjectFactory<TouchEvent> factory = new Pool.PoolObjectFactory<TouchEvent>()
				{
					@Override
					public TouchEvent CreatObject() 
					{
						return new TouchEvent();
					}
				};
		pool = new Pool<TouchEvent>(factory, 20);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		synchronized(this)
		{
			int action = (event.getAction() & MotionEvent.ACTION_MASK); //получаем тип событи€
			int pointerIndex = (event.getAction() & // получаем указатель нажати€ он нужен если 
								MotionEvent.ACTION_POINTER_INDEX_MASK) >>// зажимаем вторым и более пальцем
								MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			int pointerId = event.getPointerId(pointerIndex);
			TouchEvent touchEvent; //объ€вл€ем объект событи€ 
			switch(action)
			{
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					touchEvent = pool.newObject();
					touchEvent.x = (int)event.getX(pointerIndex);
					touchEvent.y = (int)event.getY(pointerIndex);
					touchEvent.type = MotionEvent.ACTION_DOWN;
					touchEvent.Id = pointerId;
					bufferTouch.add(touchEvent);
					isTouch[pointerId] = true;
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					touchEvent = pool.newObject();
					touchEvent.x = (int)event.getX(pointerIndex);
					touchEvent.y = (int)event.getY(pointerIndex);
					touchEvent.type = MotionEvent.ACTION_UP;
					touchEvent.Id = pointerId;
					bufferTouch.add(touchEvent);
					isTouch[pointerId] = false;
					break;
				case MotionEvent.ACTION_MOVE:
					int pointerCount = event.getPointerCount();
					for(int i = 0; i < pointerCount; i++)
					{
						pointerId = event.getPointerId(pointerIndex);
						touchEvent = pool.newObject();
						touchEvent.Id = pointerId;
						touchEvent.x = (int)event.getX(pointerIndex);
						touchEvent.y = (int)event.getY(pointerIndex);
						bufferTouch.add(touchEvent);
					}
					break;
				default:
					break;
			}
			return true;
		}
	}
	
	@Override
	public boolean isTouchDown(int pointer) 
	{
		if(pointer >= 0)
			if(pointer < 10)
				return isTouch[pointer];
		return false;
	}

	@Override
	public int getTouchX(int pointer) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTouchY(int pointer) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TouchEvent> getTouchEvents() 
	{
		synchronized(this)
		{
			int len = returnTouch.size();
			for(int i = 0; i < len; i++)
				pool.free(returnTouch.get(i));
			returnTouch.clear();
			returnTouch.addAll(bufferTouch);
			bufferTouch.clear();
			return returnTouch;
		}	
	}
	@Override
	public boolean isKeyPressed(int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<KeyEvent> getKeyEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	

}

package com.vorobiev.androidGame.gameDynObject2d;

import java.util.Random;

public class JoltOfCamera 
{
	private Random rnd = new Random();
	private int push = 0;
	private int strong;
	private float time;
	private float offsetX;
	private float offsetY;
	private float lastTime = 0;
	public boolean activate = false;
	public JoltOfCamera(int strong, float time)
	{
		this.strong = strong;
		this.time = time;
	}
	public boolean update(float deltaTime)
	{
		if(lastTime > time && push == 0)
		{
			activate = false;
			lastTime = 0;
			offsetX = 0;
			offsetY = 0;
			return true;
		}
		if(activate)
		{
			lastTime += deltaTime;
			if(push == 0)
			{
				push++;
				offsetX = rnd.nextInt(strong) * deltaTime;
				offsetY = rnd.nextInt(strong) * deltaTime;
				if(rnd.nextInt(2) == 0)	
					offsetX *= -1;	
				if(rnd.nextInt(2) == 0)
					offsetY *= -1;
			}
			else
			{	
				push = 0;
				offsetX *= -1;
				offsetY *= -1;
			}
		}
		return false;
	}
	public float getOffsetX()
	{
		return offsetX;
	}
	public float getOffsetY()
	{
		return offsetY;
	}
}

package com.vorobiev.gameClass;

import java.util.Random;

public class Bob 
{
	public static int bobX = 64;
	public static int bobY = 64;
	private final int width = 720 - bobX;
	private final int height = 1280 - bobY;
	private static final Random rnd = new Random();
	public float x, y, rotate;
	private float dirX, dirY;
	public boolean live = true;
	
	public Bob()
	{
		x = rnd.nextFloat() * width;
		y = rnd.nextFloat() * height;
		dirX = (rnd.nextInt(100) + 100) * 1;
		dirY = (rnd.nextInt(100) + 100) * 1;
		
	}
	public void update(float deltaTime)
	{
		rotate += 100 * deltaTime;
		x = x + dirX * deltaTime;
		y = y + dirY * deltaTime;
		if(x < 0)
		{
			dirX = -dirX;
			x = 0;
		}
		if(x > width)
		{
			dirX = -dirX;
			x = width;
		}
		if(y < 0)
		{
			dirY = -dirY;
			y = 0;
		}
		if(y > height)
		{
			dirY = -dirY;
			y = height;
		}
	}
}

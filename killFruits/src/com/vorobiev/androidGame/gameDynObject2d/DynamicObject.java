package com.vorobiev.androidGame.gameDynObject2d;

public abstract class DynamicObject extends GameObject
{
	public float speedX = 0.0f;
	public float speedY = 0.0f;
	public float speedAngle = 0.0f;
	public DynamicObject(float width, float height, float angle, float posX, float posY) 
	{
		super(width, height, angle, posX, posY);

	}
	
}

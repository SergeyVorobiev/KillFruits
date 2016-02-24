package com.vorobiev.androidGame.gameDynObject2d;

import com.vorobiev.androidGame.math.ZeoVector;

public abstract class GameObject 
{
	public float width;
	public float height;
	public float angle;
	public ZeoVector position = new ZeoVector();
	public GameObject(float width, float height, float angle, float posX, float posY)
	{
		this.width = width;
		this.height = height;
		this.angle = angle;
		this.position.x = posX;
		this.position.y = posY;
	}
}

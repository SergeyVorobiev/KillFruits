package com.vorobiev.androidGame.gameDynObject2d;

import com.vorobiev.gameClass.TextureRegion;

public class Line extends GameObject
{
	public TextureRegion texReg;
	public float defWidth;
	public Line(float width, float height, float angle, float posX, float posY, TextureRegion texReg) 
	{
		super(width, height, angle, posX, posY);
		this.texReg = texReg;
		this.defWidth = width;
	}
	public void setDefaultWidth()
	{
		this.width = defWidth;
	}

}

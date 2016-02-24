package com.vorobiev.upgradeFructs;

import com.vorobiev.gameClass.TextureRegion;

public class Label 
{
	public TextureRegion texReg;
	public float posX;
	public float posY;
	public float width;
	public float height;
	public Label(float posX, float posY, float width, float height, TextureRegion texReg)
	{
		this.texReg = texReg;
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
	}
	public void setTextureReg(TextureRegion texReg)
	{
		this.texReg = texReg;
	}
}

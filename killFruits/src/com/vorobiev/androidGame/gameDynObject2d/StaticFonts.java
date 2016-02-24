package com.vorobiev.androidGame.gameDynObject2d;

import com.vorobiev.gameClass.TextureRegion;

public class StaticFonts extends GameObject
{
	public TextureRegion texReg;
	public StaticFonts(float width, float height, float angle, float posX, float posY, TextureRegion texReg) 
	{
		super(width, height, angle, posX, posY);
		this.texReg = texReg;
	}

	
}

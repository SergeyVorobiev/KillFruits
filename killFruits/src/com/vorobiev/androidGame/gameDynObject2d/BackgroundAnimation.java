package com.vorobiev.androidGame.gameDynObject2d;

import com.vorobiev.gameClass.TextureRegion;

public class BackgroundAnimation 
{
	private TextureRegion texReg;
	private int startPos = 1024;
	private int maxPos = 0;
	private float lastTime = 0.0f;
	public float timeUpdate = 0.5f;
	public BackgroundAnimation(TextureRegion texReg)
	{
		this.texReg = texReg;
	}
	public void update(float deltaTime)
	{
		lastTime += deltaTime;
		if(lastTime >= timeUpdate)
		{
			lastTime = 0.0f;
			startPos--;
			if(startPos == 0)
				startPos = 1024;
			texReg.set(0, startPos);
		}	
	}
}

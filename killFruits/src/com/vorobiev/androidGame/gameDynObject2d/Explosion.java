package com.vorobiev.androidGame.gameDynObject2d;

import com.vorobiev.gameClass.TextureRegion;

public class Explosion 
{
	//private float time;
	private float lastTime = 0.0f;
	private float timeToCadr;
	private TextureRegion[] texRegs;
	private int maxCadrs;
	private int curCadrs = 0;
	public TextureRegion texReg;
	public float posX = 0.0f, posY = 0.0f;
	public float width, height, angle;
	public float offsetX = 0.0f;
	public float offsetY = 0.0f;
	public Explosion(TextureRegion[] texRegs, float width, float height, float time)
	{
		//this.time = time;
		this.texRegs = texRegs;
		this.texReg = texRegs[0];
		this.maxCadrs = texRegs.length;
		timeToCadr = time / maxCadrs;
		this.width = width;
		this.height = height;
	}
	public TextureRegion update(float deltaTime)
	{
		lastTime += deltaTime;
		this.posY -= (offsetY * deltaTime);
		if(lastTime > timeToCadr)
		{
			lastTime = 0;
			curCadrs++;
			if(curCadrs == maxCadrs)
				return null;
			texReg = texRegs[curCadrs];
		}
		return texReg;
	}
	public Explosion setPosition(float posX, float posY, float angle, float offsetX, float offsetY)
	{
		this.posX = posX;
		this.posY = posY;
		this.angle = angle;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		return this;
	}
	public void setDefault()
	{
		this.curCadrs = 0;
		this.lastTime = 0;
		this.texReg = this.texRegs[0];
		this.offsetX = 0;
		this.offsetY = 0;
	}
}

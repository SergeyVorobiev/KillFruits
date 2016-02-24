package com.vorobiev.androidGame.gameDynObject2d;

import java.util.Random;

import com.vorobiev.gameClass.Texture;
import com.vorobiev.gameClass.TextureRegion;

public class Debris 
{
	public class Deb
	{
		public TextureRegion texReg;
		public float posX = 0.0f;
		public float posY = 0.0f;
		public float width;
		public float height;
		public float angle = 0.0f;
		public int speedY;
		public int speedAngle;
		public Deb(Texture texture)
		{
			this.texReg = new TextureRegion(texture);
			
		}
	}
	public Deb deb1;
	public Deb deb2;
	public Deb deb3;
	public Deb deb4;
	public boolean isActive = false;
	private float timeShow = 0.0f;
	private float lastTime = 0.0f;
	private Random  rnd = new Random();
	public Debris(Texture texture, float width, float height)
	{
		deb1 = new Deb(texture);
		deb2 = new Deb(texture);
		deb3 = new Deb(texture);
		deb4 = new Deb(texture);
	}
	public void set(TextureRegion tr, float posX, float posY, float angle, float width, float height, float timeShow)
	{
		this.timeShow = timeShow;
		this.lastTime = 0.0f;
		this.isActive = true;
		
		deb1.texReg.u1 = tr.u1;
		deb1.texReg.v1 = tr.v1;
		deb1.texReg.u2 = (tr.u2 + tr.u1) / 2;
		deb1.texReg.v2 = (tr.v2 + tr.v1) / 2;
		//deb1.angle = angle;
		deb1.height = height / 2;
		deb1.width = width / 2;
		deb1.posX = posX - deb1.width / 2;
		deb1.posY = posY + deb1.height / 2;
		
		deb4.texReg.u1 = deb1.texReg.u2;
		deb4.texReg.v1 = deb1.texReg.v2;
		deb4.texReg.u2 = tr.u2;
		deb4.texReg.v2 = tr.v2;
		//deb4.angle = angle;
		deb4.height = height / 2;
		deb4.width = width / 2;
		deb4.posX = posX + deb1.width / 2;
		deb4.posY = posY - deb1.height / 2;
		
		deb2.texReg.u1 = (tr.u2 + tr.u1) / 2;
		deb2.texReg.v1 = tr.v1;
		deb2.texReg.u2 = tr.u2;
		deb2.texReg.v2 = (tr.v2 + tr.v1) /2;
		//deb2.angle = angle;
		deb2.height = height / 2;
		deb2.width = width / 2;
		deb2.posX = posX + deb1.width / 2;
		deb2.posY = posY + deb1.height / 2;
		
		deb3.texReg.u1 = tr.u1;
		deb3.texReg.v1 = (tr.v2 + tr.v1) / 2;
		deb3.texReg.u2 = (tr.u2 + tr.u1) / 2;
		deb3.texReg.v2 = tr.v2;
		//deb3.angle = angle;
		deb3.height = height / 2;
		deb3.width = width / 2;
		deb3.posX = posX - deb1.width / 2;
		deb3.posY = posY - deb1.height / 2;
		
		deb1.speedAngle = (rnd.nextInt(360) + 90);
		deb2.speedAngle = (rnd.nextInt(360) + 90);
		deb3.speedAngle = (rnd.nextInt(360) + 90);
		deb4.speedAngle = (rnd.nextInt(360) + 90);
		deb1.speedY = (rnd.nextInt(5) + 3);
		deb2.speedY = (rnd.nextInt(5) + 3);
		deb3.speedY = (rnd.nextInt(5) + 3);
		deb4.speedY = (rnd.nextInt(5) + 3);
	}
	public void update(float deltaTime)
	{
		lastTime += deltaTime;
		if(lastTime > timeShow)
		{
			isActive = false;
			return;
		}
		deb1.angle += deb1.speedAngle * deltaTime;
		deb2.angle += deb2.speedAngle * deltaTime;
		deb3.angle += deb3.speedAngle * deltaTime;
		deb4.angle += deb4.speedAngle * deltaTime;
		deb1.posY -= deb1.speedY * deltaTime;
		deb2.posY -= deb2.speedY * deltaTime;
		deb3.posY -= deb3.speedY * deltaTime;
		deb4.posY -= deb4.speedY * deltaTime;
	}
}

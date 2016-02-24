package com.vorobiev.androidGame.gameDynObject2d;

import java.util.Random;
import com.vorobiev.androidGame.math.ZeoVector;
import com.vorobiev.gameClass.TextureRegion;


public class Fructs extends DynamicObject
{
	public static enum fruitState{LIFE, KILL, SHOT, DISABLE};
	public boolean isTarget;
	private Random rnd = new Random();
	public static TextureRegion[] ArrTexReg;
	public TextureRegion texReg;
	public boolean go = false;
	public fruitState state;
	public int coins;
	public int size = 1;
	public int idTexture;
	public float AoE = 0.1f;
	public boolean modified = false;
	public Fructs(float width, float height, float angle, float posX, float posY) 
	{
		super(width, height, angle, posX, posY);
		randomGenerate();
	}
	public void randomGenerate()
	{
		modified = false;
		isTarget = false;
		size = 1;
		width = 0.75f;
		height = 0.75f;
		state = Fructs.fruitState.LIFE;
		float posY = 13.5f;
		angle = (float)rnd.nextInt(360);
		speedAngle = 0.2f;
		int tRnd = rnd.nextInt(10);
		if(tRnd != 7)
		{
			this.idTexture = rnd.nextInt(24);
			texReg = ArrTexReg[idTexture];
			coins = rnd.nextInt(91) + 10;
		}
		else if(rnd.nextInt(10) != 0)
		{
			this.idTexture = rnd.nextInt(6);
			texReg = ArrTexReg[idTexture + 24];
			coins = (rnd.nextInt(901) + 100) * -1;
		}
		else
		{
			texReg = ArrTexReg[30];
			coins = 0;
		}
		
		float posX = rnd.nextFloat() * 6.2f + 0.5f;
		position.set(posX, posY);
		speedY = rnd.nextFloat() * 5.0f + 1.0f;
		speedX = 0.0f;
		
		
		
		
		//print.logPrint(speedY);
	}
	public void Go(float deltaTime)
	{
		position.x += speedX * deltaTime;
		position.y -= speedY * deltaTime;
		angle += speedAngle;
		if(angle > 360.0f)
			angle = 0.0f;
		
	}
}

package com.vorobiev.androidGame.gameDynObject2d;


import android.util.FloatMath;

import com.example.testgl.GameScreen;
import com.vorobiev.androidGame.gameDynObject2d.Fructs.fruitState;
import com.vorobiev.androidGame.math.ZeoVector;
import com.vorobiev.gameClass.Pool;
import com.vorobiev.gameClass.TextureRegion;
import com.vorobiev.printlog.Print;

public class Rocket extends DynamicObject
{
	public Print print = new Print();
	public boolean activated = false;
	public int strong = 10;
	public static final int maxStrong = 100;
	public TextureRegion texReg;
	public float maxSpeed = 15.0f;
	public float timeFly = 0.0f;
	public float maxTimeFly = 2.0f;
	public Fructs target = null;
	public float StartPosX;
	public float StartPosY;
	public float timeToTarget;
	public ZeoVector speed = new ZeoVector(0.0f, 0.0f);
	public Turel parent = null;
	public boolean laser = false;
	private float maxHeight;
	public Rocket(float width, float height, float angle, float posX, float posY, TextureRegion texReg) 
	{
		super(width, height, angle, posX, posY);
		this.texReg = texReg;
		this.position = new ZeoVector(0.0f, 0.0f);
		this.maxHeight = height;
	}
	public void SetTarget(Fructs target)
	{
		this.target = target;
	}
	public Explosion update(float deltaTime, Pool<Explosion> pExp, GameScreen g)
	{
		timeFly += deltaTime;
		//print.logPrint(timeFly);
		//print.logPrint(maxTimeFly);
		if(!laser)
		{
			position.x += speed.x * deltaTime;
			position.y += speed.y * deltaTime;
		}
		else	
			this.height = this.maxHeight * FloatMath.sin((((180 / this.maxTimeFly) * timeFly) * ZeoVector.TO_RADIANS));
		if(this.target != null)	
			if(!target.isTarget || this.target.position.y < 0.5f)
				this.target = null;
		if(this.target != null && timeFly >= timeToTarget)
		{
			float Sx = timeToTarget * speed.x + StartPosX;
			float Sy = timeToTarget * speed.y + StartPosY;
			if(Math.abs(Sx - target.position.x) <= target.AoE + 0.4) // ракета на цели
			{
				Explosion exp = pExp.newObject().setPosition(Sx, Sy, angle, 0.0f, target.speedY);
				this.activated = false;
				if(strong == 0 && !laser) // это пиналка
				{
					target.state = Fructs.fruitState.KILL;
					target.isTarget = false;
					target.speedAngle = target.speedY * 2.5f;
					target.speedX = target.position.x / -1.5f;
					target.speedY = (12.8f - target.position.y) / -1.5f;
					this.target = null;
					parent.killFruct += 1;
					return exp;
				}
				else if(strong == 0 && laser)
				{
					target.state = Fructs.fruitState.SHOT;
					target.isTarget = false;
					this.target = null;
					parent.killFruct += 1;
					return null;
				}
				else
				{
					target.coins -= this.strong;
					g.gold += this.strong / 5;
					if(target.coins <= 0)
					{
						target.isTarget = false;
						target.state = fruitState.SHOT;
						parent.killFruct += 1;
					}
					this.target = null;
					return exp;
				}
			}
			this.target = null;
			//print.logPrint(time);
			//print.logPrint(deltaTime);
			//print.logPrint(this.speed.y);
		}
		if(timeFly > maxTimeFly)
		{
			activated = false;
			this.target = null;
			if(!laser)
				return pExp.newObject().setPosition(position.x, position.y, angle, 0.0f, 0.0f);
		}
		return null;
		
	}
}

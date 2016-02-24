package com.vorobiev.androidGame.gameDynObject2d;

import java.util.List;

import android.util.FloatMath;

import com.vorobiev.androidGame.math.ZeoVector;
import com.vorobiev.gameClass.TextureRegion;
import com.vorobiev.printlog.Print;

public class Turel extends GameObject
{
	private int maxRocket = 50;
	private float shotSpeed = 1.0f;
	private float lastShotTime = 0.0f;
	public int currentRocket = 1;
	public Rocket[] rocket = new Rocket[maxRocket];
	public TextureRegion texReg;
	public Fructs target = null;
	public float maxDist = 6.0f;
	public float minDist = 1.5f;
	public Print print = new Print();
	public int killFruct = 0;
	private ZeoVector lenTarget = new ZeoVector(0.0f, 0.0f);
	public Turel(float width, float height, float angle, float posX, float posY, TextureRegion texReg,
				TextureRegion texRegRocket, float rWidth, float rHeight) 
	{	
		super(width, height, angle, posX, posY);
		this.texReg = texReg;
		for(int i = 0; i < maxRocket; i++)
			this.rocket[i] = new Rocket(rWidth, rHeight, 0.0f, 0.0f, 0.0f, texRegRocket);
	}
	public Rocket update(List<Fructs> listFructs, float deltaTime)
	{
		lastShotTime += deltaTime;
		if(target != null)
		{
			if(target.state != Fructs.fruitState.LIFE || target.position.y < this.minDist || target.position.y > this.maxDist)
			{
				this.angle = 90.0f;
				target = null;
			}
		}
		if(target == null)
		{
			int len = listFructs.size();			
			for(int i = 0; i < len; i++) // попытаемся найти свою цель
			{
				Fructs f = listFructs.get(i);
				if((f.state != Fructs.fruitState.LIFE || (f.state == Fructs.fruitState.LIFE && f.coins < 0)) && !rocket[0].laser)
					continue;
				else if((f.state != Fructs.fruitState.KILL || (f.state == Fructs.fruitState.KILL && f.coins > 0)) && rocket[0].laser)
					continue;
				else if(f.coins == 0)
					continue;
				else if(f.position.y < this.maxDist && f.position.y > this.minDist)
				{
					target = f;
					if(!f.isTarget)
					{
						f.isTarget = true;
						break;
					}
				}
			}
		}
		if(target != null)
		{
			lenTarget.x = target.position.x - this.position.x;
			lenTarget.y = target.position.y - this.position.y;
			this.angle = lenTarget.getAngle();
			return getShot();
		}
		return null;	
	}
	private Rocket getShot()
	{
		if((shotSpeed - lastShotTime) <= 0)
		{
			for(int i = 0; i < currentRocket; i++)
			{
				if(!rocket[i].activated)
				{
					lastShotTime = 0;
					rocket[i].activated = true;
					rocket[i].angle = this.angle; // придать угол ракете от пушки
					if(!rocket[i].laser)
						rocket[i].position.getCoordOfRadiusVec((this.height / 2), this.angle);// узнать позицию координат от центра до кончика дула пушки
					else
						rocket[i].position.getCoordOfRadiusVec(((this.height / 2) + (rocket[i].width / 2)), this.angle); //если лазер установить позицию центра на середине его длины для отрисовки
					rocket[i].position.add(this.position); // сместить позицию ракеты от нулевых координат до координат пушки
					rocket[i].StartPosX = rocket[i].position.x;
					rocket[i].StartPosY = rocket[i].position.y;
					if(rocket[i].maxSpeed != 0)
						rocket[i].speed.getCoordOfRadiusVec(rocket[i].maxSpeed, rocket[i].angle); // получить значения скорости по x y исходя из максимальной скорости снаряда	
					rocket[i].timeFly = 0.0f; // снаряд начал полет
					rocket[i].target = this.target; // ракета получила цель
					float distToTarget = target.position.y - rocket[i].position.y - target.AoE;
					float tempSpeed = (target.speedY + rocket[i].speed.y) / rocket[i].speed.y;
					float distRocket = distToTarget / tempSpeed;
					rocket[i].timeToTarget = distRocket / rocket[i].speed.y;
					//print.logPrint(rocket[i].pointToFlyX);
					//print.logPrint(rocket[i].pointToFlyY);
					//print.logPrint(target.position.x);
					//print.logPrint(target.position.y);
					rocket[i].parent = this;
					return rocket[i];
				}
			}
		}
		return null;
	}
	public void setShotSpeedTurel(float time)
	{
		if(time > 10.0f || time < 0.01f)
			shotSpeed = 10.0f;
		else
			shotSpeed = time;
	}
	public void setNumRocketTurel(int rock)
	{
		if(rock > maxRocket || rock < 0)
			currentRocket = 1;
		else
			currentRocket = rock;
	}
	public void setSpeedRocket(float speed)
	{
		if(speed < 0 || speed > 800)
			speed = 1.0f;
		int len = rocket.length;
		if(speed > 400)
			for(int i = 0; i < len; i++)
				rocket[i].laser = true;
		for(int i = 0; i < len; i++)
		{
			rocket[i].maxSpeed = speed;
			if(rocket[i].laser)
				rocket[i].maxTimeFly = 0.5f;
			else
				rocket[i].maxTimeFly = 9.0f / speed;
		}	
	}
	public void setStrongRocket(int strong)
	{
		if(strong < 0 || strong > Rocket.maxStrong)
			strong = 1;
		int len = rocket.length;
		for(int i = 0; i < len; i++)
			rocket[i].strong = strong;
	}
	public void setMaxDistTurel(float dist)
	{
		if(dist < 2.0f || dist > 14.0f)
			dist = 2.0f;
		this.maxDist = dist;
	}
	public void setFullParameters(float time, int rock, float speed, int strong, float dist)
	{
		setShotSpeedTurel(time);
		setNumRocketTurel(rock);
		setSpeedRocket(speed);
		setStrongRocket(strong);
		setMaxDistTurel(dist);
	}
}

package com.vorobiev.androidGame.gameDynObject2d;

import com.vorobiev.androidGame.math.ZeoVector;
import com.vorobiev.gameClass.Texture;
import com.vorobiev.gameClass.TextureRegion;

public class TankShepard 
{
	public ZeoVector position = new ZeoVector(1.0f, 1.0f);
	public final TextureRegion tankTexture;
	public float width = 0.75f;
	public float height = 1.0f;
	public float speed = 0.0f;
	public final static float accel = 0.1f;
	public float directionX = 0.0f;
	public static final float maxSpeed = 50.0f;
	public boolean go = false;
	public TankShepard(Texture atlasTexture)
	{
		this.tankTexture = new TextureRegion(atlasTexture, 0, 64, 96, 128);
	}
	public void Go(float deltaTime)
	{
		if(go == true)
		{
			speed = speed + (accel * deltaTime);
			if(speed > 10.0f)
				speed = 10.0f;
			position.y += speed;
		}
		
	}
}

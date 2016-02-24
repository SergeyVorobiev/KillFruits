package com.vorobiev.androidGame.math;

import android.util.FloatMath;

public class ZeoVector 
{
	public static float TO_RADIANS = (1 / 180.0f) * (float)Math.PI;
	public static float TO_DEGREES = (1 / (float)Math.PI) * 180;
	public float x, y;
	public ZeoVector()
	{
		this.x = 0;
		this.y = 0;
	}
	public ZeoVector(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public ZeoVector(ZeoVector other)
	{
		this.x = other.x;
		this.y = other.y;
	}
	public ZeoVector copyVector()
	{
		return new ZeoVector(x, y);
	}
	public ZeoVector set(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	public ZeoVector set(ZeoVector other)
	{
		this.x = other.x;
		this.y = other.y;
		return this;
	}
	public ZeoVector add(float x, float y)
	{
		this.x += x;
		this.y += y;
		return this;
	}
	public ZeoVector add(ZeoVector other)
	{
		this.x += other.x;
		this.y += other.y;
		return this;
	}
	public ZeoVector sub(float x, float y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}
	public ZeoVector sub(ZeoVector other)
	{
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}
	public ZeoVector multiple(float x, float y)
	{
		this.x *= x;
		this.y *= y;
		return this;
	}
	public ZeoVector multiple(float scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	public float len()
	{
		return FloatMath.sqrt(x * x + y * y);
	}
	public ZeoVector normalize()
	{
		float len = len();
		if(len != 0)
		{
			this.x /= len;
			this.y /= len;
		}
		return this;
	}
	public float getAngle()
	{
		float angle = (float) Math.atan2(y, x) * TO_DEGREES;
		if(angle < 0)
			angle += 360;
		return angle;
	}
	public ZeoVector rotate(float angle)
	{
		float rad = angle * TO_RADIANS;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);
		float newX = this.x * cos - this.y * sin;
		float newY = this.x * sin + this.y * cos;
		this.x = newX;
		this.y = newY;
		return this;
	}
	public float dist(ZeoVector other)
	{
		float distX = this.x - other.x;
		float distY = this.y - other.y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}
	public float dist(float x, float y)
	{
		float distX = this.x - x;
		float distY = this.y - y;
		return FloatMath.sqrt(distX * distX + distY * distY);
	}
	public ZeoVector getCoordOfRadiusVec(float lenVec, float angle)
	{
		float rad = angle * TO_RADIANS;
		float cos = FloatMath.cos(rad);
		float sin = FloatMath.sin(rad);
		this.x = lenVec * cos;
		this.y = lenVec * sin;
		return this;
	}
}

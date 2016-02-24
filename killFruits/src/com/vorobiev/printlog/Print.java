package com.vorobiev.printlog;

import android.util.Log;

import com.vorobiev.gameClass.GLGraphics;
import com.vorobiev.gameInterface.Game;

public class Print 
{
	long startTime = System.nanoTime();
	private GLGraphics graphics;
	private String string = "NULL";
	private StringBuilder builder = new StringBuilder();
	public Print(Game game)
	{
		this.graphics = game.getGLGraphics();
	}
	public Print() {
		// TODO Auto-generated constructor stub
	}
	public void logPrint()
	{
		
		if(System.nanoTime() - startTime >= 1000000000)
		{
			builder.setLength(0);
			builder.append(graphics.getWidth());
			Log.d("WIDTH", builder.toString());
			builder.setLength(0);
			builder.append(graphics.getHeight());
			Log.d("HEIGHT", builder.toString());
			Log.d("MESSAGE", string);
			startTime = System.nanoTime();
		}
	}
	public void logPrint(String str)
	{
		Log.d("MESSAGE", str);
	}
	public void logPrint(int count)
	{
		builder.setLength(0);
		Log.d("MESSAGE", builder.append(count).toString());
	}
	public void logPrint(float count)
	{
		builder.setLength(0);
		Log.d("MESSAGE", builder.append(count).toString());
	}
	public void logPrint(String msg, float count)
	{
		builder.setLength(0);
		Log.d(msg, builder.append(count).toString());
	}
	public void setMessage(String string)
	{
		this.string = string;
	}
	public void setMessage(int count)
	{
		builder.setLength(0);
		builder.append(count);
		string = builder.toString();
	}
	
}

package com.example.testgl;

import com.vorobiev.gameClass.ScreeN;

public class TestScreen extends MainActivity
{
	@Override
	public ScreeN getStartScreen() 
	{
		//return new Test(this);
		//return new FirstTriangle(this);
		//return new TextureTriangle(this);
		//return new CanonScreen(this);
		return new LoadingScreen(this);
	}
}

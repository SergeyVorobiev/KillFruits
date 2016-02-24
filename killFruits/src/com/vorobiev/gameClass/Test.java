package com.vorobiev.gameClass;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.vorobiev.gameInterface.Game;

public class Test extends ScreeN
{
	private GLGraphics glGraphics;
	Random rnd = new Random();
	public Test(Game game) 
	{
		super(game);
		glGraphics = game.getGLGraphics();	
	}

	@Override
	public void update(float deltaTime) 
	{
		GL10 gl = glGraphics.getGL();
		gl.glClearColor(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT); //флаг указывает что нужно очистить текущий буфер
		
	}

	@Override
	public void present(float deltaTime) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
		
	}
}

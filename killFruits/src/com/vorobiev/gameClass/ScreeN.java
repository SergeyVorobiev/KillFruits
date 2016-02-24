package com.vorobiev.gameClass;

import com.vorobiev.gameInterface.Game;

public abstract class ScreeN 
{
	protected final Game game;
	public ScreeN(Game game)
	{
		this.game = game;
	}
	public abstract void update(float deltaTime);
	public abstract void present(float deltaTime);
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
}

package com.example.testgl;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.vorobiev.androidGame.gameDynObject2d.BackgroundAnimation;
import com.vorobiev.androidGame.math.Camera2D;
import com.vorobiev.gameClass.Assets;
import com.vorobiev.gameClass.GLGraphics;
import com.vorobiev.gameClass.ScreeN;
import com.vorobiev.gameClass.SpriteBatcher;
import com.vorobiev.gameInterface.Game;
import com.vorobiev.gameInterface.Input.TouchEvent;



public class MenuScreen extends ScreeN
{
	private final float frustumWidth = 7.2f;
	private final float frustumHeight = 12.8f;
	private Game glGame;
	private GLGraphics glGraphics;
	private Camera2D camera;
	private SpriteBatcher batcher;
	private BackgroundAnimation backgroundAnim;
	public MenuScreen(Game game) 
	{
		super(game);
		this.glGame = game;
		this.glGraphics = game.getGLGraphics();
		this.camera = new Camera2D(glGraphics, frustumWidth, frustumHeight);
		batcher = new SpriteBatcher(glGraphics, 100);
		backgroundAnim = new BackgroundAnimation(Assets.backgroundReg);
	}

	@Override
	public void update(float deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++)
		{
			if(touchEvents.get(i).type == TouchEvent.TOUCH_UP)
			{
				glGame.setScreen(new GameScreen(glGame));
				//return;
			}
		}
		backgroundAnim.update(deltaTime);
	}

	@Override
	public void present(float deltaTime) 
	{
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(frustumWidth / 2, frustumHeight / 2, frustumWidth, frustumHeight, Assets.backgroundReg);
		batcher.endBatch(Assets.background);
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() 
	{
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glEnable(GL10.GL_TEXTURE_2D);
		//asset.mainMenu.load();
		//asset.loadingScr.load();
		Assets.background.load();
		camera.setViewportAndMatrices();
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
		
	}

	
	

}

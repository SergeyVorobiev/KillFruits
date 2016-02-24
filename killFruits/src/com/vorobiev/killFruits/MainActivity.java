package com.example.testgl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.vorobiev.gameClass.AndroidFileIO;
import com.vorobiev.gameClass.AndroidInput;
import com.vorobiev.gameClass.GLGraphics;
import com.vorobiev.gameClass.KeyboardHandler;
import com.vorobiev.gameClass.ScreeN;
import com.vorobiev.gameInterface.FileIO;
import com.vorobiev.gameInterface.Game;
import com.vorobiev.gameInterface.Input;

public abstract class MainActivity extends Activity implements Game, Renderer
{
	enum GLGameState{Initialized, Running, Pause, Finish, Idle}
	private GLGameState state = GLGameState.Initialized;
	private GLSurfaceView surface;
	private GLGraphics glGraphics;
	private WakeLock wakelock;
	private Object Changed = new Object(); // ключ для синхронизации
	private ScreeN screen;
	private FileIO fileio;
	private Input input;
	private Input inputKey;
	long startTime = System.nanoTime();
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakelock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,"GLGame");
		fileio = new AndroidFileIO(this.getAssets());
		surface = new GLSurfaceView(this);
		surface.setRenderer(this);
		
		setContentView(surface);
		glGraphics = new GLGraphics(surface);
		input = new AndroidInput(surface);
		inputKey = new KeyboardHandler(surface);
	}
	@Override
	protected void onResume()
	{
		super.onResume();
		surface.onResume();
		wakelock.acquire();
	}
	@Override
	protected void onPause()
	{
		synchronized(Changed)
		{
			if(isFinishing())
				state = GLGameState.Finish;
			else
				state = GLGameState.Pause;
			while(true)
			{
				try
				{
					Changed.wait();
					break;
				}
				catch(InterruptedException e)
				{}
			}	
		}
		wakelock.release();
		surface.onPause();
		super.onPause();
		
	}
	@Override
	public GLGraphics getGLGraphics() 
	{
		return glGraphics;
	}
	@Override
	public void onDrawFrame(GL10 gl) 
	{
		GLGameState state = null;
		synchronized(Changed)
		{
			state = this.state;
		}
		if(state == GLGameState.Running)
		{
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			screen.update(deltaTime);
			screen.present(deltaTime);
		}
		if(state == GLGameState.Pause)
		{
			screen.pause();
			synchronized(Changed)
			{
				this.state = GLGameState.Idle;
				Changed.notifyAll();
			}		
		}
		if(state == GLGameState.Finish)
		{
			screen.pause();
			screen.dispose();
			synchronized(Changed)
			{
				this.state = GLGameState.Idle;
				Changed.notifyAll();
			}
		}	
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		glGraphics.setGl(gl);
		synchronized(Changed)
		{
			if(state == GLGameState.Initialized)
				screen = getStartScreen();
			state = GLGameState.Running;
			screen.resume();
			startTime = System.nanoTime();
		}
		
	}
	@Override
	public void setScreen(ScreeN screen) 
	{
		
		if(screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
		
	}
	@Override
	public ScreeN getCurrentScreen() 
	{
		return screen;
	}
	@Override
	public FileIO getFileIO()
	{
		return fileio;
	}
	@Override
	public Input getInput()
	{
		return input;
	}
	@Override
	public Input getInputKey()
	{
		return inputKey;
	}
	@Override
	public void Finish()
	{
		this.finish();
	}
}

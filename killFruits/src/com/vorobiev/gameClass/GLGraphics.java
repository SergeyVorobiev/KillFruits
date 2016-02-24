package com.vorobiev.gameClass;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class GLGraphics 
{
	private GLSurfaceView surface;
	public GL10 gl;
	public GLGraphics(GLSurfaceView surface)
	{
		this.surface = surface;
	}
	public void setGl(GL10 gl)
	{
		this.gl = gl;
	}
	public GL10 getGL()
	{
		return gl;
	}
	public int getWidth()
	{
		return surface.getWidth();
	}
	public int getHeight()
	{
		return surface.getHeight();
	}
	
}

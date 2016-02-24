package com.vorobiev.androidGame.math;

import javax.microedition.khronos.opengles.GL10;

import com.vorobiev.gameClass.GLGraphics;


public class Camera2D 
{
	public final ZeoVector position;
	public float zoom;
	public final float frustumWidth;
	public final float frustumHeight;
	final GLGraphics glGraphics;
	public Camera2D(GLGraphics glGraphics, float frustumWidth, float frustumHeight)
	{
		this.glGraphics = glGraphics;
		this.frustumHeight = frustumHeight;
		this.frustumWidth = frustumWidth;
		this.position = new ZeoVector(frustumWidth / 2, frustumHeight / 2);
		this.zoom = 1.0f;
	}
	public void setViewportAndMatrices()
	{
		GL10 gl = glGraphics.getGL();
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(position.x - frustumWidth * zoom / 2,
					position.x + frustumWidth * zoom / 2,
					position.y - frustumHeight * zoom / 2,
					position.y + frustumHeight * zoom / 2, 1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();	
	}
	public void touchToWorld(ZeoVector touch)
	{
		touch.x = (touch.x / (float) glGraphics.getWidth()) * frustumWidth * zoom; // координаты в масштабе мира
		touch.y = (1 - touch.y / (float) glGraphics.getHeight()) * frustumHeight * zoom; // координаты в масштабе мира
		
		touch.set((touch.x - frustumWidth / 2), (touch.y - frustumHeight / 2)); //координаты относительно центра мира
		touch.x = position.x + touch.x;// - frustumWidth / 2;
		touch.y = position.y + touch.y;// - frustumHeight / 2;
	}
}

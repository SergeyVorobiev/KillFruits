package com.vorobiev.gameClass;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
//import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.vorobiev.gameInterface.Game;

public class FirstTriangle extends ScreeN 
{
	private int VERTEX_SIZE  = (2 + 4) * 4;
	private float[] arrVert; 
	private GLGraphics glGraphics;
	private FloatBuffer vertices;
	private GL10 gl;
	//private Random rnd = new Random();
	public FirstTriangle(Game game) 
	{
		super(game);
		glGraphics = game.getGLGraphics();
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * VERTEX_SIZE);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertices = byteBuffer.asFloatBuffer();
		arrVert = new float[] { 0.0f, 0.0f, 1, 0, 0, 1,
								319.0f, 0.0f, 0, 1, 0, 1,
								160.0f, 479.0f, 0, 0, 1, 1};
		vertices.put(arrVert);
		vertices.flip();		
	}

	@Override
	public void update(float deltaTime) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void present(float deltaTime) 
	{
		gl = glGraphics.getGL(); //�������� gl
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);//�������� ����� �������� ������
		//gl.glColor4f(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1);//��������� ���� ������
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, vertices);//��������� ��� ����� ���������� �����
		vertices.position(2);
		gl.glColorPointer(4, GL10.GL_FLOAT, VERTEX_SIZE, vertices);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);//������ ����������� �� ������ �����������

	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() 
	{
		gl = glGraphics.getGL(); //�������� gl
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//��������� ��� ������� ����� ����������
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//��������� ��� ������� ����� �����
		
		gl.glClearColor(0, 0, 0, 1);//���������� ���� ������� ������
		gl.glViewport(0,  0, glGraphics.getWidth(), glGraphics.getHeight()); //���������� ������� ���������
		gl.glMatrixMode(GL10.GL_PROJECTION); // ������������� ������� �������� ��� ���������� ������
		gl.glLoadIdentity(); //������� ������� ��� ������������� ������� ��������
		gl.glOrthof(0, 320, 0, 480, 1, -1); //��������� ����� �����������	
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub

	}

}

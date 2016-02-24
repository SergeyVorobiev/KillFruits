package com.vorobiev.gameClass;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.vorobiev.gameInterface.FileIO;
import com.vorobiev.gameInterface.Game;

public class Texture 
{
	private GLGraphics glGraphics;
	private FileIO fileio;
	private String fileName;
	private int textureId;
	public int width;
	public int height;
	public Texture(Game glGame, String fileName, int width, int height)
	{
		this.glGraphics = glGame.getGLGraphics();
		this.fileio = glGame.getFileIO();
		this.fileName = fileName;
		this.width = width;
		this.height = height;
	}
	public void load()
	{
		GL10 gl = glGraphics.getGL();//�������� ��� ���
		int[] textureIds = new int[1];//������� ������  ������� �������
		gl.glGenTextures(1, textureIds, 0);//��������� ������ ������� � ������
		textureId = textureIds[0];//�������� ����� ������ �������� ��� ��������
		InputStream in = null;//
		try
		{
			in = fileio.readAsset(fileName);// �������� ����� ���� �� �����
			Bitmap bitmap = BitmapFactory.decodeStream(in);// ������������� ����� � ������
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//��������� ����� �������� ��� ��������������
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);//���������� �������� � �������� 
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);// ���������� �������
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);// ���������� �������
			gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);//�������� ������� ��������
			bitmap.recycle();// ������� �������� �� ������	
		}
		catch(IOException e)
		{
			throw new RuntimeException("s;dfjsdf;l,", e);
		}
		finally
		{
			if(in != null)
			{	try{in.close();}catch(IOException e){}}	
		}
	}
	public void setFilters(int minFilter, int magFilter)
	{
		//this.minFilter = minFilter;//
		//this.magFilter = magFilter;//
		GL10 gl = glGraphics.getGL();//
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);// ���������� �������
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);// ���������� �������
	}
	public void bind()
	{
		GL10 gl = glGraphics.getGL();//
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);	//
	}
	public void unbind()
	{
		GL10 gl = glGraphics.getGL();//
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);	//
	}
	public void dispose()
	{
		GL10 gl = glGraphics.getGL();//
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//
		int[] textureIds = {textureId};//
		gl.glDeleteTextures(1, textureIds, 0);//
	}
}


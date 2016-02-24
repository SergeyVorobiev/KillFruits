package com.vorobiev.gameClass;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Vertices 
{
	final GLGraphics glGraphics;
	final boolean hasColor;
	final boolean hasTexCoords;
	final int vertexSize;
	final IntBuffer vertices;
	final ShortBuffer indices;
	final int[] tempBuff;
	public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndices,
					boolean hasColor, boolean hasTexCoords)
	{
		this.glGraphics = glGraphics;//получить джи эль
		this.hasColor = hasColor;// используется ли цвет
		this.hasTexCoords = hasTexCoords;//используется ли текстура
		this.vertexSize = (2 + (hasColor? 4:0) + (hasTexCoords? 2:0)) * 4;//размер вершины
		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);//выделяем буфер под все вершины
		buffer.order(ByteOrder.nativeOrder());//преобразуем в родной буфер
		this.vertices = buffer.asIntBuffer();//преобразуем в тип флоат
		this.tempBuff = new int[maxVertices * vertexSize / 4];
		if(maxIndices > 0)
		{
			buffer = ByteBuffer.allocateDirect(maxIndices * 2);// получить массив индексов
			buffer.order(ByteOrder.nativeOrder());//преобразовать в родной массив
			indices = buffer.asShortBuffer();//преобразовать в шорт
		}
		else
		{
			indices = null;
		}
	}
	public void bind()
	{
		GL10 gl = glGraphics.getGL();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//координаты фигуры имеются
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT,  vertexSize,  vertices);//указываем где взять координаты
		if(hasColor)
		{
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//координаты цвета имеются
			vertices.position(2);//позиция
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);//указываем где взять координаты цвета
		}
		if(hasTexCoords)
		{
			
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//координаты текстур имеются
			vertices.position(hasColor? 6:2);//если есть координаты цвета позиции разные
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);//указываем где взять координаты текстур
		}
	}
	public void setVertices(float[] vertices, int offset, int length)
	{
		this.vertices.clear();
		int len = offset + length;
		for(int i = offset, j = 0; i < len; i++, j++)
			tempBuff[j] = Float.floatToRawIntBits(vertices[i]);
		this.vertices.put(tempBuff, 0, length);
		this.vertices.flip();
	}
	public void setIndices(short[] indices, int offset, int length)
	{
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}
	public void draw(int primitiveType, int offset, int numVertices)
	{
		GL10 gl = glGraphics.getGL();
		if(indices != null)
		{
			indices.position(offset);
			gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);// нарисовать фигуру
		}
		else
		{
			gl.glDrawArrays(primitiveType, offset, numVertices);//нарисовать фигуру
		}
		
		
	}
	public void unbind()
	{
		GL10 gl = glGraphics.getGL();
		if(hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		if(hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}

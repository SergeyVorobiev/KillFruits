package com.vorobiev.gameClass;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.LogPrinter;

import com.vorobiev.gameInterface.Game;
import com.vorobiev.gameInterface.Input.TouchEvent;
import com.vorobiev.printlog.FPSCounter;
import com.vorobiev.printlog.Print;

public class TextureTriangle extends ScreeN
{
	private int count = 0;
	private final int sizeBob = 20;
	private int orthofX = 720;
	private int orthofY = 1280;
	private GLGraphics glGraphics;
	//private Texture texture1;
	private Texture texture2;
	private GL10 gl;
	private Vertices vertices;
	private Bob[] bob;
	private FPSCounter fps = new FPSCounter();
	private Print print;
	private List<TouchEvent> touchEvents;
	public TextureTriangle(Game game) 
	{
		super(game);
		print = new Print(game);
		this.glGraphics = game.getGLGraphics();
		vertices = new Vertices(glGraphics, 4, 6, false, true);
		float rect[] = new float[] {   0.0f,     0.0f, 0.0f, 1.0f, //ЗАПОЛНЕНИЕ МАССИВА ЗНАЧЕНИЯМИ
								   Bob.bobX,     0.0f, 1.0f, 1.0f,
								   Bob.bobX, Bob.bobY, 1.0f, 0.0f,
									   0.0f, Bob.bobY, 0.0f, 0.0f};
		
		vertices.setVertices(rect, 0, rect.length); //ЗАПОЛНЕНИЕ МАССИВА ЗНАЧЕНИЯМИ
		vertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6); //}
		//texture1 = new Texture(game, "bob1.png");
		texture2 = new Texture(game, "bob2.png", 32, 32);
		bob = new Bob[sizeBob];
		for(int i = 0; i < sizeBob; i++)
			bob[i] = new Bob();
	}
	@Override
	public void update(float deltaTime) 
	{
		touchEvents = game.getInput().getTouchEvents();	
		for(int i = 0; i < touchEvents.size(); i++)
		{
			if(touchEvents.get(i).type == TouchEvent.TOUCH_DOWN)
			{
				for(int j = sizeBob - 1; j > -1; j--)
				{
					print.logPrint((int)((bob[j].x + bob[j].bobX / 2) - touchEvents.get(i).x));
					print.logPrint((int)((bob[j].y + bob[j].bobY / 2) - touchEvents.get(i).y));
					if(Math.abs((bob[j].x + bob[j].bobX / 2) - touchEvents.get(i).x) < 50 &&
					   Math.abs((bob[j].y + bob[j].bobY / 2) - touchEvents.get(i).y) < 50)
					{
						bob[j].live = false;
						break;
					}
				}
			}
		}
		for(int i = 0; i < sizeBob; i++)
		{
			if(bob[i].live == false)
				continue;
			bob[i].update(deltaTime);
		}	
	}

	@Override
	public void present(float deltaTime) 
	{
		gl = glGraphics.getGL(); // получаем объект джи эль		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);// производим очистку
		//gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);//строим треугольник	
		//vertices.bind();
		for(int i = 0; i < sizeBob; i++)
		{
			if(bob[i].live == false)
				continue;
			gl.glLoadIdentity();
			gl.glTranslatef(bob[i].x, bob[i].y, 0);
			//gl.glRotatef(bob[i].rotate, 0, 1, 0);
			vertices.draw(GL10.GL_TRIANGLES, 0, 6);
		}
	}
	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() 
	{
		gl = glGraphics.getGL(); // получаем объект джи эль
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight()); //указываем размер отображения
		gl.glClearColor(0, 0, 0, 1);// цвет очистки
		gl.glMatrixMode(GL10.GL_PROJECTION);// указываем матрицу проецирования
		gl.glLoadIdentity();//устанавливаем ее
		gl.glOrthof(0, orthofX, 0, orthofY, 1, -1);//определяем конус отображения
		gl.glEnable(GL10.GL_TEXTURE_2D);//активируем текстурирование
		texture2.load();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);	
		vertices.bind();
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		count++;
		print.setMessage(count);
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
		
	}

}

package com.vorobiev.gameClass;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.vorobiev.androidGame.gameDynObject2d.Bullet;
import com.vorobiev.androidGame.gameDynObject2d.Frutis;
import com.vorobiev.androidGame.gameDynObject2d.Line;
import com.vorobiev.androidGame.gameDynObject2d.Rocket;
import com.vorobiev.androidGame.gameDynObject2d.StaticFonts;
import com.vorobiev.androidGame.gameDynObject2d.Turel;
import com.vorobiev.androidGame.math.Camera2D;
import com.vorobiev.androidGame.math.ZeoVector;
import com.vorobiev.gameInterface.Game;
import com.vorobiev.gameInterface.Input.TouchEvent;
import com.vorobiev.printlog.Print;

public class CanonScreen extends ScreeN
{
	private float FRUSTUM_WIDTH = 7.2f; // установить длину мира
	private float FRUSTUM_HEIGHT = 12.8f;// установить высоту мира
	private GLGraphics glGraphics;
	private ZeoVector grab = new ZeoVector(0.0f, 0.0f);
	private ZeoVector vTouchPos = new ZeoVector(); //позиция касания
	private Texture atlasTexture; //текстуры
	private SpriteBatcher batcher;
	private Camera2D camera;
	private final int maxFructs = 10;
	private final int maxFontsScore = 10;
	private Frutis[] fructs = new Frutis[maxFructs];
	private StaticFonts[] fontsNum = new StaticFonts[maxFontsScore];
	private StaticFonts score;
	private StaticFonts bestScore;
	private Print print = new Print();
	private int gold = 0;
	private int deltaGold = 0;
	private float scale = 1.0f;
	private int baseLife = 10000;
	private Line redLine;
	private Line greenLine;
	private Turel turel, turel2;
	private List<Bullet> rocketToWorld = new ArrayList<Bullet>(100);
	public CanonScreen(Game game) 
	{
		super(game);
		this.glGraphics = game.getGLGraphics(); //получить объект gl
		atlasTexture = new Texture(game, "frutis.png", 1024, 1024);
		for(int i = 0; i < maxFructs; i++)
			fructs[i] = new Frutis(0.75f, 0.75f, 0.0f, atlasTexture);
		batcher = new SpriteBatcher(glGraphics, 100);
		camera = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		score = new StaticFonts(1.0f, 0.5f, 0.0f, new TextureRegion(atlasTexture, 64, 128, 128, 64));
		bestScore = new StaticFonts(2.0f, 0.5f, 0.0f, new TextureRegion(atlasTexture, 192, 128, 256, 64));
		redLine = new Line(6.0f, 0.2f, 0.0f, new TextureRegion(atlasTexture, 832, 0, 32, 32));
		greenLine = new Line(6.0f, 0.2f, 0.0f, new TextureRegion(atlasTexture, 864, 0, 32, 32));
		turel = new Turel(1.0f, 1.0f, 90.0f, new TextureRegion(atlasTexture, 896, 0, 64, 64), 
											new TextureRegion(atlasTexture, 832, 64, 64, 32), rocketToWorld);
		turel2 = new Turel(1.0f, 1.0f, 90.0f, new TextureRegion(atlasTexture, 896, 0, 64, 64), 
				new TextureRegion(atlasTexture, 832, 64, 64, 32), rocketToWorld);
		print.logPrint(1);
		int j;
		for(int i = 0; i < 10;i++)
		{
			j = 32 * i;
			fontsNum[i] = new StaticFonts(0.4f, 0.4f, 0.0f, new TextureRegion(atlasTexture, 0, j, 32, 32));
		}
	}

	@Override
	public void update(float deltaTime) 
	{
		touchHandler();	
		for(int i = 0; i < maxFructs; i++)
		{
			fructs[i].Go(deltaTime);
			if(fructs[i].state == Frutis.fruitState.LIFE)
			{
				if(fructs[i].position.y < 0)
				{
					baseLife -= fructs[i].coins;
					if(baseLife <= 0)
					{
						gold = 0;
						deltaGold = 0;
						baseLife = 10000;
						greenLine.setDefaultWidth();
					}
					greenLine.width = greenLine.defWidth * (float)baseLife / 10000.0f;
					fructs[i].randomGenerate();
				}
			}		
			else if(fructs[i].state == Frutis.fruitState.KILL)
			{
				if(fructs[i].position.x < 1.0f && fructs[i].position.y > 12.0f)
				{
					gold += fructs[i].coins;
					fructs[i].randomGenerate();		
				}
			}			
		}
		turel.update(fructs, deltaTime);
		turel2.update(fructs, deltaTime);
		int len = rocketToWorld.size();
		for(int i = 0; i < len; i++)
		{
			rocketToWorld.get(i).update(deltaTime);
			if(!rocketToWorld.get(i).isActive())
			{
				rocketToWorld.remove(i);
				--i;
				--len;
			}
		}
	}
	@Override
	public void present(float deltaTime) 
	{
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.setViewportAndMatrices();
		batcher.beginBatch(atlasTexture);
		for(int i = 0; i < maxFructs; i++)
			batcher.drawSprite(fructs[i].position.x, fructs[i].position.y, fructs[i].width, fructs[i].height, fructs[i].angle, fructs[i].texReg);
		batcher.drawSprite((camera.position.x - 3.0f), (camera.position.y + 6.2f), score.width, score.height, score.texReg);
		batcher.drawSprite((camera.position.x + 2.7f), (camera.position.y + 6.2f), bestScore.width, bestScore.height, bestScore.texReg);
		int len = rocketToWorld.size();
		for(int i = 0; i < len; i++)
		{
			Bullet bullet = rocketToWorld.get(i);
			batcher.drawSprite(bullet.getPositionX(), bullet.getPositionY(), bullet.getWidth(), bullet.getHeight(), bullet.getAngle(), bullet.getTexReg());
		}
		batcher.drawSprite(turel.position.x, turel.position.y, turel.width, turel.height, turel.angle, turel.texReg);
		batcher.drawSprite(turel.position.x, turel.position.y, turel.width, turel.height, turel.angle, turel.texReg);
		batcher.drawSprite(camera.position.x, (camera.position.y - 6.2f), redLine.width, redLine.height, redLine.texReg);
		batcher.drawSprite(camera.position.x, (camera.position.y - 6.2f), greenLine.width, greenLine.height, greenLine.texReg);
		drawString();
		batcher.endBatch();		
	}
	@Override
	public void resume() 
	{
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClearColor(0, 0, 0, 1);
		gl.glEnable(GL10.GL_TEXTURE_2D);//активируем текстурирование
		atlasTexture.load();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);			
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	private void touchHandler()
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++)
		{
			TouchEvent event = touchEvents.get(i);
			camera.touchToWorld(vTouchPos.set(event.x, event.y));
			if(event.type == TouchEvent.TOUCH_DOWN)
			{
				if(event.Id < 2)
				{
					checkTouchObject(event);
					//grab.set(event.x, event.y);	
				}
				event.type = TouchEvent.TOUCH_DRAGGED;
				
			}
			if(game.getInput().isTouchDown(i) == true && event.Id == 0)
			{
				//camera.position.x -= (event.x - grab.x) / 100;
				//camera.position.y += (event.y - grab.y) / 100;
				//grab.set(event.x, event.y);
			}
		}		
	}
	private void checkTouchObject(TouchEvent event)
	{
		for(int i = 0; i < maxFructs; i++)
		{
			if(fructs[i].state == Frutis.fruitState.LIFE || fructs[i].state == Frutis.fruitState.KILL)
			{
				if(Math.abs(fructs[i].position.x - vTouchPos.x) < fructs[i].width && Math.abs(fructs[i].position.y - vTouchPos.y) < fructs[i].height)
				{
					if(fructs[i].state == Frutis.fruitState.KILL && fructs[i].size < 4)
					{	
						fructs[i].coins *= 2;
						print.logPrint(fructs[i].coins);
						fructs[i].size += 1;
						fructs[i].width *= 1.5f;
						fructs[i].height *= 1.5f;
					}
					else
					{
						fructs[i].state = Frutis.fruitState.KILL;
						fructs[i].speedAngle = fructs[i].speedY * 2.0f;
						fructs[i].speedX = fructs[i].position.x / -1.5f;
						fructs[i].speedY = (FRUSTUM_HEIGHT - fructs[i].position.y) / -1.5f;
					}
				}
			}
			
		}
	}
	private void drawString()
	{
		int tempGold = CompGold();
		int num;
		float j;
		for(int i = 0; i < 7; i++)
		{
			num = tempGold % 10;
			tempGold /= 10;
			j = 0.2f * (6 - i) - 3.3f;
			batcher.drawSprite((camera.position.x + j), (camera.position.y + 5.8f), fontsNum[num].width * scale, fontsNum[num].height * scale, fontsNum[num].texReg);	
		}
	}
	private int CompGold()
	{
		if((gold - deltaGold) > 0)
			deltaGold += 1;
		if((gold - deltaGold) > 10)
			deltaGold += 10;
		if((gold - deltaGold) > 100)
			deltaGold += 100;
	
		return deltaGold;
	}
}

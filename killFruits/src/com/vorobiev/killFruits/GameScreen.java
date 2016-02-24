package com.example.testgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.vorobiev.androidGame.gameDynObject2d.BackgroundAnimation;
import com.vorobiev.androidGame.gameDynObject2d.Debris;
import com.vorobiev.androidGame.gameDynObject2d.Explosion;
import com.vorobiev.androidGame.gameDynObject2d.Fructs;
import com.vorobiev.androidGame.gameDynObject2d.Generator;
import com.vorobiev.androidGame.gameDynObject2d.JoltOfCamera;
import com.vorobiev.androidGame.gameDynObject2d.Line;
import com.vorobiev.androidGame.gameDynObject2d.Rocket;
import com.vorobiev.androidGame.gameDynObject2d.StaticFonts;
import com.vorobiev.androidGame.gameDynObject2d.Turel;
import com.vorobiev.androidGame.math.Camera2D;
import com.vorobiev.androidGame.math.ZeoVector;
import com.vorobiev.gameClass.Assets;
import com.vorobiev.gameClass.GLGraphics;
import com.vorobiev.gameClass.Pool;
import com.vorobiev.gameClass.Pool.PoolObjectFactory;
import com.vorobiev.gameClass.ScreeN;
import com.vorobiev.gameClass.SpriteBatcher;
import com.vorobiev.gameInterface.Game;
import com.vorobiev.gameInterface.Input.KeyEvent;
import com.vorobiev.gameInterface.Input.TouchEvent;
import com.vorobiev.printlog.FPSCounter;
import com.vorobiev.printlog.Print;
import com.vorobiev.upgradeFructs.Button;
import com.vorobiev.upgradeFructs.Label;
import com.vorobiev.upgradeFructs.Upgrade;
import com.vorobiev.upgradeFructs.UpgradeTurel;


public class GameScreen extends ScreeN
{
	private enum GameState {RUN, PAUSE1, PAUSE2};
	private GameState gameState = GameState.RUN;
	private float FRUSTUM_WIDTH = 7.2f; // установить длину мира
	private float FRUSTUM_HEIGHT = 12.8f;// установить высоту мира
	private Random rnd = new Random();
	private GLGraphics glGraphics;
	//private ZeoVector grab = new ZeoVector(0.0f, 0.0f);
	private ZeoVector vTouchPos = new ZeoVector(); //позиция касания
	private SpriteBatcher batcher; //отрисовщик
	private Camera2D camera; // камера
	private final int maxFructs = 250; // максимальное количество фруктов в мире
	private int startFruct = 10; 
	private StaticFonts[] fNum = new StaticFonts[10]; // желтые цифры
	private StaticFonts[] gNum = new StaticFonts[10]; // зеленые цифры
	private StaticFonts[] rNum = new StaticFonts[10];
	private StaticFonts[] yNum = new StaticFonts[10];
	private StaticFonts[] rSmNum = new StaticFonts[10];
	private StaticFonts[] ynNum = new StaticFonts[10];
	private StaticFonts score; // надпись очки
	private StaticFonts bestScore; //надпись лучший результат
	private StaticFonts target;
	private Print print = new Print();
	private FPSCounter fps = new FPSCounter();
	public int gold = 30000;
	private int deltaGold = 0;
	private float scale = 1.0f;
	private int baseLife = 10000;
	private Line redLine;
	private Line greenLine;
	private Turel turel;
	private Turel turel2;
	private Turel turel3;
	private Turel turel4;
	private Turel turel5;
	private Generator generator;
	private float timeGame = 0.0f;
	private boolean generat = false;
	private List<Fructs> listFructs = new ArrayList<Fructs>(maxFructs);
	private List<Rocket> listRocket = new ArrayList<Rocket>(2000);
	private List<Explosion> listExplosion = new ArrayList<Explosion>(2000);
	private List<Turel> listTurel = new ArrayList<Turel>(5);
	private List<Debris> listDebris = new ArrayList<Debris>(2000);
	private Upgrade upgrade = new Upgrade();
	private BackgroundAnimation backgroundAnim;
	private PoolObjectFactory<Explosion> factoryExplosion = new PoolObjectFactory<Explosion>()
	{
		@Override
		public Explosion CreatObject() 
		{
			return new Explosion(Assets.explosion, 0.25f, 0.25f, 0.2f);
		}
		
	};
	private PoolObjectFactory<Debris> factoryDebris = new PoolObjectFactory<Debris>()
	{
		@Override
		public Debris CreatObject() 
		{
			return new Debris(Assets.items, 32.0f, 32.0f);
		}
	};
	private PoolObjectFactory<Fructs> factoryFructs = new PoolObjectFactory<Fructs>()
			{
				@Override
				public Fructs CreatObject()
				{	
					return new Fructs(0.75f, 0.75f, 0.0f, 0.0f, 13.0f);
				}
		
			};
	private Pool<Explosion> poolExplosion = new Pool<Explosion>(factoryExplosion, 2000);
	private Pool<Debris> poolDebris = new Pool<Debris>(factoryDebris, 2000);
	private Pool<Fructs> poolFructs = new Pool<Fructs>(factoryFructs, 2000);
	private JoltOfCamera jOfCamera;
	private JoltOfCamera jSmallOfCamera;
	private UpgradeTurel upgradeTurel = new UpgradeTurel();
	public GameScreen(Game game) 
	{
		super(game);
		this.glGraphics = game.getGLGraphics(); //получить объект gl
		batcher = new SpriteBatcher(glGraphics, 10000);
		camera = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		jSmallOfCamera = new JoltOfCamera(2, 1.5f);
		jOfCamera = new JoltOfCamera(12, 1);
		Fructs.ArrTexReg = Assets.fructs;
		for(int i = 0; i < startFruct; i++)
			listFructs.add(poolFructs.newObject());
		score = new StaticFonts(1.0f, 0.5f, 0.0f, 0.0f, 0.0f, Assets.score);
		bestScore = new StaticFonts(2.0f, 0.5f, 0.0f, 0.0f, 0.0f, Assets.bestScore);
		target = new StaticFonts(2.0f, 0.4f, 0.0f, 0.0f, 0.0f, Assets.target);
		redLine = new Line(6.0f, 0.2f, 0.0f, 0.0f, 0.0f, Assets.redLine);
		greenLine = new Line(6.0f, 0.2f, 0.0f, 0.0f, 0.0f, Assets.greenLine);
		generator = new Generator(Assets.generator, Assets.electro, FRUSTUM_WIDTH, 9.0f, 2.0f);
		for(int i = 0; i < 10; i++)
		{
			fNum[i] = new StaticFonts(0.4f, 0.4f, 0.0f, 0.0f, 0.0f, Assets.numbers[i]);
			gNum[i] = new StaticFonts(0.3f, 0.3f, 0.0f, 0.0f, 0.0f, Assets.gNumbers[i]);
			yNum[i] = new StaticFonts(0.2f, 0.3f, 0.0f, 0.0f, 0.0f, Assets.yNumbers[i]);
			rNum[i] = new StaticFonts(0.5f, 0.3f, 0.0f, 0.0f, 0.0f, Assets.rNumbers[i]);
			rSmNum[i] = new StaticFonts(0.3f, 0.3f, 0.0f, 0.0f, 0.0f, Assets.rNumbers[i]);
			ynNum[i] = new StaticFonts(0.3f, 0.4f, 0.0f, 0.0f, 0.0f, Assets.yNumbers[i]);
		}
		backgroundAnim = new BackgroundAnimation(Assets.backgroundReg);
		backgroundAnim.timeUpdate = 0.04f;
	}

	@Override
	public void update(float deltaTime) 
	{
		if(gameState == GameState.RUN)
		{
			timeGame += deltaTime;
			updateJoltCamera(deltaTime);
			updateRocket(deltaTime);
			updateFructs(deltaTime);
			updateExplosion(deltaTime);
			updateDebris(deltaTime);
			touchHandler();	//отработка нажатий пользователя
			updateTurel(deltaTime);	
			returnFructLife();		
			if(generat)
				generator.update(deltaTime, listFructs);
			if(timeGame >= 6.0f)
			{
				timeGame = 0.0f;
				if(startFruct < maxFructs)
					startFruct += 1;
				listFructs.add(poolFructs.newObject());
			}
		}
		else
		{
			touchHandler();	//отработка нажатий пользователя
		}
		backgroundAnim.update(deltaTime);
	}
	private void updateJoltCamera(float deltaTime)
	{
		if(rnd.nextInt(2000) == 1463 && jSmallOfCamera.activate == false)
			jSmallOfCamera.activate = true;		
		if(jOfCamera.activate)
		{
			camera.position.x += jOfCamera.getOffsetX();
			camera.position.y += jOfCamera.getOffsetY();	
		}
		else if(jSmallOfCamera.activate)
		{
			camera.position.x += jSmallOfCamera.getOffsetX();
			camera.position.y += jSmallOfCamera.getOffsetY();
		}
		if(jSmallOfCamera.update(deltaTime))
		{
			jOfCamera.activate = true;
			int addFruct = startFruct;
			if(addFruct < 20)
				addFruct = 10;
			else if(addFruct > 20)
				addFruct = startFruct / 2;
			if((addFruct + startFruct) > maxFructs)
				addFruct = maxFructs - startFruct;
			for(int i = 0; i < addFruct; i++)
				listFructs.add(poolFructs.newObject());
		}
		jOfCamera.update(deltaTime);
	}
	private void updateFructs(float deltaTime)
	{
		int len = listFructs.size();
		for(int i = 0; i < len; i++)
		{
			Fructs f = listFructs.get(i);
			f.Go(deltaTime);
		}
		
	}
	private void updateExplosion(float deltaTime)
	{
		int len = listExplosion.size();
		for(int i = 0; i < len; i++)
		{
			Explosion exp = listExplosion.get(i);
			if(exp.update(deltaTime) == null)
			{
				exp.setDefault();
				listExplosion.remove(exp);
				poolExplosion.free(exp);
				--i; --len;
			}		
		}
	}
	private void updateRocket(float deltaTime)
	{
		int len = listRocket.size();
		for(int i = 0; i < len; i++)
		{
			Rocket r = listRocket.get(i);
			Explosion exp = r.update(deltaTime, poolExplosion, this);
			if(exp != null)
			{
				listExplosion.add(exp);
				listRocket.remove(i);
				--i;
				--len;
			}
			else if(exp == null && r.laser)
			{
				if(r.timeFly > r.maxTimeFly)
				{
					listRocket.remove(i);
					--i;
					--len;
				}
			}
		}
	}
	private void updateDebris(float deltaTime)
	{
		int len = listDebris.size();
		for(int i = 0; i < len; i++)
		{
			Debris deb = listDebris.get(i);
			deb.update(deltaTime);
			if(deb.isActive == false)
			{
				listDebris.remove(deb);
				poolDebris.free(deb);
				--len;
				--i;
			}
		}
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
				if(event.Id < 2 && vTouchPos.y <= 12.0f)
				{
					if(gameState == GameState.RUN)
						checkTouchFructs(event);
					else if(gameState == GameState.PAUSE1 && event.Id == 0)
						checkTouchTurel(event);
					else if(gameState == GameState.PAUSE2 && event.Id == 0)
						checkTouchBuy(event);
				}			
					//grab.set(event.x, event.y);	
				event.type = TouchEvent.TOUCH_DRAGGED;		
			}
			if(event.type == TouchEvent.TOUCH_UP)
			{
					if(gameState == GameState.PAUSE1 && event.Id == 0)
						checkTouchTurel(event);
					else if(gameState == GameState.PAUSE2 && event.Id == 0)
						checkTouchBuy(event);
			}
			//if(game.getInput().isTouchDown(i) == true && event.Id == 0)
			//{
				//camera.position.x -= (event.x - grab.x) / 100;
				//camera.position.y += (event.y - grab.y) / 100;
				//grab.set(event.x, event.y);
			//}
		}	
		List<KeyEvent> keyEvents = game.getInputKey().getKeyEvents();
		len = keyEvents.size();
		for(int i = 0; i < len; i++)
		{
			KeyEvent keyevent = keyEvents.get(i);
			print.logPrint(keyevent.keyCode);
			if(keyevent.keyCode == 82 && keyevent.type == keyevent.KEY_UP)
			{	
				if(gameState == GameState.RUN)
					gameState = GameState.PAUSE1;
				else if(gameState == GameState.PAUSE1)
					gameState = GameState.RUN;
				else if(gameState == GameState.PAUSE2)
					gameState = GameState.PAUSE1;
				if(gameState == GameState.PAUSE1)
					checkEnableButton1();
			}
			if(keyevent.keyCode == 4 && keyevent.type == keyevent.KEY_UP)
			{
				if(gameState == GameState.PAUSE1)
					gameState = GameState.RUN;
				else if(gameState == GameState.PAUSE2)
					gameState = GameState.PAUSE1;
				else if(gameState == GameState.RUN)
					game.Finish();	
			}
			//print.logPrint(keyevent.keyCode);
		}
	}
	private void checkTouchFructs(TouchEvent event)
	{
		int len = listFructs.size();
		for(int i = 0; i < len; i++)
		{
			Fructs f = listFructs.get(i);
			if(f.state == Fructs.fruitState.LIFE || f.state == Fructs.fruitState.KILL)
			{
				if(Math.abs(f.position.x - vTouchPos.x) < f.width && Math.abs(f.position.y - vTouchPos.y) < f.height)
				{
					if(f.state == Fructs.fruitState.KILL && f.size < 4)
					{	
						f.size += 1;
						if(f.position.x < 2.0f && f.position.y > 11.0f);
						else
						{
							f.coins *= 2;
							f.width *= 1.4f;
							f.height *= 1.4f;
						}
					}
					else if(f.state == Fructs.fruitState.LIFE)
					{
						f.isTarget = false;
						f.state = Fructs.fruitState.KILL;
						f.speedAngle = f.speedY * 2.5f;
						f.speedX = f.position.x / -1.2f;
						f.speedY = (FRUSTUM_HEIGHT - f.position.y) / -1.2f;
					}
				}
			}	
		}
	}
	private void returnFructLife()
	{
		int len = listFructs.size();
		for(int i = 0; i < len; i++)
		{
			Fructs f = listFructs.get(i);
			int key = 0;
			if(f.state == Fructs.fruitState.LIFE)
			{
				if(f.position.y < 0)
				{
					if(f.coins < 0)
						f.coins /= 15;
					baseLife -= f.coins;
					if(baseLife > 10000)
						baseLife = 10000;
					if(baseLife <= 0)
					{
						gold = 0;
						deltaGold = 0;
						baseLife = 10000;
						greenLine.setDefaultWidth();
					}
					greenLine.width = greenLine.defWidth * (float)baseLife / 10000.0f;
					key = 1;
				}
			}		
			else if(f.state == Fructs.fruitState.KILL)
			{
				if(f.position.x < 1.0f && f.position.y > 12.0f)
				{
					gold += f.coins;
					key = 1;
				}
			}
			else if(f.state == Fructs.fruitState.SHOT)
			{
				Debris deb = poolDebris.newObject();
				deb.set(f.texReg, f.position.x, f.position.y, f.angle, 0.75f, 0.75f, 1.0f);
				listDebris.add(deb);
				key = 1;
			}
			if(len > startFruct && key == 1)
			{
				listFructs.remove(f);
				--len;
				--i;
				f.randomGenerate();
				poolFructs.free(f);
			}
			else if(key == 1)
				f.randomGenerate();	
		}
	}
	private void updateTurel(float deltaTime)
	{
		int len = listTurel.size();
		for(int i = 0; i < len; i++)
		{
			Turel tur = listTurel.get(i);
			Rocket r = tur.update(listFructs, deltaTime);
			if(r != null)
				listRocket.add(r);
		}		
	}
	@Override
	public void present(float deltaTime) 
	{
		camera.setViewportAndMatrices();
		//fps.logFrame();
		batcher.beginBatch(Assets.background);
			batcher.drawSprite(camera.position.x, camera.position.y, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundReg);
		batcher.endBatch(Assets.background);
		
		if(gameState == GameState.RUN)
			paintGameRun();	
		else if(gameState == GameState.PAUSE1)
			paintGamePause1();
		else if(gameState == GameState.PAUSE2)
			paintGamePause2();
		float cameraX = camera.position.x;
		float cameraY = camera.position.y;
		batcher.beginBatch(Assets.items);
			batcher.drawSprite((cameraX - 3.0f), (cameraY + 6.2f), score.width, score.height, score.texReg);
			drawString((cameraX - 2.1f), (cameraY + 5.8f), fNum, CompGold());
		batcher.endBatch(Assets.items);	
	}
	@Override
	public void resume() 
	{
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClearColor(0, 0, 0, 1);
		gl.glEnable(GL10.GL_TEXTURE_2D);//активируем текстурирование
		Assets.items.load();
		Assets.background.load();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);			
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() 
	{
			
	}
	private void drawString(float posX, float posY, StaticFonts[] fNum, int num)
	{
		int temp;
		while(true)
		{
			float interval = fNum[0].width / 2.0f;
			temp = Math.abs(num % 10);
			num /= 10;
			batcher.drawSprite(posX, posY, fNum[temp].width * scale, fNum[temp].height * scale, fNum[temp].texReg);
			posX -= interval;
			if(num == 0)
				break;
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
		if((gold - deltaGold) > 1000)
			deltaGold += 1000;
		if((gold - deltaGold) < 0)
			deltaGold -= 1;
		if((gold - deltaGold) < -10)
			deltaGold -= 10;
		if((gold - deltaGold) < -100)
			deltaGold -= 100;
		if((gold - deltaGold) < -1000)
			deltaGold -= 1000;
		if (deltaGold < 0)
		{
			gold = 0;
			deltaGold = 0;
		}
		return deltaGold;
	}
	private void paintGameRun()
	{
		batcher.beginBatch(Assets.items);
		{
			float cameraX = camera.position.x;
			float cameraY = camera.position.y;
			int len = listFructs.size();
			for(int i = 0; i < len; i++)
			{
				Fructs f = listFructs.get(i);
				float temp = f.width / 2;
				batcher.drawSprite(f.position.x, f.position.y, f.width, f.height, f.angle, f.texReg);
				drawString(f.position.x  + temp, f.position.y + temp, ((f.coins > 0)?gNum:rSmNum), f.coins);
			}
			//batcher.drawSprite(camera.position.x, camera.position.y + 6.0f, FRUSTUM_WIDTH, 0.8f, Assets.backState);
			drawString(cameraX + 0.2f, cameraY + 5.9f, rNum, len);
		
			len = listExplosion.size();
			for(int i = 0; i < len; i++)
			{
				Explosion exp = listExplosion.get(i);
				batcher.drawSprite(exp.posX, exp.posY, exp.width, exp.height, exp.angle, exp.texReg);
			}
		
			len = listRocket.size();
			for(int i = 0; i < len; i++)
			{
				Rocket r = listRocket.get(i);
				batcher.drawSprite(r.position.x, r.position.y, r.width, r.height, r.angle, r.texReg);
			}
		
			len = listDebris.size();
			for(int i = 0; i < len; i++)
			{
				Debris d = listDebris.get(i);
				batcher.drawSprite(d.deb1.posX, d.deb1.posY, d.deb1.width, d.deb1.height, d.deb1.angle, d.deb1.texReg);
				batcher.drawSprite(d.deb2.posX, d.deb2.posY, d.deb2.width, d.deb2.height, d.deb2.angle, d.deb2.texReg);
				batcher.drawSprite(d.deb3.posX, d.deb3.posY, d.deb3.width, d.deb3.height, d.deb3.angle, d.deb3.texReg);
				batcher.drawSprite(d.deb4.posX, d.deb4.posY, d.deb4.width, d.deb4.height, d.deb4.angle, d.deb4.texReg);
			}
		
			len = listTurel.size();
			for(int i = 0; i < len; i++)
			{
				Turel t = listTurel.get(i);
				batcher.drawSprite(t.position.x, t.position.y, t.width, t.height, t.angle, t.texReg);
				drawString(t.position.x  + 0.5f, t.position.y - 0.5f, yNum, t.killFruct);
			}
			
			if(generat)
			{
				batcher.drawSprite(generator.gen1.posX, generator.gen1.posY, generator.gen1.width, generator.gen1.height, generator.gen1.angle, generator.gen1.texReg);
				batcher.drawSprite(generator.gen2.posX, generator.gen2.posY, generator.gen2.width, generator.gen2.height, generator.gen2.angle, generator.gen2.texReg);
				batcher.drawSprite(generator.gen3.posX, generator.gen3.posY, generator.gen3.width, generator.gen3.height, generator.gen3.angle, generator.gen3.texReg);
				batcher.drawSprite(generator.gen4.posX, generator.gen4.posY, generator.gen4.width, generator.gen4.height, generator.gen4.angle, generator.gen4.texReg);
				batcher.drawSprite(generator.electro1.posX, generator.electro1.posY, generator.electro1.width, generator.electro1.height, generator.electro1.texReg);
				batcher.drawSprite(generator.electro2.posX, generator.electro2.posY, generator.electro2.width, generator.electro2.height, generator.electro2.texReg);
			}
			batcher.drawSprite((cameraX - 3.0f), (cameraY + 6.2f), score.width, score.height, score.texReg);
			batcher.drawSprite(cameraX , (cameraY + 6.15f), target.width, target.height, target.texReg);
			batcher.drawSprite((cameraX + 2.7f), (cameraY + 6.2f), bestScore.width, bestScore.height, bestScore.texReg);
			batcher.drawSprite(cameraX, (cameraY - 6.2f), redLine.width, redLine.height, redLine.texReg);
			batcher.drawSprite(cameraX, (cameraY - 6.2f), greenLine.width, greenLine.height, greenLine.texReg);
		}
		batcher.endBatch(Assets.items);	
	}
	private void checkTouchTurel(TouchEvent event)
	{
		boolean pressed = game.getInput().isTouchDown(0);
		int len = upgrade.button.length;
		for(int i = 0; i < len; i++)
		{
			Button b = upgrade.button[i];
			if(Math.abs(vTouchPos.x - b.label.posX) < 1.0f && 
				Math.abs(vTouchPos.y - b.label.posY ) < 0.5f && 
				b.butEnable && pressed && b.butEnable)
			{
				if(gold >= b.cost)
					b.label.setTextureReg(Assets.touchBuy);	
				else	
					b.label.setTextureReg(Assets.touchWbBuy);
				b.press = true;
				break;
			}
			else if(b.press && b.butEnable && !pressed)
			{
				if(gold >= b.cost)
				{
					b.butEnable = false;
					gold = gold - b.cost;
					upgrade.listUpgrade.remove(b.label);
					upgrade.str[i].setTextureReg(Assets.touchToUpgrade);
					upgrade.tur[i].setTextureReg(upgrade.upTur[i].texReg);
					upgrade.isBuyTurel[i] = true;
					buyTurelYes(i);
					checkEnableButton1();
				}
				else
					b.label.setTextureReg(Assets.wbBuy);	
				b.press = false;
				break;
			}
		}
		for(int i = 0; i < 6; i++)
		{
			if(Math.abs(vTouchPos.x - upgrade.tur[i].posX) < 1.8f &&
				Math.abs(vTouchPos.y - upgrade.tur[i].posY) < 1.8f &&	upgrade.isBuyTurel[i] && !pressed)
			{
				gameState = GameState.PAUSE2;
				upgrade.touchTurel = i;
				checkEnableButton1();
				break;
			}
		}
	}
	private void checkTouchBuy(TouchEvent event)
	{
		int j = 0, k = 4; 
		List<Label> ll = upgradeTurel.listTurel1;
		if(upgrade.touchTurel == Upgrade.TUREL1)
		{
			
		}
		else if(upgrade.touchTurel == Upgrade.TUREL2)
			{j = 4; k = 8; ll = upgradeTurel.listTurel2;}
		else if(upgrade.touchTurel == Upgrade.TUREL3)
			{j = 8; k = 12; ll = upgradeTurel.listTurel3;}	
		else if(upgrade.touchTurel == Upgrade.TUREL4)
			{j = 12; k = 15; ll = upgradeTurel.listTurel4;}
		else if(upgrade.touchTurel == Upgrade.TUREL5)
			{j = 15; k = 16; ll = upgradeTurel.listTurel5;}
		else
			{j = 16; k = 17; ll = upgradeTurel.listTurel6;}
		boolean pressed = game.getInput().isTouchDown(0);
		for(int i = j; i < k; i++)
		{
			Button b = upgradeTurel.button[i];
			if(Math.abs(vTouchPos.x - b.label.posX) < 0.8f && 
				Math.abs(vTouchPos.y - b.label.posY ) < 0.4f && 
				b.butEnable && pressed)
			{
				if(gold >= b.cost)
					b.label.setTextureReg(Assets.touchBuy);	
				else	
					b.label.setTextureReg(Assets.touchWbBuy);
				b.press = true;
				break;
			}
			else if(b.press && b.butEnable && !pressed)
			{
				if(gold >= b.cost)
				{
					b.level += 1;
					if(b.level <= b.maxLevel)
					{
						ll.add(upgradeTurel.star[i][b.level - 1]);
						gold = gold - b.cost;
						upgrade.iUpTur[upgrade.touchTurel] += 1;
						setUpgradeTurel(upgrade.touchTurel);
					}
					if(b.level == b.maxLevel)
					{
						b.butEnable = false;
						ll.remove(b.label);
						if(upgrade.iUpTur[upgrade.touchTurel] == upgrade.maxUpTur[upgrade.touchTurel])
							upgrade.str[upgrade.touchTurel].setTextureReg(Assets.max);	
					}
					checkEnableButton1();
				}
				else
					b.label.setTextureReg(Assets.wbBuy);	
				b.press = false;
				break;
			}
		}
	}
	private void paintGamePause1()
	{
		batcher.beginBatch(Assets.items);
		{
			int len = upgrade.listUpgrade.size();
			for(int i = 0; i < len; i++)	
			{
				Label l = upgrade.listUpgrade.get(i);
				batcher.drawSprite(l.posX, l.posY, l.width, l.height, l.texReg);
			}
		}
		batcher.endBatch(Assets.items);	
	}
	private void checkEnableButton1()
	{
		if(gameState == GameState.PAUSE1)
		{
			int len2 = upgrade.button.length;
			for(int j = 0; j < len2; j++)
			{
				Button b = upgrade.button[j];
				if(b.butEnable)
				{
					if(gold >= b.cost)
						b.label.setTextureReg(Assets.buy);
					else
						b.label.setTextureReg(Assets.wbBuy);
				}
			}
		}
		else if(gameState == GameState.PAUSE2)
		{
			int len2 = upgradeTurel.button.length;
			for(int j = 0; j < len2; j++)
			{
				Button b = upgradeTurel.button[j];
				if(b.butEnable)
				{
					if(gold >= b.cost)
						b.label.setTextureReg(Assets.buy);
					else
						b.label.setTextureReg(Assets.wbBuy);
				}
			}
		}
	}
	private void buyTurelYes(int i)
	{
		if(i == 0)
		{
			listTurel.add(turel = new Turel(1.0f, 1.0f, 90.0f, 5.0f, 1.0f, Assets.turel1, Assets.kugel, 0.1f, 0.1f));
			turel.setFullParameters(0.04f, 20, 100, 10, 6.0f);
		}
		else if(i == 1)
		{
			listTurel.add(turel2 = new Turel(1.0f, 1.0f, 90.0f, 2.2f, 1.0f, Assets.turel1, Assets.kugel,  0.1f, 0.1f));
			turel2.setFullParameters(0.04f, 20, 100, 10, 6.0f);
		}
		else if(i == 2)
		{
			listTurel.add(turel3 = new Turel(1.0f, 1.0f, 90.0f, 3.6f, 1.0f, Assets.rTurel, Assets.rocket1, 0.3f, 0.15f));
			turel3.setFullParameters(0.08f, 20, 50, 40, 6.0f);
		}
		else if(i == 3)
		{
			listTurel.add(turel4 = new Turel(1.0f, 1.0f, 90.0f, 6.4f, 1.0f, Assets.rTurel2, Assets.rocket2, 0.6f, 0.3f));
			turel4.setFullParameters(0.32f, 20, 50, 0, 6.0f);
		}
		else if(i == 4)
		{
			listTurel.add(turel5 = new Turel(1.0f, 1.0f, 90.0f, 0.8f, 1.0f, Assets.lTurel, Assets.bLaser, 14.0f, 0.5f));
			turel5.setFullParameters(0.5f, 1, 700, 0, 13.0f);
		}
		else if(i == 5)
		{
			generat = true;
		}
	}
	private void paintGamePause2()
	{
		int len = 0, j = 0, k = 4;
		List<Label> ll;
		if(upgrade.touchTurel == Upgrade.TUREL1)
		{
			len = upgradeTurel.listTurel1.size();
			ll = upgradeTurel.listTurel1;
		}
		else if(upgrade.touchTurel == Upgrade.TUREL2)
		{
			len = upgradeTurel.listTurel2.size();
			ll = upgradeTurel.listTurel2;
			j = 4; k = 8;
		}
		else if(upgrade.touchTurel == Upgrade.TUREL3)
		{
			len = upgradeTurel.listTurel3.size();
			ll = upgradeTurel.listTurel3;
			j = 8; k = 12;
		}
		else if(upgrade.touchTurel == Upgrade.TUREL4)
		{
			len = upgradeTurel.listTurel4.size();
			ll = upgradeTurel.listTurel4;
			j = 12; k = 15;
		}
		else if(upgrade.touchTurel == Upgrade.TUREL5)
		{
			len = upgradeTurel.listTurel5.size();
			ll = upgradeTurel.listTurel5;
			j = 15; k = 16;
		}
		else
		{
			len = upgradeTurel.listTurel6.size();
			ll = upgradeTurel.listTurel6;
			j = 16; k = 17;
		}
		batcher.beginBatch(Assets.items);
		{
			for(int i = 0; i < len; i++)	
			{
				Label l = ll.get(i);
				batcher.drawSprite(l.posX, l.posY, l.width, l.height, l.texReg);
			}
			for(int i = j; i < k; i++)
			{
				Button b = upgradeTurel.button[i];
				if(b.butEnable)
					drawString(b.label.posX + 0.5f, b.label.posY + 0.5f, ynNum, b.cost);
			}
		}
		batcher.endBatch(Assets.items);	
	}
	private void setUpgradeTurel(int tur)
	{
		
	}
}

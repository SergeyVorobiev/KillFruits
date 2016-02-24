package com.example.testgl;

import com.vorobiev.gameClass.Assets;
import com.vorobiev.gameClass.ScreeN;
import com.vorobiev.gameClass.Texture;
import com.vorobiev.gameClass.TextureRegion;
import com.vorobiev.gameInterface.Game;

public class LoadingScreen extends ScreeN
{
	private Game glGame;
	public LoadingScreen(Game game) 
	{
		super(game);
		this.glGame = game;
	}

	@Override
	public void update(float deltaTime) 
	{
		Assets.mainMenu = new Texture(glGame, "picture/mainMenuScr.png", 512, 1024);
		Assets.mainMenuReg = new TextureRegion(Assets.mainMenu, 0, 0, 512, 1024);
		Assets.loadingScr = new Texture(glGame, "picture/loadingScr.png", 512, 1024);
		Assets.loadingScrReg = new TextureRegion(Assets.loadingScr, 0, 1024, 512, 1024);
		Assets.background = new Texture(glGame, "picture/back2.png", 512, 2048);
		Assets.backgroundReg = new TextureRegion(Assets.background, 0, 0, 512, 1024);
		Assets.items = new Texture(glGame, "picture/fructs.png", 1024, 1024);
		Texture texIt = Assets.items;
		Assets.space = new TextureRegion(texIt, 992, 0, 32, 32);
		Assets.level = new TextureRegion(texIt, 128, 384, 256, 32);
		Assets.rate = new TextureRegion(texIt, 128, 416, 256, 32);
		Assets.ammu = new TextureRegion(texIt, 128, 448, 256, 32);
		Assets.speed = new TextureRegion(texIt, 128, 480, 256, 32);
		Assets.strong = new TextureRegion(texIt, 128, 512, 256, 32);
		Assets.touchToUpgrade = new TextureRegion(texIt, 128, 544, 256, 64);
		Assets.max = new TextureRegion(texIt, 128, 608, 256, 64);
		Assets.cost100000 = new TextureRegion(texIt, 128, 672, 256, 64);
		Assets.wbTurel1 = new TextureRegion(texIt, 512, 384, 64, 64);
		Assets.wbTurel2 = new TextureRegion(texIt, 576, 384, 64, 64);
		Assets.wbTurel3 = new TextureRegion(texIt, 640, 384, 64, 64);
		Assets.wbTurel4 = new TextureRegion(texIt, 702, 384, 64, 64);
		Assets.generate = new TextureRegion(texIt, 768, 384, 64, 64);
		Assets.wbGenerate = new TextureRegion(texIt, 832, 384, 64, 64);
		Assets.star = new TextureRegion(texIt, 768, 448, 64, 64);
		Assets.buy = new TextureRegion(texIt, 512, 448, 128, 64);
		Assets.wbBuy = new TextureRegion(texIt, 640, 448, 128, 64);
		Assets.touchWbBuy = new TextureRegion(texIt, 640, 512, 128, 64);
		Assets.touchBuy = new TextureRegion(texIt, 512, 512, 128, 64);
		Assets.backState = new TextureRegion(texIt, 128, 384, 256, 64);
		Assets.electro = new TextureRegion(texIt, 512, 320, 256, 64);
		Assets.generator = new TextureRegion(texIt, 672, 160, 32, 32);
		Assets.bestScore = new TextureRegion(texIt, 192, 128, 256, 64);
		Assets.score = new TextureRegion(texIt, 64, 128, 128, 64);
		Assets.target = new TextureRegion(texIt, 128, 320, 256, 64);
		Assets.redLine = new TextureRegion(texIt, 832, 0, 32, 32);
		Assets.greenLine = new TextureRegion(texIt, 864, 0, 32, 32);
		Assets.turel1 = new TextureRegion(texIt, 896, 0, 64, 64);
		Assets.rTurel = new TextureRegion(texIt, 960, 64, 64, 64);
		Assets.rTurel2 = new TextureRegion(texIt, 448, 128, 64, 64);
		Assets.lTurel = new TextureRegion(texIt, 512, 128, 64, 64);
		Assets.rLaser = new TextureRegion(texIt, 578, 128, 32, 32);
		Assets.yLaser = new TextureRegion(texIt, 610, 128, 32, 32);
		Assets.bLaser = new TextureRegion(texIt, 642, 128, 32, 32);
		Assets.explosion = new TextureRegion[3];
		for(int i = 0; i < 3; i++)
			Assets.explosion[i] = new TextureRegion(texIt, (578 + i * 32), 160, 32, 32);
		Assets.rocket1 = new TextureRegion(texIt, 832, 64, 64, 32);
		Assets.rocket2 = new TextureRegion(texIt, 832, 96, 64, 32);
		
		Assets.kugel = new TextureRegion(texIt, 960, 0, 32, 32);
		Assets.fructs = new TextureRegion[31];
		TextureRegion[] fruct = Assets.fructs;
		int k = 0;
		for(int j = 0; j < 2; j++)
			for(int i = 1; i < 13; i++)
				fruct[k++] = new TextureRegion(texIt, 64 * i, 64 * j, 64, 64);
		for(int i = 0; i < 7; i++)
			fruct[i + 24] = new TextureRegion(texIt, 128 + i * 64, 192, 64, 64);
		Assets.numbers = new TextureRegion[10];
		Assets.gNumbers = new TextureRegion[10];
		Assets.rNumbers = new TextureRegion[10];
		Assets.yNumbers = new TextureRegion[10];
		TextureRegion[] number = Assets.numbers;
		TextureRegion[] gNumber = Assets.gNumbers;
		TextureRegion[] rNumber = Assets.rNumbers;
		TextureRegion[] yNumber = Assets.yNumbers;
		for(int i = 0; i < 10; i++)
		{
			number[i] = new TextureRegion(texIt, 0, 32 * i, 32, 32);
			gNumber[i] = new TextureRegion(texIt, 32, 32 * i, 32, 32);
			rNumber[i] = new TextureRegion(texIt, 64, 32 * i + 192, 32, 32);
			yNumber[i] = new TextureRegion(texIt, 96, 32 * i + 192, 32 ,32);
			
		}
		game.setScreen(new MenuScreen(glGame));
	}
	
	@Override
	public void present(float deltaTime) 
	{
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}

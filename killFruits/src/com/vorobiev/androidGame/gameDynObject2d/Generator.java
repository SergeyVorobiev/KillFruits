package com.vorobiev.androidGame.gameDynObject2d;

import java.util.List;

import com.vorobiev.androidGame.gameDynObject2d.Fructs.fruitState;
import com.vorobiev.gameClass.TextureRegion;

public class Generator 
{
	public class Gen
	{
		public float width;
		public float height;
		public float posX;
		public float posY;
		public float angle;
		public TextureRegion texReg;
		public Gen(float width, float height, float posX, float posY, float angle, TextureRegion texReg)
		{
			this.width = width;
			this.height = height;
			this.posX = posX;
			this.posY = posY;
			this.angle = angle;
			this.texReg = texReg;
		}
	}
	public class Electro
	{
		public float width;
		public float height;
		public float posX;
		public float posY;
		public float angle;
		public TextureRegion texReg;
		public float delta = 0.0f;
		public Electro(float width, float height, float posX, float posY, float angle, TextureRegion texReg)
		{
			this.width = width;
			this.height = height;
			this.posX = posX;
			this.posY = posY;
			this.angle = angle;
			this.texReg = texReg;
		}
	}
	public Gen gen1;
	public Gen gen2;
	public Gen gen3;
	public Gen gen4;
	public Electro electro1;
	public Electro electro2;
	private int level = 3;
	private float mod1 = 1.1f;
	private float mod2 = 2;
	public Generator(TextureRegion texGen, TextureRegion texElectro, float world_width, float posY, float height)
	{
		gen1 = new Gen(0.5f, 0.5f, 0.25f, posY + height / 2, 180.0f, texGen);
		gen2 = new Gen(0.5f, 0.5f, 0.25f, posY - height / 2, 180.0f, texGen);
		gen3 = new Gen(0.5f, 0.5f, world_width - 0.25f, posY + height / 2, 0.0f, texGen);
		gen4 = new Gen(0.5f, 0.5f, world_width - 0.25f, posY - height / 2, 0.0f, texGen);
		electro1 = new Electro(world_width - 1.0f, 0.25f, world_width / 2, gen1.posY, 0.0f, texElectro);
		electro2 = new Electro(world_width - 1.0f, 0.25f, world_width / 2, gen2.posY, 0.0f, texElectro);
		for(int i = 1; i < level; i++)
		{
			mod1 *= mod1;
			mod2 *= mod2;
		}
	}
	public void update(float deltaTime, List<Fructs> lFr)
	{
		if(electro1.delta < 256)
			electro1.delta += 8;
		else
			electro1.delta = 0.0f;
		electro1.texReg.set(512 + electro1.delta, 320);
		int len = lFr.size();
		for(int i = 0; i < len; i++)
		{
			Fructs f = lFr.get(i);
			if(f.position.y < gen1.posY && f.position.y > gen4.posY && f.size == 1 && f.state == fruitState.LIFE && f.modified == false && f.coins > 0)
			{
				f.size += level;
				f.modified = true;
				f.coins *= mod2;
				f.width /= mod1;
				f.height /= mod1;
			}
			if(f.position.y < gen4.posY && f.state == fruitState.LIFE && f.modified && f.coins > 0)
			{
				f.modified = false;
				f.coins /= mod2;
				f.width *= mod1;
				f.height *= mod1;
				f.size = 1;
			}
		}
		
	}
}

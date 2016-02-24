package com.vorobiev.upgradeFructs;

public class Button 
{
	public int level = 0;
	public int maxLevel = 0;
	public float multiple = 1.1f;
	public int cost = 0;
	public boolean butEnable = true;
	public Label label;
	public boolean press = false;
	public Button(int cost)
	{
		this.cost = cost;
	}
	public Button(int cost, int maxLevel)
	{
		this.cost = cost;
		this.maxLevel = maxLevel;
	}
}

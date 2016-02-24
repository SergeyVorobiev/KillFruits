package com.vorobiev.upgradeFructs;

import java.util.ArrayList;
import java.util.List;

import com.vorobiev.gameClass.Assets;

public class UpgradeTurel
{
	public Label picture[] = new Label[6]; 
	public Label rate = new Label(1.0f, 8.0f, 2.0f, 0.5f, Assets.rate);
	public Label level = new Label(1.0f, 8.0f, 2.0f, 0.5f, Assets.level);
	public Label ammu = new Label(1.0f, 6.5f, 2.0f, 0.5f, Assets.ammu);
	public Label speed = new Label(1.0f, 5.0f, 2.0f, 0.5f, Assets.speed);
	public Label strong = new Label(1.0f, 3.5f, 2.0f, 0.5f, Assets.strong);
	public Label star[][] = new Label[17][6];
	
	public List<Label> listTurel1 = new ArrayList<Label>(30);
	public List<Label> listTurel2 = new ArrayList<Label>(30);
	public List<Label> listTurel3 = new ArrayList<Label>(30);
	public List<Label> listTurel4 = new ArrayList<Label>(30);
	public List<Label> listTurel5 = new ArrayList<Label>(30);
	public List<Label> listTurel6 = new ArrayList<Label>(30);
	public Button button[] = new Button[17];
	public Label buy[] = new Label[4];
	public UpgradeTurel()
	{
		int i = 0;
		listTurel1.add(picture[i++]= new Label(3.6f, 10.5f, 1.8f, 1.8f, Assets.turel1));
		listTurel2.add(picture[i++]= new Label(3.6f, 10.5f, 1.8f, 1.8f, Assets.turel1));
		listTurel3.add(picture[i++]= new Label(3.6f, 10.5f, 1.8f, 1.8f, Assets.rTurel));
		listTurel4.add(picture[i++]= new Label(3.6f, 10.5f, 1.8f, 1.8f, Assets.rTurel2));
		listTurel5.add(picture[i++]= new Label(3.6f, 10.5f, 1.8f, 1.8f, Assets.lTurel));
		listTurel6.add(picture[i++]= new Label(3.6f, 10.5f, 1.8f, 1.8f, Assets.generate));
		startPriceButton();
		for(i = 0; i < 4; i++)
			button[i].label = new Label(6.2f, 8.0f - i * 1.5f, 1.5f, 0.7f, Assets.wbBuy);
		for(i = 0; i < 4; i++)
			button[i + 4].label = new Label(6.2f, 8.0f - i * 1.5f, 1.5f, 0.7f, Assets.wbBuy);
		for(i = 0; i < 4; i++)
			button[i + 8].label = new Label(6.2f, 8.0f - i * 1.5f, 1.5f, 0.7f, Assets.wbBuy);
		for(i = 0; i < 3; i++)
			button[i + 12].label = new Label(6.2f, 8.0f - i * 1.5f, 1.5f, 0.7f, Assets.wbBuy);
		for(i = 0; i < 2; i++)
			button[i + 15].label = new Label(6.2f, 8.0f, 1.5f, 0.7f, Assets.wbBuy);
		listTurel1.add(rate);
		listTurel1.add(ammu);
		listTurel1.add(speed);
		listTurel1.add(strong);
		listTurel1.add(button[0].label);
		listTurel1.add(button[1].label);
		listTurel1.add(button[2].label);
		listTurel1.add(button[3].label);
		listTurel2.add(rate);
		listTurel2.add(ammu);
		listTurel2.add(speed);
		listTurel2.add(strong);
		listTurel2.add(button[4].label);
		listTurel2.add(button[5].label);
		listTurel2.add(button[6].label);
		listTurel2.add(button[7].label);
		listTurel3.add(rate);
		listTurel3.add(ammu);
		listTurel3.add(speed);
		listTurel3.add(strong);
		listTurel3.add(button[8].label);
		listTurel3.add(button[9].label);
		listTurel3.add(button[10].label);
		listTurel3.add(button[11].label);
		listTurel4.add(rate);
		listTurel4.add(ammu);
		listTurel4.add(speed);
		listTurel4.add(button[12].label);
		listTurel4.add(button[13].label);
		listTurel4.add(button[14].label);
		listTurel5.add(rate);
		listTurel5.add(button[15].label);
		listTurel6.add(level);
		listTurel6.add(button[16].label);
		for(i = 0; i < 6; i++)
		{
			star[0][i] = new Label(2.5f + i * 0.5f, 8.0f, 0.5f, 0.5f, Assets.star);
			star[1][i] = new Label(2.5f + i * 0.5f, 6.5f, 0.5f, 0.5f, Assets.star);
			star[2][i] = new Label(2.5f + i * 0.5f, 5.0f, 0.5f, 0.5f, Assets.star);
			star[3][i] = new Label(2.5f + i * 0.5f, 3.5f, 0.5f, 0.5f, Assets.star);
			star[4][i] = new Label(2.5f + i * 0.5f, 8.0f, 0.5f, 0.5f, Assets.star);
			star[5][i] = new Label(2.5f + i * 0.5f, 6.5f, 0.5f, 0.5f, Assets.star);
			star[6][i] = new Label(2.5f + i * 0.5f, 5.0f, 0.5f, 0.5f, Assets.star);
			star[7][i] = new Label(2.5f + i * 0.5f, 3.5f, 0.5f, 0.5f, Assets.star);
			star[8][i] = new Label(2.5f + i * 0.5f, 8.0f, 0.5f, 0.5f, Assets.star);
			star[9][i] = new Label(2.5f + i * 0.5f, 6.5f, 0.5f, 0.5f, Assets.star);
			star[10][i] = new Label(2.5f + i * 0.5f, 5.0f, 0.5f, 0.5f, Assets.star);
			star[11][i] = new Label(2.5f + i * 0.5f, 3.5f, 0.5f, 0.5f, Assets.star);
			star[12][i] = new Label(2.5f + i * 0.5f, 8.0f, 0.5f, 0.5f, Assets.star);
			star[13][i] = new Label(2.5f + i * 0.5f, 6.5f, 0.5f, 0.5f, Assets.star);
			star[14][i] = new Label(2.5f + i * 0.5f, 5.0f, 0.5f, 0.5f, Assets.star);
			star[15][i] = new Label(2.5f + i * 0.5f, 8.0f, 0.5f, 0.5f, Assets.star);
			star[16][i] = new Label(2.5f + i * 0.5f, 8.0f, 0.5f, 0.5f, Assets.star);
		}
		
	}
	private void startPriceButton()
	{
			button[0] = new Button(5000, 6);
			button[1] = new Button(5000, 6);
			button[2] = new Button(5000, 6);
			button[3] = new Button(5000, 6);
			button[4] = new Button(5000, 6);
			button[5] = new Button(5000, 6);
			button[6] = new Button(5000, 6);
			button[7] = new Button(5000, 6);
			button[8] = new Button(5000, 6);
			button[9] = new Button(5000, 6);
			button[10] = new Button(5000, 6);
			button[11] = new Button(5000, 6);
			button[12] = new Button(5000, 6);
			button[13] = new Button(5000, 6);
			button[14] = new Button(5000, 6);
			button[15] = new Button(5000, 6);
			button[16] = new Button(5000, 3);
	}
}

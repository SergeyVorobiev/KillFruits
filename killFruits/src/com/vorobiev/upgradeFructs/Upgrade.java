package com.vorobiev.upgradeFructs;

import java.util.ArrayList;
import java.util.List;

import com.vorobiev.gameClass.Assets;

public class Upgrade 
{
	public static final int TUREL1 = 0;
	public static final int TUREL2 = 1;
	public static final int TUREL3 = 2;
	public static final int TUREL4 = 3;
	public static final int TUREL5 = 4;
	public static final int TUREL6 = 5;
	public final int maxUpTur[] = new int [6]; 
	public final int iUpTur[] = new int [6];
	public int touchTurel;
	public List<Label> listUpgrade = new ArrayList<Label>(18);
	public Button[] button = new Button[6];
	public Label[] str = new Label[6];
	public Label[] tur = new Label[6];
	public Label[] upTur = new Label[6];
	public boolean[] isBuyTurel = new boolean[6];
	public Upgrade()
	{
		maxUpTur[0] = 24;
		maxUpTur[1] = 24;
		maxUpTur[2] = 24;
		maxUpTur[3] = 18;
		maxUpTur[4] = 6;
		maxUpTur[5] = 3;
		for(int i = 0; i < 6; i++)
		{
			isBuyTurel[i] = false;
			iUpTur[i] = 0;
		}
		int i = 0;
		listUpgrade.add(tur[i++] = new Label(1.25f, 11.0f, 1.8f, 1.8f, Assets.wbTurel1));
		listUpgrade.add(tur[i++] = new Label(3.6f, 11.0f, 1.8f, 1.8f, Assets.wbTurel1));
		listUpgrade.add(tur[i++] = new Label(5.95f, 11.0f, 1.8f, 1.8f, Assets.wbTurel2));
		listUpgrade.add(tur[i++] = new Label(1.25f, 5.0f, 1.8f, 1.8f, Assets.wbTurel3));
		listUpgrade.add(tur[i++] = new Label(3.6f, 5.0f, 1.8f, 1.8f, Assets.wbTurel4));
		listUpgrade.add(tur[i++] = new Label(5.95f, 5.0f, 1.8f, 1.8f, Assets.wbGenerate));
		i = 0;
		upTur[i++] = new Label(1.25f, 11.0f, 1.8f, 1.8f, Assets.turel1);
		upTur[i++] = new Label(3.6f, 11.0f, 1.8f, 1.8f, Assets.turel1);
		upTur[i++] = new Label(5.95f, 11.0f, 1.8f, 1.8f, Assets.rTurel);
		upTur[i++] = new Label(1.25f, 5.0f, 1.8f, 1.8f, Assets.rTurel2);
		upTur[i++] = new Label(3.6f, 5.0f, 1.8f, 1.8f, Assets.lTurel);
		upTur[i++] = new Label(5.95f, 5.0f, 1.8f, 1.8f, Assets.generate);
		i = 0;
		listUpgrade.add(str[i++] = new Label(1.25f, 9.6f, 2.0f, 1.0f, Assets.cost100000));
		listUpgrade.add(str[i++] = new Label(3.6f, 9.6f, 2.0f, 1.0f, Assets.cost100000));
		listUpgrade.add(str[i++] = new Label(5.95f, 9.6f, 2.0f, 1.0f, Assets.cost100000));
		listUpgrade.add(str[i++] = new Label(1.25f, 3.6f, 2.0f, 1.0f, Assets.cost100000));
		listUpgrade.add(str[i++] = new Label(3.6f, 3.6f, 2.0f, 1.0f, Assets.cost100000));
		listUpgrade.add(str[i++] = new Label(5.95f, 3.6f, 2.0f, 1.0f, Assets.cost100000));
		
		for(int j = 0; j < 6; j++)
			button[j] = new Button(5000);
		i = 0;
		listUpgrade.add(button[i++].label = new Label(1.25f, 8.6f, 2.0f, 1.0f, Assets.wbBuy));
		listUpgrade.add(button[i++].label = new Label(3.6f, 8.6f, 2.0f, 1.0f, Assets.wbBuy));
		listUpgrade.add(button[i++].label = new Label(5.95f, 8.6f, 2.0f, 1.0f, Assets.wbBuy));
		listUpgrade.add(button[i++].label = new Label(1.25f, 2.6f, 2.0f, 1.0f, Assets.wbBuy));
		listUpgrade.add(button[i++].label = new Label(3.6f, 2.6f, 2.0f, 1.0f, Assets.wbBuy));
		listUpgrade.add(button[i++].label = new Label(5.95f, 2.6f, 2.0f, 1.0f, Assets.wbBuy));	
	}
	
}

package com.vorobiev.gameInterface;

import com.vorobiev.gameClass.GLGraphics;
import com.vorobiev.gameClass.ScreeN;

public interface Game 
{
	Input getInput();
	Input getInputKey();
	FileIO getFileIO();
	GLGraphics getGLGraphics();
	void setScreen(ScreeN screen);
	ScreeN getCurrentScreen();
	ScreeN getStartScreen();
	void Finish();
}

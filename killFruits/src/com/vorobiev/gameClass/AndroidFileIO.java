package com.vorobiev.gameClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;
import android.os.Environment;

import com.vorobiev.gameInterface.FileIO;

public class AndroidFileIO implements FileIO
{
	private AssetManager asset;
	private String extPath;
	public AndroidFileIO(AssetManager asset)
	{
		this.asset = asset;
		this.extPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	@Override
	public InputStream readAsset(String fileName) throws IOException 
	{
		return asset.open(fileName);
	}

	@Override
	public InputStream readFile(String fileName) throws IOException 
	{
		
		return new FileInputStream(extPath + fileName);
	}

	@Override
	public OutputStream writeFile(String fileName) throws IOException 
	{
		
		return new FileOutputStream(extPath + fileName);
	}

}

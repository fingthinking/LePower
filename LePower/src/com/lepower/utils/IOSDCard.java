package com.lepower.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class IOSDCard {
	private String SDPath=null;
	private String getSDPath(){
		return SDPath;
	}
	public IOSDCard() {//IOSDCard
		// TODO Auto-generated constructor stub
		SDPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"";
		Log.d("heheda", SDPath);
	}
	/**
	 * 在SD卡上创建文件
	 * @param fileName
	 * @return
	 */
	public File CreatSDFile(String fileName){
		File file =new File(SDPath+"/"+fileName);//这里不需要加/吗，试试。这种创建是在根目录下创建
		Log.d("heheda", SDPath+"/"+fileName);
		try{
			file.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}
		return file;
	}
	/**
	 * 在SD卡根目录下创建目录
	 * @param dirName
	 * @return
	 */
	public File CreatSDDir(String dirName){
		File dir=new File(SDPath+"/"+dirName);//根目录下创建文件夹
		Log.d("heheda", SDPath+"/"+dirName);
		if(!dir.exists()){
			dir.mkdir();
		}
		return dir;
	}
	/**
	 * 判断SD卡根目录下某一个文件是否存在
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName){
		File file=new File(SDPath+fileName);//这里不需要加/
		Log.d("heheda", SDPath+fileName);
		return file.exists();
	}
	
//	public File write2SDCardFromInput(String path,String fileName,InputStream inputStream){
//		File file=null;
//		File folder=null;
//		OutputStream outputStream=null;
//		
//		folder=CreatSDDir(path);
//		file=CreatSDFile(path+fileName);
//		outputStream=new FileOutputStream(file);
//		
//		byte buffer[]=new byte[4*]
//	}
	

}

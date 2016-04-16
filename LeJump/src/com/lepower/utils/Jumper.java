package com.lepower.utils;


public class Jumper
{
	private String date;
	private String jumpCount;
	public Jumper(String date, String jumpCount)
	{
		this.date = date;
		this.jumpCount = jumpCount;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getJumpCount()
	{
		return jumpCount;
	}
	public void setJumpCount(String jumpCount)
	{
		this.jumpCount = jumpCount;
	}
	
	
}

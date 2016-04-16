package com.lestep.utils;

import java.text.NumberFormat;


public class NumFormat {
	/**
	 * 取数字的精度
	 * @param num
	 * @param accuracy
	 * @return
	 */
	public static double roundOff(double num, int accuracy){
		NumberFormat nFormat = NumberFormat.getInstance();
		nFormat.setMaximumFractionDigits(accuracy);
		Double value = Double.valueOf(nFormat.format(num));
		return value;
		
	}
}

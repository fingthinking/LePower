package com.lepower.utils;

import android.text.TextUtils;

/**
 * 获取运动等级
 * @author menhao
 *
 */
public class LevelUtils {
	
	public static String getLevel(String cal){
		float allCal = FormatString(cal); 
		if (allCal > 0 && allCal <= 1000) {
			return "运动列兵";
		}else if (allCal > 1000 && allCal <= 2000) {
			return "运动士官";
		}else if (allCal > 2000 && allCal <= 4000) {
			return "运动少尉";
		}else if (allCal > 4000 && allCal <= 8000) {
			return "运动中尉";
		}else if (allCal > 8000 && allCal <= 16000) {
			return "运动上尉";
		}else if (allCal > 16000 && allCal <= 32000){
			return "运动少校";
		}else if (allCal > 32000 && allCal <= 64000) {
			return "运动中校";
		}else if (allCal > 64000 && allCal <= 128000) {
			return "运动上校";
		}else if (allCal > 128000 && allCal <= 256000) {
			return "运动大校";
		}else {
			return "运动上将";
		}
	}
	
	public static float FormatString(String cal){
		return Float.parseFloat(cal);
	}
}

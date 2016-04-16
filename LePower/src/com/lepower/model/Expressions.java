package com.lepower.model;

import java.util.HashMap;
import java.util.Map;

import com.lepower.R;

public class Expressions {
	
	public static Map<String, Integer> EMOJMAP; //表情名与资源id的映射
	
	public static int[] EMOJID = new int[] {

		R.drawable.emoji_1a000,     R.drawable.emoji_1a001,    
		 R.drawable.emoji_1a003,     R.drawable.emoji_1a004,     R.drawable.emoji_1a005,     R.drawable.emoji_1a006,     
		 R.drawable.emoji_1a007,     R.drawable.emoji_1a008,     R.drawable.emoji_1a009,     R.drawable.emoji_1a010,     
		 R.drawable.emoji_1a011,     R.drawable.emoji_1a012,     R.drawable.emoji_1a013,     R.drawable.emoji_1a014,     
		 R.drawable.emoji_1a015,     R.drawable.emoji_1a016,     R.drawable.emoji_1a017,     R.drawable.emoji_1a018,     
		 R.drawable.emoji_1a019,     R.drawable.emoji_1a020,     R.drawable.emoji_1a021,     R.drawable.emoji_1a022,     
		 R.drawable.emoji_1a023,     R.drawable.emoji_1a024,     R.drawable.emoji_1a025,     R.drawable.emoji_1a026,     
		 R.drawable.emoji_1a027,     R.drawable.emoji_1a028,     R.drawable.emoji_1a029, 
		R.drawable.emoji_00300,     R.drawable.emoji_00310,     R.drawable.emoji_00320,     R.drawable.emoji_00330,     
		R.drawable.emoji_00340,     R.drawable.emoji_00350,     R.drawable.emoji_00360,     R.drawable.emoji_00370,     
		R.drawable.emoji_00380,     R.drawable.emoji_00390,       R.drawable.emoji_1f004,     
		 R.drawable.emoji_1f1e8,     R.drawable.emoji_1f232,     R.drawable.emoji_1f36d,     R.drawable.emoji_1f380,     
		 R.drawable.emoji_1f381,     R.drawable.emoji_1f382,     R.drawable.emoji_1f388,     R.drawable.emoji_1f389,    
		 R.drawable.emoji_1f3a4,     R.drawable.emoji_1f3ae,     R.drawable.emoji_1f3b5,     R.drawable.emoji_1f3be,    
		 R.drawable.emoji_1f3c0,     R.drawable.emoji_1f3c1,     R.drawable.emoji_1f3c3,     R.drawable.emoji_1f3c6,     
		 R.drawable.emoji_1f3c8,     R.drawable.emoji_1f3ca,     R.drawable.emoji_1f3ee,     R.drawable.emoji_1f446,     
		 R.drawable.emoji_1f447,     R.drawable.emoji_1f448,     R.drawable.emoji_1f449,     R.drawable.emoji_1f44a,    
		 R.drawable.emoji_1f44b,     R.drawable.emoji_1f44c,     R.drawable.emoji_1f44d,     R.drawable.emoji_1f44e,     
		 R.drawable.emoji_1f44f,     R.drawable.emoji_1f451,     R.drawable.emoji_1f459,     R.drawable.emoji_1f47b,    
		 R.drawable.emoji_1f47d,     R.drawable.emoji_1f47f,     R.drawable.emoji_1f480,     R.drawable.emoji_1f484,    
		 R.drawable.emoji_1f485,     R.drawable.emoji_1f48a,     R.drawable.emoji_1f48b,     R.drawable.emoji_1f48e,     
		 R.drawable.emoji_1f48f,     R.drawable.emoji_1f490,     R.drawable.emoji_1f494,     R.drawable.emoji_1f4a1,     
		 R.drawable.emoji_1f4a2,     R.drawable.emoji_1f4a3,     R.drawable.emoji_1f4a4,     R.drawable.emoji_1f4a9,     
		 R.drawable.emoji_1f4aa,     R.drawable.emoji_1f4af,     R.drawable.emoji_1f4b0,     R.drawable.emoji_1f51e,     
		 R.drawable.emoji_1f64c,     R.drawable.emoji_1f64f,     R.drawable.emoji_1f6b2,     R.drawable.emoji_1f6bd,     
		 R.drawable.emoji_1f6c0,     R.drawable.emoji_26bd0,     R.drawable.emoji_270a0,     R.drawable.emoji_270b0,    
		 R.drawable.emoji_270c0,     R.drawable.emoji_27640,     R.drawable.emoji_32990,     


	};


	public static String[] EMOJNAMES = new String[] {

		 "[emoji_1a009]",    "[emoji_1a010]",    
		 "[emoji_1a011]",    "[emoji_1a012]",    "[emoji_1a013]",    "[emoji_1a014]",    
		 "[emoji_1a015]",    "[emoji_1a016]",    "[emoji_1a017]",    "[emoji_1a018]",    
		 "[emoji_1a019]",    "[emoji_1a020]",    "[emoji_1a021]",    "[emoji_1a022]",    
		 "[emoji_1a023]",    "[emoji_1a024]",    "[emoji_1a025]",    "[emoji_1a026]",    
		 "[emoji_1a027]",    "[emoji_1a028]",    "[emoji_1a029]", 
		 "[emoji_00300]",    "[emoji_00310]",    "[emoji_00320]",    "[emoji_00330]",   
		 "[emoji_00340]",    "[emoji_00350]",    "[emoji_00360]",    "[emoji_00370]",    
		 "[emoji_00380]",    "[emoji_00390]",    "[emoji_1a000]",    "[emoji_1a001]",    
		 "[emoji_1a003]",    "[emoji_1a004]",    "[emoji_1a005]",    "[emoji_1a006]",    
		 "[emoji_1a007]",    "[emoji_1a008]",      "[emoji_1f004]",    
		 "[emoji_1f1e8]",    "[emoji_1f232]",    "[emoji_1f36d]",    "[emoji_1f380]",    
		 "[emoji_1f381]",    "[emoji_1f382]",    "[emoji_1f388]",    "[emoji_1f389]",    
		 "[emoji_1f3a4]",    "[emoji_1f3ae]",    "[emoji_1f3b5]",    "[emoji_1f3be]",    
		 "[emoji_1f3c0]",    "[emoji_1f3c1]",    "[emoji_1f3c3]",    "[emoji_1f3c6]",    
		 "[emoji_1f3c8]",    "[emoji_1f3ca]",    "[emoji_1f3ee]",    "[emoji_1f446]",   
		 "[emoji_1f447]",    "[emoji_1f448]",    "[emoji_1f449]",    "[emoji_1f44a]",    
		 "[emoji_1f44b]",    "[emoji_1f44c]",    "[emoji_1f44d]",    "[emoji_1f44e]",    
		 "[emoji_1f44f]",    "[emoji_1f451]",    "[emoji_1f459]",    "[emoji_1f47b]",    
		 "[emoji_1f47d]",    "[emoji_1f47f]",    "[emoji_1f480]",    "[emoji_1f484]",    
		 "[emoji_1f485]",    "[emoji_1f48a]",    "[emoji_1f48b]",    "[emoji_1f48e]",    
		 "[emoji_1f48f]",    "[emoji_1f490]",    "[emoji_1f494]",    "[emoji_1f4a1]",    
		 "[emoji_1f4a2]",    "[emoji_1f4a3]",    "[emoji_1f4a4]",    "[emoji_1f4a9]",   
		 "[emoji_1f4aa]",    "[emoji_1f4af]",    "[emoji_1f4b0]",    "[emoji_1f51e]",    
		 "[emoji_1f64c]",    "[emoji_1f64f]",    "[emoji_1f6b2]",    "[emoji_1f6bd]",    
		 "[emoji_1f6c0]",    "[emoji_26bd0]",    "[emoji_270a0]",    "[emoji_270b0]",    
		 "[emoji_270c0]",    "[emoji_27640]",    "[emoji_32990]",    


	};

	static{
		EMOJMAP=new HashMap<String, Integer>();
		for(int i=0;i<EMOJNAMES.length;i++){
			EMOJMAP.put(EMOJNAMES[i], EMOJID[i]);
		}
	}
	
//	public static int[] expressionImgs = new int[] { R.drawable.f000,
//			R.drawable.f001, R.drawable.f002, R.drawable.f003, R.drawable.f004,
//			R.drawable.f005, R.drawable.f006, R.drawable.f007, R.drawable.f008,
//			R.drawable.f009, R.drawable.f010, R.drawable.f011, R.drawable.f012,
//			R.drawable.f013, R.drawable.f014, R.drawable.f015, R.drawable.f016,
//			R.drawable.f017, R.drawable.f018, R.drawable.f019,
//			R.drawable.delete };

	/**
	 * 本地表情的名字
	 */
//	public static String[] expressionImgNames = new String[] { "[f000]",
//			"[f001]", "[f002]", "[f003]", "[f004]", "[f005]", "[f006]",
//			"[f007]", "[f008]", "[f009]", "[f010]", "[f011]", "[f012]",
//			"[f013]", "[f014]", "[f015]", "[f016]", "[f017]", "[f018]",
//			"[f019]", "[f020]", "[f021]", "[f022]" };

	/**
	 * 
	 * 
	 * 
	 */
	/*
	 * 服务器存储的表情名字
	 */
	public static String[] expressionRegImgNames = new String[] {
			"\\U0001F601", "f0asd01", "f00asd2", "fasd003", "f0gf04", "f00fg5",
			"f0gfdh06", "fhjgh007", "f0gh08", "ffgh009", "f010", "f011",
			"f012", "f013", "f014", "f015", "f016", "f017", "f05err18",
			"f045fd19", "f0234sdf20", "fsdfg021", "f0jjjh22", "f0hjh23" };

	/**
	 * 
	 * 在存入数据库时，将表情名字进行替换即可
	 * 
	 */
	public static String[] replaceStrings(String[] str, String[] str2) {
		String newStr[] = new String[str.length - 1];
		for (int i = 0; i < str.length; i++) {
			newStr[i] = str[i].replace(str[i], str2[i]);
		}
		return newStr;
	}

}

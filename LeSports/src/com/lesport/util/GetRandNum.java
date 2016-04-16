package com.lesport.util;

import java.util.Random;
//import java.util.Scanner;

/**
 * 
 * @author 曹汝帅
 * @描述：获取验证码（4位）
 * @version V.1
 * @日期：2016-03-08
 *
 */
public class GetRandNum {
	
	private final static  int CODENUM = 4;	//随机验证码的位数
	/**
	 * @描述：设为静态方法，可直接调用，生成4位随机验证码
	 * @return 验证码
	 */
	public static String getRandNum() {
		
		String str = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";  
        String str2[] = str.split(",");//将字符串以,分割  
        Random random = new Random();//创建Random类的对象rand  
        int index = 0;  
        String verCode = "";//创建内容为空字符串对象randStr  
        
        for(int i=0; i<CODENUM; i++) {
        	index = random.nextInt(str2.length-1);
        	verCode += str2[index];
        }
     
		return verCode;
	}
	
}

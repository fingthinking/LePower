package com.lesport.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import net.sf.json.JSONObject;

/**
 * @描述：MD5加密，只对外提供一个静态接口（MD5编码加密）
 * @author crsh
 * @日期：2016-03-09
 */
public class EncodeByMD5 {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * @描述：对外提供的唯一接口
	 * @param originalPwd
	 *            原始密码（未加密前）
	 * @return 加密后的字符串
	 */
	public static String createPwdToDB(String originPwd) {
		return encodeByMD5(originPwd);
	}

	/**
	 * @描述：对字符串进行MD5编码加密
	 * @param originStr
	 * @return
	 */
	private static String encodeByMD5(String originStr) {
		if (originStr != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5"); // 创建具有指定算法名称的信息摘要
				byte[] results = md.digest(originStr.getBytes()); // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				String resultString = byteArrayToHexString(results); // 将字节数组变成十六进制字符串返回
				return resultString.toUpperCase(); // 转换成大写，非必须这样做
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * @描述：将字节数组转换成十六进制的字符串
	 * @param b
	 *            字节数组
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultBuf = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultBuf.append(byteToHexString(b[i]));
		}
		return resultBuf.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
//	public static void main(String[] args) {
//		String pwd = EncodeByMD5.createPwdToDB("crsh02");
//		System.out.println(pwd);
//		System.out.println(pwd.length());
//		String test = "crsh02";
//		System.out.println(pwd.equals(EncodeByMD5.createPwdToDB(test)));
//		
////		JSONObject jsonVerCode = JSONObject.fromObject(pwd);
////		String verCode = "crsh";
////		String str = "{\"resCode\":\"0\",\"resMsg\":\"send email is ok\",\"data\":[{}]}";
////		JSONObject json = JSONObject.fromObject(str);
////		json.put("verCode",verCode);
////		System.out.println(json.toString());
//		
//	}
}

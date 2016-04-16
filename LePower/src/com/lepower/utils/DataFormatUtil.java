package com.lepower.utils;

import java.math.BigDecimal;

public class DataFormatUtil {
	
	
	
	public static String formatData(String dataString){
		if(dataString.equals("")){
			return "0";
		}
		if(!dataString.contains(".")){
			return dataString;
		}else{
			double dataDouble=Double.valueOf(dataString);
			BigDecimal bdBigDecimal=new BigDecimal(dataDouble);
			double dataDoubleResult=bdBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return dataDoubleResult+"";//返回字符串
		}
		
	}
	
	
	public static String formatDataTimeS(String timeS){
		int timeInt=Integer.valueOf(timeS);
		int hour= timeInt/3600;
		int min=(timeInt%3600)/60;
		int sec=(timeInt%3600)%60;
		String result="0秒";
		if(hour>0){
			result=hour+"小时"+min+"分"+sec+"秒";
		}else if(min>0){
			result=min+"分"+sec+"秒";
		}else{
			result=sec+"秒";
		}
		return result;
	}
	// 通过毫秒计算时间
		public static String getTimeFromMili(long milisecond){
			StringBuilder sb = new StringBuilder();
			if(milisecond > 60 * 60 * 1000){
				long hour = milisecond / (60 * 60 * 1000);
				sb.append(hour+"小时");
				milisecond %= 60 * 60 * 1000;
			}
			if(milisecond > 1000 * 60){
				long minute = milisecond / (60 * 1000);
				sb.append(minute+"分");
				milisecond %= 60*1000;
			}
			if(milisecond > 1000){
				long second = milisecond / 1000;
				sb.append(second+"秒");
			}else{
				sb.append("0秒");
			}
			return sb.toString();
			
		}
	
	public static String formatDataTimeM(String timeM){
		
		int timeInt=(int) (Double.valueOf(timeM)*60);//变成s
		int hour= timeInt/3600;
		int min=(timeInt%3600)/60;
		int sec=(timeInt%3600)%60;
		String result="0秒";
		if(hour>0){
			result=hour+"小时"+min+"分"+sec+"秒";
		}else if(min>0){
			result=min+"分"+sec+"秒";
		}else{
			result=sec+"秒";
		}
		return result;
	}
	
	public static String formatDataM2KM(String disM){
		
		Double dism=Double.valueOf(disM);//
		if(dism>999){
			Double calResult=dism/1000;
			
			BigDecimal bdBigDecimal=new BigDecimal(calResult);
			double dataDoubleResult=bdBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return dataDoubleResult+"km";
		}else{
			return dism+"m";
		}
	}
	
	public static String formatDataCal2KCal(String calString){
		Double cal=Double.valueOf(calString);//
		if(cal>999){
			Double calResult=cal/1000;
			
			BigDecimal bdBigDecimal=new BigDecimal(calResult);
			double dataDoubleResult=bdBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return dataDoubleResult+"kCal";
		}else{
			return cal+"Cal";
		}
	}
	
	
//	{"resCode":0,"resMsg":"","data":
//		[{"sportCal":"","sportCount":"0","sportDis":"","sportNum":"","sportSteps":"","sportTime":"","type":"run","userId":"","userName":""},
//		 {"sportCal":"2.4488848","sportCount":"4","sportDis":"35.239999999999995","sportNum":"","sportSteps":"185","sportTime":"103507","type":"walk","userId":"","userName":""},
//		 {"sportCal":"621.48","sportCount":"5","sportDis":"8.57","sportNum":"","sportSteps":"","sportTime":"1.67","type":"bike","userId":"","userName":""},
//		 {"sportCal":"","sportCount":"0","sportDis":"","sportNum":"","sportSteps":"","sportTime":"","type":"pushup","userId":"","userName":""},
//		 {"sportCal":"1.7404000000000002","sportCount":"7","sportDis":"","sportNum":"19","sportSteps":"","sportTime":"","type":"jump","userId":"","userName":""},
//		 {"sportCal":"","sportCount":"0","sportDis":"","sportNum":"","sportSteps":"","sportTime":"","type":"situp","userId":"","userName":""}
//		 ]}
}

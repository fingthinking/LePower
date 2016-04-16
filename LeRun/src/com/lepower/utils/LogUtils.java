package com.lepower.utils;

import android.util.Log;
/**
 * 日志输出管理
 * @author fing	2016-10-11
 *
 */
public class LogUtils {
	public static boolean LOG_PRINT = true;

	private static String generateTag() {
		StackTraceElement caller = new Throwable().getStackTrace()[2];
		String tag = "%s.%s(L:%d)";
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName
				.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, caller.getMethodName(),
				caller.getLineNumber());
		return tag;
	}

	public static <T> void e(T msg) {
		if (LOG_PRINT)
			Log.e(generateTag(), "" + msg);
	}

	public static <T> void i(T msg) {
		if (LOG_PRINT)
			Log.i(generateTag(), "" + msg);
	}

	public static <T> void v(T msg) {
		if (LOG_PRINT)
			Log.v(generateTag(), "" + msg);
	}

	public static <T> void d(T msg) {
		if (LOG_PRINT)
			Log.d(generateTag(), "" + msg);
	}

	public static <T> void w(T msg) {
		if (LOG_PRINT)
			Log.w(generateTag(), "" + msg);
	}

	public static <T> void e(String tag, T msg) {
		if (LOG_PRINT)
			Log.e(generateTag()+"->"+tag, "" + msg);
	}

	public static <T> void d(String tag, T msg) {
		if (LOG_PRINT)
			Log.d(generateTag()+"->"+tag, "" + msg);
	}

	public static <T> void v(String tag, T msg) {
		if (LOG_PRINT)
			Log.v(generateTag()+"->"+tag, "" + msg);
	}

	public static <T> void i(String tag, T msg) {
		if (LOG_PRINT)
			Log.i(generateTag()+"->"+tag, "" + msg);
	}

	public static <T> void w(String tag, T msg) {
		if (LOG_PRINT)
			Log.w(generateTag()+"->"+tag, "" + msg);
	}
}

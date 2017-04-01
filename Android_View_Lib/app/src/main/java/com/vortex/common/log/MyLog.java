package com.vortex.common.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

import com.vortex.common.util.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * <pre>
 * Copyright:	Copyright (c)2015 
 * Company:		Vortex 
 * @Author       Eli 
 * @Created at：   2015年8月12日 上午11:29:00 
 * Description:  自定义日志
 * 
 * 
 * </pre>
 */
public class MyLog {
	public static boolean isDebug ;
	private static BufferedWriter mBufferedWriter;
	private static final String dir = Environment.getExternalStorageDirectory()
			.getPath() + "/" + "glyf/log";

	/**
	 * 判断关联AndroidManifest.xml中的debug设置;
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isApplicationDebug(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_ACTIVITIES);
			int flags = packageInfo.applicationInfo.flags;
			int debug = (flags & ApplicationInfo.FLAG_DEBUGGABLE);
			if (debug != 0) {
				// development mode
				isDebug = true;
			} else {
				// release mode
				isDebug = false;
			}
		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}
		return isDebug;
	}

	/**
	 * 写文件
	 */
	private static void writeLogFile(String tag, String msg) {

		if (mBufferedWriter == null && FileUtils.isSDCARDMounted()) {
			try {
				File mFile = new File(new StringBuilder(FileUtils.mkDir(dir)
						.getPath() + File.separator)
						.append("log")
						.append("_")
						.append(new SimpleDateFormat("yyyyMMdd_HH")
								.format(new Date())).append(".txt").toString());
				mBufferedWriter = new BufferedWriter(
						new FileWriter(mFile, true));
			} catch (IOException e) {
				e.printStackTrace();
				mBufferedWriter = null;
				return;
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss:SSS");
		String buffer = sdf.format(new Date()) + ": " + tag + ": " + msg + "\n";
		try {
			mBufferedWriter.append(buffer);
			mBufferedWriter.flush();
		} catch (Exception e) {
			endWriteFile();
		}

	}

	/**
	 * 结束记录文件
	 */
	public static void endWriteFile() {
		if (mBufferedWriter != null) {
			try {
				mBufferedWriter.flush();
				mBufferedWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mBufferedWriter = null;
		}
	}

	/**
	 * 销毁
	 */
	public static void destroy() {
		endWriteFile();
	}

	public static void v(String tag, String msg) {
		if (isDebug) {
			android.util.Log.v(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void v(String tag, String msg, Throwable thr) {
		if (isDebug) {
			android.util.Log.v(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void d(String tag, String msg) {
		if (isDebug) {
			android.util.Log.d(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void d(String tag, String msg, Throwable thr) {
		if (isDebug) {
			android.util.Log.d(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void i(String tag, String msg) {
		if (isDebug) {
			android.util.Log.i(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void i(String tag, String msg, Throwable thr) {
		if (isDebug) {
			android.util.Log.i(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void w(String tag, String msg) {
		if (isDebug) {
			android.util.Log.w(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void w(String tag, String msg, Throwable thr) {
		if (isDebug) {
			android.util.Log.w(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));
		}
	}

	public static void w(String tag, Throwable thr) {
		if (isDebug) {
			android.util.Log.w(tag, buildMessage(""), thr);

		}
	}

	public static void e(String tag, String msg) {
		if (isDebug) {
			android.util.Log.e(tag, buildMessage(msg));
			writeLogFile(tag, buildMessage(msg));

		}
	}

	public static void e(String tag, String msg, Throwable thr) {
		if (isDebug) {
			android.util.Log.e(tag, buildMessage(msg), thr);
			writeLogFile(tag, buildMessage(msg));

		}
	}

	private static String buildMessage(String msg) {
		StackTraceElement caller = new Throwable().fillInStackTrace()
				.getStackTrace()[2];
		String className = caller.getClassName();
		String simpleClassName = className
				.substring(className.lastIndexOf(".") + 1);
		return new StringBuilder().append(simpleClassName).append(".")
				.append(caller.getMethodName()).append("(")
				.append(caller.getLineNumber()).append("):").append(msg)
				.toString();
	}

}

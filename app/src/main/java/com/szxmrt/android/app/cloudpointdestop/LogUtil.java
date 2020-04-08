package com.szxmrt.android.app.cloudpointdestop;

import android.util.Log;

public class LogUtil {

	private static boolean DEBUG = true;

	public static void setDebug(boolean debug) {
		DEBUG = debug;
	}

	public static void e(String tag, int i) {
		if (DEBUG) Log.e(tag, i + "");
	}

	public static void e(String tag, String s) {
		if (DEBUG) Log.e(tag, s);
	}
}

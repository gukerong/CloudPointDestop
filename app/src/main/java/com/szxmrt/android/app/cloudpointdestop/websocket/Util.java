package com.szxmrt.android.app.cloudpointdestop.websocket;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class Util {

	public static String getReqId() {
		return "JAVA_" + getSec() + "_" + (long) (Math.random() * 100000);
	}

	// 秒
	public static long getSec() {
		return System.currentTimeMillis() / 1000;
	}

	// 随机数
	public static String RandomStr(int len) {
		if (len < 3) {
			len = 3;
		}
		char[] ch = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z'
		};
		char[] buf = new char[len];
		for (int i = 0; i < len; i++) {
			int index = (int) (Math.random() * ch.length);
			buf[i] = ch[index];
		}
		return new String(buf);
	}

	public static String MapToUrl(Map<String, String> map) {
		if (map == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sb.append("&")
						.append(entry.getKey())
						.append("=")
						.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String GetExceptionInfoString(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}

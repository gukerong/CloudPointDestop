package com.szxmrt.android.app.cloudpointdestop.net;

import android.content.SharedPreferences;

import com.szxmrt.android.app.cloudpointdestop.DeviceUtils;
import com.szxmrt.android.app.cloudpointdestop.MyApplication;

import java.util.concurrent.atomic.AtomicLong;

public class NetConfigure {

	private static int RETRY_COUNT = 10;

	private static String TOKEN = null;
	private static String VENDOR_ID = null;
	private static SharedPreferences SHARED;
	private static String DATA_KEY = "K4" + System.nanoTime(); // 随机数据密钥;
	private static AtomicLong REQUEST_ID = new AtomicLong(0L);
	private static String BASE_URL = "http://api.xinchihuo.com.cn/ruitai/";
	private static String SOCKET_URL = "ws://srv.xinchihuo.com.cn:13010/websocket";
	private static String OUTPUT_URL = "https://gitee.com/gukerong/CloudPointDesktop/raw/master/app/release/output.json";
	private static String APK_URL = "https://raw.githubusercontent.com/gukerong/CloudPointDestop/master/app/release/app-release.apk?token=AHGOD72QWKZHM2CKG4GGLKS6RQZEC";

	static {
		SHARED = MyApplication.getContext().getSharedPreferences("CONFIG_NAME", MyApplication.MODE_PRIVATE);
	}

	public static String getVendorId() {
		return VENDOR_ID;
	}

	public static void setVendorId(String vendorId) {
		VENDOR_ID = vendorId;
	}

	public static String getToken() {
		if (TOKEN == null) {
			TOKEN = SHARED.getString("TOKEN", null);
			if (null == TOKEN) {
				String uuid = DeviceUtils.getDeviceUUID(MyApplication.getContext());
				SHARED.edit().putString("TOKEN", uuid).apply();
				TOKEN = SHARED.getString("TOKEN", null);
			}
		}
		return TOKEN;
	}

	public static String getBaseUrl() {
		return BASE_URL;
	}

	public static String getSocketUrl() {
		return SOCKET_URL;
	}

	public static String getOutputUrl() {
		return OUTPUT_URL;
	}

	public static String getApkUrl() {
		return APK_URL;
	}

	public static String getDataKey() {
		return DATA_KEY;
	}

	public static long incrementAndGet() {
		return REQUEST_ID.incrementAndGet();
	}

	public static void setVendorName(String vendorName) {
		if (vendorName != null) {
			SHARED.edit().putString("VENDOR_NAME", vendorName).apply();
		}
	}

	public static String getVendorName() {
		return SHARED.getString("VENDOR_NAME", "");
	}

	public static boolean isInit() {
		return SHARED.getBoolean("INIT", false);
	}

	public static void init(String vendorName) {
		boolean init = SHARED.getBoolean("INIT", false);
		if (!init) {
			if (vendorName != null) {
				SHARED.edit().putString("VENDOR_NAME", vendorName).apply();
				String uuid = DeviceUtils.getDeviceUUID(MyApplication.getContext());
				SHARED.edit().putString("TOKEN", uuid).apply();
				SHARED.edit().putBoolean("INIT", true).apply();
			}
		}
	}
}

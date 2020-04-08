package com.szxmrt.android.app.cloudpointdestop;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import java.io.IOException;
import java.util.Random;

import phoenix.os.PhoenixPowerManager;

public class DeviceUtils {

	public static String getDeviceUUID(Context context) {
		return buildDeviceUUID(context);
	}

	private static String buildDeviceUUID(Context context) {
		String androidId = getAndroidId(context);
		if ("9774d56d682e549c".equals(androidId)) {
			androidId = randomString(16);
		}
		return androidId;
/*        String androidId = getAndroidId(context);
        LogUtil.e("aaa",androidId);
        LogUtil.e("aaa",getBuildInfo());
        if ("9774d56d682e549c".equals(androidId)) {
            Random random = new Random();
            androidId = Integer.toHexString(random.nextInt())
                + Integer.toHexString(random.nextInt())
                + Integer.toHexString(random.nextInt());
        }
        return new UUID(androidId.hashCode(), getBuildInfo().hashCode()).toString();*/
	}

	@SuppressWarnings("all")
	private static String getAndroidId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	private static String getBuildInfo() {
		return Build.BRAND + "/" +
				Build.PRODUCT + "/" +
				Build.DEVICE + "/" +
				Build.ID + "/" +
				Build.VERSION.INCREMENTAL;
	}

	private static String randomString(int length) {
		Random randGen = new Random();
		char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
				"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(64)];
		}
		return new String(randBuffer);
	}

	public static void reboot() {
		try {
			PhoenixPowerManager.reboot("restart");
			Runtime.getRuntime().exec("su -c reboot");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


/*
    private static void saveDeviceUUID(Context context, String uuid) {
        context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE)
            .edit()
            .putString("uuid", uuid)
            .apply();
    }

    private static String loadDeviceUUID(Context context) {
        return context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE)
            .getString("uuid", null);
    }
*/


}

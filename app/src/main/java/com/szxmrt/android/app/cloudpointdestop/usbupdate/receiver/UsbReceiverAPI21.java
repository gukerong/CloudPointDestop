package com.szxmrt.android.app.cloudpointdestop.usbupdate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;


public class UsbReceiverAPI21 extends BroadcastReceiver {
	public static final String ACTION_USB_RECEIVER = "ACTION_USB_RECEIVER";
	private static final String TAG = UsbReceiverAPI21.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String path = intent.getData().getPath();
		if (action.equals(VolumeInfo.ACTION_USB_DEVICE_ATTACHED)) {
			// Log.e("action===", "装载");
		} else if (action.equals(VolumeInfo.ACTION_USB_DEVICE_DETACHED)) {
			//Log.d("action===", "卸载USB");
		} else if (Intent.ACTION_MEDIA_UNMOUNTED.equals(action)) {
		} else if (Intent.ACTION_MEDIA_MOUNTED.equals(action)) {
			processMountedMessage(context, path);
		} else if (action.equals(Intent.ACTION_MEDIA_REMOVED)) {
		}
	}


	private void processUnmountedMessage(Context context, Bundle bundle) {

/*		Intent mIntent = new Intent(MainActivity.ACTION_USB_RECEIVER);
		mIntent.putExtra("message", "false");
		context.sendBroadcast(mIntent);*/

	}

	private void processMountedMessage(Context context, String path) {
		if (null != path && !"".equals(path)) {
			Intent intent = new Intent(ACTION_USB_RECEIVER);
			intent.putExtra("data", "USB_MOUNT");
			intent.putExtra("path", path);
			LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
			localBroadcastManager.sendBroadcast(intent);
		}
	}

}

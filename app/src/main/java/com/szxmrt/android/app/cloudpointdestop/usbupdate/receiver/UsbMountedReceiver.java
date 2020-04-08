package com.szxmrt.android.app.cloudpointdestop.usbupdate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UsbMountedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String path = intent.getStringExtra("path");
		if (mOnUsbListener != null) mOnUsbListener.onListener(intent.getAction(), path);
	}


	private OnUsbListener mOnUsbListener;

	public void setOnUsbListener(OnUsbListener onUsbListener) {
		mOnUsbListener = onUsbListener;
	}

	public void removeOnUsbListener() {
		if (mOnUsbListener != null) mOnUsbListener = null;
	}

	public interface OnUsbListener {
		void onListener(String action, String path);
	}
}

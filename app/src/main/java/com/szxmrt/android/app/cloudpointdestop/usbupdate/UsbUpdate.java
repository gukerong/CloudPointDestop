package com.szxmrt.android.app.cloudpointdestop.usbupdate;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;

import com.szxmrt.android.app.cloudpointdestop.R;
import com.szxmrt.android.app.cloudpointdestop.usbupdate.receiver.UsbMountedReceiver;
import com.szxmrt.android.app.cloudpointdestop.usbupdate.receiver.UsbReceiverAPI21;

import java.io.File;


public class UsbUpdate {

	private Context mContext;
	private IntentFilter mIntentFilter;
	private UsbMountedReceiver mUSBMountedReceiver;
	private LocalBroadcastManager mLocalBroadcastManager;

	public UsbUpdate(Context context) {
		mContext = context;
		mUSBMountedReceiver = new UsbMountedReceiver();
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
		mIntentFilter = new IntentFilter(UsbReceiverAPI21.ACTION_USB_RECEIVER);
		mUSBMountedReceiver.setOnUsbListener((action, path) -> showUpdateAppDialog(path));
	}

	public void register(){
		mLocalBroadcastManager.registerReceiver(mUSBMountedReceiver, mIntentFilter);
	}

	public void unregister() {
		if (mUSBMountedReceiver != null) {
			mLocalBroadcastManager.unregisterReceiver(mUSBMountedReceiver);
			mUSBMountedReceiver.removeOnUsbListener();
		}
	}

	private void showUpdateAppDialog(String path) {
		AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
		normalDialog.setIcon(R.mipmap.ic_launcher);
		normalDialog.setTitle("提示");
		normalDialog.setMessage("检测到APK，是否更新？");
		normalDialog.setNegativeButton("取消", (dialog, which) -> {
		});
		normalDialog.setPositiveButton("确定", (dialog, which) -> updateApp(path));
		// 显示
		normalDialog.show();
	}

	private void updateApp(String path) {
		if (null != path && !"".equals(path)) {
			File file = new File(path + "/mofawu/魔法屋广告.apk");
			if (!file.exists()) return;
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			mContext.startActivity(intent);
		}
	}
}

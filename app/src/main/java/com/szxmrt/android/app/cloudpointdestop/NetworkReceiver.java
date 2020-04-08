package com.szxmrt.android.app.cloudpointdestop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkReceiver extends BroadcastReceiver {

	private OnNetworkConnectedListener mOnNetworkConnectedListener;

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		//NetworkInfo mobileNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		//NetworkInfo wifiNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo allNetInfo = cm.getActiveNetworkInfo();
		if (allNetInfo != null && allNetInfo.isConnected()) {
			if (mOnNetworkConnectedListener != null) mOnNetworkConnectedListener.connected();
		}
	}

	public void setOnNetworkConnectedListener(OnNetworkConnectedListener listener){
		mOnNetworkConnectedListener = listener;
	}

	public void removeListener(){
		mOnNetworkConnectedListener = null;
	}

	public interface OnNetworkConnectedListener{
		void connected();
	}
}

package com.szxmrt.android.app.cloudpointdestop.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.Nullable;

import com.szxmrt.android.app.cloudpointdestop.LogUtil;
import com.szxmrt.android.app.cloudpointdestop.NetworkReceiver;
import com.szxmrt.android.app.cloudpointdestop.net.NetConfigure;


public class SplashActivity extends BaseActivity {

	private final static String TAG = "SplashActivity";

	private NetworkReceiver mNetworkReceiver;

	@Override
	Integer getLayout() {
		return null;
	}

	@Override
	void initVariable() {
		LogUtil.e(TAG, "onCreate");
		if (!isTaskRoot()) {
			finish();
			return;
		}
		checkNetworkStatusChange();
	}

	@Override
	void initOnclick() {
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.e(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.e(TAG, "onPause");
		if (mNetworkReceiver != null) {
			mNetworkReceiver.removeListener();
			unregisterReceiver(mNetworkReceiver);
		}
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtil.e(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.e(TAG, "onDestroy");
	}

	@SuppressWarnings("ConstantConditions")
	private void checkNetworkStatusChange() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkRequest request = new NetworkRequest.Builder()
					.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
					.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
					.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
					.build();
			cm.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {
				@Override
				public void onAvailable(@Nullable Network network) {
					super.onAvailable(network);
					activityIntent();
				}
			});
		} else {
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			mNetworkReceiver = new NetworkReceiver();
			registerReceiver(mNetworkReceiver, filter);
			mNetworkReceiver.setOnNetworkConnectedListener(this::activityIntent);
		}
	}

	private void activityIntent() {
		if (NetConfigure.isInit()) {
			LogUtil.e(TAG, "打开首页");
			startActivity(new Intent(this, MainActivity.class));
		} else {
			LogUtil.e(TAG, "打开登录界面");
			startActivity(new Intent(this, LoginActivity.class));
		}
	}
}

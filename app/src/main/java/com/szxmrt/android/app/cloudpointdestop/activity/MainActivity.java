package com.szxmrt.android.app.cloudpointdestop.activity;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cloudpoint.plugins.sdk.adv.AdvView;
import com.cloudpoint.plugins.sdk.adv.CPAdvSdk;
import com.szxmrt.android.app.cloudpointdestop.DimenUtil;
import com.szxmrt.android.app.cloudpointdestop.LogUtil;
import com.szxmrt.android.app.cloudpointdestop.MyApplication;
import com.szxmrt.android.app.cloudpointdestop.R;
import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;
import com.szxmrt.android.app.cloudpointdestop.net.NetConfigure;
import com.szxmrt.android.app.cloudpointdestop.net.entity.BaseResponse;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorInfo;
import com.szxmrt.android.app.cloudpointdestop.presenter.MainPresenter;
import com.szxmrt.android.app.cloudpointdestop.usbupdate.UsbUpdate;

public class MainActivity extends BaseActivity {
	private final static String TAG = "MainActivity";

	private AdvView mAdvView;
	private UsbUpdate mUsbUpdate;
	private TextView mDeviceNoText;
	private MainPresenter mMainPresenter;
	private final Handler mHandler = new Handler();

	@Override
	Integer getLayout() {
		return R.layout.activity_main;
	}

	@Override
	void initVariable() {
		mAdvView = findViewById(R.id.adv_view);
		mDeviceNoText = findViewById(R.id.tv_a_main_device_no);
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mDeviceNoText.getLayoutParams();
		layoutParams.topMargin = DimenUtil.px2dp(DimenUtil.getStatusBarHeight(this));
		mDeviceNoText.setLayoutParams(layoutParams);
		mMainPresenter = new MainPresenter();
		mUsbUpdate = new UsbUpdate(this);
		LogUtil.e(TAG, "onCreate");
	}

	@Override
	void initOnclick() {
		mDeviceNoText.setOnClickListener(view -> exitAdvert());
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.e(TAG, "onResume");
		mMainPresenter.setRequestResultListener(this::initResult);
		mMainPresenter.init();
		mUsbUpdate.register();
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.e(TAG, "onPause");
		CPAdvSdk.bindView(null);
		mMainPresenter.stopWebPing();
		mUsbUpdate.unregister();
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

	private void initResult(String action, int ret, BaseResponse response) {
		if (ret != 0) {
			mHandler.postDelayed(() -> mMainPresenter.init(), 1000 * 60);
		}
		switch (action) {
			case HttpCode.GET_RSA_PUBKEY:
				break;
			case HttpCode.SAVE_DATA_KEY:
				break;
			case HttpCode.VENDOR_LOGIN:
				break;
			case HttpCode.GET_VENDOR_INFO:
				if (ret == 0) {
					mDeviceNoText.setText(NetConfigure.getVendorName());
					MyApplication.initAdvert(((VendorInfo) response).getData().getInfo());
					CPAdvSdk.bindView(mAdvView);
					mMainPresenter.startWebPing();
				}
				break;
			default:
				break;
		}
	}

	private int mCount = 0;

	private void exitAdvert() {
		if (mCount++ > 10) {
			mCount = 0;
			Intent intent = new Intent(Settings.ACTION_SETTINGS);
			startActivity(intent);
		}
	}
}

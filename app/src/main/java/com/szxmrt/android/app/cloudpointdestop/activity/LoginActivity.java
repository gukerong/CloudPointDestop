package com.szxmrt.android.app.cloudpointdestop.activity;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szxmrt.android.app.cloudpointdestop.LogUtil;
import com.szxmrt.android.app.cloudpointdestop.R;
import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;
import com.szxmrt.android.app.cloudpointdestop.net.NetConfigure;
import com.szxmrt.android.app.cloudpointdestop.net.entity.BaseResponse;
import com.szxmrt.android.app.cloudpointdestop.presenter.LoginPresenter;


public class LoginActivity extends BaseActivity {
	private final static String TAG = "LoginActivity";

	private Button mEnterBtn;
	private TextView mExitText;
	private String mVendorName;
	private Group mLoadingGroup;
	private EditText mDeviceNumEdt;
	private ConstraintLayout mRootLayout;
	private LoginPresenter mLoginPresenter;
	private final Handler mHandler = new Handler();

	@Override
	Integer getLayout() {
		return R.layout.activity_login;
	}

	@Override
	void initVariable() {
		mExitText = findViewById(R.id.tv_a_login_exit);
		mEnterBtn = findViewById(R.id.btn_a_login_enter);
		mRootLayout = findViewById(R.id.cl_a_login_root);
		mDeviceNumEdt = findViewById(R.id.edt_a_login_num);
		mLoadingGroup = findViewById(R.id.group_a_login_loading);
		mLoginPresenter = new LoginPresenter();
		LogUtil.e(TAG, "onCreate");
	/*	NetClient.getOutput().subscribe(new Observer<Output>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(Output output) {
				LogUtil.e("aaaaa",output.toString());
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {

			}
		});*/
	}

	@Override
	void initOnclick() {
		mExitText.setOnClickListener(view -> exitAdvert());
		mRootLayout.setOnClickListener(view -> checkInputFromStatus());
		mEnterBtn.setOnClickListener(view -> {
			checkInputFromStatus();
			mLoadingGroup.setVisibility(View.VISIBLE);
			mVendorName = mDeviceNumEdt.getText().toString();
			if (mVendorName.length() > 0) {
				mLoginPresenter.init(mVendorName, this::initResult);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.e(TAG, "onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.e(TAG, "onResume");
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
		switch (action) {
			case HttpCode.GET_RSA_PUBKEY:
				if (ret != 0) {
					mLoadingGroup.setVisibility(View.GONE);
				}
			case HttpCode.SAVE_DATA_KEY:
				if (ret != 0) {
					mLoadingGroup.setVisibility(View.GONE);
				}
				break;
			case HttpCode.VENDOR_LOGIN:
				mLoadingGroup.setVisibility(View.GONE);
				if (ret == 0) {
					NetConfigure.init(mVendorName);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							NetConfigure.init(mVendorName);
							startActivity(new Intent(LoginActivity.this, MainActivity.class));
							finish();
						}
					});
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

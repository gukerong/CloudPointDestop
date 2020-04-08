package com.szxmrt.android.app.cloudpointdestop.presenter;

import com.szxmrt.android.app.cloudpointdestop.net.RequestResultListener;

public abstract class BasePresenter {

	RequestResultListener mRequestResultListener;

	public void setRequestResultListener(RequestResultListener listener) {
		mRequestResultListener = listener;
	}
}

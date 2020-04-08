package com.szxmrt.android.app.cloudpointdestop.presenter;

import com.szxmrt.android.app.cloudpointdestop.net.RequestResultListener;

public class LoginPresenter extends BasePresenter {

	public void init(String deviceName, RequestResultListener listener) {
		InitPresenter initPresenter = new InitPresenter();
		initPresenter.setRequestResultListener((action, ret, response) -> {
			if (listener != null) listener.onResult(action, ret, response);
		});
		initPresenter.init(deviceName);
	}

}

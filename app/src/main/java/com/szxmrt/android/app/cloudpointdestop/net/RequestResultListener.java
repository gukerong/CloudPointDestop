package com.szxmrt.android.app.cloudpointdestop.net;

import com.szxmrt.android.app.cloudpointdestop.net.entity.BaseResponse;

public interface RequestResultListener {

	void onResult(String action,int ret, BaseResponse response);
}

package com.szxmrt.android.app.cloudpointdestop.net.entity;

import android.support.annotation.NonNull;

import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;

public class SubmitKey extends BaseResponse<SubmitKey.Data> {

    @Override
    public String getName() {
        return HttpCode.SAVE_DATA_KEY;
    }

    public class Data{
	    @NonNull
	    @Override
	    public String toString() {
		    return super.toString();
	    }
    }
}

package com.szxmrt.android.app.cloudpointdestop.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInterceptor implements Interceptor {

	private int mMaxRetry;//最大重试次数
	private int mRetryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

	public RetryInterceptor(int maxRetry) {
		this.mMaxRetry = maxRetry;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		try {
			Response response = chain.proceed(request);
			while (!response.isSuccessful() && mRetryNum < mMaxRetry) {
				Log.e("aaaa", mRetryNum + "");
				mRetryNum++;
				response = chain.proceed(request);
			}
			return response;
		} catch (Exception e) {
/*			if (mRetryNum < mMaxRetry) {
				mRetryNum++;
				return chain.proceed(request);
			}
			Log.e("aaaaa", e.toString());
			e.printStackTrace();*/
			return null;
		}
	}
}
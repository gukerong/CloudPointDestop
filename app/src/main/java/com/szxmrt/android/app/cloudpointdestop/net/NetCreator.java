package com.szxmrt.android.app.cloudpointdestop.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class NetCreator {

	private static int TIME_OUT = 3000;


	public static WebApiService getNetService() {
		return NetCreatorHolder.WEB_SERVICE;
	}

	private static class OkHttpHolder {
		private static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
				.writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
				.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
				.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
				//.addInterceptor(new RetryInterceptor(5))
				.build();
	}

	private static class RetrofitHolder {
		private static Retrofit RETROFIT = new Retrofit.Builder()
				.baseUrl(NetConfigure.getBaseUrl())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(FastJsonConverterFactory.create())
				.client(OkHttpHolder.OK_HTTP_CLIENT)
				.build();
	}

	private static class NetCreatorHolder {
		private static WebApiService WEB_SERVICE = RetrofitHolder.RETROFIT.create(WebApiService.class);
	}
}

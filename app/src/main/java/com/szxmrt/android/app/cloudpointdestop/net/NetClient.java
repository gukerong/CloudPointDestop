package com.szxmrt.android.app.cloudpointdestop.net;


import com.szxmrt.android.app.cloudpointdestop.net.entity.Output;
import com.szxmrt.android.app.cloudpointdestop.net.entity.PublicKey;
import com.szxmrt.android.app.cloudpointdestop.net.entity.SubmitKey;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorInfo;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorLogin;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NetClient {

	public static Observable<PublicKey> getPublicKey(Map<String, Object> map) {
		return NetCreator.getNetService()
				.getPublicKey(NetConfigure.getBaseUrl(), map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retryWhen(new NetFunction());
	}

	public static Observable<SubmitKey> submitKey(Map<String, Object> map) {
		if (map == null) return null;
		String url = NetUtil.createUrl((String) map.get("opr"));
		return NetCreator.getNetService()
				.submitKey(url, map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retryWhen(new NetFunction());
	}

	public static Observable<VendorLogin> vendorLogin(Map<String, Object> map) {
		Map<String, Object> dataMap = NetUtil.createResponseMap(map, EncodeMode.AES);
		if (dataMap == null) return null;
		String url = NetUtil.createUrl((String) map.get("opr"));
		return NetCreator.getNetService()
				.connect(url, dataMap)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retryWhen(new NetFunction());
	}

	public static Observable<VendorInfo> getVendorInfo(Map<String, Object> map) {
		Map<String, Object> dataMap = NetUtil.createResponseMap(map, EncodeMode.AES);
		if (dataMap == null) return null;
		String url = NetUtil.createUrl((String) map.get("opr"));
		return NetCreator.getNetService()
				.getVendorInfo(url, dataMap)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retryWhen(new NetFunction());
	}

	public static Observable<Output> getOutput() {
		return NetCreator.getNetService()
				.getOutput()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retryWhen(new NetFunction());
	}
}

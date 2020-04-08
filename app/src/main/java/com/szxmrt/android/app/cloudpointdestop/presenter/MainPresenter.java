package com.szxmrt.android.app.cloudpointdestop.presenter;

import android.util.Log;

import com.szxmrt.android.app.cloudpointdestop.LogUtil;
import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;
import com.szxmrt.android.app.cloudpointdestop.net.NetClient;
import com.szxmrt.android.app.cloudpointdestop.net.NetConfigure;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorInfo;
import com.szxmrt.android.app.cloudpointdestop.websocket.WebDataSource;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainPresenter extends BasePresenter {

	public void init() {
		String vendorId = NetConfigure.getVendorId();
		if (null != vendorId && !"".equals(vendorId)) {
			getVendorInfo();
		} else {
			String vendorName = NetConfigure.getVendorName();
			if (!"".equals(vendorName)) {
				InitPresenter initPresenter = new InitPresenter();
				initPresenter.setRequestResultListener((action, ret, response) -> {
					if (HttpCode.VENDOR_LOGIN.equals(action)) {
						if (ret == 0) {
							getVendorInfo();
						}
					}
					if (mRequestResultListener != null) {
						mRequestResultListener.onResult(action, ret, response);
					}
				});
				initPresenter.init(vendorName);
			}
		}

	}

	public void startWebPing() {
		WebDataSource.getInstance().startPing();
	}
	public void stopWebPing(){
		WebDataSource.getInstance().stopPing();
	}

	@SuppressWarnings("ConstantConditions")
	private void getVendorInfo() {
		Map<String, Object> map = new HashMap<>();
		map.put("opr", HttpCode.GET_VENDOR_INFO);
		map.put("vendor_id", NetConfigure.getVendorId());
		NetClient.getVendorInfo(map).subscribe(new Observer<VendorInfo>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(VendorInfo vendorInfo) {
				LogUtil.e(HttpCode.GET_VENDOR_INFO, vendorInfo.toString());
				if (vendorInfo != null) {
					if (mRequestResultListener != null) {
						mRequestResultListener.onResult(HttpCode.GET_VENDOR_INFO, vendorInfo.getRet(), vendorInfo);
					}
				}
			}

			@Override
			public void onError(Throwable e) {
				Log.e(HttpCode.GET_VENDOR_INFO, e.toString());
			}

			@Override
			public void onComplete() {

			}
		});
	}

}

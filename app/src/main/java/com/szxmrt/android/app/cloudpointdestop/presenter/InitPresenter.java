package com.szxmrt.android.app.cloudpointdestop.presenter;

import android.util.Log;

import com.szxmrt.android.app.cloudpointdestop.LogUtil;
import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;
import com.szxmrt.android.app.cloudpointdestop.net.NetClient;
import com.szxmrt.android.app.cloudpointdestop.net.NetConfigure;
import com.szxmrt.android.app.cloudpointdestop.net.entity.Crypt;
import com.szxmrt.android.app.cloudpointdestop.net.entity.PublicKey;
import com.szxmrt.android.app.cloudpointdestop.net.entity.SubmitKey;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorLogin;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class InitPresenter extends BasePresenter {


	private final Map<String, Object> mPublicKeyMap;
	private final static String TAG = InitPresenter.class.getSimpleName();

	public InitPresenter() {
		mPublicKeyMap = new WeakHashMap<>();
		mPublicKeyMap.put("is_plain", "1");
		mPublicKeyMap.put("opr", HttpCode.GET_RSA_PUBKEY);
	}

	public void init(String deviceName) {
		NetClient.getPublicKey(mPublicKeyMap).subscribe(new Observer<PublicKey>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(PublicKey publicKey) {
				if (publicKey != null) {
					LogUtil.e(HttpCode.GET_RSA_PUBKEY,publicKey.toString());
					if (publicKey.getRet() == 0) {
						submitKey(publicKey.getData(), deviceName);
					}
					if (mRequestResultListener != null) {
						mRequestResultListener.onResult(HttpCode.GET_RSA_PUBKEY, publicKey.getRet(), publicKey);
					}
				}
			}

			@Override
			public void onError(Throwable e) {
				LogUtil.e(HttpCode.GET_RSA_PUBKEY, e.toString());
				if (mRequestResultListener != null) {
					mRequestResultListener.onResult(HttpCode.GET_RSA_PUBKEY, -100000, null);
				}
			}

			@Override
			public void onComplete() {

			}
		});
	}

	private void submitKey(PublicKey.Data data, String vendorName) {
		Map<String, Object> map = checkRSAKey(data);
		if (map != null) {
			NetClient.submitKey(map).subscribe(new Observer<SubmitKey>() {
				@Override
				public void onSubscribe(Disposable d) {

				}

				@Override
				public void onNext(SubmitKey submitKey) {
					if (submitKey != null) {
						LogUtil.e(HttpCode.SAVE_DATA_KEY,submitKey.toString());
						if (submitKey.getRet() == 0) {
							vendorLogin(vendorName);
						}
						if (mRequestResultListener != null) {
							mRequestResultListener.onResult(HttpCode.SAVE_DATA_KEY, submitKey.getRet(), submitKey);
						}
					}
				}

				@Override
				public void onError(Throwable e) {
					Log.e(HttpCode.SAVE_DATA_KEY, e.toString());
					if (mRequestResultListener != null) {
						mRequestResultListener.onResult(HttpCode.SAVE_DATA_KEY, -100000, null);
					}
				}

				@Override
				public void onComplete() {

				}
			});
		}
	}

	@SuppressWarnings("ConstantConditions")
	private void vendorLogin(String vendorName) {
		Map<String, Object> map = new HashMap<>();
		map.put("opr", HttpCode.VENDOR_LOGIN);
		map.put("vendor_num", vendorName);
		NetClient.vendorLogin(map).subscribe(new Observer<VendorLogin>() {
			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onNext(VendorLogin vendorLogin) {
				LogUtil.e(HttpCode.VENDOR_LOGIN, vendorLogin.toString());
				if (vendorLogin != null) {
					if (vendorLogin.getRet() == 0) {
						NetConfigure.setVendorId(vendorLogin.getData().getVendorId());
					}
					if (mRequestResultListener != null) {
						mRequestResultListener.onResult(HttpCode.VENDOR_LOGIN, vendorLogin.getRet(), vendorLogin);
					}
				}
			}

			@Override
			public void onError(Throwable e) {
				LogUtil.e(HttpCode.VENDOR_LOGIN, e.toString());
				if (mRequestResultListener != null) {
					mRequestResultListener.onResult(HttpCode.VENDOR_LOGIN, -100000, null);
				}
			}

			@Override
			public void onComplete() {

			}
		});
	}

	private Map<String, Object> checkRSAKey(PublicKey.Data data) {
		String publicKey = data.getPublicKey();
		if (null == publicKey || publicKey.equals("")) {
			Log.e("publicKey", "data error, public key null");
			return null;
		}
		StringBuilder keyString = new StringBuilder();
		for (String s : publicKey.trim().split("[\r\n]+")) {
			if (s.startsWith("-----")) {
				continue;
			}
			keyString.append(s.trim());
		}
		String keyEnc = Crypt.RSA.Encrypt(keyString.toString(), NetConfigure.getDataKey());
		if (null == keyEnc || keyEnc.equals("")) {
			Log.e("publicKey", "ras encrypt err");
			return null;
		}
		Map<String, Object> map = new WeakHashMap<>();
		map.put("opr", HttpCode.SAVE_DATA_KEY);
		map.put("is_plain", "1");
		map.put("token", NetConfigure.getToken());
		map.put("key_enc", keyEnc);
		return map;
	}
}

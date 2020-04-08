package com.szxmrt.android.app.cloudpointdestop.websocket;


import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.szxmrt.android.app.cloudpointdestop.net.HttpCode;
import com.szxmrt.android.app.cloudpointdestop.net.NetConfigure;
import com.szxmrt.android.app.cloudpointdestop.net.entity.Crypt;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class WebDataSource {

	private URI mServiceURI;
	private MyWebSocketClient mClient;
	private static WebDataSource WEB_DATA_SOURCE;
	private final Map<String, Object> mPingString = new HashMap<>();
	private final Map<String, String> mHeaderMap = new HashMap<>();
	private static String TAG = WebDataSource.class.getSimpleName();
	private final AtomicLong mAtomicLong = new AtomicLong(0);


	//private final String mPingString = "{\"Timestamp\":true}";
	private WebDataSource() {
		mPingString.put("Timestamp", true);
		mHeaderMap.put("Origin", "rockyshi://13554992610");
		try {
			String url = NetConfigure.getSocketUrl();
			if (url != null)
				mServiceURI = new URI(url);
		} catch (URISyntaxException e) {
			Log.e(TAG, e.toString());
		}
	}

	public static WebDataSource getInstance() {
		if (WEB_DATA_SOURCE == null) {
			synchronized (WebDataSource.class) {
				if (WEB_DATA_SOURCE == null) {
					WEB_DATA_SOURCE = new WebDataSource();
				}
			}
		}
		return WEB_DATA_SOURCE;
	}

	public void startPing() {
		ping();
	}

	public void stopPing() {
		if (mThread != null) {
			mThread.interrupt();
			mThread = null;
		}
		if (mClient != null) {
			mClient.close();
		}
	}

	private Thread mThread;

	private void ping() {
		if (mThread != null) {
			mThread.interrupt();
			mThread = null;
		}
		mThread = new Thread() {
			@Override
			public void run() {
				super.run();
				while (true) {
					try {
						if (mClient == null || mClient.isClosed()) {
							connect();
						} else {
							try {
								call(HttpCode.CMD_PING, JSONObject.toJSONString(mPingString), true);
							} catch (Exception e) {
								Log.e("call", e.toString());
							}
						}
						Thread.sleep(1000 * 30);
					} catch (InterruptedException e) {
						e.printStackTrace();
						break;
					}
				}
			}
		};
		mThread.start();
	}

	private void connect() {
		if (mClient == null || mClient.isClosed()) {
			try {
				if (mServiceURI == null)
					mServiceURI = new URI(NetConfigure.getSocketUrl());
				mClient = new MyWebSocketClient(mServiceURI, new Draft_17(), mHeaderMap, 5000);
				mClient.connect();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	private void call(String name, String param, boolean once) throws Exception {
		//String data = Crypt.AES.Encrypt(mDataKey, param);
		String sign = Crypt.Md5(param + NetConfigure.getDataKey());
		WebRequest webRequest = new WebRequest();
		webRequest.setToken(NetConfigure.getToken());
		webRequest.setSign(sign);
		webRequest.setEncmode("");
		webRequest.setIsPlain(0);
		webRequest.setId(Util.getReqId());
		webRequest.setName(name);
		webRequest.setParam(param);
		webRequest.setOnce(once);
		//LogUtil.e("aaaaaa",webRequest.toString());
		mClient.send(webRequest.toString());
	}

	/**
	 * 上报终端信息
	 */
	private void cmdTerminfo() {
		try {
			String param = "{\"token\":\"" + NetConfigure.getToken() + "\"," +
					"\"term_time\":" + getCurrentTimeSecond() + "}";
			call(HttpCode.CMD_TERMINF0, param, true);
		} catch (Exception e) {
			Log.e(TAG, "terminfo error");
			Log.e(TAG, e.toString());
		}
	}

	private class MyWebSocketClient extends WebSocketClient {

		private MyWebSocketClient(URI uri, Draft draft, Map<String, String> map, int i) {
			super(uri, draft, map, i);
		}

		@Override
		public void onOpen(ServerHandshake serverHandshake) {
			Log.e(TAG, serverHandshake.getHttpStatusMessage());
		}

		@Override
		public void onMessage(String msg) {
		}

		@Override
		public void onClose(int i, String s, boolean b) {
			if (mClient != null) {
				mClient.close();
				mClient = null;
			}
			// -1 无网络， 1000 连接断开， 1006 网络断开
			Log.e(TAG, i + "   " + s + "   " + b);
		}

		@Override
		public void onError(Exception e) {
			if (mClient != null) {
				mClient.close();
				mClient = null;
			}
			Log.e(TAG, e.toString());
		}
	}

	private long getCurrentTimeSecond() {
		return System.currentTimeMillis() / 1000;
	}
}

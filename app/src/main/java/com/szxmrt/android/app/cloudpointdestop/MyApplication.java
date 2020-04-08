package com.szxmrt.android.app.cloudpointdestop;

import android.app.Application;
import android.content.Context;

import com.cloudpoint.plugins.network.NetworkStats;
import com.cloudpoint.plugins.sdk.adv.CPAdvSdk;
import com.cloudpoint.plugins.sdk.adv.IAdvPlayer;
import com.cloudpoint.shell.common.device.DeviceAddress;
import com.cloudpoint.shell.common.device.DeviceLatitudeLongitude;
import com.cloudpoint.shell.common.device.DeviceLocation;
import com.google.gson.Gson;
import com.szxmrt.android.app.cloudpointdestop.net.entity.VendorInfo;

import java.util.Random;


public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

	private static boolean INITIALIZED;
	private static Context GLOBAL_CONTEXT;

	@Override
	public void onCreate() {
		super.onCreate();
		GLOBAL_CONTEXT = getApplicationContext();
		Thread.setDefaultUncaughtExceptionHandler(this);
		LogUtil.setDebug(false);
		// 设置是否输出debug日志
		CPAdvSdk.setDebug(false);
		INITIALIZED = CPAdvSdk.init(MyApplication.this, "201", true, true);
	}

	public static Context getContext() {
		return GLOBAL_CONTEXT;
	}


	private static void printUsage() {
		new Thread(new Runnable() {
			@Override
			public void run() {

				while (true) {

					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					long ts = System.currentTimeMillis();
					NetworkStats.DataUsage usage = CPAdvSdk.getDataUsage();

					long bytes = usage.getRx() + usage.getTx();

					long k = 1024;
					long m = 1024 * k;
					long g = 1024 * m;
					double u = 0.0f;
					String data = "";
					if (bytes / g > 1) {
						u = bytes * 1.0 / g;
						data = String.format(" usage : %5.2f g , used : %5d ms", u, (System.currentTimeMillis() - ts));
					} else if (bytes / m > 1) {
						u = bytes * 1.0 / m;
						data = String.format(" usage : %5.2f m , used : %5d ms", u, (System.currentTimeMillis() - ts));
					} else if (bytes / k > 1) {
						u = bytes / k;

						data = String.format(" usage : %5.2f k  , used : %5d ms", u,
								(System.currentTimeMillis() - ts));
					} else {
						u = bytes;
						data = String.format(" usage : %5.2f b , used : %5d ms", u, (System.currentTimeMillis() - ts));

					}
					System.out.println(data);
				}

			}
		}).start();
	}


	private void locationWithBaiduApi() {
		//6. 设置 location信息
		String loc = null; // 使用集成百度定位sdk时，将loc设置为空
		CPAdvSdk.setLocation(loc);
	}

	public static void initAdvert(VendorInfo.Data.Info info) {
		if (INITIALIZED) {
			// 检测是否初始化播放器
			CPAdvSdk.setPlayerListener(new IAdvPlayer() {
				@Override
				public void onPlayerError() {
					System.out.println("播放器未加载！");
				}
			});
			// 处理计费或手动播放线上广告
			CPAdvSdk.setAdvertisementEvent(new AdvEventListener());
			CPAdvSdk.enableRoudPlay(true);
			locationWithoutBaiduApi(info);
			CPAdvSdk.enableAdv(true);
			//printUsage();
		}
	}


	private static void locationWithoutBaiduApi(VendorInfo.Data.Info info) {
		//设置位置信息
		DeviceAddress address = new DeviceAddress();
		String addressString = "中国" + info.getProvince() + info.getCity() + info.getArea() + info.getAddress();
		address.setAddress(addressString);
		address.setCity(info.getCity());
		address.setDistrict(info.getArea());
		address.setLocationDescrible(info.getDetail_address());
		address.setProvince(info.getProvince());
		address.setStreet(info.getAddress());
/*		LogUtil.e("aaaa",addressString);
		LogUtil.e("aaaa",info.getCity());
		LogUtil.e("aaaa",info.getArea());
		LogUtil.e("aaaa",info.getDetail_address());
		LogUtil.e("aaaa",info.getProvince());
		LogUtil.e("aaaa",info.getAddress());
		LogUtil.e("aaaa",info.getLongitude()+"");
		LogUtil.e("aaaa",info.getLatitude()+"");*/

		DeviceLatitudeLongitude gps = new DeviceLatitudeLongitude();
		gps.setCoorType("bd09ll");
		gps.setErrorCode(161);
		gps.setLangtitude(info.getLongitude());
		gps.setLatitude(info.getLatitude());
		//gps.setRadius(0.0f);
		Random random = new Random();
		gps.setRadius(30 + random.nextInt(20) + random.nextFloat());

/*		address.setAddress("中国北京市朝阳区北苑路229号");
		address.setCity("北京市");
		address.setDistrict("朝阳区");
		address.setLocationDescrible("在金泉港附近");
		address.setProvince("北京市");
		address.setStreet("北苑路");

		DeviceLatitudeLongitude gps = new DeviceLatitudeLongitude();
		gps.setCoorType("bd09ll");
		gps.setErrorCode(161);
		gps.setLangtitude(116.423292);
		gps.setLatitude(40.010727);
		gps.setRadius(50.499428f);*/

		DeviceLocation location = new DeviceLocation(gps, address);
		//
		// {"location":{"address":"中国北京市朝阳区北苑路229号","city":"北京市","disrict":"朝阳区","location_desc":"在金泉港附近",
		// "province":"北京市","street":"北苑路"},"gps":{"coor_type":"bd09ll","error_code":161,"langtitude":116.423292,
		// "latitude":40.010727,"radius":50.499428}}

		CPAdvSdk.setLocation(new Gson().toJson(location));
		//System.out.println("loc:" + new Gson().toJson(location));
	}

	@Override
	@SuppressWarnings("NullableProblems")
	public void uncaughtException(Thread thread, Throwable throwable) {

	}
}

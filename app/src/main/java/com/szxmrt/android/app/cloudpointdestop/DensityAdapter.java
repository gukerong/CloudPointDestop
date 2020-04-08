package com.szxmrt.android.app.cloudpointdestop;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

public class DensityAdapter {

	public static int MATCH_WIDTH = 0;
	public static int MATCH_HEIGHT = 1;
	public static int MATCH_UNIT_DP = 0;
	public static int MATCH_UNIT_PT = 1;
	public static int STANDARD_DPI = 160;
	private static int DENSITY_STATUS = 0;         // 0 初始状态，1 已经进行适配

	private static float DEFAULT_XDPI;
	private static float DEFAULT_DENSITY;
	private static int DEFAULT_DENSITY_DPI;
	private static int DEFAULT_WIDTH_PIXELS;
	private static int DEFAULT_HEIGHT_PIXELS;
	private static float DEFAULT_SCALE_DENSITY;
	private static Application.ActivityLifecycleCallbacks CALLBACK;

	@SuppressWarnings("all")
	public static void matchDensity(final Application application, final float targetSize, final int matchUnit,
	                                final int orientation) {

		DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
		DEFAULT_DENSITY = displayMetrics.density;
		DEFAULT_DENSITY_DPI = displayMetrics.densityDpi;
		DEFAULT_WIDTH_PIXELS = displayMetrics.widthPixels;
		DEFAULT_HEIGHT_PIXELS = displayMetrics.heightPixels;
		DEFAULT_SCALE_DENSITY = displayMetrics.scaledDensity;
		DEFAULT_XDPI = displayMetrics.xdpi;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			application.registerComponentCallbacks(new ComponentCallbacks() {
				@Override
				public void onConfigurationChanged(Configuration newConfig) {
					if (newConfig != null && newConfig.fontScale > 0) {
						DEFAULT_SCALE_DENSITY = application.getResources().getDisplayMetrics().scaledDensity;
					}
				}

				@Override
				public void onLowMemory() {

				}
			});

		}

		CALLBACK = new Application.ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				if (activity != null) {
					if (matchUnit == MATCH_UNIT_PT) {
						matchDensityByPt(activity, targetSize, orientation);
					} else {
						matchDensityByDp(activity, targetSize, orientation);
					}
				}
			}

			@Override
			public void onActivityStarted(Activity activity) {

			}

			@Override
			public void onActivityResumed(Activity activity) {

			}

			@Override
			public void onActivityPaused(Activity activity) {

			}

			@Override
			public void onActivityStopped(Activity activity) {

			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

			}

			@Override
			public void onActivityDestroyed(Activity activity) {

			}
		};

		application.registerActivityLifecycleCallbacks(CALLBACK);

	}

	/**
	 * 使用 dp 作为适配单位（适合在新项目中使用，在老项目中使用会对原来既有的 dp 值产生影响）
	 * dp 与 px 之间的换算:
	 * px = density * dp
	 * density = dpi / 160
	 * px = dp * (dpi / 160)
	 *
	 * @param context     context
	 * @param targetSize  设计图的宽/高（单位: dp）
	 * @param orientation 适配基准
	 */
	private static void matchDensityByDp(Context context, float targetSize, int orientation) {
		DENSITY_STATUS = 1;
		float targetDensity;
		if (orientation == MATCH_WIDTH) {
			targetDensity = (float) DEFAULT_WIDTH_PIXELS / targetSize;
		} else {
			targetDensity = (float) DEFAULT_HEIGHT_PIXELS / targetSize;
		}
		int targetDpi = (int) (targetDensity * STANDARD_DPI);
		float targetScaleDensity = targetDensity * (DEFAULT_SCALE_DENSITY / DEFAULT_DENSITY);
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		displayMetrics.density = targetDensity;
		displayMetrics.densityDpi = targetDpi;
		displayMetrics.scaledDensity = targetScaleDensity;
	}

	/**
	 * 使用 pt 作为适配单位（因为 pt 比较冷门，新老项目皆适合使用；也可作为 dp 适配的补充，
	 * 在需要同时适配宽度和高度时，使用 pt 来适配 dp 未适配的宽度或高度）
	 * pt 转 px 算法: pt * metrics.xdpi * (1.0f/72)
	 *
	 * @param context     context
	 * @param targetSize  设计图的宽/高（单位: pt）
	 * @param orientation 适配基准
	 */
	private static void matchDensityByPt(@NonNull final Context context, float targetSize, int orientation) {
		DENSITY_STATUS = 1;
		final float targetXdpi;
		if (orientation == MATCH_WIDTH) {
			targetXdpi = DEFAULT_WIDTH_PIXELS * 72f / targetSize;
		} else {
			targetXdpi = DEFAULT_HEIGHT_PIXELS * 72f / targetSize;
		}
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		displayMetrics.xdpi = targetXdpi;
	}

	/**
	 * 重置适配信息，取消适配
	 *
	 * @param context   context
	 * @param matchUnit 需要取消适配的单位
	 */
	public static void resetMatch(Context context, int matchUnit) {
		if (DENSITY_STATUS == 0) return;
		final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		if (matchUnit == MATCH_UNIT_DP) {
			if (displayMetrics.density != DEFAULT_DENSITY) {
				displayMetrics.density = DEFAULT_DENSITY;
			}
			if (displayMetrics.densityDpi != DEFAULT_DENSITY_DPI) {
				displayMetrics.densityDpi = DEFAULT_DENSITY_DPI;
			}
			if (displayMetrics.scaledDensity != DEFAULT_SCALE_DENSITY) {
				displayMetrics.scaledDensity = DEFAULT_SCALE_DENSITY;
			}
		} else if (matchUnit == MATCH_UNIT_PT) {
			if (displayMetrics.xdpi != DEFAULT_XDPI) {
				displayMetrics.xdpi = DEFAULT_XDPI;
			}
		}
	}
}

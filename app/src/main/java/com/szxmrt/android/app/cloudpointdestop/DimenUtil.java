package com.szxmrt.android.app.cloudpointdestop;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.WindowManager;



public class DimenUtil {
	public static int getScreenWidth(Context context) {
		final Resources resources = context.getResources();
		final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		return displayMetrics.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		final Resources resources = context.getResources();
		final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		return displayMetrics.heightPixels;
	}

	public static int getTotalScreenHeight(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context
				.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		if (windowManager == null) return 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
			return displayMetrics.heightPixels;
		} else {
			windowManager.getDefaultDisplay().getMetrics(displayMetrics);
			return displayMetrics.heightPixels + getVirtualBarHeight() + getStatusBarHeight(context);
		}
	}

	/**
	 * 获取虚拟菜单栏高度
	 *
	 * @return 高度
	 */

	public static int getVirtualBarHeight() {
		Resources resource = MyApplication.getContext().getResources();
		int resourceId = resource.getIdentifier("navigation_bar_height", "dimen", "android");
		return resource.getDimensionPixelSize(resourceId);
	}

	/**
	 * 获取状态栏高度
	 *
	 * @return 高度
	 */

	public static int getStatusBarHeight(Context context) {
		Resources resource = context.getResources();
		int resourceId = resource.getIdentifier("status_bar_height", "dimen", "android");
		return resource.getDimensionPixelSize(resourceId);
	}

	/**
	 * 判断是否有虚拟菜单栏
	 */
	public static boolean isNavigationBarAvailable() {
		boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
		boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
		return (!(hasBackKey && hasHomeKey));
	}

	/**
	 * 判断软键盘是否显示
	 *
	 * @return boolean
	 */
	public static boolean isSoftShowing(AppCompatActivity activity) {
		//获取当前屏幕内容的高度
		int screenHeight = activity.getWindow().getDecorView().getHeight();
		//获取View可见区域的bottom
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		if (isNavigationBarAvailable())
			return screenHeight - rect.bottom - getVirtualBarHeight() != 0;
		else
			return screenHeight - rect.bottom != 0;
	}

	public static int dp2px(int dpValue) {
		float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5F);
	}

	public static int px2dp(float pxValue) {
		final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}

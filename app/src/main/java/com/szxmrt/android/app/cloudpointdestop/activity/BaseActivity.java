package com.szxmrt.android.app.cloudpointdestop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.szxmrt.android.app.cloudpointdestop.DensityAdapter;
import com.szxmrt.android.app.cloudpointdestop.DimenUtil;

public abstract class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		DensityAdapter.matchDensity(getApplication(), 1080, DensityAdapter.MATCH_UNIT_DP, DensityAdapter.MATCH_WIDTH);
		super.onCreate(savedInstanceState);
		halfFullScreen(this);
		if (getLayout() != null) {
			setContentView(getLayout());
		}
		initVariable();
		initOnclick();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	abstract Integer getLayout();

	abstract void initVariable();

	abstract void initOnclick();

	@SuppressWarnings("ConstantConditions")
	void checkInputFromStatus() {
		boolean b = DimenUtil.isSoftShowing(this);
		InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (b) {
			imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	// 半全屏（半透明状态栏 + 隐藏虚拟按键）
	public static void halfFullScreen(AppCompatActivity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.setStatusBarColor(0x80868686);
			View decorView = window.getDecorView();
			int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_IMMERSIVE
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			decorView.setSystemUiVisibility(options);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = activity.getWindow();
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}
}

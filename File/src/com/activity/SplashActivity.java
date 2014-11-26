package com.activity;

import android.os.Bundle;
import base.BaseActivity;

import com.example.test2.R;
import com.lidroid.xutils.ViewUtils;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);
		ViewUtils.inject(this);
	}
}

package com.view;

import widget.utils.LogUtils;
import android.os.Bundle;
import base.BaseActivity;

import com.example.test2.R;

public class MainActivity extends BaseActivity {
	private String MAINACTIVITY_TAG = getTag();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LogUtils.i(MAINACTIVITY_TAG, "到这了");
	}

}

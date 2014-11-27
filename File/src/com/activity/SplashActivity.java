package com.activity;

import java.util.ArrayList;

import widget.utils.LogUtils;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import base.BaseActivity;

import com.example.test2.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.view.linkedviewpager.LinkedViewPager.LinkedViewPager;
import com.view.linkedviewpager.LinkedViewPager.MyLinkedPagerAdapter;

public class SplashActivity extends BaseActivity {
	private String SPLASHACTIVITY_TAG = getTag();

	@ViewInject(R.id.pager)
	private LinkedViewPager mPager;
	@ViewInject(R.id.main_scrolllayout)
	private LinkedViewPager mFramePager;

	private ArrayList<View> mPageViews;
	private MyLinkedPagerAdapter mPagerAdapter;

	private ArrayList<View> mFramePageViews;
	private MyLinkedPagerAdapter mFramePagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);
		ViewUtils.inject(this);
		LogUtils.i(SPLASHACTIVITY_TAG, "启动画面");
		initView();

	}

	private void initView() {
		mPageViews = new ArrayList<View>();
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view1 = inflater.inflate(R.layout.transparent_layer, null);
		initPagerView(view1, "这里是购物者的天堂");
		View view2 = inflater.inflate(R.layout.transparent_layer, null);
		initPagerView(view2, "这里是吃货的圣地");
		View view3 = inflater.inflate(R.layout.transparent_layer, null);
		initPagerView(view3, "我们会把服务做的越来越好");
		View view4 = inflater.inflate(R.layout.transparent_layer, null);
		initPagerView(view4, "在这里，你可以轻松一键购票");
		View view5 = inflater.inflate(R.layout.transparent_layer, null);
		initPagerView(view5, "这是一种专为你而定制的简约生活");

		mPageViews.add(view1);
		mPageViews.add(view2);
		mPageViews.add(view3);
		mPageViews.add(view4);
		mPageViews.add(view5);
		mPagerAdapter = new MyLinkedPagerAdapter(mPageViews);
		mPager.setAdapter(mPagerAdapter);

		// 添加帧视图
		mFramePageViews = new ArrayList<View>();
		View frameView1 = inflater.inflate(R.layout.transparent_layer_image,
				null);
		initFramePagerView(frameView1, R.drawable.frame_view1);
		View frameView2 = inflater.inflate(R.layout.transparent_layer_image,
				null);
		initFramePagerView(frameView2, R.drawable.frame_view2);
		View frameView3 = inflater.inflate(R.layout.transparent_layer_image,
				null);
		initFramePagerView(frameView3, R.drawable.frame_view3);
		View frameView4 = inflater.inflate(R.layout.transparent_layer_image,
				null);
		initFramePagerView(frameView4, R.drawable.frame_view4);
		View frameView5 = inflater.inflate(R.layout.transparent_layer_image,
				null);
		initFramePagerView(frameView5, R.drawable.frame_view5);
		mFramePageViews.add(frameView1);
		mFramePageViews.add(frameView2);
		mFramePageViews.add(frameView3);
		mFramePageViews.add(frameView4);
		mFramePageViews.add(frameView5);

		mFramePagerAdapter = new MyLinkedPagerAdapter(mFramePageViews);
		mFramePager.setAdapter(mFramePagerAdapter);

		mPager.setFollowViewPager(mFramePager);

		ViewUtils.inject(this, view5);
		button.setVisibility(View.VISIBLE);
		button.setClickable(true);
	}

	@ViewInject(R.id.button)
	private Button button;

	@OnClick(R.id.button)
	public void click(View view) {
		LogUtils.i(SPLASHACTIVITY_TAG, "成功了");
		openActivity(MainActivity.class);
	}

	public void initFramePagerView(View frameView, int drawable) {
		ImageView image = (ImageView) frameView.findViewById(R.id.image);
		image.setImageResource(drawable);
	}

	public void initPagerView(View view, String text) {
		TextView textView1 = (TextView) view.findViewById(R.id.text);
		textView1.setText(text);
	}
}

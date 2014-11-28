package com.fragment;

import widget.utils.LogUtils;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import base.BaseFragment;

import com.example.test2.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.view.autoscrollviewpager.AutoScrollViewPager;
import com.view.horizontalscrollview.CenterLockHorizontalScrollview;

public class MainPageFragment extends BaseFragment {
	private final static String MAINPAGE = "MAINPAGE";
	@ViewInject(R.id.advertisement)
	private AutoScrollViewPager scrollViewPager;

	@ViewInject(R.id.swipe_container)
	private SwipeRefreshLayout swipe_container;

	@ViewInject(R.id.scrollView)
	private CenterLockHorizontalScrollview scrollview;

	@ViewInject(R.id.listview)
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mainpage, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewUtils.inject(this, view);
		setRefresh();
	}

	/**
	 * 设置刷新
	 */
	private void setRefresh() {
		swipe_container.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		swipe_container.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipe_container.setRefreshing(true);
				// loadData();
			}
		});
	}

	/**
	 * 点击事件
	 * 
	 * @param view
	 */
	@OnClick({ R.id.meishi, R.id.dianying, R.id.yule, R.id.jiudian,
			R.id.meirong, R.id.wanggou, R.id.news, R.id.all })
	public void click(View view) {
		switch (view.getId()) {
		case R.id.meishi:
			LogUtils.i(MAINPAGE, "1");

			break;
		case R.id.dianying:
			LogUtils.i(MAINPAGE, "2");
			break;
		case R.id.yule:
			LogUtils.i(MAINPAGE, "3");
			break;
		case R.id.jiudian:
			LogUtils.i(MAINPAGE, "4");
			break;
		case R.id.meirong:
			LogUtils.i(MAINPAGE, "5");
			break;
		case R.id.wanggou:
			LogUtils.i(MAINPAGE, "6");
			break;
		case R.id.news:
			LogUtils.i(MAINPAGE, "7");
			break;
		case R.id.all:
			LogUtils.i(MAINPAGE, "8");
			break;
		}
	}

}

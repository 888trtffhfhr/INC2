package com.activity;

import java.util.ArrayList;
import java.util.List;

import widget.utils.LogUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import base.BaseActivity;

import com.example.test2.R;
import com.fragment.MainPageFragment;
import com.fragment.MoreFragment;
import com.fragment.MyLifeFragment;
import com.fragment.NearbyFragment;
import com.fragment.ShopFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.view.customview.popwindow.PopWindowUtils;
import com.view.special.ResideMenu.ResideMenu;
import com.view.special.ResideMenu.ResideMenuItem;

public class MainActivity extends BaseActivity implements OnClickListener {
	private String MAINACTIVITY_TAG = getTag();

	@ViewInject(R.id.radiogroup)
	private RadioGroup radioGroup;

	@ViewInject(R.id.location)
	private Button location;

	@ViewInject(R.id.head)
	private View view;

	private ResideMenu menu;
	private ResideMenuItem MainPageItem;
	private ResideMenuItem NearbyItem;
	private ResideMenuItem MyShopItem;
	private ResideMenuItem ShopKeeperItem;
	private ResideMenuItem SettingItem;

	private MainPageFragment mainPageFragment;
	private NearbyFragment nearbyFragment;
	private ShopFragment shopFragment;
	private MyLifeFragment myLifeFragment;
	private MoreFragment moreFragment;
	private static int index = 0;
	private static int preIndex = 0;

	private List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);

		LogUtils.i(MAINACTIVITY_TAG, "到这了");
		initFragment();
		InitMenu();
		radioGroup.setOnCheckedChangeListener(new onCheckListener(this));

		this.addFragment(R.id.container, mainPageFragment);
	}

	/**
	 * 初始化各个Fragment对象
	 */
	private void initFragment() {
		mainPageFragment = new MainPageFragment();
		nearbyFragment = new NearbyFragment();
		shopFragment = new ShopFragment();
		myLifeFragment = new MyLifeFragment();
		moreFragment = new MoreFragment();
		fragments = new ArrayList<Fragment>();
		fragments.add(mainPageFragment);
		fragments.add(nearbyFragment);
		fragments.add(shopFragment);
		fragments.add(myLifeFragment);
		fragments.add(moreFragment);

	}

	/**
	 * 初始化侧滑菜单
	 */
	private void InitMenu() {
		menu = new ResideMenu(this);
		menu.attachToActivity(this);
		// menu.setScaleValue(0.8f);
		menu.setBackground(R.drawable.giftcard_tuan);
		MainPageItem = new ResideMenuItem(this,
				R.drawable.channel_coupon_pressed, "首页");
		MyShopItem = new ResideMenuItem(this, R.drawable.channel_gift_pressed,
				"团购");
		NearbyItem = new ResideMenuItem(this, R.drawable.channel_flow_up, "定位");
		ShopKeeperItem = new ResideMenuItem(this,
				R.drawable.channel_m_card_pressed, "购物");
		SettingItem = new ResideMenuItem(this, R.drawable.channel_push_pressed,
				"设置");

		MainPageItem.setOnClickListener(this);
		MyShopItem.setOnClickListener(this);
		ShopKeeperItem.setOnClickListener(this);
		SettingItem.setOnClickListener(this);
		NearbyItem.setOnClickListener(this);
		menu.addHeaderView(R.drawable.serve_life);

		menu.addMenuItem(MainPageItem, ResideMenu.DIRECTION_LEFT);
		menu.addMenuItem(NearbyItem, ResideMenu.DIRECTION_LEFT);
		menu.addMenuItem(MyShopItem, ResideMenu.DIRECTION_LEFT);
		menu.addMenuItem(ShopKeeperItem, ResideMenu.DIRECTION_LEFT);
		menu.addMenuItem(SettingItem, ResideMenu.DIRECTION_LEFT);
		menu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

	}

	/**
	 * 点击事件
	 * 
	 * @param view
	 */
	@OnClick({ R.id.menu_button, R.id.location, R.id.search, R.id.code })
	public void click(View view) {
		switch (view.getId()) {
		case R.id.menu_button:
			LogUtils.i(MAINACTIVITY_TAG, "9");
			menu.openMenu(ResideMenu.DIRECTION_LEFT);
			break;
		case R.id.location:
			LogUtils.i(MAINACTIVITY_TAG, "10");
			ArrayList<String> list = new ArrayList<String>();
			list.add("连接WIFI进行定位(推荐)");
			list.add("开启GPS定位");
			list.add("手动选取城市定位");
			PopupWindow popWindow = PopWindowUtils.createPopWindow(list, this,
					new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							switch (position) {
							case 0:
								LogUtils.i(MAINACTIVITY_TAG, "定位1");
								break;
							case 1:
								LogUtils.i(MAINACTIVITY_TAG, "定位2");
								break;
							case 2:
								LogUtils.i(MAINACTIVITY_TAG, "定位3");
								break;
							default:
								break;
							}
						}
					});
			location.setPressed(true);
			popWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0,
					-getScreenHeight() / 2 + location.getHeight() + 200);
			break;
		case R.id.search:
			LogUtils.i(MAINACTIVITY_TAG, "11");
			break;
		case R.id.code:
			LogUtils.i(MAINACTIVITY_TAG, "12");
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * radioButton点击事件
	 * 
	 * @author edison
	 * 
	 */
	private class onCheckListener implements OnCheckedChangeListener {
		MainActivity mainActivity;

		public onCheckListener(MainActivity mainActivity) {
			this.mainActivity = mainActivity;
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkID) {
			if (checkID == R.id.shopping) {
				index = 1;
			} else if (checkID == R.id.nearby) {
				index = 2;
			} else if (checkID == R.id.account) {
				index = 3;
			} else if (checkID == R.id.more) {
				index = 4;
			}
			LogUtils.i(MAINACTIVITY_TAG, "当前索引是" + index);
			switchFragment(index);
			preIndex = index;
		}
	}

	/**
	 * 侧滑菜单点击监听事件
	 */
	@Override
	public void onClick(View view) {
		if (view == MainPageItem) {
			LogUtils.i(MAINACTIVITY_TAG, "0");
			index = 0;

		} else if (view == NearbyItem) {
			LogUtils.i(MAINACTIVITY_TAG, "1");
			index = 1;

		} else if (view == MyShopItem) {
			LogUtils.i(MAINACTIVITY_TAG, "2");
			index = 2;

		} else if (view == ShopKeeperItem) {

			LogUtils.i(MAINACTIVITY_TAG, "3");
			index = 3;
		} else if (view == SettingItem) {

			LogUtils.i(MAINACTIVITY_TAG, "4");
			index = 4;
		}
		switchFragment(index);
		menu.closeMenu();
	}

	/**
	 * 调用父类的切换Fragment
	 * 
	 * @param index
	 */
	public void switchFragment(int index) {
		this.switchFragment(R.id.container, fragments.get(preIndex),
				fragments.get(index));
		preIndex = index;
	}

}

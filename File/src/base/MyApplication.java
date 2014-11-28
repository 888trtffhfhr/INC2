package base;

import java.util.LinkedList;
import java.util.List;

import widget.utils.NetChangeObserver;
import widget.utils.NetWorkUtil;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.lidroid.xutils.DbUtils;

public class MyApplication extends Application {
	private final static String APP_TAG = "MYAPPLICATION";
	private NetChangeObserver netChangeObserver;
	private static MyApplication myApplication;
	private List<Activity> activities;
	/**
	 * 操纵的全局数据库
	 */
	private DbUtils database;

	public static MyApplication getIntance() {
		if (myApplication == null) {
			myApplication = new MyApplication();
		}
		return myApplication;
	}

	/**
	 * 百度地图定位组件初始化
	 */

	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;

	@Override
	public void onCreate() {
		super.onCreate();
		initApp(this);
	}

	// 返回值：
	// 61 ： GPS定位结果
	// 62 ： 扫描整合定位依据失败。此时定位结果无效。
	// 63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
	// 65 ： 定位缓存的结果。
	// 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
	// 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
	// 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
	// 161： 表示网络定位结果
	// 162~167： 服务端定位失败。

	public void initApp(Application application) {
		activities = new LinkedList<Activity>();
		database = DbUtils.create(application, "config");
		ShareSDK.initSDK(application);
		mLocationClient = new LocationClient(
				application.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(
				application.getApplicationContext());

		mVibrator = (Vibrator) application.getApplicationContext()
				.getSystemService(Service.VIBRATOR_SERVICE);

		// 注册网络监听广播类
		NetworkStateReceiver.registerNetworkStateReceiver(application);
		netChangeObserver = new MeNetChangeObserver();
		NetworkStateReceiver.registerObserver(netChangeObserver);
	}

	/**
	 * 网路状态观察者
	 */

	private class MeNetChangeObserver extends NetChangeObserver {

		@Override
		public void onConnect(NetWorkUtil.NetType type) {
			super.onConnect(type);
			switch (type) {
			case wifi:
				Toast.makeText(getApplicationContext(), "当前wifi已连接",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(getApplicationContext(), "当前为移动网络,注意流量哦",
						Toast.LENGTH_SHORT).show();
				break;
			}

		}

		@Override
		public void onDisConnect() {
			super.onDisConnect();

			/**
			 * TODO
			 */

			// CustomDialog.Builder builder = new CustomDialog.Builder(this);
			// builder.setTitle("网络设置").setMessage("当前网络不可用!请设置网络");
			// builder.setPositiveButton("移动网络",
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// Intent intent = new Intent(
			// Settings.ACTION_DATA_ROAMING_SETTINGS);
			// context.startActivityForResult(intent, 0);
			// dialog.dismiss();
			// }
			// }).setNegativeButton("WIFI设置 ",
			// new DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// Intent intent = new Intent(
			// Settings.ACTION_WIFI_SETTINGS);
			// context.startActivityForResult(intent, 0);
			// dialog.dismiss();
			// }
			//
			// });
			// CustomDialog dialog = builder.create();
			// dialog.setCanceledOnTouchOutside(false);
			// dialog.setOnKeyListener(new OnKeyListener() {
			//
			// @Override
			// public boolean onKey(DialogInterface dialog, int keyCode,
			// KeyEvent event) {
			// context.finish();
			// return true;
			// }
			// });
			// dialog.show();

		}
	}

	/**
	 * 将activity加入到LinkedList中以便于统一销毁
	 * 
	 * @param activity
	 */
	public void addActivty(Activity activity) {
		if (activities != null | activities.size() >= 0) {
			if (!activities.contains(activity)) {
				activities.add(activity);
			}
		} else {
			activities.add(activity);
		}
	}

	/**
	 * 退出应用
	 */
	public void exit() {
		if (activities != null && activities.size() != 0) {
			for (Activity activity : activities) {
				activity.finish();
			}
			System.exit(0);
		}
	}

	/**
	 * 定位事件处理
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append(location.getAddrStr());
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\n网络定位地址addr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				if (location.getAddrStr() != null) {
					Log.i("BaiduLocationApi",
							"定位成功" + "当前城市是" + location.getAddrStr());
					mLocationClient.stop();
				}
			}
			Log.i("BaiduLocationApiDem", sb.toString());
		}

	}

	// public DisplayMetrics getDisplayMetrics() {
	// DisplayMetrics displayMetrics = new DisplayMetrics();
	// WindowManager windowManager = (WindowManager)
	// getSystemService(Context.WINDOW_SERVICE);
	// windowManager.getDefaultDisplay().getMetrics(displayMetrics);
	// return displayMetrics;
	// }
	//
	// /**
	// * 获取屏幕宽度
	// *
	// * @return
	// */
	// public int getScreenWidth() {
	// return getDisplayMetrics().widthPixels;
	//
	// }
	//
	// /**
	// * 获取屏幕长度
	// *
	// * @return
	// */
	// public int getScreenHeight() {
	// return getDisplayMetrics().heightPixels;
	// }
}

package base;

import java.util.LinkedList;
import java.util.List;

import widget.net.DBConfig;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import cn.sharesdk.framework.ShareSDK;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class MyApplication extends Application {
	// private static MyApplication myApplication;
	private List<Activity> activities;
	/**
	 * 操纵的全局数据库
	 */
	private DbUtils database;
	/**
	 * 配置全局的数据库变量
	 */
	private DBConfig config;

	// public static MyApplication getIntance() {
	// if (myApplication == null) {
	// myApplication = new MyApplication();
	// }
	// return myApplication;
	// }
	/**
	 * 百度地图定位组件初始化
	 */

	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;

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

	@Override
	public void onCreate() {
		super.onCreate();
		activities = new LinkedList<Activity>();
		database = DbUtils.create(this, "config");
		ShareSDK.initSDK(this);
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());

		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
	}

	/**
	 * 将首次加载的欢迎界面状态存入数据库
	 */
	public void saveFirstSplash() {
		config = new DBConfig();
		config.setFirst(false);
		try {
			database.save(config);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是第一次装App
	 * 
	 * @return
	 */
	public boolean isFirst() {
		List<DBConfig> findAll;
		try {
			findAll = database.findAll(DBConfig.class);
			if (findAll != null) {

				for (int i = 0; i < findAll.size(); i++) {
					if (!findAll.get(i).isFirst) {
						System.out.println("不是第一次");
						return false;
					} else {
					}
				}
			} else {
				return true;
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return true;
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

}

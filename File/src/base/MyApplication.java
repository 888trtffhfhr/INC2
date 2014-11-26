package base;

import java.util.LinkedList;
import java.util.List;

import widget.net.DBConfig;
import android.app.Activity;
import android.app.Application;
import cn.sharesdk.framework.ShareSDK;

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

	@Override
	public void onCreate() {
		super.onCreate();
		activities = new LinkedList<Activity>();
		database = DbUtils.create(this, "config");
		ShareSDK.initSDK(this);
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
}

package base;

import widget.net.HttpUtil;
import widget.net.IntentUtils;
import widget.toast.Crouton;
import widget.toast.Style;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

/**
 * @author 菲克
 * 
 */
public class BaseActivity extends FragmentActivity {
	private Dialog progressDialog;
	private boolean isPressed;
	private double preTime = 0;
	private FragmentTransaction transaction;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		myApplication = (MyApplication) this.getApplication();
		myApplication.addActivty(this);
		transaction = getSupportFragmentManager().beginTransaction();
	}

	public MyApplication getApplicationObject() {
		return myApplication;
	}

	@Override
	public View onCreateView(View parent, String name, Context context,
			AttributeSet attrs) {
		return super.onCreateView(parent, name, context, attrs);
	}

	/**
	 * 创建万能Bundle对象进行传值操作
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Bundle putBundleObject(String key, Object value) {
		Bundle bundle = new Bundle();
		if (value instanceof String) {
			bundle.putString(key, (String) value);
		} else if (value instanceof Integer) {
			bundle.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			bundle.putBoolean(key, (Boolean) value);
		} else {
			bundle.putParcelable(key, (Parcelable) value);
		}
		return bundle;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 
	 * 携带自定义Activity切换效果的启动Activity
	 * 
	 * @param anotherClass
	 */
	public void openActivity(Class<?> anotherClass, Bundle bundle) {
		openActivity(anotherClass, bundle, 0);
	}

	public void openActivity(Class<?> anotherClass) {
		openActivity(anotherClass, null, 0);
	}

	public void openAcitivity(Class<?> anotherClass, int requestCode) {
		openActivity(anotherClass, null, requestCode);
	}

	public void openActivity(Class<?> anotherClass, Bundle bundle,
			int requestCode) {
		Intent intent = new Intent(this, anotherClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		if (requestCode == 0) {
			IntentUtils.startPreviewActivity(this, intent, 0);
		} else {
			IntentUtils.startPreviewActivity(this, intent, requestCode);
		}
	}

	public DisplayMetrics getDisplayMetrics() {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	public int getScreenWidth() {
		return getDisplayMetrics().widthPixels;

	}

	/**
	 * 获取屏幕长度
	 * 
	 * @return
	 */
	public int getScreenHeight() {
		return getDisplayMetrics().heightPixels;
	}

	// ////////经常在activity中的操作

	/**
	 * 
	 * 监测网络状态
	 * 
	 * @return
	 */
	public boolean hasNetWork() {
		return HttpUtil.isNetworkAvailable(this);
	}

	/**
	 * 
	 * 自定义Toast
	 * 
	 * @param message
	 */
	public void showShortToast(String message) {
		Crouton.makeText(this, message, Style.CONFIRM).show();
	}

	public void showLongToast(String message, int id) {
		Crouton.makeText(this, message, Style.CONFIRM, id).show();
	}

	/**
	 * 
	 * 退回键
	 * 
	 * @param view
	 */
	public void doBack(View view) {
		onBackPressed();

	}

	/**
	 * 替换fragment
	 * 
	 * @param layoutID
	 * @param fragment
	 */
	public void replaceFragment(int layoutID, Fragment fragment) {
		transaction.replace(layoutID, fragment).commit();
	}

	/**
	 * 添加fragment
	 * 
	 * @param layoutID
	 * @param fragment
	 */
	public void addFragment(int layoutID, Fragment fragment) {

		transaction.add(layoutID, fragment).commit();
	}

	/**
	 * 隐藏单个fragment
	 * 
	 * @param fragment
	 */
	public void hideFragment(Fragment fragment) {
		transaction.hide(fragment).commit();
	}

	/**
	 * 显示单个fragment
	 * 
	 * @param fragment
	 */
	public void showFragment(Fragment fragment) {
		transaction.show(fragment).commit();
	}
	// /**
	// *
	// * 存储临时的字符串数据,类似于SharedPreference
	// *
	// * @param key
	// * @param value
	// */
	// public void setDataCache(String key, String value) {
	// if (TextUtils.isEmpty(value) && TextUtils.isEmpty(key)) {
	// ACache.get(this).put(key, value);
	// }
	// }
	//
	// /**
	// *
	// * 取出临时数据
	// *
	// * @param key
	// * @return
	// */
	// public String getDataCache(String key) {
	// return ACache.get(this).getAsString(key);
	// }
}

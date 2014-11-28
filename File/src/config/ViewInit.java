package config;

import android.graphics.Bitmap.Config;

import com.example.test2.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 用于初始化一些常用的视图控件属性
 * 
 * @author edison
 * 
 */

/**
 * 初始化BitmapUtils
 * 
 * @author edison
 * 
 */
public class ViewInit {
	public static BitmapUtils initBitmapUtils(BitmapUtils bitmapUtils) {
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Config.RGB_565);
		return bitmapUtils;
	}

}

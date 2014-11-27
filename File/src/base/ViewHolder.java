package base;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

public class ViewHolder<T> {
	private View convertView;
	private SparseArray<View> itemViewArray;
	private T mClass;
	private BitmapUtils bitmapUtils;

	private ViewHolder(BitmapUtils bitmapUtils, Context context,
			View convertView, ViewGroup parent, int layoutID) {
		this.bitmapUtils = bitmapUtils;
		itemViewArray = new SparseArray<View>();
		convertView = LayoutInflater.from(context).inflate(layoutID, null);
		convertView.setTag(this);
		this.convertView = convertView;
	}

	public void setClass(T mClass) {
		this.mClass = mClass;
	}

	@SuppressWarnings({ "rawtypes" })
	public static ViewHolder getViewHolder(BitmapUtils bitmapUtils,
			Context context, View convertView, ViewGroup parent, int layoutID) {
		if (convertView == null) {
			return new ViewHolder(bitmapUtils, context, convertView, parent,
					layoutID);
		} else {
			return (ViewHolder) convertView.getTag();
		}
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <T extends View> T getViewById(int itemID) {
		View itemView = itemViewArray.get(itemID);
		if (itemView == null) {
			itemView = convertView.findViewById(itemID);
			itemViewArray.put(itemID, itemView);
		}
		return (T) itemView;
	}

	public View getConvertView() {
		return convertView;
	}

	public Object getFieldValueByName(String fieldName) {
		try {
			Field field = mClass.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(mClass);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ViewHolder<T> setViewVisible(int itemID, int visibility) {
		View view = getViewById(itemID);
		view.setVisibility(visibility);
		return this;
	}

	public ViewHolder<T> setTextOnClickListenerById(int itemID,
			OnClickListener listener) {
		TextView view = getViewById(itemID);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHolder<T> setImageOnClickListenerById(int itemID,
			OnClickListener listener) {
		ImageView view = getViewById(itemID);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHolder<T> setText(int itemID, String fieldName) {
		TextView textView = getViewById(itemID);
		textView.setText((String) getFieldValueByName(fieldName));
		return this;
	}

	public ViewHolder<T> setImage(int itemID, String fieldName) {

		ImageView imageView = getViewById(itemID);
		// bitmapUtils.display(imageView, imageURL);
		bitmapUtils.display(imageView, (String) getFieldValueByName(fieldName),
				new BitmapLoadCallBack<ImageView>() {
					@Override
					public void onLoading(ImageView container, String uri,
							BitmapDisplayConfig config, long total, long current) {
						System.out.println(current / total * 100 + "%");
					}

					@Override
					public void onLoadCompleted(ImageView container,
							String uri, Bitmap bitmap,
							BitmapDisplayConfig config, BitmapLoadFrom from) {
						fadeInDisplay(container, bitmap);

					}

					@Override
					public void onLoadFailed(ImageView container, String uri,
							Drawable drawable) {
						System.out.println("失败");
					}
				});
		return this;
	}

	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
			android.R.color.transparent);

	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
		final TransitionDrawable transitionDrawable = new TransitionDrawable(
				new Drawable[] { TRANSPARENT_DRAWABLE,
						new BitmapDrawable(imageView.getResources(), bitmap) });
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.startTransition(500);
	}

}

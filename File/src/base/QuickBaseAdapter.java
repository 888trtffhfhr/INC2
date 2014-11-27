package base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.BitmapUtils;

/**
 * DOC::::: BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
 * 
 * listView = (ListView) findViewById(R.id.listview); bitmapUtils =
 * ViewInit.initBitmapUtils(bitmapUtils); listView.setAdapter(new
 * QuickBaseAdapter<User>(bitmapUtils, this, users,
 * R.layout.list_item).setProperty("name", R.id.textview,
 * QuickBaseAdapter.TEXT_TAG).setProperty("age", R.id.imageview,
 * QuickBaseAdapter.IMAGE_TAG));
 * 
 * @author edison
 * 
 * @param <T>
 * 
 *            这是一个抽象的Adapter基类，使用方法如上面,当需要监听相应的点击事件时,只需要继承这个抽象类,复写抽象方法 示例如上所述。。
 */
public abstract class QuickBaseAdapter<T> extends BaseAdapter {

	private Context context;
	private List<T> mClasses;
	private int layoutID;
	private BitmapUtils bitmapUtils;
	public final static int TEXT_TAG = 0;
	public final static int IMAGE_TAG = 1;
	public List<Property> properties;

	private static class Property {
		private String fieldName;
		private int itemID;
		private int tag;

		public Property(String fieldName, int itemID, int tag) {
			this.fieldName = fieldName;
			this.itemID = itemID;
			this.tag = tag;
		}

		public String getFieldName() {
			return fieldName;
		}

		public int getItemID() {
			return itemID;
		}

		public int getTag() {
			return tag;
		}
	}

	public QuickBaseAdapter<T> setProperty(String fieldName, int itemID, int tag) {
		Property property = new Property(fieldName, itemID, tag);
		properties.add(property);
		return this;
	}

	public QuickBaseAdapter(BitmapUtils bitmapUtils, Context context,
			List<T> mClasses, int layoutID) {
		this.bitmapUtils = bitmapUtils;
		this.context = context;
		this.mClasses = mClasses;
		this.layoutID = layoutID;
		this.properties = new ArrayList<QuickBaseAdapter.Property>();
	}

	public QuickBaseAdapter(Context context, List<T> mClasses, int layoutID) {
		this.context = context;
		this.mClasses = mClasses;
		this.layoutID = layoutID;
	}

	@Override
	public int getCount() {
		return mClasses == null ? 0 : mClasses.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mClasses.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder<T> viewHolder = ViewHolder.getViewHolder(bitmapUtils,
				context, convertView, arg2, layoutID);
		viewHolder.setClass(mClasses.get(position));
		setViews(viewHolder, position);
		return viewHolder.getConvertView();
	}

	@SuppressWarnings("rawtypes")
	private void setViews(ViewHolder viewHolder, final int position) {
		for (int i = 0; i < properties.size(); i++) {
			Property property = properties.get(i);
			if (property.getTag() == TEXT_TAG) {
				viewHolder.setText(property.getItemID(),
						property.getFieldName());
				viewHolder.setTextOnClickListenerById(property.getItemID(),
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								setTextViewClick(arg0, position);
							}
						});
			} else if (property.getTag() == IMAGE_TAG) {
				viewHolder.setImage(property.getItemID(),
						property.getFieldName());
				viewHolder.setImageOnClickListenerById(property.getItemID(),
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								setImageViewClick(arg0, position);
							}
						});
			}
		}
	}

	/**
	 * 
	 * 这里是文字的点击事件
	 * 
	 * @param position
	 * @return
	 */
	public abstract void setTextViewClick(View view, int position);

	/**
	 * 这里是图片的点击事件
	 * 
	 * @param position
	 * @return
	 */
	public abstract void setImageViewClick(View view, int position);

}

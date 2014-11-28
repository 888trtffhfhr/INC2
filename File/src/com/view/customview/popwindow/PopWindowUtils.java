package com.view.customview.popwindow;

import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import base.BaseActivity;

import com.example.test2.R;

public class PopWindowUtils {
	private static PopupWindow popupWindow;
	private static ListView listView;

	public static PopupWindow createPopWindow(List<String> list,
			BaseActivity activity, OnItemClickListener listener) {
		View view = LayoutInflater.from(activity).inflate(
				R.layout.popwindow_group_list, null);
		listView = (ListView) view.findViewById(R.id.lvGroup);
		GroupAdapter adapter = new GroupAdapter(activity, list);
		listView.setAdapter(adapter);
		popupWindow = new PopupWindow(view, 400, LayoutParams.WRAP_CONTENT);
		listView.setOnItemClickListener(listener);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		return popupWindow;
	}

}

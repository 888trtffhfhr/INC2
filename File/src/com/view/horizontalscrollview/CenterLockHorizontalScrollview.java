package com.view.horizontalscrollview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CenterLockHorizontalScrollview extends HorizontalScrollView {
	Context context;
	int prevIndex = 0;
	private ViewGroup parent;

	public CenterLockHorizontalScrollview(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setSmoothScrollingEnabled(true);

	}

	public void setAdapter(Context context, Adapter mAdapter) {

		try {
			fillViewWithAdapter(mAdapter);
		} catch (ZeroChildException e) {

			e.printStackTrace();
		}
	}

	private void fillViewWithAdapter(Adapter mAdapter)
			throws ZeroChildException {
		if (getChildCount() == 0) {
			throw new ZeroChildException(
					"CenterLockHorizontalScrollView must have one child");
		}
		if (getChildCount() == 0 || mAdapter == null)
			return;

		parent = (ViewGroup) getChildAt(0);

		parent.removeAllViews();

		for (int i = 0; i < mAdapter.getCount(); i++) {
			parent.addView(mAdapter.getView(i, null, parent));
		}
	}

	public void addFooter(View view, OnClickListener listener) {
		parent.addView(view);
		view.setOnClickListener(listener);
	}

	public void setCenter(int index) {

		ViewGroup parent = (ViewGroup) getChildAt(0);

		View preView = parent.getChildAt(prevIndex);
		preView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(5, 5, 5, 5);
		preView.setLayoutParams(lp);

		View view = parent.getChildAt(index);
		view.setBackgroundColor(Color.parseColor("#6699FF"));

		int screenWidth = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();

		int scrollX = (view.getLeft() - (screenWidth / 2))
				+ (view.getWidth() / 2);
		this.smoothScrollTo(scrollX, 0);
		prevIndex = index;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

	}

}

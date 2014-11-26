package widget.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewUtils {
	public static void setListViewHeightBasedOnChildren(AbsListView absListView) {
		ListAdapter listAdapter = absListView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, absListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = absListView.getLayoutParams();
		if (absListView instanceof ListView) {
			params.height = totalHeight
					+ (((ListView) absListView).getDividerHeight() * (listAdapter
							.getCount() - 1));
		} else if (absListView instanceof GridView) {
			params.height = totalHeight;
		}

		absListView.setLayoutParams(params);
	}

}

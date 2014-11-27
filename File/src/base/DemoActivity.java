package base;
//package com.example.demo27;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.ListView;
//
//import com.lidroid.xutils.BitmapUtils;
//
//public class MainActivity extends Activity {
//	private ListView listView;
//	private List<User> users;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		users = new ArrayList<User>();
//		for (int i = 0; i < 40; i++) {
//			User user = new User();
//			user.setName("item" + i);
//			user.setAge("http://su.bdimg.com/static/superplus/img/logo_white_ee663702.png");
//			users.add(user);
//		}
//		BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
//
//		listView = (ListView) findViewById(R.id.listview);
//		bitmapUtils = ViewInit.initBitmapUtils(bitmapUtils);
//		listView.setAdapter(new MainPageAdapter<User>(bitmapUtils, this, users,
//				R.layout.list_item)
//				.setProperty("name", R.id.textview, QuickBaseAdapter.TEXT_TAG)
//				.setProperty("age", R.id.imageview, QuickBaseAdapter.IMAGE_TAG)
//				.setProperty("name", R.id.textview2, 0));
//	}
//}

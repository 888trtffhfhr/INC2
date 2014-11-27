//package base;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//
//import com.lidroid.xutils.BitmapUtils;
//
//public class MainPageAdapter<T> extends QuickBaseAdapter<T> {
//
//	public MainPageAdapter(BitmapUtils bitmapUtils, Context context,
//			List<T> mClasses, int layoutID) {
//		super(bitmapUtils, context, mClasses, layoutID);
//	}
//
//	public MainPageAdapter(Context context, List<T> mClasses, int layoutID) {
//		super(context, mClasses, layoutID);
//	}
//
//	@Override
//	public void setTextViewClick(View view, int position) {
//		switch (view.getId()) {
//		case R.id.textview:
//			System.out.println("textview1" + position);
//			break;
//		case R.id.textview2:
//			System.out.println("textview2" + position);
//			break;
//		default:
//			break;
//		}
//
//	}
//
//	@Override
//	public void setImageViewClick(View view, int position) {
//		switch (view.getId()) {
//		case R.id.textview:
//			System.out.println("ImageView1" + position);
//			break;
//		case R.id.textview2:
//			System.out.println("ImageView2" + position);
//			break;
//		default:
//			break;
//		}
//	}
//
// }

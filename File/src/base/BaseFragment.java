package base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.R;

public class BaseFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * 得到一个headerView
	 * 
	 * @param layoutID
	 * @return
	 */
	public View getHeaderView(int layoutID) {
		return LayoutInflater.from(getActivity()).inflate(layoutID, null);
	}

	/**
	 * 开启下一个fragment
	 * 
	 * @param layoutID
	 * @param fragment
	 */
	public void openFragment(int layoutID, Fragment fragment) {
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(layoutID, fragment).addToBackStack(null)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	/**
	 * 关闭当前的fragment
	 */
	public void closeFragment() {
		getActivity().getSupportFragmentManager().popBackStack();
	}

	/**
	 * fragment嵌套fragment，添加子fragment
	 * 
	 * @param layoutID
	 * @param fragment
	 */
	public void addFragment(int layoutID, Fragment fragment) {
		getChildFragmentManager()
				.beginTransaction()
				.add(layoutID, fragment)
				.setCustomAnimations(R.animator.fragment_left_slide_in,
						R.animator.fragment_left_slide_out,
						R.animator.fragment_right_slide_in,
						R.animator.fragment_right_slide_out)
				.addToBackStack(null).commit();
	}

	/**
	 * fragment嵌套fragment,替换子fragment
	 * 
	 * @param layoutID
	 * @param fragment
	 */
	public void replaceFragment(int layoutID, Fragment fragment) {
		getChildFragmentManager()
				.beginTransaction()
				.replace(layoutID, fragment)
				.setCustomAnimations(R.animator.fragment_left_slide_in,
						R.animator.fragment_left_slide_out,
						R.animator.fragment_right_slide_in,
						R.animator.fragment_right_slide_out)
				.addToBackStack(null).commit();
	}

}

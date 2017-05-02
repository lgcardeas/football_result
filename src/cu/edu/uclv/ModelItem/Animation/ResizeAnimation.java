package cu.edu.uclv.ModelItem.Animation;

import cu.edu.uclv.ModelItem.ItemAdapter;
import cu.edu.uclv.ModelItem.ItemNoticia;
import cu.edu.uclv.ModelItem.ItemResult;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView.LayoutParams;

public class ResizeAnimation extends Animation {
	private View mView;
	private float mToHeight;
	private float mFromHeight;

	private float mToWidth;
	private float mFromWidth;

	private ItemAdapter mListAdapter;
	private ItemNoticia mListItem;
	private ItemResult mLisResult;

	public ResizeAnimation(ItemAdapter listAdapter, ItemNoticia listItem,
			float fromWidth, float fromHeight, float toWidth, float toHeight, View view) {
		mToHeight = toHeight;
		mToWidth = toWidth;
		mFromHeight = fromHeight;
		mFromWidth = fromWidth;
		mView = view;
		mListAdapter = listAdapter;
		mListItem = listItem;
		setDuration(200);
	}
	
	public ResizeAnimation(ItemAdapter listAdapter, ItemResult listItem,
			float fromWidth, float fromHeight, float toWidth, float toHeight, View view) {
		mToHeight = toHeight;
		mToWidth = toWidth;
		mFromHeight = fromHeight;
		mFromWidth = fromWidth;
		mView = view;
		mListAdapter = listAdapter;
		mLisResult = listItem;
		setDuration(200);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		
		float height = (mToHeight - mFromHeight) * interpolatedTime
				+ mFromHeight;
		float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
		LayoutParams p = (LayoutParams) mView.getLayoutParams();
		p.height = (int) height;
		p.width = (int) width;
		if (mListItem != null){
			mListItem.setCurrentHeight(p.height);
		}else{
			mLisResult.setCurrentHeight(p.height);
		}
		mListAdapter.notifyDataSetChanged();
	}
}

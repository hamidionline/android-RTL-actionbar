package com.markupartist.android.widget;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.markupartist.android.widget.actionbar.R;

public class ComboBoxView extends LinearLayout implements OnClickListener {

	private ArrayList<String> mItems;
	private TextView mText;
	private ListView mList;
	private LinearLayout mWindowView;
	private PopupWindow mWindowsPopper;

	private onItemClickedListener mListener;
	
	private int mMenuWidth = 300;

	public ComboBoxView(Context context) {
		super(context);
		initView();
	}

	public ComboBoxView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		mText = new TextView(getContext());
		mText.setPadding(0, 5, 0, 5);
		mText.setTextSize(20);
		mText.setSingleLine(true);
		mText.setEllipsize(TruncateAt.END);
		mText.setTextColor(getResources().getColor(android.R.color.black));
		mText.setBackgroundResource(R.drawable.abs__spinner_ab_holo_dark);
		this.addView(mText,new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		mWindowView = new LinearLayout(getContext());
		mList = new ListView(getContext());
		mList.setPadding(5, 5, 5, 5);

		mText.setOnClickListener(this);

	}

	public void setAdapter(ArrayList<String> items) {
		mItems = items;
		StableArrayAdapter adapter = new StableArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mItems);

		mList.setCacheColorHint(getResources().getColor(R.color.actionbar_navigation_menu_backcolor));
		mList.setAdapter(adapter);

		if (items.size() > 0)
			mText.setText(items.get(0));

		mWindowView.removeAllViews();
		mWindowView.addView(mList, mMenuWidth, LayoutParams.WRAP_CONTENT);
		mWindowsPopper = new PopupWindow(mWindowView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mWindowsPopper.setBackgroundDrawable(getContext().getResources().getDrawable(R.color.actionbar_navigation_menu_backcolor));
		mWindowsPopper.setFocusable(true);
		mWindowsPopper.setOutsideTouchable(true);
		
	}

	public void setOnItemClickListener(onItemClickedListener listener) {
		this.mListener = listener;
	}

	public String getText() {
		return mText.getText().toString();
	}

	public void setText(String text) {
		mText.setText(text);
	}

	@Override
	public void onClick(View v) {

//		int left = (mText.getWidth() + mText.getLeft() - mMenuWidth);
		mWindowsPopper.showAsDropDown(mText,0,0);


		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position, long arg3) {
				if (mListener != null) {
					String selectedItem = (String) mList.getAdapter().getItem(position);
					mListener.onItemClicked(position, selectedItem);
					mText.setText(selectedItem);
				}
				mWindowsPopper.dismiss();
			}
		});


	}

	public interface onItemClickedListener {
		public void onItemClicked(int position, String text);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		public StableArrayAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout row = new LinearLayout(getContext());
			row.setGravity(Gravity.RIGHT);

			TextView rowTitle = new TextView(getContext());
			rowTitle.setText(getItem(position));
			rowTitle.setTextSize(20);
			rowTitle.setSingleLine(true);
			rowTitle.setTextColor(getResources().getColor(android.R.color.black));
			rowTitle.setPadding(5, 5, 5, 5);
			row.addView(rowTitle);

			return row;
		}

	}

}

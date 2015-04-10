package com.rishi.bechaty.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rishi.bechaty.R;
import com.rishi.bechaty.entity.Users;
import com.rishi.bechaty.util.CommonUtil;

public class UserAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Users> data = new ArrayList<Users>();
	private LayoutInflater inflater = null;
	Users tempValues = null;
	int i = 0;
	CommonUtil cmmnUtl;

	public UserAdapter(Activity a, ArrayList<Users> d) {

		activity = (Activity) a;
		data = d;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cmmnUtl = CommonUtil.getInstance(activity);

	}

	public void changeData(ArrayList<Users> _data) {
		data = _data;
		notifyDataSetChanged();
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {

		public TextView userName;

	}

	@Override
	public View getView(int pos, View v, ViewGroup arg2) {

		tempValues = (Users) data.get(pos);
		ViewHolder holder;
		if (v == null)
			v = inflater.inflate(R.layout.chat_item, null);

		holder = new ViewHolder();
		holder.userName = (TextView) v.findViewById(R.id.userName);

		if (tempValues != null)
			holder.userName.setText("" + tempValues.getUsername());

		return v;
	}

}

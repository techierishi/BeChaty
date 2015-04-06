package com.rishi.bechaty.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rishi.bechaty.R;
import com.rishi.bechaty.entity.ChatEntity;

public class ChatListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<ChatEntity> data;
	private static LayoutInflater inflater = null;
	ChatEntity tempValues = null;
	int i = 0;

	public ChatListAdapter(Context a, ArrayList<ChatEntity> d) {

		activity = (Activity) a;
		data = d;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void changeData(ArrayList<ChatEntity> _data) {
		data = _data;
		notifyDataSetChanged();
	}

	public int getCount() {

		if (data != null) {
			if (data.size() <= 0)
				return 1;
			else
				return data.size();
		}
		return 1;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {

		public RelativeLayout bgrl;
		public TextView text1Name;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

		if (convertView == null) {

			vi = inflater.inflate(R.layout.chat_row, null);
			holder = new ViewHolder();
			holder.bgrl = (RelativeLayout) vi.findViewById(R.id.bgrl);
			holder.text1Name = (TextView) vi.findViewById(R.id.text1Name);

			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data != null) {
			if (data.size() <= 0) {

			} else {
				tempValues = null;
				tempValues = (ChatEntity) data.get(position);
				holder.text1Name.setText(tempValues.getMessage_body());
			}
		}

		return vi;
	}

}

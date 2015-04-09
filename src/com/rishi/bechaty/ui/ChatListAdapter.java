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
import com.rishi.bechaty.entity.ChatEntity;

public class ChatListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<ChatEntity> data = new ArrayList<ChatEntity>();
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
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {

		public TextView text1Name;

	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		tempValues = (ChatEntity) data.get(position);

		// if (convertView == null) {

		if (tempValues != null) {
			if (tempValues.isOut()) {
				vi = inflater.inflate(R.layout.chat_item_sent, null);
			} else {
				vi = inflater.inflate(R.layout.chat_item_rcv, null);
			}
		} else {
			vi = inflater.inflate(R.layout.chat_item_sent, null);
		}

		holder = new ViewHolder();
		holder.text1Name = (TextView) vi.findViewById(R.id.lbl1);

		// vi.setTag(holder);
		// } else
		// holder = (ViewHolder) vi.getTag();

		holder.text1Name.setText(tempValues.getMessage_body());

		return vi;
	}

}

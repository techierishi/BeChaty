package com.rishi.bechaty.ui;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rishi.bechaty.R;
import com.rishi.bechaty.entity.ChatEntity;
import com.rishi.bechaty.util.CC;
import com.rishi.bechaty.util.CommonUtil;

public class ChatListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<ChatEntity> data = new ArrayList<ChatEntity>();
	private static LayoutInflater inflater = null;
	ChatEntity tempValues = null;
	int i = 0;
	CommonUtil cmmnUtl;

	public ChatListAdapter(Context a, ArrayList<ChatEntity> d) {

		activity = (Activity) a;
		data = d;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		cmmnUtl = CommonUtil.getInstance(activity);

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
		public ImageView chat_image;
		public TextView lbl2;
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
		holder.chat_image = (ImageView) vi.findViewById(R.id.chat_image);
		holder.lbl2 = (TextView) vi.findViewById(R.id.lbl2);

		// vi.setTag(holder);
		// } else
		// holder = (ViewHolder) vi.getTag();

		if (tempValues.getMessage_type() != null
				&& tempValues.getMessage_type().trim().equals(CC.MSG_TYPE_IMG)) {
			Bitmap bitMp = cmmnUtl.getBitmapFromString(tempValues
					.getMessage_body());

			holder.chat_image.setImageBitmap(bitMp);
			holder.chat_image.setVisibility(View.VISIBLE);
			holder.text1Name.setText("");
		} else {
			holder.text1Name.setText(tempValues.getMessage_body());
		}

		holder.lbl2.setText("" + cmmnUtl.currentTime());

		return vi;
	}
}

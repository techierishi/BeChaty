package com.rishi.bechaty;

import com.rishi.bechaty.entity.Users;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class UserList extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);

		getActionBar().setDisplayHomeAsUpEnabled(false);

		updateUserStatus(true);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		updateUserStatus(false);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		loadUserList();

	}

	
	private void updateUserStatus(boolean online) {

	}

	
	private void loadUserList() {

	}

	
	private class UserAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Users getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int pos, View v, ViewGroup arg2) {
			if (v == null)
				v = getLayoutInflater().inflate(R.layout.chat_item, null);

			return v;
		}

	}
}

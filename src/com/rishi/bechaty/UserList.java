package com.rishi.bechaty;

import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.rishi.bechaty.entity.Users;
import com.rishi.bechaty.ui.UserAdapter;
import com.rishi.bechaty.util.CC;
import com.rishi.bechaty.util.Popups;
import com.rishi.bechaty.util.Popups.CustomETDialogCallback;

public class UserList extends BaseActivity {

	static ArrayList<Users> usersList = new ArrayList<Users>();
	private ListView mList;
	UserAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);

		mList = (ListView) findViewById(R.id.list);

		if (CC.connection != null) {

			Presence presence = new Presence(Presence.Type.available);
			CC.connection.sendPacket(presence);

			final Roster roster = CC.connection.getRoster();
			Collection<RosterEntry> entries = roster.getEntries();

			for (RosterEntry entry : entries) {

				Users map = new Users();
				Presence entryPresence = roster.getPresence(entry.getUser());

				Presence.Type type = entryPresence.getType();

				map.setUsername(entry.getName().toString());
				map.setStatus(type.toString());

				Log.e("USER", entry.getName().toString());

				usersList.add(map);

			}

		}

		Log.d("Users : ", "" + usersList.toString());

		mAdapter = new UserAdapter(this, usersList);
		mList.setAdapter(mAdapter);

		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (usersList != null && usersList.isEmpty())
					setRecipient(usersList.get(position).getUsername());
				startActivity(new Intent(UserList.this, ChatScreen.class));
			}
		});

		if (usersList != null && usersList.isEmpty()) {
			Popups.showToast(
					"Can't fetch users. Please enter a username in popup.",
					this);
			// Show user popup

			Popups.showEditextPopup(this, "Enter User to chat with",
					new CustomETDialogCallback() {

						@Override
						public void onEtOkClick(String _recipient) {
							setRecipient(_recipient);
							startActivity(new Intent(UserList.this,
									ChatScreen.class));

						}
					});
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadUserList();

	}

	private void loadUserList() {

	}

}

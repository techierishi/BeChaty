package com.rishi.bechaty;

import java.util.ArrayList;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.rishi.bechaty.entity.ChatEntity;
import com.rishi.bechaty.ui.ChatListAdapter;
import com.rishi.bechaty.util.CC;
import com.rishi.bechaty.util.Popups;
import com.rishi.bechaty.util.Popups.CustomETDialogCallback;

public class ChatScreen extends BaseActivity {

	private ArrayList<ChatEntity> messages = new ArrayList<ChatEntity>();

	private ChatListAdapter mAdapter;

	private Handler mHandler = new Handler();
	private EditText mSendText;
	private ListView mList;
	private XMPPConnection connection;
	ActionBar abar;
	Context mContext;

	/**
	 * Called with the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Log.i("XMPPClient", "onCreate called");
		setContentView(R.layout.chat_screen);
		mContext = ChatScreen.this;

		mSendText = (EditText) this.findViewById(R.id.sendText);
		Log.i("XMPPClient", "mSendText = " + mSendText);
		mList = (ListView) this.findViewById(R.id.listMessages);
		Log.i("XMPPClient", "mList = " + mList);
		setListAdapter();

		setConnection(CC.connection);

		// Show user popup
		if (getRecipient().trim().isEmpty())
			Popups.showEditextPopup(this, "Enter User to chat with",
					new CustomETDialogCallback() {

						@Override
						public void onEtOkClick(String _recipient) {
							setRecipient(_recipient);
							// abar.setTitle("" + getRecipient());

						}
					});

		// Set a listener to send a chat text message
		Button send = (Button) this.findViewById(R.id.send);
		send.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(View view) {
				String to = getRecipient();

				if (!to.trim().isEmpty()) {
					String text = mSendText.getText().toString();

					Log.i("XMPPClient", "Sending text [" + text + "] to [" + to
							+ "]");
					Message msg = new Message(to, Message.Type.chat);

					try {
						JSONObject message_json = new JSONObject();
						message_json.put("type", CC.MSG_TYPE_TXT);
						message_json.put("body", text);
						msg.setBody(message_json.toString());
						connection.sendPacket(msg);

					} catch (JSONException e) {
						e.printStackTrace();
					}

					ChatEntity ceObj = toMessageObject(
							"" + connection.getUser(), text);
					messages.add(ceObj);
					mAdapter.changeData(messages);
					scrollMyListViewToBottom();
					mSendText.setText("");
				} else {
					Popups.showPopup("Please add a recipient !", mContext);
				}

			}
		});
		// customActionBar();
	}

	/**
	 * Called by Settings dialog when a connection is establised with the XMPP
	 * server
	 * 
	 * @param connection
	 */
	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
		if (connection != null) {
			// Add a packet listener to get messages sent to us
			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			connection.addPacketListener(new PacketListener() {
				public void processPacket(Packet packet) {
					Message message = (Message) packet;
					if (message.getBody() != null) {
						String fromName = StringUtils.parseBareAddress(message
								.getFrom());
						Log.i("XMPPClient", "Got text [" + message.getBody()
								+ "] from [" + fromName + "]");

						ChatEntity ceObj = fromMessageObject(fromName, message);

						messages.add(ceObj);

						// Add the incoming message to the list view
						mHandler.post(new Runnable() {
							public void run() {
								mAdapter.changeData(messages);

							}
						});
						scrollMyListViewToBottom();
					}
				}
			}, filter);
		}
	}

	private ChatEntity toMessageObject(String toName, String message) {
		ChatEntity ceObj = new ChatEntity();
		ceObj.setUsername(toName);
		ceObj.setMessage_body(message);
		ceObj.setOut(true);
		return ceObj;
	}

	private ChatEntity fromMessageObject(String fromName, Message message) {
		try {
			ChatEntity ceObj = new ChatEntity();
			ceObj.setUsername("" + fromName);
			JSONObject message_json = new JSONObject(message.getBody());

			String message_type = message_json.getString("type");

			if (message_type.trim().equals(CC.MSG_TYPE_TXT)) {
				ceObj.setMessage_type(CC.MSG_TYPE_TXT);
			} else if (message_type.trim().equals(CC.MSG_TYPE_IMG)) {
				ceObj.setMessage_type(CC.MSG_TYPE_IMG);
			}
			ceObj.setMessage_body("" + message_json.getString("body"));
			ceObj.setOut(false);
			return ceObj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	private void setListAdapter() {
		mAdapter = new ChatListAdapter(ChatScreen.this, messages);
		mList.setAdapter(mAdapter);
	}

	public void customActionBar() {

		// Toolbar mToolbar = (Toolbar) findViewById(R.id.apptoolbar);
		// setSupportActionBar(mToolbar);
		abar = getSupportActionBar();
		abar.setTitle("BeChaty");
	}

	private void setRecipient(String rName) {
		SharedPreferences sharedPref = getApplicationContext()
				.getSharedPreferences("recipient_sp", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("recipient", "" + rName);
		editor.commit();
	}

	private String getRecipient() {
		SharedPreferences sharedPref = getApplicationContext()
				.getSharedPreferences("recipient_sp", Context.MODE_PRIVATE);
		String recipient = sharedPref.getString("recipient", "");
		return recipient;
	}

	private void scrollMyListViewToBottom() {
		mList.post(new Runnable() {
			@Override
			public void run() {
				// Select the last row so it will scroll into view...
				mList.setSelection(mAdapter.getCount() - 1);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_attach) {

			View menuButton = findViewById(R.id.action_attach);

			int[] location = new int[2];
			menuButton.getLocationOnScreen(location);

			point = new Point();
			point.x = location[0];
			point.y = location[1];
			showStatusPopup(this, point);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// The method that displays the popup.
	private void showStatusPopup(final Activity context, Point p) {

		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context
				.findViewById(R.id.llStatusChangePopup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.attachpopup, null);

		// Creating the PopupWindow
		changeStatusPopUp = new PopupWindow(context);
		changeStatusPopUp.setContentView(layout);
		changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
		changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		changeStatusPopUp.setFocusable(true);

		LinearLayout llGallery = (LinearLayout) layout
				.findViewById(R.id.llGallery);

		llGallery.setOnClickListener(galleryClickListener);

		// Some offset to align the popup a bit to the left, and a bit down,
		// relative to button's position.
		int OFFSET_X = -50;
		int OFFSET_Y = 80;

		// Displaying the popup at the specified location, + offsets.
		changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x
				+ OFFSET_X, p.y + OFFSET_Y);
	}

	OnClickListener galleryClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Popups.showToast(" galleryClickListener clicked  ", ChatScreen.this);

			changeStatusPopUp.dismiss();
		}
	};
}

package com.rishi.bechaty;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rishi.bechaty.util.CC;
import com.rishi.bechaty.util.Popups;

public class Login extends BaseActivity {

	// private XMPPClient xmppClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		setTouchNClick(R.id.btnLogin);
		setTouchNClick(R.id.btnReg);
		// xmppClient = new XMPPClient();

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.btnReg) {
			startActivityForResult(new Intent(this, Register.class), 10);
		} else {
			new AsyncTask<String, String, Boolean>() {
				@Override
				protected Boolean doInBackground(String... params) {
					String host = getTxt(Login.this, R.id.host);
					String port = getTxt(Login.this, R.id.port);
					String service = getTxt(Login.this, R.id.service);
					String username = getTxt(Login.this, R.id.userid);
					String password = getTxt(Login.this, R.id.password);

					// Create a connection
					ConnectionConfiguration connConfig = new ConnectionConfiguration(
							host, Integer.parseInt(port), service);
					XMPPConnection connection = new XMPPConnection(connConfig);

					try {
						connection.connect();
						Log.i("XMPPClient", "[SettingsDialog] Connected to "
								+ connection.getHost());
					} catch (XMPPException ex) {
						Log.e("XMPPClient",
								"[SettingsDialog] Failed to connect to "
										+ connection.getHost());
						Log.e("XMPPClient", ex.toString());
						// xmppClient.setConnection(null);
						CC.connection = null;
					}
					try {
						connection.login(username, password);
						Log.i("XMPPClient",
								"Logged in as " + connection.getUser());

						// Set the status to available
						Presence presence = new Presence(
								Presence.Type.available);
						connection.sendPacket(presence);
						// xmppClient.setConnection(connection);
						CC.connection = connection;
					} catch (XMPPException ex) {
						Log.e("XMPPClient",
								"[SettingsDialog] Failed to log in as "
										+ username);
						Log.e("XMPPClient", ex.toString());
						// xmppClient.setConnection(null);
						CC.connection = null;
					}

					if (connection != null) {
						return true;
					} else {
						return false;
					}
				}

				@Override
				protected void onPostExecute(Boolean result) {
					if (result) {
						startActivity(new Intent(Login.this, ChatScreen.class));
					} else {
						Popups.showToast("Invalid credentials !", Login.this);
					}

					super.onPostExecute(result);
				}
			}.execute();

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10 && resultCode == RESULT_OK)
			finish();

	}

}

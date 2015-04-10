package com.rishi.bechaty;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.rishi.bechaty.util.CC;
import com.rishi.bechaty.util.Popups;

public class Login extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		setTouchNClick(R.id.btnLogin);
		setTouchNClick(R.id.btnReg);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		final String host = getTxt(Login.this, R.id.host);
		final String port = getTxt(Login.this, R.id.port);
		final String service = getTxt(Login.this, R.id.service);
		final String username = getTxt(Login.this, R.id.userid);
		final String password = getTxt(Login.this, R.id.password);

		// Create a connection [starts]
		final ConnectionConfiguration connConfig = new ConnectionConfiguration(
				host, Integer.parseInt(port), service);
		final XMPPConnection connection = new XMPPConnection(connConfig);
		CC.connection = connection;

		new AsyncTask<String, String, Boolean>() {
			@Override
			protected Boolean doInBackground(String... params) {
				try {
					CC.connection.connect();
					Log.e(CC.TAG, "Host : " + CC.connection.getHost());
				} catch (XMPPException ex) {
					Log.e(CC.TAG,
							"Host not connected : " + CC.connection.getHost());
					ex.printStackTrace();
					CC.connection = null;
				}
				return true;
			}
		}.execute();
		// Create a connection [ends]

		if (v.getId() == R.id.btnReg) {

			startActivityForResult(new Intent(this, Register.class), 10);

		} else {

			// User login [starts]
			new AsyncTask<String, String, Boolean>() {
				@Override
				protected Boolean doInBackground(String... params) {

					try {
						connection.login(username, password);
						Log.i(CC.TAG, "Logged in as " + connection.getUser());
						Presence presence = new Presence(
								Presence.Type.available);
						connection.sendPacket(presence);
						CC.connection = connection;

					} catch (XMPPException ex) {
						Log.e(CC.TAG, "Failed to logIn as " + username);
						ex.printStackTrace();
						CC.connection = null;
					}

					if (CC.connection != null) {
						return true;
					} else {
						return false;
					}
				}

				@Override
				protected void onPostExecute(Boolean result) {
					if (result) {
						startActivity(new Intent(Login.this, UserList.class));
					} else {
						Popups.showToast("Invalid credentials !", Login.this);
					}

					super.onPostExecute(result);
				}
			}.execute();
			// User login [ends]
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10 && resultCode == RESULT_OK)
			finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return false;
	}

}

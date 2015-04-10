package com.rishi.bechaty;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.XMPPException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.rishi.bechaty.util.CC;
import com.rishi.bechaty.util.Popups;

public class Register extends BaseActivity {

	private EditText user;

	private EditText pwd;

	private EditText email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		email = (EditText) findViewById(R.id.email);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		final String u = user.getText().toString();
		final String p = pwd.getText().toString();
		final String e = email.getText().toString();

		if (CC.connection != null) {

			new AsyncTask<String, String, Boolean>() {
				@Override
				protected Boolean doInBackground(String... params) {
					try {

						AccountManager accountManager = new AccountManager(
								CC.connection);
						Map<String, String> mObj = new HashMap<String, String>();
						mObj.put("email", e);
						accountManager.createAccount(u, p, mObj);
					} catch (XMPPException ex) {
						ex.printStackTrace();
					}
					return true;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					finish();
					super.onPostExecute(result);
				}
			}.execute();

		} else {
			Popups.showToast("Error !", this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return false;
	}
}

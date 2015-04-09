package com.rishi.bechaty;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


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

		String u = user.getText().toString();
		String p = pwd.getText().toString();
		String e = email.getText().toString();

	}
}

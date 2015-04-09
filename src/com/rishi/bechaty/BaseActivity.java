package com.rishi.bechaty;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.rishi.bechaty.util.TouchEffect;

public class BaseActivity extends ActionBarActivity implements OnClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static final TouchEffect TOUCH = new TouchEffect();

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setupActionBar();
	}

	protected void setupActionBar() {
		final ActionBar actionBar = getActionBar();
		if (actionBar == null)
			return;
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setLogo(R.drawable.icon);
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.actionbar_bg));
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	public View setTouchNClick(int id) {

		View v = setClick(id);
		if (v != null)
			v.setOnTouchListener(TOUCH);
		return v;
	}

	public View setClick(int id) {

		View v = findViewById(id);
		if (v != null)
			v.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {

	}

	protected String getTxt(Activity ctx, int id) {
		EditText widget = (EditText) ctx.findViewById(id);
		return widget.getText().toString();
	}
}

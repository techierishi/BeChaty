package com.rishi.bechaty;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.rishi.bechaty.util.Popups;
import com.rishi.bechaty.util.TouchEffect;

public class BaseActivity extends ActionBarActivity implements OnClickListener {
	PopupWindow changeStatusPopUp;
	Point point;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_attach) {
			Popups.showToast("No Settings  ", this);
			int[] location = new int[2];

			point = new Point();
			point.x = 20;
			point.y = 50;
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

		// Some offset to align the popup a bit to the left, and a bit down,
		// relative to button's position.
		int OFFSET_X = -20;
		int OFFSET_Y = 50;

		// Clear the default translucent background
		changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

		// Displaying the popup at the specified location, + offsets.
		changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x
				+ OFFSET_X, p.y + OFFSET_Y);
	}
}

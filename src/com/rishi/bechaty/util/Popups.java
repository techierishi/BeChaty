package com.rishi.bechaty.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rishi.bechaty.R;

public class Popups {
	public static void showToast(String message, Context ctx) {
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}

	public static void showPopup(String message, Context ctx) {

		final Dialog dialog = new Dialog(ctx, R.style.FullHeightDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog);
		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.alert_text);
		text.setText(message);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public static void showOkPopup(Context ctx, String txt,
			final CustomDialogCallback obj) {
		final Dialog dialog = new Dialog(ctx, R.style.FullHeightDialog);
		dialog.setContentView(R.layout.custom_dialog);
		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.alert_text);
		text.setText(txt);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				obj.onOkClick();
			}
		});

		dialog.show();

	}

	public interface CustomDialogCallback {
		public void onOkClick();
	}

	public static void showEditextPopup(Context ctx, String defaulttext,
			final CustomETDialogCallback obj) {
		final Dialog dialog = new Dialog(ctx, R.style.FullHeightDialog);
		dialog.setContentView(R.layout.custom_edittext_dialog);
		// set the custom dialog components - text, image and button
		final EditText alert_etext = (EditText) dialog
				.findViewById(R.id.alert_etext);
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		alert_etext.setHint("" + defaulttext);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String entered_text = alert_etext.getText().toString();
				dialog.dismiss();
				obj.onEtOkClick(entered_text);
			}
		});

		dialog.show();

	}

	public interface CustomETDialogCallback {
		public void onEtOkClick(String strr);
	}

}

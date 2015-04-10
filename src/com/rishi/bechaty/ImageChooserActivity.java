package com.rishi.bechaty;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.rishi.bechaty.util.CommonUtil;
import com.rishi.bechaty.util.Popups;
import com.rishi.bechaty.util.StringRandomGen;

public class ImageChooserActivity extends BaseActivity {

	private static final int IMAGE_PICK = 1;
	private static final int IMAGE_CAPTURE = 2;

	int activity_layout;

	File fileOrPhotoObj;
	protected CommonUtil cmnUtl;

	Context _callbackContaner;

	public void setChildsContentView(int ccv) {
		activity_layout = ccv;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cmnUtl = CommonUtil.getInstance(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case IMAGE_PICK:
				this.imageFromGallery(resultCode, data);
				break;
			case IMAGE_CAPTURE:
				this.imageFromCamera(resultCode, data);
				break;
			default:
				break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void imageFromCamera(int resultCode, Intent data) {
		Bitmap newImage = (Bitmap) data.getExtras().get("data");

		StringRandomGen rs = new StringRandomGen();
		String fn = rs.generateRandomString() + ".png";
		// Saving image here
		cmnUtl.storeImage(newImage, "" + fn);

		if (_callbackContaner != null) {
			OnImageGenerated obj = (OnImageGenerated) _callbackContaner;
			obj.onImageGenerated(newImage);
		}

	}

	private void imageFromGallery(int resultCode, Intent data) {
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();

		File ff = new File(filePath);
		StringRandomGen rs = new StringRandomGen();
		String fn = rs.generateRandomString() + ".png";
		Bitmap newImage = cmnUtl.decodeFile(ff);

		// Saving image here
		cmnUtl.storeImage(newImage, "" + fn);

		if (_callbackContaner != null) {
			OnImageGenerated obj = (OnImageGenerated) _callbackContaner;
			obj.onImageGenerated(newImage);
		}

	}

	public class ImagePickListener implements OnClickListener {

		ImagePickListener(Context ctx, PopupWindow pWindow) {
			_callbackContaner = ctx;

		}

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			startActivityForResult(
					Intent.createChooser(intent, "Choose Image"), IMAGE_PICK);

		}
	}

	public class TakePictureListener implements OnClickListener {

		TakePictureListener(Context ctx, PopupWindow pWindow) {
			_callbackContaner = ctx;

		}

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, IMAGE_CAPTURE);

		}
	}

	public interface OnImageGenerated {
		public void onImageGenerated(Bitmap bMap);
	}

}
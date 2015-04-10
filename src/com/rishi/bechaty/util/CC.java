package com.rishi.bechaty.util;

import org.jivesoftware.smack.XMPPConnection;

import android.os.Environment;

public class CC {

	public static final String MSG_TYPE_TXT = "text";
	public static final String MSG_TYPE_IMG = "image";
	public static final String EXTRA_DATA = "extraData";
	public static final String IMAGE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/BeChaty/Images/";
	public static XMPPConnection connection = null;

	public static final String TAG = "BeChaty";
}

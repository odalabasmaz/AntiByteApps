package com.antibyteapps.middleware.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Orhun Dalabasmaz
 * @since 28.02.2015.
 */
public class ActivityUtils {

	public static void showShortMessage(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showLongMessage(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}

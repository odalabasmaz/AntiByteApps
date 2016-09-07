package com.antibyteapps.middleware.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.antibyteapps.middleware.util.ActivityUtils;

/**
 * @author Orhun Dalabasmaz
 * @since 28.02.2015.
 */
public class ChargeReceiver
		extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ActivityUtils.showLongMessage(context, intent.getAction());
//		Toast.makeText(context, "POWER CONNECTED", Toast.LENGTH_LONG).show();
//		Toast.makeText(context, "POWER DISCONNECTED", Toast.LENGTH_LONG).show();
	}
}

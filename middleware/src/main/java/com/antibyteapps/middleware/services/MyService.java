package com.antibyteapps.middleware.services;

import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import com.antibyteapps.middleware.base.BaseService;

/**
 * @author Orhun Dalabasmaz
 * @since 28.02.2015.
 */
public class MyService
		extends BaseService {

	/**
	 * music play background
	 */

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		showShortMessage("Service created..");
	}

	@Override
	public ComponentName startService(Intent service) {
		showShortMessage("Service started..");
		return super.startService(service);
	}

	@Override
	public boolean stopService(Intent service) {
		showShortMessage("Service stopped..");
		return super.stopService(service);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
}

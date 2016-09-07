package com.antibyteapps.middleware.base;

import android.app.Service;

import com.antibyteapps.middleware.util.ActivityUtils;

/**
 * @author Orhun Dalabasmaz
 * @since 28.02.2015.
 */
public abstract class BaseService
		extends Service {

	protected final void showShortMessage(String message) {
		ActivityUtils.showShortMessage(getApplicationContext(), message);
	}

	protected final void showLongMessage(String message) {
		ActivityUtils.showLongMessage(getApplicationContext(), message);
	}

}

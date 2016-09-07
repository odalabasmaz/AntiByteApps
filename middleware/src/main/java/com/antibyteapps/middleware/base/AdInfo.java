package com.antibyteapps.middleware.base;

import com.google.android.gms.ads.AdSize;

/**
 * @author Orhun Dalabasmaz
 * @since 11.03.2015.
 */
public class AdInfo {

	private int adViewId;
	private AdSize adSize;
	private String adUnitId;
	private String deviceId;

	public AdInfo(int adViewId, AdSize adSize, String adUnitId, String deviceId) {
		this.adViewId = adViewId;
		this.adSize = adSize;
		this.adUnitId = adUnitId;
		this.deviceId = deviceId;
	}

	public int getAdViewId() {
		return adViewId;
	}

	public AdSize getAdSize() {
		return adSize;
	}

	public String getAdUnitId() {
		return adUnitId;
	}

	public String getDeviceId() {
		return deviceId;
	}
}

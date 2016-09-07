package com.antibyteapps.middleware.base;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.antibyteapps.middleware.util.ActivityUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.antibyteapps.middleware.R;
import com.antibyteapps.middleware.fragment.FragmentListeners;
import com.antibyteapps.middleware.view.DetailedButton;
import com.antibyteapps.middleware.view.PausableChronometer;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Orhun Dalabasmaz
 * @since 27.02.2015.
 */
public abstract class BaseActivity
		extends ActionBarActivity
		implements FragmentListeners {

	private final ViewHelperActivity viewHelper;
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	protected BaseActivity() {
		this.viewHelper = new SimpleViewHelperActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}

	@Override
	protected void onStart() {
		super.onStart();
		setAds();
	}

	private void setAds() {
		final AdInfo adInfo = getAdInfo();
		if (adInfo == null) {
			return;
		}

		final int adViewId = adInfo.getAdViewId();
		final String deviceId = adInfo.getDeviceId();
		final AdSize adSize = adInfo.getAdSize();
		final String adUnitId = adInfo.getAdUnitId();

		final AdView adView = (AdView) findViewById(adViewId);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				adView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAdOpened() {
				super.onAdOpened();
			}
		});

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice(deviceId)
				.build();
//		adView.setAdUnitId(adUnitId);
//		adView.setAdSize(adSize);
		adView.loadAd(adRequest);
	}

	protected final void showShortMessage(String message) {
		ActivityUtils.showShortMessage(getApplicationContext(), message);
	}

	protected final void showLongMessage(String message) {
		ActivityUtils.showLongMessage(getApplicationContext(), message);
	}

	protected final void showAlert(String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(message);
		alert.setCancelable(false);
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
	}

	protected final String getLocalUriPath(final int value) {
		//todo
		return "android.resource://" + "com.android.helloworld" + value;
	}

	public final ComponentName startService(Class service) {
		return startService(new Intent(getApplicationContext(), service));
	}

	public final boolean stopService(Class service) {
		return stopService(new Intent(getApplicationContext(), service));
	}

	protected final boolean isServiceRunning() {
		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		String packageName = getApplication().getPackageName();
		for (ActivityManager.RunningServiceInfo runningServiceInfo :
				activityManager.getRunningServices(Integer.MAX_VALUE)) {
			String runningPackageName = runningServiceInfo.service.getPackageName();
			if (packageName.equals(runningPackageName)) {
				return true;
			}
		}
		return false;
	}

	protected final Intent createIntent(Class activity) {
		return new Intent(this, activity);
	}

	protected final void startNextActivity(Class activity) {
		startNextActivity(activity, Collections.<String, String>emptyMap(), Collections.<String, Serializable>emptyMap());
	}

	protected final void startNextActivity(Class activity, Map<String, Serializable> objects) {
		startNextActivity(activity, Collections.<String, String>emptyMap(), objects);
	}

	protected final void startNextActivity(Class activity, Map<String, String> extraValues, Map<String, Serializable> objects) {
		Intent nextIntent = createIntent(activity);
		for (String key : extraValues.keySet()) {
			nextIntent.putExtra(key, extraValues.get(key));
		}
		for (String key : objects.keySet()) {
			nextIntent.putExtra(key, objects.get(key));
		}
		startActivity(nextIntent);
//		this.finish();//todo ??
	}

	//todo API 17 öncesinde böyle kullanmak zorundayız !!!!

	/**
	 * Generate a value suitable for use in {@link # setId(int)}.
	 * This value will not collide with ID values generated at build time by aapt for R.id.
	 *
	 * @return a generated ID value
	 */
	public static int generateViewId() {
		for (; ; ) {
			final int result = sNextGeneratedId.get();
			// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
			if (sNextGeneratedId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	protected void pauseThread(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void setEnabledAllViews(List<Integer> ids, boolean enabled) {
		for (int id : ids) {
			setEnabledAllViews(id, enabled);
		}
	}

	protected void setEnabledAllViews(int id, boolean enabled) {
		final View view = findViewById(id);
		view.setEnabled(enabled);
	}

	protected void setClickableAllViews(List<Integer> ids, boolean clickable) {
		for (int id : ids) {
			setClickableAllViews(id, clickable);
		}
	}

	protected void setClickableAllViews(int id, boolean clickable) {
		final View view = findViewById(id);
		view.setClickable(clickable);
	}

	/**
	 * in order to use fragment in
	 */
	@Override
	public void onFragmentInteraction(Uri uri) {
	}

	/**
	 * chronometer
	 */
	protected void startStopChronometer(View view) {
		ToggleButton button = (ToggleButton) view;
		final boolean isChecked = button.isChecked();
		final Chronometer chronometer = findChronometerById(R.id.chronometer);
		if (isChecked) {
			chronometer.start();
		} else {
			chronometer.stop();
		}
	}

	protected AdInfo getAdInfo() {
		// must be implemented in order to show Ads
		return null;
	}


	/**
	 *
	 * */
	protected TextView findTextViewById(int id) {
		return viewHelper.findTextViewById(id);
	}

	protected Button findButtonById(int id) {
		return viewHelper.findButtonById(id);
	}

	protected DetailedButton findDetailedButtonById(int id) {
		return (DetailedButton) this.findButtonById(id);
	}

	protected ListView findListViewById(int id) {
		return viewHelper.findListViewById(id);
	}
	protected ExpandableListView findExpandableListViewById(int id) {
		return viewHelper.findExpandableListViewById(id);
	}

	protected ImageView findImageViewById(int id) {
		return viewHelper.findImageViewById(id);
	}

	protected VideoView findVideoViewById(int id) {
		return viewHelper.findVideoViewById(id);
	}

	protected ProgressBar findProgressBarById(int id) {
		return viewHelper.findProgressBarById(id);
	}

	protected TableLayout findTableLayoutById(int id) {
		return viewHelper.findTableLayoutById(id);
	}

	protected Chronometer findChronometerById(int id) {
		return viewHelper.findChronometerById(id);
	}

	protected PausableChronometer findPausableChronometerById(int id) {
		return (PausableChronometer) viewHelper.findChronometerById(id);
	}

	protected RadioGroup findRadioGroupById(int id) {
		return viewHelper.findRadioGroupById(id);
	}
}

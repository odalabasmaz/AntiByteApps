package com.antibyteapps.middleware.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

/**
 * @author Orhun Dalabasmaz
 * @since 05.03.2015.
 */
public class PausableChronometer
		extends Chronometer {

	private long timeWhenStopped;

	public PausableChronometer(Context context) {
		super(context);
	}

	public PausableChronometer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PausableChronometer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void start() {
		setBase(SystemClock.elapsedRealtime() - timeWhenStopped);
		super.start();
	}

	public void pause() {
		super.stop();
		timeWhenStopped = SystemClock.elapsedRealtime() - getBase();
	}

	public void stop() {
		super.stop();
	}

	public void reset() {
		super.stop();
		setBase(SystemClock.elapsedRealtime());
		timeWhenStopped = 0;
	}

	public String getElapsedTime() {
		final long elapsedTime = timeWhenStopped;
		double seconds = (double) elapsedTime / 1000.0;
		if (seconds < 1.0) {
			return String.format("%.0f ms", seconds * 1000);
		} else {
			return String.format("%.2f s", seconds);
		}
	}

}

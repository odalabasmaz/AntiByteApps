package com.orhundalabasmaz.antibyteapps.matchitifyoucan.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.orhundalabasmaz.antibyteapps.matchitifyoucan.R;
import com.orhundalabasmaz.antibyteapps.middleware.base.BaseActivity;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();
		progressAction(2000);
	}

	private void progressAction(final int millisecond) {
		final MainActivity activity = this;
		new Thread() {
			@Override
			public void run() {
				final ProgressBar progressBar = findProgressBarById(R.id.progressBar);
				final int ms = millisecond / 100;
				for (int value = 0; value <= 100; ++value) {
					try {
						Thread.sleep(ms);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progressBar.setProgress(value);
				}
				startNextActivity(WelcomeActivity.class);
				activity.finish();
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

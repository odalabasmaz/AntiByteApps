package com.orhundalabasmaz.antibyteapps.matchitifyoucan.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdSize;
import com.orhundalabasmaz.antibyteapps.matchitifyoucan.R;
import com.orhundalabasmaz.antibyteapps.matchitifyoucan.activity.gameOptions.GameOption;
import com.orhundalabasmaz.antibyteapps.middleware.base.AdInfo;
import com.orhundalabasmaz.antibyteapps.middleware.base.BaseActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class WelcomeActivity extends BaseActivity {

	private GameOption gameOption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		gameOption = (GameOption) getIntent().getSerializableExtra(GameOption.GAME_OPTION);
	}

	@Override
	protected AdInfo getAdInfo() {
		String adUnitId = getString(R.string.banner_ad_unit_id);
		String deviceId = getString(R.string.device_id);
		return new AdInfo(R.id.adView,
				AdSize.SMART_BANNER,
				adUnitId,
				deviceId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_welcome, menu);
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

	/**
	 * on button clicks
	 */
	public void onPlayNow(View view) {
		Map<String, Serializable> objects = new HashMap<>();
		objects.put(GameOption.GAME_OPTION, gameOption);
		startNextActivity(SimpleGameActivity.class, objects);
	}

	public void onOptions(View view) {
		startNextActivity(OptionsActivity.class);
	}

	public void onAbout(View view) {
		showLongMessage("copyrights by 'Anti-Byte Apps'");
	}

}

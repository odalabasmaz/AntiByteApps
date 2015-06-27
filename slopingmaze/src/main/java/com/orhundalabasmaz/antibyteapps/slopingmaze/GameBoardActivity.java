package com.orhundalabasmaz.antibyteapps.slopingmaze;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.orhundalabasmaz.antibyteapps.middleware.base.BaseActivity;


public class GameBoardActivity extends BaseActivity {
	private Sensor mSensor;
	private SensorManager mSensorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
		initSensor();
	}

	private void initSensor() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
//		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		mSensorManager.registerListener(new SensorEventListener() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				if (event.sensor == mSensor) {
					findTextViewById(R.id.infoText).setText("Info: " + event.values[0]);

				}

				//todo if reaches the edges
				if (false) {
					mSensorManager.unregisterListener(this);
				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}
		}, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_game_board, menu);
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

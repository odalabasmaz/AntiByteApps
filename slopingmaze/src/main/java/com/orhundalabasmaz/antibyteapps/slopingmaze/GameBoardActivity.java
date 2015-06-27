package com.orhundalabasmaz.antibyteapps.slopingmaze;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.orhundalabasmaz.antibyteapps.middleware.base.BaseActivity;


public class GameBoardActivity
		extends BaseActivity
		implements SensorEventListener {

	private Sensor mSensor;
	private SensorManager mSensorManager;
	private boolean mInitialized;
	private float mLastX, mLastY, mLastZ;
//	private final float NOISE = (float) 2.0;
	private final float THRESHOLD = (float) 0.01;
	private final float SENSIBILITY = (float) 20;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_board);
		initSensor();
	}

	private void initSensor() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			image = (ImageView) findViewById(R.id.angrybird);
			mInitialized = true;
		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);
//			if (deltaX < NOISE) deltaX = (float) 0.0;
//			if (deltaY < NOISE) deltaY = (float) 0.0;
//			if (deltaZ < NOISE) deltaZ = (float) 0.0;

			/*if (deltaX > deltaY) {
				findTextViewById(R.id.infoText).setText("horizontal");
			} else if (deltaY > deltaX) {
				findTextViewById(R.id.infoText).setText("vertical");
			}*/

			final float leftPos = image.getX();
//			final int rightPos = image.getRight();
//			final int horizontalPos = leftPos;

			float diff = y - mLastY;
			if (Math.abs(diff) < THRESHOLD) diff = 0;

			if (diff > 0 || mLastY > 0) {
				findTextViewById(R.id.infoText).setText("right");
				image.setX(leftPos + SENSIBILITY);
			} else if (diff < 0 || mLastY < 0) {
				findTextViewById(R.id.infoText).setText("left");
				image.setX(leftPos - SENSIBILITY);
			} else {
				// do nothing
			}
			findTextViewById(R.id.anglesText).setText("X: " + mLastX + ", Y: " + mLastY + " Z: " + mLastZ);

			mLastX = x;
			mLastY = y;
			mLastZ = z;
		}

		//todo if reaches the edges
		if (isReachTheEdges()) {
			mSensorManager.unregisterListener(this);
		}
	}

	private boolean isReachTheEdges() {
		return false;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
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

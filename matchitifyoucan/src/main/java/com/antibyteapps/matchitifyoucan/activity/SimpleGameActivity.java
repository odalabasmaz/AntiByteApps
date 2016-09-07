package com.antibyteapps.matchitifyoucan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import com.antibyteapps.matchitifyoucan.R;
import com.antibyteapps.matchitifyoucan.activity.gameOptions.GameOption;
import com.antibyteapps.middleware.base.BaseActivity;
import com.antibyteapps.middleware.view.DetailedButton;
import com.antibyteapps.middleware.view.PausableChronometer;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple memory game..
 *
 * @author Orhun Dalabasmaz
 * @since 28.02.2015.
 */
public class SimpleGameActivity extends BaseActivity {

	public static int NUMBER_OF_BOARD_ROWS;
	public static int NUMBER_OF_BOARD_COLS;

	private static int NUMBER_OF_MATCHED;
	private DetailedButton openedButton;
	private DetailedButton clickedButton;
	private List<String> values;
	private List<Integer> buttonIds;
	private PausableChronometer chronometer;
	private View.OnClickListener onClickListener;

	private GameOption gameOption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_game);

		gameOption = (GameOption) getIntent().getSerializableExtra(GameOption.GAME_OPTION);
		chronometer = findPausableChronometerById(R.id.chronometer);
		onClickListener = getOnClickListener();
		initGameBoard();
	}

	private View.OnClickListener getOnClickListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				clickedButton = (DetailedButton) view;
				clickedButton.setTextWithHidden();
				clickedButton.setClickable(false);
//						clickedButton.setBackgroundColor(getResources().getColor(R.color.button_material_light));
				if (openedButton == null) {
					openedButton = clickedButton;
				} else {
					setClickableAllViews(buttonIds, false);
					new Thread() {
						@Override
						public void run() {
							pauseThread(500);
							checkBoard();
							setClickableAllViews(buttonIds, true);
						}
					}.start();
				}
			}
		};
	}

	private void init() {
		NUMBER_OF_MATCHED = 0;
		openedButton = null;
		clickedButton = null;
		values = new ArrayList<>();
		buttonIds = new ArrayList<>();
		NUMBER_OF_BOARD_ROWS = gameOption.getGameDimension().getRowNumber();
		NUMBER_OF_BOARD_COLS = gameOption.getGameDimension().getColNumber();

		for (int i = 0; i < getNumberOfSuccessMatched(); ++i) {
			values.add(Integer.toString(i));
			values.add(Integer.toString(i));
		}
		chronometer.reset();
	}

	private void initGameBoard() {
		init();
		// todo should remove old childs ??

		final TableLayout tableLayout = findTableLayoutById(R.id.gameBoard);
		for (int i = 0; i < NUMBER_OF_BOARD_ROWS; ++i) {
			final TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

			for (int j = 0; j < NUMBER_OF_BOARD_COLS; ++j) {
				DetailedButton button = new DetailedButton(this);
				button.setOnClickListener(onClickListener);
				final int generateViewId = generateViewId();
				button.setId(generateViewId);
				buttonIds.add(generateViewId);
				button.setHiddenText(getTextForButton());
				tableRow.addView(button);
			}
			tableLayout.addView(tableRow, i);
		}
		setClickableAllViews(buttonIds, false);
	}

	private int getNumberOfSuccessMatched() {
		return NUMBER_OF_BOARD_ROWS * NUMBER_OF_BOARD_COLS / 2;
	}

	private void checkBoard() {
		final SimpleGameActivity thisActivity = this;
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (openedButton.getHiddenText().equals(clickedButton.getHiddenText())) {
//						openedButton.setBackgroundColor(getResources().getColor(R.color.button_material_dark));
//						clickedButton.setBackgroundColor(getResources().getColor(R.color.button_material_dark));
					clickedButton.setVisibility(View.INVISIBLE);
					openedButton.setVisibility(View.INVISIBLE);
					openedButton = null;
					++NUMBER_OF_MATCHED;
					if (NUMBER_OF_MATCHED == getNumberOfSuccessMatched()) {
						chronometer.pause();
						AlertDialog.Builder alert = new AlertDialog.Builder(thisActivity);
						alert
								.setMessage("YOU HAVE SUCCEED ! (Time: " + chronometer.getElapsedTime() + ")")
								.setCancelable(false)
								.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										startNextActivity(WelcomeActivity.class);
									}
								}).create().show();
					}
				} else {
					clickedButton.clearText();
					openedButton.clearText();
					openedButton = null;
				}
			}
		});
	}

	/**
	 * chronometer button activated..
	 */
	public void startStopChronometer(View view) {
		ToggleButton button = (ToggleButton) view;
		final boolean isChecked = button.isChecked();

		if (isChecked) {
			chronometer.start();
		} else {
			chronometer.pause();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (openedButton != null) {
						openedButton.clearText();
						openedButton = null;
					}
					if (clickedButton != null) {
						clickedButton.clearText();
						clickedButton = null;
					}
				}
			});
		}
		setClickableAllViews(buttonIds, isChecked);
	}

	/**
	 * todo GENERATION
	 */
	public String getTextForButton() {
		int index = ((int) (Math.random() * 100)) % values.size();
		final String value = values.get(index);
		values.remove(index);
		return value;
	}

	@Override
	public void onBackPressed() {
		//todo are you sure ?   "YES | NO"
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert
				.setMessage("Are you sure ?")
				.setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				})
				.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_simple_game, menu);
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

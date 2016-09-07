package com.antibyteapps.matchitifyoucan.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.antibyteapps.matchitifyoucan.activity.gameOptions.GameDimension;
import com.antibyteapps.matchitifyoucan.activity.gameOptions.GameLevel;
import com.antibyteapps.matchitifyoucan.activity.gameOptions.GameOption;
import com.antibyteapps.matchitifyoucan.activity.gameOptions.GameType;
import com.antibyteapps.matchitifyoucan.R;
import com.antibyteapps.middleware.base.BaseActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OptionsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
	}

	public void setOptions(View view) {
		final RadioGroup rgGameBoard = findRadioGroupById(R.id.rg_gameBoard);
		final RadioGroup rgGameType = findRadioGroupById(R.id.rg_gameType);
		final RadioGroup rgGameLevel = findRadioGroupById(R.id.rg_gameLevel);

		final int gameBoardId = rgGameBoard.getCheckedRadioButtonId();
		final GameDimension gameDimension = GameOption.DIMENSIONS_MAP.get(gameBoardId);

		final int gameTypeId = rgGameType.getCheckedRadioButtonId();
		final GameType gameType = GameOption.TYPES_MAP.get(gameTypeId);

		final int gameLevelId = rgGameLevel.getCheckedRadioButtonId();
		final GameLevel gameLevel = GameOption.LEVELS_MAP.get(gameLevelId);

		final GameOption gameOption = new GameOption(gameDimension, gameType, gameLevel);
		Map<String, Serializable> objects = new HashMap<>();
		objects.put(GameOption.GAME_OPTION, gameOption);
		startNextActivity(WelcomeActivity.class, objects);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_options, menu);
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

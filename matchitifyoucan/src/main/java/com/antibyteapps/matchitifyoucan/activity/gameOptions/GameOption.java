package com.antibyteapps.matchitifyoucan.activity.gameOptions;

import com.antibyteapps.matchitifyoucan.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Orhun Dalabasmaz
 * @since 06.03.2015.
 */
public class GameOption
		implements Serializable {

	public static final String GAME_OPTION = "GAME_OPTION";
	public static final Map<Integer, GameDimension> DIMENSIONS_MAP = new HashMap<>();
	public static final Map<Integer, GameType> TYPES_MAP = new HashMap<>();
	public static final Map<Integer, GameLevel> LEVELS_MAP = new HashMap<>();

	static {
		DIMENSIONS_MAP.put(R.id.rb_2x2, new GameDimension(2, 2));
		DIMENSIONS_MAP.put(R.id.rb_4x4, new GameDimension(4, 4));
		DIMENSIONS_MAP.put(R.id.rb_6x6, new GameDimension(6, 6));

		TYPES_MAP.put(R.id.rb_numbers, GameType.NUMBERS);
		TYPES_MAP.put(R.id.rb_alphaNumeric, GameType.ALPHANUMERIC);
		TYPES_MAP.put(R.id.rb_image, GameType.IMAGE);
		TYPES_MAP.put(R.id.rb_sound, GameType.SOUND);

		LEVELS_MAP.put(R.id.rb_easy, GameLevel.EASY);
		LEVELS_MAP.put(R.id.rb_medium, GameLevel.MEDIUM);
		LEVELS_MAP.put(R.id.rb_hard, GameLevel.HARD);
		LEVELS_MAP.put(R.id.rb_impossible, GameLevel.IMPOSSIBLE);
	}

	private GameDimension gameDimension;
	private GameType gameType;
	private GameLevel gameLevel;

	public GameOption(GameDimension gameDimension, GameType gameType, GameLevel gameLevel) {
		this.gameDimension = gameDimension;
		this.gameType = gameType;
		this.gameLevel = gameLevel;
	}

	public GameDimension getGameDimension() {
		return gameDimension;
	}

	public GameType getGameType() {
		return gameType;
	}

	public GameLevel getGameLevel() {
		return gameLevel;
	}
}

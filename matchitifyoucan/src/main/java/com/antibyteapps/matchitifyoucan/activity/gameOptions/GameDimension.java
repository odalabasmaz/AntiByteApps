package com.antibyteapps.matchitifyoucan.activity.gameOptions;

import java.io.Serializable;

/**
 * @author Orhun Dalabasmaz
 * @since 06.03.2015.
 */
public class GameDimension
		implements Serializable {
	private int rowNumber;
	private int colNumber;

	public GameDimension(int rowNumber, int colNumber) {
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public int getColNumber() {
		return colNumber;
	}
}

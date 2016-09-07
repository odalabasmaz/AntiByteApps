package com.antibyteapps.middleware.view;

import android.content.Context;
import android.widget.Button;

/**
 * @author Orhun Dalabasmaz
 * @since 04.03.2015.
 */
public class DetailedButton extends Button {

	private String hiddenText;
	private ButtonState state;

	public DetailedButton(Context context) {
		super(context);
	}

	public String getHiddenText() {
		return hiddenText;
	}

	public void setHiddenText(String hiddenText) {
		this.hiddenText = hiddenText;
	}

	public ButtonState getState() {
		return state;
	}

	public void setState(ButtonState state) {
		this.state = state;
	}

	public void setTextWithHidden() {
		this.setText(this.getHiddenText());
	}

	public void clearText() {
		this.setText(null);
	}
}

package com.antibyteapps.middleware.base;

import android.app.Activity;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * @author Orhun Dalabasmaz
 * @since 28.02.2015.
 */
public class SimpleViewHelperActivity
		extends Activity
		implements ViewHelperActivity {

	private Activity activity;

	public SimpleViewHelperActivity(Activity activity) {
		this.activity = activity;
	}

	public TextView findTextViewById(int id) {
		return (TextView) activity.findViewById(id);
	}

	public Button findButtonById(int id) {
		return (Button) activity.findViewById(id);
	}

	public ListView findListViewById(int id) {
		return (ListView) activity.findViewById(id);
	}
	public ExpandableListView findExpandableListViewById(int id) {
		return (ExpandableListView) activity.findViewById(id);
	}

	public ImageView findImageViewById(int id) {
		return (ImageView) activity.findViewById(id);
	}

	public VideoView findVideoViewById(int id) {
		return (VideoView) activity.findViewById(id);
	}

	public ProgressBar findProgressBarById(int id) {
		return (ProgressBar) activity.findViewById(id);
	}

	public TableLayout findTableLayoutById(int id) {
		return (TableLayout) activity.findViewById(id);
	}

	public Chronometer findChronometerById(int id) {
		return (Chronometer) activity.findViewById(id);
	}

	public RadioGroup findRadioGroupById(int id) {
		return (RadioGroup) activity.findViewById(id);
	}
}

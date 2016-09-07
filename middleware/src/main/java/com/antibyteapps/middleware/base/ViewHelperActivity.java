package com.antibyteapps.middleware.base;

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
public interface ViewHelperActivity {
	TextView findTextViewById(int id);

	Button findButtonById(int id);

	ListView findListViewById(int id);

	ExpandableListView findExpandableListViewById(int id);

	ImageView findImageViewById(int id);

	VideoView findVideoViewById(int id);

	ProgressBar findProgressBarById(int id);

	TableLayout findTableLayoutById(int id);

	Chronometer findChronometerById(int id);

	RadioGroup findRadioGroupById(int id);

}

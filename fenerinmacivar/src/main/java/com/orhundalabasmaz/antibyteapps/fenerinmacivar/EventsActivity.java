package com.orhundalabasmaz.antibyteapps.fenerinmacivar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.orhundalabasmaz.antibyteapps.fenerinmacivar.network.EventStatus;
import com.orhundalabasmaz.antibyteapps.fenerinmacivar.network.MatchEvent;
import com.orhundalabasmaz.antibyteapps.middleware.base.BaseActivity;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unchecked")
public class EventsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		initEvents();
	}

/*	private void initEvents_old() {
		final List<MatchEvent> events = (List<MatchEvent>) getIntent().getSerializableExtra("EVENTS");
		final ExpandableListView listView = findExpandableListViewById(R.id.listView);
		for (MatchEvent event : events) {
			TextView view = new TextView(this);
			view.setText(event.getEventName());
			listView.addView(view);
		}
	}*/

	private static Calendar now;
	private static final Locale LOCALE_TR = new Locale("tr", "TR");
	private static final DateFormat sdf = new SimpleDateFormat("yyyyMMdd", LOCALE_TR);

	private void initEvents() {
		boolean showPastEvents = false;
		now = Calendar.getInstance();
		final List<MatchEvent> events = (List<MatchEvent>) getIntent().getSerializableExtra("EVENTS");
		final TableLayout tableLayout = findTableLayoutById(R.id.table);
		int index = 0;
		int eventIndex = 0;
		EventStatus eventStatus;
		final String TAB = "\t";
		final String TAB2 = TAB + TAB;
		for (MatchEvent event : events) {
			final String eventDate = event.getEventDate();
			eventStatus = getEventStatus(eventDate);
			if (!showPastEvents && EventStatus.PAST.equals(eventStatus)) continue;
			addValue(tableLayout, eventDate, index++, eventIndex, eventStatus);
			addValue(tableLayout, TAB + event.getEventHeader(), index++, eventIndex, eventStatus);
			addValue(tableLayout, TAB2 + event.getEventName(), index++, eventIndex, eventStatus);
			addValue(tableLayout, TAB2 + event.getEventPlaceAndTime(), index++, eventIndex, eventStatus);
			if (StringUtils.isNotBlank(event.getEventBroadcaster()))
				addValue(tableLayout, TAB2 + event.getEventBroadcaster(), index++, eventIndex, eventStatus);
			++eventIndex;
		}
	}

	private EventStatus getEventStatus(final String eventDate) {
		final String[] split = eventDate.split(" ");
		final int day = Integer.parseInt(split[0]);
		final int month = getMonth(split[1]);
		final int year = Integer.parseInt(split[2]);
		final Calendar date = Calendar.getInstance();
		date.set(year, month, day);

		final int res = sdf.format(date.getTimeInMillis()).compareTo(sdf.format(now.getTimeInMillis()));

		if (res > 0) return EventStatus.FUTURE;
		else if (res < 0) return EventStatus.PAST;
		else return EventStatus.PRESENT;
	}

	private int getMonth(final String month) {
		if (StringUtils.isBlank(month)) return -1;
		else if (month.equalsIgnoreCase("ocak")) return 0;
		else if (month.equalsIgnoreCase("şubat")) return 1;
		else if (month.equalsIgnoreCase("mart")) return 2;
		else if (month.equalsIgnoreCase("nisan")) return 3;
		else if (month.equalsIgnoreCase("mayıs")) return 4;
		else if (month.equalsIgnoreCase("haziran")) return 5;
		else if (month.equalsIgnoreCase("temmuz")) return 6;
		else if (month.equalsIgnoreCase("ağustos")) return 7;
		else if (month.equalsIgnoreCase("eylül")) return 8;
		else if (month.equalsIgnoreCase("ekim")) return 9;
		else if (month.equalsIgnoreCase("kasım")) return 10;
		else if (month.equalsIgnoreCase("aralık")) return 11;
		else return -1;
	}

	private void addValue(final TableLayout tableLayout, final String value, final int index, final int eventIndex, final EventStatus eventStatus) {
		TableRow row = new TableRow(this);
		row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
		TextView view = new TextView(this);
		view.setText(value);
		row.addView(view);
		switch (eventStatus) {
			case PAST:
				view.setTextColor(Color.WHITE);
				row.setBackgroundColor(Color.GRAY);
				break;
			case PRESENT:
				view.setTextColor(Color.BLACK);
				row.setBackgroundColor(Color.GREEN);
				break;
			case FUTURE:
				if (eventIndex % 2 == 0) {
					view.setTextColor(Color.BLUE);
					row.setBackgroundColor(Color.YELLOW);
				} else {
					view.setTextColor(Color.YELLOW);
					row.setBackgroundColor(Color.BLUE);
				}
				break;
			default:

		}
		tableLayout.addView(row, index);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_events, menu);
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

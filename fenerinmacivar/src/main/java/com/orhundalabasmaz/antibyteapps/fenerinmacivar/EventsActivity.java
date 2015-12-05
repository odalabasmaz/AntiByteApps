package com.orhundalabasmaz.antibyteapps.fenerinmacivar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.orhundalabasmaz.antibyteapps.fenerinmacivar.network.EventStatus;
import com.orhundalabasmaz.antibyteapps.fenerinmacivar.network.MatchEvent;
import com.orhundalabasmaz.antibyteapps.middleware.base.AdInfo;
import com.orhundalabasmaz.antibyteapps.middleware.base.BaseActivity;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("unchecked")
public class EventsActivity extends BaseActivity {
	private static final String TAB = "\t";
	private static final String TAB2 = "\t\t";
	private static final String NEW_LINE = "\n";
	private static final int COLOR_BLUE = Color.parseColor("#1F246A");
	private static final int COLOR_YELLOW = Color.parseColor("#D4C968"); //E9DB5B
	private static final int COLOR_GREEN_LIGHT = Color.parseColor("#7CFF00");
	private static final int COLOR_GREEN_DARK = Color.parseColor("#70E600");
	private static final int COLOR_ICON = Color.parseColor("#4B9900");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		initIcons();
		initEvents();
	}

	@Override
	protected AdInfo getAdInfo() {
		String adUnitId = getString(R.string.banner_ad_unit_id);
		String deviceId = getString(R.string.device_id);
		return new AdInfo(R.id.adView,
				AdSize.SMART_BANNER,
				adUnitId,
				deviceId);
	}

	private static Calendar now;
	private static final Locale LOCALE_TR = new Locale("tr", "TR");
	private static final DateFormat sdf = new SimpleDateFormat("yyyyMMdd", LOCALE_TR);

	private String getEventString(MatchEvent event) {
		final StringBuilder sb = new StringBuilder();
		sb.append(event.getEventHeader()).append(NEW_LINE)
				.append(TAB2).append(event.getEventName()).append(NEW_LINE)
				.append(TAB2).append(event.getEventPlaceAndTime()).append(NEW_LINE);
		if (StringUtils.isNotBlank(event.getEventBroadcaster()))
			sb.append(TAB2).append(event.getEventBroadcaster()).append(NEW_LINE);
		return sb.toString();
	}

	private void initEvents() {
		final TableLayout tableLayout = findTableLayoutById(R.id.table);
		final List<MatchEvent> events = (List<MatchEvent>) getIntent().getSerializableExtra("EVENTS");
		if (events == null) {
			addWarningMessageToTable(tableLayout);
			return;
		}

		boolean isAnyEvent = false;
		boolean showPastEvents = false;
		now = Calendar.getInstance();
		int eventIndex = 0;
		EventStatus eventStatus;
		String lastEventDate = null;
		final String TAB = "\t";
		final String TAB2 = TAB + TAB;
		for (MatchEvent event : events) {
			final String eventDate = event.getEventDate();
			eventStatus = getEventStatus(eventDate);
			if (!showPastEvents && EventStatus.PAST.equals(eventStatus)) {
				continue;
			} else {
				isAnyEvent = true;
			}

			if (!eventDate.equals(lastEventDate))
				addDateValue(tableLayout, eventDate);

			addValue(tableLayout, event, eventIndex, eventStatus);

			++eventIndex;
			lastEventDate = eventDate;
		}

		if (!isAnyEvent) {
			addNoEventFoundMessageToTable(tableLayout);
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

	private enum EVENT_TYPE {FOOTBALL, BASKETBALL, VOLLEYBALL, TABLE_TENNIS, DEFAULT}

	private Map<EVENT_TYPE, Bitmap> EVENT_ICONS = new HashMap<>();

	private void initIcons() {
		int width = 150;
		int height = 150;
		Bitmap bmp;
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.football3);
		EVENT_ICONS.put(EVENT_TYPE.FOOTBALL, Bitmap.createScaledBitmap(bmp, width, height, true));
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.basketball2);
		EVENT_ICONS.put(EVENT_TYPE.BASKETBALL, Bitmap.createScaledBitmap(bmp, width, height, true));
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.volleyball2);
		EVENT_ICONS.put(EVENT_TYPE.VOLLEYBALL, Bitmap.createScaledBitmap(bmp, width, height, true));
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.table_tennis);
		EVENT_ICONS.put(EVENT_TYPE.TABLE_TENNIS, Bitmap.createScaledBitmap(bmp, width, height, true));
//		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//		EVENT_ICONS.put(EVENT_TYPE.DEFAULT, Bitmap.createScaledBitmap(bmp, width, height, true));
	}

	private ImageView getIconForEvent(EventsActivity activity, EVENT_TYPE eventType) {
		final ImageView icon = new ImageView(activity);
		final Bitmap eventIcon = EVENT_ICONS.get(eventType);
		icon.setImageBitmap(eventIcon);
		return icon;
	}

	private static final List<String> FOOTBALL_EVENT_VALUES = new ArrayList<>();
	private static final List<String> BASKETBALL_EVENT_VALUES = new ArrayList<>();
	private static final List<String> VOLLEYBALL_EVENT_VALUES = new ArrayList<>();
	private static final List<String> TABLE_TENNIS_EVENT_VALUES = new ArrayList<>();

	static {
		FOOTBALL_EVENT_VALUES.add("futbol");
		FOOTBALL_EVENT_VALUES.add("süper lig");
		FOOTBALL_EVENT_VALUES.add("uefa avrupa ligi");

		BASKETBALL_EVENT_VALUES.add("basketbol");
		BASKETBALL_EVENT_VALUES.add("euroleague");

		VOLLEYBALL_EVENT_VALUES.add("voleybol");
		VOLLEYBALL_EVENT_VALUES.add("cev");

		TABLE_TENNIS_EVENT_VALUES.add("masa tenisi");
	}

	private EVENT_TYPE determineEventType(final String eventString) {
		final String eventValue = eventString.toLowerCase(LOCALE_TR);
		for (String value : TABLE_TENNIS_EVENT_VALUES) {
			if (eventValue.contains(value)) return EVENT_TYPE.TABLE_TENNIS;
		}
		for (String value : VOLLEYBALL_EVENT_VALUES) {
			if (eventValue.contains(value)) return EVENT_TYPE.VOLLEYBALL;
		}
		for (String value : BASKETBALL_EVENT_VALUES) {
			if (eventValue.contains(value)) return EVENT_TYPE.BASKETBALL;
		}
		for (String value : FOOTBALL_EVENT_VALUES) {
			if (eventValue.contains(value)) return EVENT_TYPE.FOOTBALL;
		}
		return EVENT_TYPE.DEFAULT;
	}

	private int getDpSizeInPixels(View view, int dps) {
		final float scale = view.getContext().getResources().getDisplayMetrics().density;
		return (int) (dps * scale + 0.5f);
	}

	private void addValue(final TableLayout tableLayout, final MatchEvent event,
	                      final int eventIndex, final EventStatus eventStatus) {
		final EVENT_TYPE eventType = determineEventType(event.toString());

		final TableRow row = new TableRow(this);
		row.setLayoutParams(new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.MATCH_PARENT));
		tableLayout.addView(row);

		final LinearLayout rowLayout = new LinearLayout(this);
		/*rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));*/
//		rowLayout.setWeightSum(1.0f);
		row.addView(rowLayout);

		final ImageView iconView = getIconForEvent(this, eventType);
		int iconSize = getDpSizeInPixels(iconView, 50);
		final LinearLayout.LayoutParams iconLayout = new LinearLayout.LayoutParams(
				iconSize,
				ViewGroup.LayoutParams.MATCH_PARENT);
		iconLayout.gravity = Gravity.CENTER;
		iconView.setLayoutParams(iconLayout);
//		iconView.setBackgroundColor(COLOR_ICON);
		rowLayout.addView(iconView);

		final LinearLayout eventLayout = new LinearLayout(this);
		final LinearLayout.LayoutParams eventLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		eventLayoutParams.setMargins(getDpSizeInPixels(eventLayout, 5), 0, 0, 0);
		eventLayout.setLayoutParams(eventLayoutParams);
		eventLayout.setOrientation(LinearLayout.VERTICAL);
		rowLayout.addView(eventLayout);

		int valueIndex = 0;
		addEventToLayout(eventLayout, rowLayout, eventStatus, event.getEventHeader(), eventIndex, valueIndex++);
		addEventToLayout(eventLayout, rowLayout, eventStatus, event.getEventName(), eventIndex, valueIndex++);
		addEventToLayout(eventLayout, rowLayout, eventStatus, event.getEventPlaceAndTime(), eventIndex, valueIndex++);
		addEventToLayout(eventLayout, rowLayout, eventStatus, event.getEventBroadcaster(), eventIndex, valueIndex);
	}

	private void setColor(LinearLayout rowLayout, EventStatus eventStatus,
	                      TextView textView, int rowIndex) {
		switch (eventStatus) {
			case PAST:
				textView.setTextColor(Color.WHITE);
				rowLayout.setBackgroundColor(Color.GRAY);
				break;
			case PRESENT:
				textView.setTextColor(Color.BLACK);
				if (rowIndex % 2 == 0) {
					rowLayout.setBackgroundColor(COLOR_GREEN_LIGHT);
				} else {
					rowLayout.setBackgroundColor(COLOR_GREEN_DARK);
				}
				break;
			case FUTURE:
				if (rowIndex % 2 == 0) {
					textView.setTextColor(COLOR_BLUE);
					rowLayout.setBackgroundColor(COLOR_YELLOW);
				} else {
					textView.setTextColor(COLOR_YELLOW);
					rowLayout.setBackgroundColor(COLOR_BLUE);
				}
				break;
			default:
		}
	}

	private void addEventToLayout(final LinearLayout eventLayout, LinearLayout rowLayout, EventStatus eventStatus,
	                              final String value, int rowIndex, int valueIndex) {
		if (valueIndex == 3 && StringUtils.isBlank(value))
			return;
		final TextView textView = new TextView(this);
		final LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		if (valueIndex == 0)
			textView.setTypeface(Typeface.DEFAULT_BOLD);
		else
			textLayoutParams.setMargins(getDpSizeInPixels(textView, 10), 0, 0, 0);
		textView.setLayoutParams(textLayoutParams);
		textView.setText(value);
		setColor(rowLayout, eventStatus, textView, rowIndex);
		eventLayout.addView(textView);
	}

	private void addDateValue(final TableLayout tableLayout, final String value) {
		final TableRow row = new TableRow(this);
		row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));
		final TextView view = new TextView(this);
		view.setText(value);
		row.addView(view);
		view.setTextColor(Color.WHITE);
		row.setBackgroundColor(Color.GRAY);
		tableLayout.addView(row);
	}

	private void addWarningMessageToTable(TableLayout tableLayout) {
		final TableRow row = new TableRow(this);
		row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));
		final TextView view = new TextView(this);
		final String value = getString(R.string.warning_message);
		view.setText(value);
		view.setTextColor(Color.YELLOW);
		row.addView(view);
		tableLayout.addView(row, 0);
	}

	private void addNoEventFoundMessageToTable(TableLayout tableLayout) {
		final TableRow row = new TableRow(this);
		row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));
		final TextView view = new TextView(this);
		final String value = getString(R.string.no_event_message);
		view.setText(value);
		view.setTextColor(Color.YELLOW);
		row.addView(view);
		tableLayout.addView(row, 0);
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

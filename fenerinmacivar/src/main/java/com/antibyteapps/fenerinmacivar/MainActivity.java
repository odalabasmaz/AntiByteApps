package com.antibyteapps.fenerinmacivar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.antibyteapps.fenerinmacivar.network.GetContentAsyncTask;
import com.antibyteapps.fenerinmacivar.network.MatchEvent;
import com.antibyteapps.fenerinmacivar.R;
import com.antibyteapps.fenerinmacivar.network.FBConnect;
import com.antibyteapps.middleware.base.BaseActivity;

import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();
		final String FB_URL = "http://www.fenerbahce.org/index.asp";
		new GetContentAsyncTask() {
			@Override
			protected void onPostExecute(final Document doc) {
				final Map<String, Serializable> objects = new HashMap<>();
				if (doc != null) {
					final FBConnect fbConnect = FBConnect.getInstance();
					final List<MatchEvent> matchEvents = fbConnect.getEvents(doc);
					objects.put("EVENTS", (Serializable) matchEvents);
				}
				startNextActivity(EventsActivity.class, objects);
				MainActivity.this.finish();
			}
		}.execute(FB_URL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_main, menu);
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

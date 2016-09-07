package com.antibyteapps.fenerinmacivar.network;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

/**
 * @author Orhun Dalabasmaz
 * @since 05.04.2015.
 */
public class GetContentAsyncTask extends AsyncTask<String, Void, Document> {
	private final static int TIMEOUT = 10000;

	@Override
	protected Document doInBackground(String... params) {
		Document doc = null;
		try {
			final String url = params[0];
			doc = Jsoup.connect(url).timeout(TIMEOUT).ignoreContentType(true).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}

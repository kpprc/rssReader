package edu.kpprc.rssreader;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * Created by Frank on 2017/3/9.
 */

public class RssService extends IntentService {
    /*RSS Feed URL*/
    private static final String RSS_LINK = "http://rss.cnn.com/rss/edition.rss";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    private String TAG = "RssService";

    public RssService() {
        super("RssService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        List<RssItem> rssItems = null;
        try {
            RssParser parser = new RssParser();
            rssItems = parser.parse(getInputStream(RSS_LINK));
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}

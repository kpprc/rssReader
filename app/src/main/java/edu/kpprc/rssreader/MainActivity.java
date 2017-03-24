package edu.kpprc.rssreader;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class  MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //private ProgressBar progressBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
        // listView.setAdapter(adapter);
        startService();

    }

    private void startService() {
        Intent intent = new Intent(MainActivity.this, RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        startService(intent);
    }
    /**
     * Once the {@link RssService} finishes its task, the result is sent to this
     * ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            //progressBar.setVisibility(View.GONE);
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            if (items != null) {
                RssAdapter adapter = new RssAdapter(MainActivity.this, items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, "An error occurred while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
        };
    };
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        RssAdapter adapter = (RssAdapter) adapterView.getAdapter();
        RssItem item = (RssItem) adapter.getItem(i);
        Uri uri = Uri.parse(item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}

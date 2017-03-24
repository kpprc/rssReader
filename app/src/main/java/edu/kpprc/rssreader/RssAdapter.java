package edu.kpprc.rssreader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Frank on 2017/3/9.
 */

public class RssAdapter extends BaseAdapter {
    private final List<RssItem> items;
    private final Context context;
    public RssAdapter(Context context, List<RssItem> items) {
        this.items = items;
        this.context = context;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.rss_item, null);
            holder = new ViewHolder();
            holder.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemTitle.setText(items.get(i).getTitle());
        return view;
    }

    static class ViewHolder {
        TextView itemTitle;
    }
}

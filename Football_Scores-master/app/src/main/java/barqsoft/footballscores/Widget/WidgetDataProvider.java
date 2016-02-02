package barqsoft.footballscores.Widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.Utils.Config;
import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utils.Utils;
import barqsoft.footballscores.Utils.WidgetUtils;

/**
 * Created by bebi2 on 2/1/2016.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";


    List<String> mCollection = new ArrayList<>();
    Context mContext = null;


    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }


    @Override
    public void onCreate() {
        initData();
    }


    @Override
    public void onDataSetChanged() {
        initData();
    }


    @Override
    public void onDestroy() {


    }


    @Override
    public int getCount() {
        return mCollection.size();
    }


    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, mCollection.get(position));
        return view;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }


    @Override
    public int getViewTypeCount() {
        return 1;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }


    private void initData() {
        mCollection.clear();
        for (int i = 1; i <= 10; i++) {
            mCollection.add("ListView item " + i);
        }
    }
}

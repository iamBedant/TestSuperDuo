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

    Cursor data = null;
    Context mContext = null;


    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }


    @Override
    public void onCreate() {

    }


    @Override
    public void onDataSetChanged() {
        initData();
    }


    @Override
    public void onDestroy() {
        if (data != null) {
            data.close();
            data = null;
        }

    }


    @Override
    public int getCount() {
        return data == null ? 0 : data.getCount();
    }


    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(position)) {
            return null;
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.today_list_item);
        WidgetUtils.setFixtureView(mContext, views, data);
        final Intent intentMessage = new Intent();
        final Bundle extras = new Bundle();
        extras.putString(Config.LATEST_FIXTURE_SCORES_DATE, data.getString(data.getColumnIndex(DatabaseContract.scores_table.DATE_COL)));
        extras.putInt(Config.SCORES_MATCH_ID, data.getInt(data.getColumnIndex(DatabaseContract.scores_table.MATCH_ID)));
        intentMessage.putExtras(extras);
        views.setOnClickFillInIntent(R.id.today_list_item, intentMessage);
        return views;
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
        if (data != null) {
            data.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        // load today fixture
        data = mContext.getContentResolver().query(
                DatabaseContract.scores_table.buildScoreWithDate(),
                null,
                null,
                new String[]{Utils.getTodayLocaleDate()},
                DatabaseContract.scores_table.TIME_COL + " ASC, " + DatabaseContract.scores_table.HOME_COL + " ASC");

        // and restore the identity again
        Binder.restoreCallingIdentity(identityToken);
    }
}

package barqsoft.footballscores.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;

/**
 * Created by bebi2 on 2/2/2016.
 */
public class WidgetUtils {



    public static void setFixtureView(Context context, RemoteViews views, Cursor cursor) {

      //  setPicasso(context, views, R.id.home_crest, cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_LOGO_COL)));

        String homeTeamName = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_COL));
        views.setTextViewText(R.id.home_name, homeTeamName);
        //views.setTextColor(R.id.home_name, ContextCompat.getColor(context, R.color.blue09));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            Utils.setImageContentDescription(views, R.id.home_crest, homeTeamName);
        }
        // score and match time
        views.setTextViewText(R.id.score_textview, Utils.getScores(
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL)),
                cursor.getInt(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL))));
       // views.setTextColor(R.id.score_textview, ContextCompat.getColor(context, R.color.blue09));
        views.setTextViewText(R.id.date_textview, cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.TIME_COL)));
       // views.setTextColor(R.id.date_textview, ContextCompat.getColor(context, R.color.blue09));

        // away team logo and name
      //  setPicasso(context, views, R.id.away_crest, cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_LOGO_COL)));

        String awayTeamName = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_COL));
        views.setTextViewText(R.id.away_name, awayTeamName);
      //  views.setTextColor(R.id.away_name, ContextCompat.getColor(context, R.color.blue09));

        // set content description on away team logo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            Utils.setImageContentDescription(views, R.id.away_crest, awayTeamName);
        }
    }

    private static void setPicasso(@NonNull Context context, RemoteViews views, int viewId, @NonNull String imageUrl) {
        // refer https://github.com/square/picasso/issues/587
        /*Picasso.with(context)
                .load(Utility.builtURI(imageUrl))
                .into(views, viewId, appWidgetIds);*/
        /**
         * To fix the issue of Picasso call from "RemoteViews getViewAt(int position)"
         * need to change the code as app is crashing for today widget while image loading Picasso bug
         * java.lang.IllegalStateException: Method call should happen from the main thread.
         *
         2nd option : use Glide for image loading on RemoteView.
         *
         */

        try {
            //java.lang.IllegalArgumentException: Path must not be empty
            if (imageUrl.length() > 0) {
                Bitmap logoBitmap = Picasso.with(context).load(Utils.builtURI(imageUrl)).get();
                views.setImageViewBitmap(viewId, logoBitmap);
            } else {
                views.setImageViewResource(viewId, R.drawable.ic_launcher);
            }

        } catch (IOException | IllegalArgumentException e) {
            views.setImageViewResource(viewId, R.drawable.ic_launcher);
            e.printStackTrace();
        }

    }

}

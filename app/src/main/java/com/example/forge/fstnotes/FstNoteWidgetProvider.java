package com.example.forge.fstnotes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Created by Forge on 10/15/2016.
 */

public class FstNoteWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, FstNoteWidgetService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.fstnote_widget);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(appWidgetIds[i], R.id.notes_widget_list, intent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);        Log.i("WidgetProvider", "Widget on update! " + appWidgetIds[0]);
    }
}
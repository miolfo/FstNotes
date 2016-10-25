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

    public static final String ADD_NOTE_ACTION = "com.example.forge.fstnotes.ADD_NOTE_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ADD_NOTE_ACTION)){
            Intent startedIntent = new Intent(context, MainActivity.class);
            context.startActivity(startedIntent);
        }

        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, FstNoteWidgetService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.fstnote_widget);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(appWidgetIds[i], R.id.notes_widget_list, intent);

            //Intent addNewIntent = new Intent(context, FstNoteWidgetProvider.class);
            //addNewIntent.setAction(FstNoteWidgetProvider.ADD_NOTE_ACTION);
            //addNewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            //PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, addNewIntent,
            //        PendingIntent.FLAG_UPDATE_CURRENT);
            //rv.setPendingIntentTemplate(R.id.add_new_note_widget, toastPendingIntent);

            Intent intent1 = new Intent(ADD_NOTE_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.add_new_note_widget, pendingIntent );

            //RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.fstnote_widget);
            //remoteViews.setOnClickPendingIntent(R.id.add_new_note_widget, getPendingSelfIntent(context, ADD_NOTE_ACTION));

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }


        super.onUpdate(context, appWidgetManager, appWidgetIds);        Log.i("WidgetProvider", "Widget on update! " + appWidgetIds[0]);
    }
}

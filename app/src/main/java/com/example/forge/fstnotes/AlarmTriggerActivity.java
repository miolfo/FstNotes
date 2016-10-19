package com.example.forge.fstnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by Forge on 10/13/2016. */


public class AlarmTriggerActivity extends Activity {

    private Activity thisActivity;
    private Intent mIntent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String message = getIntent().getStringExtra("REMINDER_MESSAGE");
        final int reminderId = getIntent().getIntExtra("REMINDER_INT",-1);
        thisActivity = this;
        final ReminderManager rm = new ReminderManager(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Reminder")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        thisActivity.closeContextMenu();
                        thisActivity.finish();
                    }
                })
                .setNegativeButton("Snooze", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(cal.getTimeInMillis() + 60 * 5 * 1000);
                        rm.AddAlarm(cal, reminderId, message);
                        dialog.cancel();
                        thisActivity.closeContextMenu();
                        thisActivity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        //Update widget to make current note be shown as expired
        Intent intent = new Intent(this, FstNoteWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
        awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(new ComponentName(this, FstNoteWidgetProvider.class)), R.id.notes_widget_list);
    }
}

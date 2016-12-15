package com.example.forge.fstnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.TimerTask;

/**
 * Created by Forge on 10/13/2016. */


public class AlarmTriggerActivity extends Activity {

    private Activity thisActivity;
    private static Uri mAlarmUri = null;
    private static Ringtone mAlarm = null;

    private static final String RESTRICTED_MESSAGE = "Unlock the phone to view alarm!";
    private static final int ALARM_RUNNING_TIME_MS = 30000;
    private static final long[] VIBRATION_PATTERN = {0,400,400,400,400,400};

    private Runnable stopAlarmScheduled = new Runnable() {
        @Override
        public void run() {
            stopAlarm();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;

        final int reminderId = getIntent().getIntExtra("REMINDER_INT",-1);
        final ReminderManager rm = new ReminderManager(this);
        final Vibrator v = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        Handler h = new Handler();
        //Play alert sound
        try {
            mAlarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mAlarm = RingtoneManager.getRingtone(getApplicationContext(), mAlarmUri);
            mAlarm.play();
            //Set the alarm sound to stop after x seconds
            h.postDelayed(stopAlarmScheduled, ALARM_RUNNING_TIME_MS);
            v.vibrate(VIBRATION_PATTERN, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Set flags in order to make the phone screen power up when alarm plays
        //and also show the notification throught the lock screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        //If the screen is locked, don't show the actual reminder message
        final String message;
        KeyguardManager km = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        if(km.inKeyguardRestrictedInputMode()) message = RESTRICTED_MESSAGE;
        else message = getIntent().getStringExtra("REMINDER_MESSAGE");

        final String origMessage = getIntent().getStringExtra("REMINDER_MESSAGE");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Reminder")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        AlarmTriggerActivity.stopAlarm();
                        dialog.cancel();
                        thisActivity.closeContextMenu();
                        thisActivity.finish();
                    }
                })
                .setNegativeButton("Snooze", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        AlarmTriggerActivity.stopAlarm();
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(cal.getTimeInMillis() + 60 * 5 * 1000);
                        rm.AddAlarm(cal, reminderId, origMessage);
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

    private static void stopAlarm(){
        if(mAlarm != null) mAlarm.stop();
    }

}

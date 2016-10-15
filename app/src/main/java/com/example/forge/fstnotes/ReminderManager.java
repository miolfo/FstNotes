package com.example.forge.fstnotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Forge on 10/12/2016.
 */


/*
A reminder manager class to manage the Alarms for the system
 */
public class ReminderManager {

    private Context mContext;
    private AlarmManager mAlarmManager;

    public ReminderManager(Context context){
        mContext = context;
        mAlarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public void AddAlarm(Calendar calendar, int intentId, String reminderMessage){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("REMINDER_MESSAGE", reminderMessage);
        intent.putExtra("REMINDER_ID", intentId);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, intentId, intent, 0);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }

    public void CancelAlarm(int intentId){
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, intentId, intent, 0);
        mAlarmManager.cancel(alarmIntent);
    }
}

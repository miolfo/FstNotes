package com.example.forge.fstnotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Forge on 2.11.2016.
 */

public class FstBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Log.i("FstNotes", "Rebooted phone and re-set alarms");
            //Re-set all the alarms
            FileHandler fh = new FileHandler(context);
            ReminderManager rm = new ReminderManager(context);
            ArrayList<Note> notes = fh.LoadNotes();
            for(Note n: notes){
                if(n.HasReminder() && Calendar.getInstance().getTimeInMillis() < n.GetNoteCalendar().getTimeInMillis()){
                    rm.AddAlarm(n.GetNoteCalendar(), n.GetNoteId(), n.GetNoteText());
                }
            }
        }
    }
}

package com.example.forge.fstnotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Forge on 10/13/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO: Restart all notifications on device restart
        //TODO: https://developer.android.com/training/scheduling/alarms.html#boot
        Intent i = new Intent(context, AlarmTriggerActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

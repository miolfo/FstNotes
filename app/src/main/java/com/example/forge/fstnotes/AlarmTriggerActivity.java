package com.example.forge.fstnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Forge on 10/13/2016. */


public class AlarmTriggerActivity extends Activity {

    private Activity thisActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisActivity = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Reminder")
                .setMessage("Reminder text placeholder")
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
                        dialog.cancel();
                        thisActivity.closeContextMenu();
                        thisActivity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

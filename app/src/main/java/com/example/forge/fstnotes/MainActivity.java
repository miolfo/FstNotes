package com.example.forge.fstnotes;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private NotePopup mNotePopup;
    private NoteAdapter mNoteAdapter;
    private FileHandler mFileHandler;
    private AlertDialog.Builder mDialogBuilder;
    private ItemSelectDialog mItemSelectDialog;
    private ReminderManager mReminderManager;

    private int mPosClicked = -1;   //Used with long click to identify which item was clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = (ListView) findViewById(R.id.note_list);
        mNotePopup = new NotePopup(this);
        mFileHandler = new FileHandler(this);
        mItemSelectDialog = new ItemSelectDialog(this);
        mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setMessage("Delete note?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener);
        //TODO: On startup, check if alarms are set, and set them if they aren't
        mReminderManager = new ReminderManager(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotePopup.Show();
            }
        });

        initListview();
        //Load the notes stored in internal storage
        ArrayList<Note> existingNotes = mFileHandler.LoadNotes();
        for(Note n: existingNotes){
            AddNoteInitial(n);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    //Delete note
                    Note n = (Note)mNoteAdapter.getItem(mPosClicked);
                    mFileHandler.DeleteNote(n);
                    mNoteAdapter.remove(n);
                    mReminderManager.CancelAlarm(n.GetNoteId());
                    mNoteAdapter.notifyDataSetChanged();
                    updateWidget();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    public void AddNote(Note n){
        mNoteAdapter.add(n);
        mFileHandler.WriteNote(n);
        if(n.HasReminder() && Calendar.getInstance().getTimeInMillis() < n.GetNoteCalendar().getTimeInMillis()){
            addAlarm(n);
        }
        updateWidget();
        mNoteAdapter.sort(new Util.NoteComparator());
    }

    //Called from ItemSelectDialog when delete button is clicked
    public void DeleteNoteClicked(){
        mDialogBuilder.show();
    }

    //Called from ItemSelectDialog when edit button is clicked
    public void EditNoteClicked(){
        //TODO: Implementation

    }

    private void updateWidget(){
        Intent intent = new Intent(this, FstNoteWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
        awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(new ComponentName(this, FstNoteWidgetProvider.class)), R.id.notes_widget_list);
    }

    //Adding notes in the startup, where they don't have to be written to disk
    //and they don't require an Alarm
    private void AddNoteInitial(Note n){
        mNoteAdapter.add(n);
        //TODO: Don't necessarily reset the alarm, check if it exists, then redo it if it doesn't exist
        //Don't add alarms for already expired notes
        if(n.HasReminder() && Calendar.getInstance().getTimeInMillis() < n.GetNoteCalendar().getTimeInMillis()){
            addAlarm(n);
        }
    }

    private void initListview(){
        ArrayList<Note> notes = new ArrayList<>();
        mNoteAdapter = new NoteAdapter(this, notes);
        mListView.setAdapter(mNoteAdapter);
        mListView.setLongClickable(true);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mPosClicked = position;
                mItemSelectDialog.show();
                return false;
            }
        });
    }

    private void addAlarm(Note n){
        mReminderManager.AddAlarm(n.GetNoteCalendar(), n.GetNoteId(), n.GetNoteText());
    }
}

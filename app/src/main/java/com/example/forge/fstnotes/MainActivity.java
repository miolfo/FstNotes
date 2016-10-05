package com.example.forge.fstnotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private NewNotePopup mNewNotePopup;
    private NoteAdapter mNoteAdapter;
    private FileHandler mFileHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = (ListView) findViewById(R.id.note_list);
        mNewNotePopup = new NewNotePopup(this);
        mFileHandler = new FileHandler(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewNotePopup.Show();
            }
        });

        initListview();
        //addTestContent();
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

    public void AddNote(Note n){
        mNoteAdapter.add(n);
        mFileHandler.WriteNote(n);
    }

    //Adding notes in the startup, where they dont have to be written to disk
    private void AddNoteInitial(Note n){
        mNoteAdapter.add(n);
    }

    private void initListview(){
        ArrayList<Note> notes = new ArrayList<>();
        mNoteAdapter = new NoteAdapter(this, notes);
        mListView.setAdapter(mNoteAdapter);
    }

    private void addTestContent(){
        String[] testNotes = {"123", "sirkustirehtööri", "asd asd asd asd asd asd asd asd asd ", "     ", "TOP KEK"};
        for(String s: testNotes){
            AddNote(new Note(s));
        }
    }
}

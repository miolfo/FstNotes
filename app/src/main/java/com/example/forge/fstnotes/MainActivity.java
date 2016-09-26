package com.example.forge.fstnotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private NewNotePopup mNewNotePopup;
    private NoteAdapter mNoteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = (ListView) findViewById(R.id.note_list);
        mNewNotePopup = new NewNotePopup(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewNotePopup.Show();
            }
        });

        addTestContent();
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
    }

    private void addTestContent(){
        ArrayList<Note> notes = new ArrayList<>();
        String[] testNotes = {"123", "MOIKKA EEVI TÄÄ ON TÄRKEÄ MUISTUTUS JA TESTAA VIESTIN PITUUTTA", "juuh elikkäs", "kala"};
        for (String s: testNotes) {
            notes.add(new Note(s));
        }
        mNoteAdapter = new NoteAdapter(this, notes);
        mListView.setAdapter(mNoteAdapter);
    }
}

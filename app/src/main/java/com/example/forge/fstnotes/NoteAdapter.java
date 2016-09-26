package com.example.forge.fstnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Forge on 9/20/2016.
 */

public class NoteAdapter extends ArrayAdapter {

    public NoteAdapter(Context context, ArrayList<Note> notes){
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Note n = (Note)getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_note, parent, false);
        }

        TextView noteText = (TextView)convertView.findViewById(R.id.note_text);
        TextView reminderText = (TextView)convertView.findViewById(R.id.reminder_text);

        noteText.setText(n.GetNoteText());
        reminderText.setText(n.HasReminder() ? "Reminder" : "No reminder");

        return convertView;
    }
}

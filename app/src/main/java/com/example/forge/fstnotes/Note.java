package com.example.forge.fstnotes;

/**
 * Created by Forge on 9/20/2016.
 */

/**
 * Class used to define a single note
 */
public class Note {

    private String noteText;
    private boolean reminder;

    public Note(String note){
        noteText = note;
    }

    public String GetNoteText(){
        return noteText;
    }

    public void EditNote(String newNote){
        noteText = newNote;
    }

    public boolean HasReminder(){
        return reminder;
    }

    //TODO: Add a time to the reminder
    public void SetReminder(){
        reminder = true;
    }

    @Override
    public String toString(){
        return noteText;
    }
}

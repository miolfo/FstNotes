package com.example.forge.fstnotes;

/**
 * Created by Forge on 9/20/2016.
 */



/**
 * Class used to define a single note
 */
public class Note {

    public static class NoteTime {
        public int hour, minute;
    }

    public static class NoteDate {
        public int day, month, year;
    }
    private String noteText;
    private boolean reminder;
    private NoteDate noteDate;
    private NoteTime noteTime;

    public Note(String note){
        noteText = note;
    }

    public String GetNoteText(){
        return noteText;
    }

    public void EditNoteText(String newNote){
        noteText = newNote;
    }
    public void EditNoteTime(NoteTime newNoteTime){ noteTime = newNoteTime;}
    public void EditNoteDate(NoteDate newNoteDate){ noteDate = newNoteDate;}

    public boolean HasReminder(){
        return reminder;
    }

    //TODO: Add a noteTime to the reminder
    public void SetReminder(NoteTime noteTime, NoteDate noteDate){
        reminder = true;
        this.noteTime = noteTime;
        this.noteDate = noteDate;
    }

    public NoteDate GetNoteDate(){
        return noteDate;
    }

    public NoteTime GetNoteTime(){
        return noteTime;
    }

    public String GetReminderString(){
        if(!reminder) return "----/--/--, --:--";
        else{
            return noteDate.year + "/" + noteDate.month + "/" + noteDate.day + ", "
                    + noteTime.hour + ":" + noteTime.minute;
        }
    }

    @Override
    public String toString(){
        return noteText;
    }
}

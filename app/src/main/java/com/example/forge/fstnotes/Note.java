package com.example.forge.fstnotes;

/**
 * Created by Forge on 9/20/2016.
 */


import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Class used to define a single note
 */
public class Note {

    public static class NoteTime {
        public int hour, minute;
        @Override
        public String toString(){
            return (hour + ":" + minute);
        }
    }

    public static class NoteDate {
        public int day, month, year;
        @Override
        public String toString(){
            return (day + ":" + month + ":" + year);
        }
    }

    private static Random mRandom;
    private String noteText;
    private String noteFileName;
    private boolean reminder;
    private NoteDate noteDate;
    private NoteTime noteTime;
    private int noteId;

    public Note(String note){
        mRandom = new Random();
        noteText = note;
        //Placeholders for date and time
        NoteDate nd = new NoteDate();
        nd.day = -1;
        nd.month = -1;
        nd.year = -1;
        NoteTime nt = new NoteTime();
        nt.hour = -1;
        nt.minute = -1;
        noteDate = nd;
        noteTime = nt;
        noteFileName = createFileName();
    }

    public String GetNoteText(){
        return noteText;
    }

    public String GetNoteFileName() {
        return noteFileName;
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

    //Set the filename, used when loading notes from internal storage
    /*public void SetFileName(String fileName){
        noteFileName = fileName;
    }*/

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

    public int GetNoteId(){
        return noteId;
    }

    public Calendar GetNoteCalendar(){
        Calendar cal = Calendar.getInstance();
        Note.NoteDate nd = GetNoteDate();
        Note.NoteTime nt = GetNoteTime();
        cal.set(nd.year, nd.month, nd.day);
        cal.set(Calendar.HOUR_OF_DAY, nt.hour);
        cal.set(Calendar.MINUTE, nt.minute);
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    /*@Override
    public String toString(){
        return noteDate.toString() + "," + noteTime.toString() + "," + noteText + "," + noteFileName;
    }*/

    private String createFileName(){
        //Generate an identifier for use with reminder PendingIntents
        int i1 = noteText.hashCode();
        int i2 = mRandom.nextInt();
        noteId = i1 | i2;
        String filename = UUID.randomUUID().toString();
        //Todo: Check that the filename and/or id doesn't already exist to avoid overwriting
        return (filename + ".fnote");
    }
}

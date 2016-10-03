package com.example.forge.fstnotes;

/**
 * Created by Forge on 9/20/2016.
 */


import java.util.Random;

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

    private Random mRandom;
    private String noteText;
    private String noteFileName;
    private boolean reminder;
    private NoteDate noteDate;
    private NoteTime noteTime;

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
    public void SetFileName(String fileName){
        noteFileName = fileName;
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
        return noteDate.toString() + "," + noteTime.toString() + "," + noteText;
    }

    private String createFileName(){
        int rndmInt = mRandom.nextInt();
        int noteHash = GetNoteText().hashCode();
        //Todo: Check that the filename doesnt already exist to avoid overwriting
        return (String.valueOf(rndmInt) + String.valueOf(noteHash) + ".fnote");
    }
}

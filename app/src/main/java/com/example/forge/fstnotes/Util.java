package com.example.forge.fstnotes;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Forge on 10/16/2016.
 */

public class Util {
    //Sort the list of notes, and return them as a Pair (i = 0 = expired,
    //i = 2 = upcoming notes)
    public static Pair<ArrayList<Note>, ArrayList<Note>> SortNotes(ArrayList<Note> allNotes){
        ArrayList<Note> expiredNotes = new ArrayList<>();
        ArrayList<Note> upcomingNotes = new ArrayList<>();
        long currTimeMillis = Calendar.getInstance().getTimeInMillis();
        for(Note n: allNotes){
            if(n.GetNoteCalendar().getTimeInMillis() < currTimeMillis){
                expiredNotes.add(n);
            } else{
                upcomingNotes.add(n);
            }
        }
        return new Pair(expiredNotes, upcomingNotes);
    }

    public static ArrayList<Note> SortNotesByDate(ArrayList<Note> notes){
        Collections.sort(notes, new NoteComparator());
        return notes;
    }

    //Convert the calendar string to a shorter representation
    public static String TimeAsString(Calendar cal){
        Calendar curr = Calendar.getInstance();
        long diff = cal.getTimeInMillis() - curr.getTimeInMillis();
        //Calendar diffc = Calendar.getInstance();
        //diffc.setTimeInMillis(diff);
        if(diff < 0) return "Expired";
        //If the date is same, return the time of the note
        else if(sameDate(cal, curr)){
            return FormatTime(cal);
        }
        //Else return the date
        else{
            return FormatDate(cal);
        }
    }

    public static String FormatDate(Calendar cal){
        return cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String FormatTime(Calendar cal){
        return cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
    }

    //Check if the both calendars have same date
    private static boolean sameDate(Calendar c1, Calendar c2){
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }


    private static class NoteComparator implements Comparator<Note>{
        @Override
        public int compare(Note o1, Note o2) {
            long t1 = o1.GetNoteCalendar().getTimeInMillis();
            long t2 = o2.GetNoteCalendar().getTimeInMillis();
            if(t1 < t2) return -1;
            if(t1 == t2) return 0;
            return 1;
        }
    }
}

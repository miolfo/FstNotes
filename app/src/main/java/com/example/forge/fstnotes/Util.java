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

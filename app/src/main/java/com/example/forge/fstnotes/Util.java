package com.example.forge.fstnotes;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;

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
}

package com.example.forge.fstnotes;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Forge on 10/3/2016.
 */

public class FileHandler {
    private File mFileDir;
    private FileOutputStream mFos;
    private FileInputStream mFis;
    private Context mContext;

    public FileHandler(Context context){
        mFileDir = context.getFilesDir();
        mContext = context;
    }

    public boolean WriteNote(Note note){
        try {
            mFos = mContext.openFileOutput(note.GetNoteFileName(), Context.MODE_APPEND);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
        try{
            mFos.write(note.toString().getBytes());
            Log.i("FileHandler", "Wrote " + note.toString());
            mFos.close();
        } catch (java.io.IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Note> LoadNotes(){
        File allFiles = mContext.getFilesDir();
        File[] fileList = allFiles.listFiles();
        ArrayList<Note> notes = new ArrayList<>();
        //Find all note files
        for(File f: fileList){
            if(f.getName().contains(".fnote")){
                Log.i("FileHandler", f.getAbsolutePath());
                try {
                    mFis = mContext.openFileInput(f.getName());
                    InputStreamReader isr = new InputStreamReader(mFis);
                    BufferedReader br = new BufferedReader(isr);
                    try {
                        String fileContents = br.readLine();
                        //Log.i("FileHandler", fileContents);
                        Note n = noteFromString(fileContents);
                        notes.add(n);
                    } catch (java.io.IOException e){
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e){
                    //Should never happen, since file name is gotten from the files
                    e.printStackTrace();
                }
            }
        }
        return notes;
    }

    private Note noteFromString(String noteString){
        String[] splitStr = noteString.split(",");
        Log.i("FileHandler", noteString);
        String noteMsg = splitStr[2];
        String noteTimeStr = splitStr[1];
        String noteDateStr = splitStr[0];
        String noteFileName = splitStr[3];

        String[] splitTime = noteTimeStr.split(":");
        Note.NoteTime nt = new Note.NoteTime();
        nt.hour = Integer.parseInt(splitTime[0]);
        nt.minute = Integer.parseInt(splitTime[1]);

        String[] splitDate = noteDateStr.split(":");
        Note.NoteDate nd = new Note.NoteDate();
        nd.day = Integer.parseInt(splitDate[0]);
        nd.month = Integer.parseInt(splitDate[1]);
        nd.year = Integer.parseInt(splitDate[2]);

        Note n = new Note(noteMsg);
        //Add a reminder if a reminder is set
        if(nd.day != -1) n.SetReminder(nt, nd);
        n.SetFileName(noteFileName);
        return n;
    }
}

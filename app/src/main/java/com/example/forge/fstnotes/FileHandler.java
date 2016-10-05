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
import com.google.gson.Gson;


/**
 * Created by Forge on 10/3/2016.
 */

public class FileHandler {
    private File mFileDir;
    private FileOutputStream mFos;
    private FileInputStream mFis;
    private Context mContext;
    private Gson mGson;

    public FileHandler(Context context){
        mFileDir = context.getFilesDir();
        mContext = context;
        mGson = new Gson();
    }

    public boolean WriteNote(Note note){
        try {
            mFos = mContext.openFileOutput(note.GetNoteFileName(), Context.MODE_APPEND);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
        try{
            mFos.write(mGson.toJson(note).getBytes());
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
                try {
                    mFis = mContext.openFileInput(f.getName());
                    InputStreamReader isr = new InputStreamReader(mFis);
                    BufferedReader br = new BufferedReader(isr);
                    try {
                        String fileContents = br.readLine();
                        Log.i("FileHandler", "Reading JSON: " + fileContents);
                        Note n = mGson.fromJson(fileContents, Note.class);
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
}

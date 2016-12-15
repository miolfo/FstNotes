package com.example.forge.fstnotes;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * Created by Forge on 9/26/2016.
 */

public class NotePopup extends PopupWindow {

    private final float POPUP_WIDTH = 0.85f;
    private final float POPUP_HEIGHT = 0.65f;
    private Button mSaveButton, mCancelButton;
    private Button mDatePickerButton, mTimePickerButton;
    private CheckBox mReminderSetCheck;
    private EditText mEditText;
    private boolean mEditingNote = false;
    private int mEditedNotePos = -1;
    private PickerPopup mPickerPopup;

    private Context mContext;
    private MainActivity mActivity;

    public NotePopup(MainActivity activity){
        super();
        mActivity = activity;
        mContext = activity.getBaseContext();

        setupPopupWindow();

        mEditText = (EditText)getContentView().findViewById(R.id.new_note_text);
        mReminderSetCheck = (CheckBox)getContentView().findViewById(R.id.note_reminder_check);
        mSaveButton = (Button)getContentView().findViewById(R.id.save_new_note);
        mCancelButton = (Button)getContentView().findViewById(R.id.cancel_new_note);
        mDatePickerButton = (Button)getContentView().findViewById(R.id.date_picker_button);
        mTimePickerButton = (Button)getContentView().findViewById(R.id.time_picker_button);
        mPickerPopup = new PickerPopup(mActivity, this);
        setupButtonListeners();
    }

    public void Show(){
        showAtLocation(mActivity.findViewById(R.id.content_main), Gravity.CENTER, 0,0);
        setPickersToCurrent();
        mEditingNote = false;
    }

    public void ShowForEditing(Note editable, int editableNotePos){
        showAtLocation(mActivity.findViewById(R.id.content_main), Gravity.CENTER, 0,0);
        mEditingNote = true;
        mEditedNotePos = editableNotePos;
        mEditText.setText(editable.GetNoteText());
        //If the note has a reminder, set pickers to those values
        if(editable.HasReminder()) {
            setReminderEnabled(true);
            Note.NoteDate nd = editable.GetNoteDate();
        }
        //If no reminder was set, set current date to pickers
        else{
            setReminderEnabled(false);
            setPickersToCurrent();
        }
    }

    /**
     * Called whenever the picker popup window is closed by pressing ok
     */
    public void notifyPickersChanged(){
        Note.NoteDate nd = mPickerPopup.GetSetDate();
        Note.NoteTime nt = mPickerPopup.GetSetTime();
        mDatePickerButton.setText(nd.day + "/" + (nd.month+1) + "/" + nd.year);
        mTimePickerButton.setText(nt.hour + ":" + nt.minute);
    }

    private void setPickersToCurrent(){
        Calendar curr =  Calendar.getInstance();

    }

    private void setReminderEnabled(boolean enabled){
        mReminderSetCheck.setChecked(enabled);
        mTimePickerButton.setEnabled(enabled);
        mDatePickerButton.setEnabled(enabled);
    }

    private void setupButtonListeners(){

        //Disable pickers and bind their enabling to the checkbox
        setReminderEnabled(false);
        mReminderSetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                mTimePickerButton.setEnabled(cb.isChecked());
                mDatePickerButton.setEnabled(cb.isChecked());
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = mEditText.getText().toString();
                Note note = new Note(noteText);
                if (mReminderSetCheck.isChecked()) {
                    Note.NoteTime nt = mPickerPopup.GetSetTime();
                    Note.NoteDate nd = mPickerPopup.GetSetDate();
                    note.SetReminder(nt, nd);
                }
                if(mEditingNote){
                    mActivity.NoteEdited(note, mEditedNotePos);
                }
                else {
                    mActivity.AddNote(note);
                }
                dismiss();
                //Empty the text input field
                mEditText.setText("");
                mReminderSetCheck.setChecked(false);
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //Empty the text input field
                mEditText.setText("");
                mReminderSetCheck.setChecked(false);
            }
        });
        mDatePickerButton.setOnClickListener(mPickerButtonClicked);
        mTimePickerButton.setOnClickListener(mPickerButtonClicked);
    }

    private View.OnClickListener mPickerButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("FstNotes", String.valueOf(v.getId() == R.id.time_picker_button));
            mPickerPopup.Show(v.getId() == R.id.time_picker_button);
        }
    };

    private void setupPopupWindow(){
        Point screenSize = new Point();
        WindowManager wm  = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(screenSize);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setFocusable(true);     //Focusable to enable soft keyboard
        setOutsideTouchable(false); //Dont leak touches outside the popup
        if(Build.VERSION.SDK_INT >= 21) {
            setElevation(20);   //Visual enhancements (shadow under the window)
        }
        setContentView(inflater.inflate(R.layout.new_note, null, false));
        setWidth((int)(screenSize.x * POPUP_WIDTH));
        //setWidth(getContentView().findViewById(R.id.new_note_layout).getMeasuredWidth());
        setHeight((int)(screenSize.y * POPUP_HEIGHT));
    }
}

package com.example.forge.fstnotes;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
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


/**
 * Created by Forge on 9/26/2016.
 */

public class NotePopup extends PopupWindow {

    private final float POPUP_WIDTH = 1f;
    private final float POPUP_HEIGHT = 0.95f;
    private Button mSaveButton, mCancelButton;
    private CheckBox mReminderSetCheck;
    private EditText mEditText;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;


    private Context mContext;
    private MainActivity mActivity;

    public NotePopup(MainActivity activity){
        super();
        mActivity = activity;
        mContext = activity.getBaseContext();

        setupPopupWindow();

        mEditText = (EditText)getContentView().findViewById(R.id.new_note_text);
        mReminderSetCheck = (CheckBox)getContentView().findViewById(R.id.note_reminder_check);
        mTimePicker = (TimePicker)getContentView().findViewById(R.id.note_time_picker);
        mDatePicker = (DatePicker)getContentView().findViewById(R.id.note_date_picker);
        mSaveButton = (Button)getContentView().findViewById(R.id.save_new_note);
        mCancelButton = (Button)getContentView().findViewById(R.id.cancel_new_note);
        mTimePicker.setIs24HourView(true);

        setupButtonListeners();
    }

    public void Show(){
        showAtLocation(mActivity.findViewById(R.id.content_main), Gravity.CENTER, 0,0);
    }

    private void setupButtonListeners(){

        //Disable pickers and bind their enabling to the checkbox
        mTimePicker.setEnabled(false);
        mDatePicker.setEnabled(false);
        mReminderSetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                mTimePicker.setEnabled(cb.isChecked());
                mDatePicker.setEnabled(cb.isChecked());
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = mEditText.getText().toString();
                Note note = new Note(noteText);
                if(mReminderSetCheck.isChecked()) {
                    Note.NoteTime nt = getNoteTime();
                    Note.NoteDate nd = getNoteDate();
                    note.SetReminder(nt, nd);
                }
                mActivity.AddNote(note);
                dismiss();
                //Empty the text input field
                mEditText.setText("");
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                //Empty the text input field
                mEditText.setText("");
            }
        });
    }

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

    private Note.NoteTime getNoteTime(){
        Note.NoteTime nt = new Note.NoteTime();
        if(Build.VERSION.SDK_INT >= 23) {
            nt.hour = mTimePicker.getHour();
            nt.minute = mTimePicker.getMinute();
        }
        else {
            nt.hour = mTimePicker.getCurrentHour();
            nt.minute = mTimePicker.getCurrentMinute();
        }
        return nt;
    }

    private Note.NoteDate getNoteDate(){
        Note.NoteDate nd = new Note.NoteDate();
        nd.day = mDatePicker.getDayOfMonth();
        nd.month = mDatePicker.getMonth();
        nd.year = mDatePicker.getYear();
        return nd;
    }
}

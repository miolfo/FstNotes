package com.example.forge.fstnotes;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TimePicker;

/**
 * Created by Forge on 7.12.2016.
 */

public class PickerPopup extends PopupWindow {
    private static final float POPUP_WIDTH = 0.8f;
    private static final float POPUP_HEIGHT = 0.7f;

    private TimePicker mTimePicker;
    private DatePicker mDatePicker;

    private Context mContext;
    private MainActivity mActivity;

    public PickerPopup(MainActivity mainActivity){
        mActivity = mainActivity;
        mContext = mainActivity.getBaseContext();
        setupPopupWindow();
        setupPickers();
    }

    public void Show(boolean timePicker){
        LinearLayout layout = (LinearLayout)getContentView().findViewById(R.id.picker_view);
        layout.removeAllViews();
        if(timePicker){
            layout.addView(mTimePicker);
        } else{

            layout.addView(mDatePicker);
        }
        showAtLocation(mActivity.findViewById(R.id.content_main), Gravity.CENTER, 0,0);
    }

    public Note.NoteDate GetSetDate(){
        Note.NoteDate nd = new Note.NoteDate();
        nd.day = mDatePicker.getDayOfMonth();
        nd.month = mDatePicker.getMonth();
        nd.year = mDatePicker.getYear();
        return nd;
    }

    public Note.NoteTime GetSetTime(){
        Note.NoteTime nt = new Note.NoteTime();
        nt.hour = mTimePicker.getCurrentHour();
        nt.minute = mTimePicker.getCurrentMinute();
        return nt;
    }

    private void setupPickers(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mTimePicker = new TimePicker(mContext);
        mTimePicker.setLayoutParams(params);
        mTimePicker.setIs24HourView(true);

        mDatePicker = new DatePicker(mContext);
        mDatePicker.setLayoutParams(params);
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
        setContentView(inflater.inflate(R.layout.picker_popup, null, false));
        setWidth((int)(screenSize.x * POPUP_WIDTH));
        //setWidth(getContentView().findViewById(R.id.new_note_layout).getMeasuredWidth());
        setHeight((int)(screenSize.y * POPUP_HEIGHT));
    }
}

package com.example.forge.fstnotes;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;


/**
 * Created by Forge on 9/26/2016.
 */

public class NewNotePopup extends PopupWindow {

    private final float POPUP_WIDTH = 0.8f;
    private final float POPUP_HEIGHT = 0.5f;
    private Button mSaveButton, mCancelButton;
    private EditText mEditText;


    private Context mContext;
    private MainActivity mActivity;

    public NewNotePopup(MainActivity activity){
        super();
        mActivity = activity;
        mContext = activity.getBaseContext();
        setupPopupWindow();
        setupButtonListeners();
    }

    public void Show(){
        showAtLocation(mActivity.findViewById(R.id.content_main), Gravity.CENTER, 0,0);
    }

    private void setupButtonListeners(){
        mEditText = (EditText)getContentView().findViewById(R.id.new_note_text);
        mSaveButton = (Button)getContentView().findViewById(R.id.save_new_note);
        mCancelButton = (Button)getContentView().findViewById(R.id.cancel_new_note);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = mEditText.getText().toString();
                mActivity.AddNote(new Note(noteText));
                dismiss();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
        setHeight((int)(screenSize.y * POPUP_HEIGHT));
    }
}

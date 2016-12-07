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

    private Context mContext;
    private MainActivity mActivity;

    public PickerPopup(MainActivity mainActivity){
        mActivity = mainActivity;
        mContext = mainActivity.getBaseContext();
        setupPopupWindow();
    }

    public void Show(boolean timePicker){
        LinearLayout layout = (LinearLayout)getContentView().findViewById(R.id.picker_view);
        layout.removeAllViews();
        if(timePicker){
            TimePicker tp = new TimePicker(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tp.setLayoutParams(params);
            layout.addView(tp);
        } else{
            DatePicker dp = new DatePicker(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dp.setLayoutParams(params);
            layout.addView(dp);
        }
        showAtLocation(mActivity.findViewById(R.id.content_main), Gravity.CENTER, 0,0);
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

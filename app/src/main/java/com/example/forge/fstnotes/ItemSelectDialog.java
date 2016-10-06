package com.example.forge.fstnotes;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Forge on 10/6/2016.
 */

public class ItemSelectDialog extends Dialog {

    private MainActivity mActivity;
    private Button mEditButton, mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_select_dialog);
        mEditButton = (Button) findViewById(R.id.note_edit_button);
        mDeleteButton = (Button) findViewById(R.id.note_delete_button);
        mEditButton.setOnClickListener(buttonListener);
        mDeleteButton.setOnClickListener(buttonListener);
    }

    public ItemSelectDialog(MainActivity activity){
        super(activity);
        mActivity = activity;
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.note_edit_button:
                    mActivity.EditNoteClicked();
                    dismiss();
                    break;
                case R.id.note_delete_button:
                    mActivity.DeleteNoteClicked();
                    dismiss();
                    break;
            }
        }
    };
}

package com.example.forge.fstnotes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by Forge on 10/16/2016.
 */

public class FstNoteWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FstNoteViewsFactory(this.getApplicationContext(), intent);
    }


    class FstNoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        private Context mContext;
        private Intent mIntent;
        private ArrayList<Note> mNotes;

        public FstNoteViewsFactory(Context context, Intent intent){
            mContext = context;
            mIntent = intent;
        }

        @Override
        public void onCreate() {
            FileHandler fh = new FileHandler(mContext);
            mNotes = Util.SortNotesByDate(fh.LoadNotes());
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mNotes.size();
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews single = new RemoteViews(mContext.getPackageName(), R.layout.single_note);
            single.setTextViewText(R.id.note_text, mNotes.get(position).GetNoteText());
            single.setTextViewText(R.id.reminder_text, Util.TimeAsString(mNotes.get(position).GetNoteCalendar()));
            return single;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public void onDataSetChanged() {

        }
    }
}

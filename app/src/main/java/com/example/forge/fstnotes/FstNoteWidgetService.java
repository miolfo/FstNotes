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
        private ArrayList<Note> mExpiredNotes, mUpcomingNotes;

        public FstNoteViewsFactory(Context context, Intent intent){
            mContext = context;
            mIntent = intent;
        }

        @Override
        public void onCreate() {
            FileHandler fh = new FileHandler(mContext);
            Pair<ArrayList<Note>, ArrayList<Note>> pair = Util.SortNotes(fh.LoadNotes());
            mExpiredNotes = pair.first;
            mUpcomingNotes = pair.second;
            Log.i("ViewsFactory", "Expired notes count: " + mExpiredNotes.size());
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mExpiredNotes.size();
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
            single.setTextViewText(R.id.note_text, mExpiredNotes.get(position).GetNoteText());
            single.setTextViewText(R.id.reminder_text, mExpiredNotes.get(position).GetReminderString());
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

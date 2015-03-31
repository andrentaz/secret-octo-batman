package com.myco.lcreporter;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.AlphabetIndexer;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;

/**
 * Created by andre on 3/31/15.
 */
public class IndexedListAdapter extends SimpleCursorAdapter implements SectionIndexer {

    AlphabetIndexer alphaIndexer;
    Context mContext;

    public IndexedListAdapter(Context context, int layout, Cursor c,
                              String[] from, int[] to) {
        super(context, layout, c, from, to, 0);
        mContext = context;
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        if (c != null) {
            alphaIndexer = new AlphabetIndexer(c,
                    c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME),
                    " ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }

        return super.swapCursor(c);
    }

    @Override
    public int getPositionForSection(int section) {
        return alphaIndexer.getPositionForSection(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return alphaIndexer.getSectionForPosition(position);
    }

    @Override
    public Object[] getSections() {
        return alphaIndexer == null ? null : alphaIndexer.getSections();
    }

}
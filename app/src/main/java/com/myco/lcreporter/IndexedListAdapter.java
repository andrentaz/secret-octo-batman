package com.myco.lcreporter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by andre on 3/31/15.
 */
public class IndexedListAdapter extends SimpleCursorAdapter implements SectionIndexer {

    private AlphabetIndexer alphaIndexer;
    private Context mContext;
    private int mLayoutId;


    /**
     * Creates the Adapter
     * @param context Context Application
     * @param layout layout ID
     * @param c Cursor
     * @param from columns that should be read from the DB
     * @param to views id where the Adapter put the data
     */
    public IndexedListAdapter(Context context, int layout, Cursor c,
                              String[] from, int[] to) {
        super(context, layout, c, from, to, 0);
        mContext = context;
        mLayoutId = layout;
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

    /* ------------------------------------------------------------------------------------------ */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mLayoutId, parent, false);

        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        Cursor c = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone._ID + " = " + contactId,
                null,
                null
        );

        String number = "NO NUMBER";
        if(c.moveToFirst()) {
            number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        // Getting the Number
        String contactsData[] = queryContactsInfo(contactId);

        TextView tvName     = (TextView) view.findViewById(R.id.tvName);
        TextView tvNumber   = (TextView) view.findViewById(R.id.tvNumber);

        tvName.setText(contactsData[0]);
        tvNumber.setText(contactsData[1]);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Getting the Number
        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        String contactsData[] = queryContactsInfo(contactId);

        TextView tvName     = (TextView) view.findViewById(R.id.tvName);
        TextView tvNumber   = (TextView) view.findViewById(R.id.tvNumber);

        tvName.setText(contactsData[0]);
        tvNumber.setText(contactsData[1]);
    }

    /**
     * Auxiliary to newView method
     * @param idCol the ID of the contact
     * @return an array with the data
     */
    public String[] queryContactsInfo(String idCol) {

        String ret[] = new String[] { "No name", "No number"};

        /* Preparing the query */
        Uri contactsUri         = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String projection[]     = new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String selection        = ContactsContract.CommonDataKinds.Phone._ID + " = " + idCol;
        String[] selectionArgs  = new String[] {idCol};

        /* Query for the cursor */
        Cursor cursor = mContext.getContentResolver().query(
                contactsUri,
                null,
                selection,
                null,
                null
        );

        /* Read the data */
        while (cursor.moveToNext()) {
            // Name
            ret[0] = cursor.getString(
                    cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    )
            );

            // Number
            ret[1] = cursor.getString(
                    cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
            );
        }

        return ret;

    }
}
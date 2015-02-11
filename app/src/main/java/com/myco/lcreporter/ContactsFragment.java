package com.myco.lcreporter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by nakagaki on 11/02/2015.
 */
public class ContactsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    /* ------------------------------------------------------------------------------------------ */
    /* CONSTANTS */
    /**
     * Defines an array that contains column names to move from
     * the Cursor to the ListView
     */
    @SuppressLint("InlineApi")
    private final static String[] FROM_COLUMS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };

    /**
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    // Define global mutable variables
    // Define a ListView object
    ListView mContactsList;
    // Define variables for the contact the user selects
    // the contact's _ID value
    long mContactId;
    // The contact's LOOKUP_KEY
    String mContactKey;
    // A content URI for the selected contact
    Uri mContactUri;
    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;
    /* ------------------------------------------------------------------------------------------ */

    // Default Constructor
    public ContactsFragment() {}

    /* Call back when the Fragment is created, it inflates the Contact List */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the Resource
        return inflater.inflate(R.layout.contacts_list_veiw, container, false);
    }

    /* ------------------------------------------------------------------------------------------ */

    /* LoaderCallbacks Methods */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /* OnItemClickListener Methods */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

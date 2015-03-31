package com.myco.lcreporter;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nakagaki on 30/03/2015.
 */
public class ContactsListFragment extends ListFragment implements
        LoaderCallbacks<Cursor> {

    public interface OnContactSelectedListener {

        /**
         * Callback when the contact is selected from the list of contacts
         * @param name String with the name of the contact.
         * @param number String with the number of the contact.
         * @param view The View selected to see the CheckBox
         */
        public void onContactNameSelected(String name, String number, View view);

    }

    private Map<String, String> mItems = new HashMap<String, String>();
    private OnContactSelectedListener mContactsListener;
    private SimpleCursorAdapter mAdapter;
    private String mCurrentFilter = null;

    // Search String
    private static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.Contacts.HAS_PHONE_NUMBER,
        ContactsContract.Contacts.LOOKUP_KEY
    };

    // Inflate the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_contact_layout, container, false);
    }

    // Set the adapter and some UI information
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Creates the option menu
        setHasOptionsMenu(true);

        // Initiate the Loader
        getLoaderManager().initLoader(0, null, this);

        // Create the adapter to pass the Views to the ListView
        mAdapter = new IndexedListAdapter(
                getActivity(),
                R.layout.item_contact,
                null,
                new String[] {ContactsContract.Contacts.DISPLAY_NAME},
                new int[] {R.id.tvName});

        // Set the adapter
        setListAdapter(mAdapter);
        getListView().setFastScrollEnabled(true);
    }

    /* Make sure that the activity implements the inner interface */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mContactsListener = (OnContactSelectedListener) activity;
        } catch (ClassCastException	e) {
            throw new ClassCastException(activity.toString() +
                    " must implement OnContactSelectedListener");
        }
    }

    /* When the user selects a contact - WILL BE CHANGED */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		/* Retrieving the phone numbers in order to see if we have more than one */
        String phoneNumber = null;
        String name = null;

        /* Make the query to get the number and the name */
        String[] projection = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        final Cursor phoneCursor = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.Data.CONTACT_ID + "=?",
                new String[]{String.valueOf(id)},
                null
        );

        /* Get the number and the name from the contact */
        if(phoneCursor.moveToFirst() && phoneCursor.isLast()) {
            final int contactNumberColumnIndex 	= phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNumber = phoneCursor.getString(contactNumberColumnIndex);
            name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        }

        /* Calls the activity method to deal with the data */
        mContactsListener.onContactNameSelected(name, phoneNumber, v);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Uri baseUri;

        if (mCurrentFilter != null) {
            baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI,
                    Uri.encode(mCurrentFilter));
        } else {
            baseUri = ContactsContract.Contacts.CONTENT_URI;
        }

        String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";

        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return new CursorLoader(
                getActivity(),
                baseUri,
                CONTACTS_SUMMARY_PROJECTION,
                selection,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    /* ------------------------------------------------------------------------------------------ */

    public void add(Sheep sheep) {
        mItems.put(sheep.getNumber(), sheep.getName());
    }

    public void delete(Sheep sheep) {
        mItems.remove(sheep.getNumber());
    }

    public Map<String, String> retrieveList() {
        return mItems;
    }
}

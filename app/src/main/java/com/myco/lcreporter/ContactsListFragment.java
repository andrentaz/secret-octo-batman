package com.myco.lcreporter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nakagaki on 30/03/2015.
 */
public class ContactsListFragment extends ListFragment {

    public interface OnContactSelectedListener {
        public void onContactNameSelected(Sheep sheep, View view);
    }

    private List<Sheep> mItems;
    private final Map<String, Sheep> mSelectedItems = new HashMap<String, Sheep>();
    private ContactsAdapter mAdapter;
    private OnContactSelectedListener mListener;
    private final String[] projection = {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the items
        mItems = new ArrayList<Sheep>();
        populate();

        // Initialize the adapter
        mAdapter = new ContactsAdapter(getActivity(), mItems);
        setListAdapter(mAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the PeopleDialogListener so we can send events to the host
            mListener = (OnContactSelectedListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement OnContactNameSelectedDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_contact_layout, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Sheep sheep = mAdapter.getItem(position);
        mListener.onContactNameSelected(sheep, v);
    }

    /* ------------------------------------------------------------------------------------------ */
    private void populate() {

        // Initialize the cursor
        Cursor cursor = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );

        // Populate the list
        while (cursor.moveToNext()) {

            // Name
            String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            );

            // Number
            String number = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            );

            // Add the Sheep to the list
            mItems.add(new Sheep(name, number));
        }
    }

    public void add(Sheep sheep) {
        mSelectedItems.put(sheep.getNumber(), sheep);
    }

    public void remove(Sheep sheep) {
        mSelectedItems.remove(sheep.getNumber());
    }

    public Map<String, Sheep> retrieveList() {
        return mSelectedItems;
    }

}

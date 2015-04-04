package com.myco.lcreporter;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private final Map<String, Sheep> mSelectedItems = new HashMap<>();
    private ContactsAdapter mAdapter;
    private OnContactSelectedListener mListener;
    private Bitmap mThumb;

    /* Projections */
    private final String[] commonProjection = {
            CommonDataKinds.Phone._ID,
            CommonDataKinds.Phone.DISPLAY_NAME,
            CommonDataKinds.Phone.NUMBER
    };

    private final String[] thumbProjection = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the items
        mItems = new ArrayList<>();
        setThumb();
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
                CommonDataKinds.Phone.CONTENT_URI,
                commonProjection,
                null,
                null,
                CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );

        // Populate the list
        while (cursor.moveToNext()) {

            // Name
            String name = cursor.getString(
                    cursor.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME)
            );

            // Number
            String number = cursor.getString(
                    cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER)
            );

            Sheep sheep = getSheep(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)),
                    name,
                    number
            );

            // Add the Sheep to the list
            mItems.add(sheep);
        }
    }

    private void setThumb() {
        Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.generic_thumb);
        mThumb = Bitmap.createScaledBitmap(tmp, 100, 100, true);
    }

    private Sheep getSheep(String contactId, String name, String number) {

        String selection = ContactsContract.Contacts.DISPLAY_NAME + " LIKE '" + name +"'";

        Cursor cursor = getActivity().getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                thumbProjection,
                selection,
                null,
                null
        );

        // The index of the _ID column in the Cursor
        int idColumn;
        // The index of the LOOKUP_KEY column in the Cursor
        int lookupKeyColumn;
        // A Content URI to the desired contact
        Uri contactUri;
        // Bitmap to bind to the Badge
        Bitmap thumb;

        if (cursor.getCount() == 0) {
            return null;
        } else {

            while (cursor.moveToFirst()) {

                /* Get the indexes to set the Badge */
                idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                lookupKeyColumn = cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);

                /* Get the URI */
                contactUri = ContactsContract.Contacts.getLookupUri(
                        cursor.getLong(idColumn),
                        cursor.getString(lookupKeyColumn)
                );

                int thumbnailColumn =
                        cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);

                String thumbnailUri = cursor.getString(thumbnailColumn);

                if (thumbnailUri == null) {
                    thumb = mThumb;
                } else {
                    thumb = loadContactPhotoThumbnail(thumbnailUri);
                }

                return new Sheep(name, number, contactUri, thumb);
            }
        }
        return null;
    }

    private Bitmap loadContactPhotoThumbnail(String photoData) {

        // Creates an asset file descriptor for the thumbnail file.
        AssetFileDescriptor afd = null;

        // try-catch block for file not found
        try {
            // Creates a holder for the URI.
            Uri thumbUri = Uri.parse(photoData);

            /*
             * Retrieves an AssetFileDescriptor object for the thumbnail
             * URI
             * using ContentResolver.openAssetFileDescriptor
             */
            afd = getActivity().getContentResolver().
                    openAssetFileDescriptor(thumbUri, "r");
            /*
             * Gets a file descriptor from the asset file descriptor.
             * This object can be used across processes.
             */
            FileDescriptor fileDescriptor = afd.getFileDescriptor();
            // Decode the photo file and return the result as a Bitmap
            // If the file descriptor is valid
            if (fileDescriptor != null) {
                // Decodes the bitmap
                return BitmapFactory.decodeFileDescriptor(
                        fileDescriptor, null, null);
            }
            // If the file isn't found
        } catch (FileNotFoundException e) {
                /*
                 * Handle file not found errors
                 */
            e.printStackTrace();

            // In all cases, close the asset file descriptor
        } finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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

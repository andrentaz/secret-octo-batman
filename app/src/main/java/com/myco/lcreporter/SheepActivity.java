package com.myco.lcreporter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by nakagaki on 11/02/2015.
 */
public class SheepActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int PICK_CONTACT = 0;
    private static final int CONTACTS_LOADER = 0;
    private Intent contactsIntent;
    private ArrayList<String> contactsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheep);
        getLoaderManager().initLoader(CONTACTS_LOADER, null, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri contactUri;
        Cursor cursor;
        String[] projection = {ContactsContract.Data._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        /* Check the data */
        switch (requestCode) {
            case PICK_CONTACT:      // Pegou o contato
                if (resultCode == RESULT_OK) {
                    contactUri = data.getData();
                    cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                }
        }
    }

    /*
    * Callback that's invoked when the system has initialized the Loader and
    * is ready to start the query. This usually happens when initLoader() is
    * called. The loaderID argument contains the ID value passed to the
    * initLoader() call.
    */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /*
         * Takes action based on the ID of the Loader that's being created
         */
        switch (id) {
            case CONTACTS_LOADER:
                return new CursorLoader(this);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /* ------------------------------------------------------------------------------------------ */
    /* Button functions */
    /**
     * When it's done, uses the gDrive API to build the Data sheet of the report
     * @param view
     */
    public void fillReport(View view) {

    }

    /**
     * Add someone from the Contact List of the Device
     * @param view
     */
    public void addContact(View view) {
        // Set the intent
        this.contactsIntent =  = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(this.contactsIntent, PICK_CONTACT);
    }

    /**
     * Add someone manually, by inputting the name and phone number
     * @param view
     */
    public void addPeople(View view) {

    }

    /* ------------------------------------------------------------------------------------------ */
}

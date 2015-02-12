package com.myco.lcreporter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nakagaki on 11/02/2015.
 * Activity that collects the data of the people that was in the meeting.
 */
public class SheepActivity extends Activity {
    private static final int PICK_CONTACT = 0;
    private ArrayList<String> contactsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheep);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri contactUri;
        Cursor cursor;
        String[] projection = {ContactsContract.Data._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String name, number;
        int column;

        /* Check the data */
        switch (requestCode) {
            case PICK_CONTACT:      // Pegou o contato
                if (resultCode == RESULT_OK) {
                    // Query the databank
                    contactUri = data.getData();
                    cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    // Get the data
                    column = cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    name = cursor.getString(column);

                    column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    number = cursor.getString(column);

                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG);
                }
        }
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
        Intent contactsIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactsIntent, PICK_CONTACT);
    }

    /**
     * Add someone manually, by inputting the name and phone number
     * @param view
     */
    public void addPeople(View view) {

    }

    /* ------------------------------------------------------------------------------------------ */
}

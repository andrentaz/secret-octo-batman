package com.myco.lcreporter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nakagaki on 11/02/2015.
 * Activity that collects the data of the people that was in the meeting.
 */
public class SheepActivity extends ActionBarActivity {
    private static final int PICK_CONTACT = 0;
    private ArrayList<String> contactsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheep);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sheep, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                this.startActivity(settingIntent);
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Cursor cursor;
        String name, number = "No Number!";
        int nameIndex, idIndex, hasNumber;


        /* Check the data */
        switch (requestCode) {
            case PICK_CONTACT:      // Pegou o contato
                if (resultCode == RESULT_OK) {
                    // Query the databank

                    cursor = getContentResolver().query(data.getData(),
                            null, null, null, null);

                    while (cursor.moveToNext()) {
                        // Get the Indexes
                        nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                        idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                        hasNumber = cursor.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER);

                        // Get the data
                        name = cursor.getString(nameIndex);

                        if (Integer.parseInt(cursor.getString(hasNumber)) > 0)
                            number = queryNumber(cursor.getString(idIndex));

                        Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                }
        }
    }

    private String queryNumber(String id) {
        String number = "No Contact Number";
        Cursor cur = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                new String[]{id},
                null
        );

        while (cur.moveToNext()) {
            number = cur.getString(
                    cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            );
        }

        return number;
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

package com.myco.lcreporter;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nakagaki on 11/02/2015.
 * Activity that collects the data of the people that was in the meeting.
 */
public class SheepActivity extends ActionBarActivity
        implements  PeopleDialogFragment.PeopleDialogListener,
                    DeletionDialogFragment.DeletionDialogListener{

    private static final int PICK_CONTACT = 0;
    private ContactListFragment clfragment = new ContactListFragment();
    private Sheep cacheSheep;
    private int cachePos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheep);

        // Check if the Activity is using the container
        if (findViewById(R.id.fragment_list_container) != null){

            // Checks if it's been restored from a previous state
            if (savedInstanceState != null) {
                return;
            }

            // Inflate the ListView fragment case not
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_list_container, clfragment).commit();
        }
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

                        //Toast.makeText(getApplicationContext(), number, Toast.LENGTH_LONG).show();
                        Sheep sheep = new Sheep(name, number);
                        this.clfragment.addSheep(sheep);
                    }
                    cursor.close();
                }
        }
    }

    private String queryNumber(String id) {
        String number = "No Number";
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
     * WARNING! IT'S NOT USING THE PATTERN OF LISTENER AND INTERFACE! CAN BE A BAD IDEA!
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
        PeopleDialogFragment newfrag = new PeopleDialogFragment();
        newfrag.show(getSupportFragmentManager(), "PeopleDialogTag");
    }

    /* ------------------------------------------------------------------------------------------ */

    /**
     * Show the Undo Toast after the user remove someone from the list
     * @param viewContainer
     */
    public static void showUndoToast(final View viewContainer) {
            viewContainer.setVisibility(View.VISIBLE);
            viewContainer.setAlpha(1);
            viewContainer.animate().alpha(0.4f).setDuration(5000)
                .withEndAction(new Runnable() {
                @Override
                public void run() {
                    viewContainer.setVisibility(View.GONE);
                }
            });
    }

    /**
     * Undo the removal of the people
     * @param view
     */
    public void undoRemove(View view) {
        this.clfragment.insertSheep(this.cachePos, this.cacheSheep);
        findViewById(R.id.undobar).setVisibility(View.GONE);
    }

    /* ------------------------------------------------------------------------------------------ */
    /* Interface Listener methods */
    @Override
    public void onPeopleDialogPositiveClick(DialogFragment dialog) {
        // User pressed OK, so we need to grab the values from the
        // dialog's fields and apply them to the Views in the Main
        // Activity
        Dialog dialogView = dialog.getDialog();
        EditText userView = (EditText) dialogView.findViewById(R.id.dialog_username);
        EditText numberView = (EditText) dialogView.findViewById(R.id.dialog_cellphone);

        this.clfragment.addSheep(new Sheep(
                        userView.getText().toString(),
                        numberView.getText().toString()
                )
        );

        dialog.dismiss();
    }

    @Override
    public void onPeopleDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onDeletionDialogPositiveClick(DialogFragment dialog, int position) {
        this.cacheSheep = this.clfragment.getSheep(position);
        this.cachePos = position;
        this.clfragment.removeSheep(position);
        dialog.dismiss();
        this.showUndoToast(findViewById(R.id.undobar));
    }

    @Override
    public void onDeletionDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    /* ------------------------------------------------------------------------------------------ */
}

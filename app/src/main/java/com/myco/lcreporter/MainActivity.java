package com.myco.lcreporter;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends ActionBarActivity
        implements  DatePickerFragment.DatePickerListener,
                    PeopleDialogFragment.PeopleDialogListener,
                    DeletionDialogFragment.DeletionDialogListener,
                    ActionBar.TabListener {

    // Constants
    private static final String DATE_PICKER_TAG = "com.myco.lcreporter.MainActivity.Date_Picker";
    public static final int NUM_PAGES = 3;
    private static final int PICK_CONTACT = 0;
    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 1;

    // Attributes - Pager
    private SharedPreferences mySettings;   // Preferences
    private ViewPager mPager;               // Display the pages
    private NucleoPagerAdapter mAdapter;    // Provide the pages to the ViewPager

    // Attributes - List Contacts
    private Sheep cacheSheep;
    private int cachePos;
    private SheepListFragment mListFrag;

    // Attributes - Tabs
    private String[] tabs = { "Núcleo", "Equipe", "Ovelhas" };
    private CsvFormatter mFormatter;

    // Google API
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);

        // Set the Default Values of the Settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Create the adapter that will return the Fragments
        this.mAdapter = new NucleoPagerAdapter(getSupportFragmentManager());

        // Setup the Action Bar
        final ActionBar actionBar = getSupportActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent
        //actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        this.mPager = (ViewPager) findViewById(R.id.pager);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (String temp: tabs) {
            actionBar.addTab(actionBar.newTab().setText(temp).setTabListener(this));
        }

        // Set the third fragment and the formatter
        this.mListFrag = (SheepListFragment) this.mAdapter.getItem(2);
        this.mFormatter = new CsvFormatter();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}


    /* ------------------------------------------------------------------------------------------ */
    /* Google API Stuff */
    // Change to Connect when start the application
/*    @Override
    protected void onStart() {
        super.onStart();
        //this.mGoogleApiClient.connect();
    } */

/*    // If connection failed
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
                // Unable to resolve, message user appropriately
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }
    } */



    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            case R.id.action_share:
                this.shareReport();
                return super.onOptionsItemSelected(item);

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void shareReport() {
        String temp, dataString;
        int size = this.mListFrag.getSize();

        // Creates the CSV Text
        for (int i=0; i<size; i++) {
            temp = (i+1) + ", " + this.mListFrag.getSheepInfo(i);
            this.mFormatter.addRow(temp);
        }
        dataString = this.mFormatter.toString();
        this.mFormatter.erase();

        // Write in a Temp file
        File file = new File(getCacheDir() + File.separator + "Report.csv");
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);
            out.write(dataString.getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creates the Implicit Intent
        Uri csv = FileProvider.getUriForFile(getApplicationContext(), "com.myco.lcreporter", file);
        grantUriPermission("com.myco.lcreporter", csv, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Toast.makeText(getApplicationContext(), csv.getPath(), Toast.LENGTH_LONG).show();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, csv);
        sendIntent.setType("text/csv");

        // Sets the Info in the Settings
        this.mAdapter.setInfo();

        // Start Intent
        if (getPackageManager().queryIntentActivities(
                sendIntent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            startActivity(Intent.createChooser(sendIntent,
                    getResources().getString(R.string.sendCsvFile)));
        }

        revokeUriPermission(csv, Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    /* ------------------------------------------------------------------------------------------ */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /* Check if the result is ok */
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_CONTACT:      // Get the contact

                    /* Get the list */
                    List<Sheep> array = (List<Sheep>) data
                            .getSerializableExtra(ContactsPickerActivity.SHEEP_ARRAY);

                    mListFrag.addList(array);

                    break;

                /*case RESOLVE_CONNECTION_REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        this.mGoogleApiClient.connect();
                    }
                    break;*/
                default:
                    Toast.makeText(this, "No Action Found!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


    public void showDatePickerDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
    }

    @Override
    public void onDateSetChange(String newDate) {
        /* Changing the preferences using a SharedPreferences Object */
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        /* Changes and apply */
        SharedPreferences.Editor editor = this.mySettings.edit();
        editor.putString("pref_date", newDate);
        editor.commit();

        // Setting Button Text
        TextView textView = (TextView) findViewById(R.id.textclk_date);
        String date = this.mySettings.getString("pref_date", "");
        textView.setText(date);
    }

    /* ------------------------------------------------------------------------------------------ */
    /* ------------------------------------------------------------------------------------------ */
    /* List Stuff */
    /**
     * Add someone from the Contact List of the Device
     * WARNING! IT'S NOT USING THE PATTERN OF LISTENER AND INTERFACE! CAN BE A BAD IDEA!
     * @param view
     */
    public void addContact(View view) {
        // Set the Activity to picker
        Intent contactsIntent = new Intent(this, ContactsPickerActivity.class);
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
        this.mListFrag.insertSheep(this.cachePos, this.cacheSheep);
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

        this.mListFrag.addSheep(new Sheep(
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
        this.cacheSheep = this.mListFrag.getSheep(position);
        this.cachePos = position;
        this.mListFrag.removeSheep(position);
        dialog.dismiss();
        this.showUndoToast(findViewById(R.id.undobar));
    }

    @Override
    public void onDeletionDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}

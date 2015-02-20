package com.myco.lcreporter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity
                implements DatePickerFragment.DatePickerListener {

    public static final int NUM_PAGES = 3;
    private SharedPreferences mySettings;
    private ViewPager mPager;            // Display the pages
    private NucleoPagerAdapter mAdapter;    // Provide the pages to the ViewPager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Instantiate a ViewPager and a PagerAdapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new NucleoPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
    }

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
    protected void onResume() {
        super.onResume();
        //this.getSettingsBoxes();
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showDatePickerDialog(View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    /* ------------------------------------------------------------------------------------------ */
    /* Button Methods */
    /* Save the changes in the settings fields and them changes the Activity */
    public void goEqpActivity (View view) {
        /* Shared Preferences */
        String leaderName, coleaderName, lt1Name, hostName;

        /* Getting User Input */
        EditText editText = (EditText) findViewById(R.id.editText_lider);
        leaderName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText_colider);
        coleaderName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText_lt1);
        lt1Name = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText_host);
        hostName = editText.getText().toString();

        /* Changing the preferences using a SharedPreferences Object */
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        /* Changes and apply */
        SharedPreferences.Editor editor = this.mySettings.edit();
        editor.putString("pref_lider", leaderName);
        editor.putString("pref_colider", coleaderName);
        editor.putString("pref_lt1", lt1Name);
        editor.putString("pref_host", hostName);

        // Commit
        editor.commit();


        /* Changing the activity */
        Intent nextActivityIntent = new Intent(this, EqpActivity.class);
        startActivity(nextActivityIntent);
    }


    /* ------------------------------------------------------------------------------------------ */
    /* My Methods */

    /**
     * Read content int the Preferences File to update in the Text Box*/
    public void getSettingsBoxes() {
        // Getting the preferences object
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = this.mySettings.getString("pref_lider", "");

        // Putting the Settings Text in the Box
        // Leader
        EditText editText = (EditText) findViewById(R.id.editText_lider);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Co Leader
        name = this.mySettings.getString("pref_colider", "");
        editText = (EditText) findViewById(R.id.editText_colider);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // LT1
        name = this.mySettings.getString("pref_lt1", "");
        editText = (EditText) findViewById(R.id.editText_lt1);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Host
        name = this.mySettings.getString("pref_host", "");
        editText = (EditText) findViewById(R.id.editText_host);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Setting Button Text
        Button button = (Button) findViewById(R.id.button_date);
        name = this.mySettings.getString("pref_date", "");
        button.setText(name);
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
        Button button = (Button) findViewById(R.id.button_date);
        String date = this.mySettings.getString("pref_date", "");
        button.setText(date);
    }
}

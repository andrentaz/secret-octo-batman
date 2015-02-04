package com.myco.lcreporter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public static final String EXTRA_LIDER = "com.myco.lcreporter.LIDER";
    public static final String EXTRA_COLIDER = "com.myco.lcreporter.COLIDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(settingIntent);
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showDatePickerDialog(View view) {
        /*DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");*/

        /* Creates the DatePickerActivity */
        Intent datePickerIntent = new Intent(this, DatePickerActivity.class);
        this.startActivity(datePickerIntent);

    }

    public void fillReport(View view) {
        /* CREATING A NEW ACTIVITY
        Intent intentFill = new Intent(this, FillReportActivity.class);
        EditText editText;
        String leaderName, coleaderName;

        // Getting Leader Text
        editText = (EditText) findViewById(R.id.editText_lider);
        leaderName = editText.getText().toString();
        intentFill.putExtra(EXTRA_LIDER, leaderName);

        // Getting Coleader Text
        editText = (EditText) findViewById(R.id.editText_colider);
        coleaderName = editText.getText().toString();
        intentFill.putExtra(EXTRA_COLIDER, coleaderName);

        // 1.0 - Creates another activity
        startActivity(intentFill);
        */

        /* Shared Preferences */
        EditText editText = (EditText) findViewById(R.id.editText_lider);
        String leaderName = editText.getText().toString();

        /* Changing the preferences using a SharedPreferences Object */
        SharedPreferences prefs = this.getSharedPreferences("com.myco.lcreporter",
                Context.MODE_PRIVATE);

        /* Changes and apply */
        prefs.edit().putString("com.myco.lcreporter.pref_cat_lideranca.pref_lider",
                leaderName).apply();
    }
}

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
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final String EXTRA_LIDER = "com.myco.lcreporter.LIDER";
    public static final String EXTRA_COLIDER = "com.myco.lcreporter.COLIDER";
    private SharedPreferences mySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting the preferences object
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String accLeader = this.mySettings.getString("pref_lider", "");

        // Putting the Settings Text in the Box
        // Leader
        EditText editText = (EditText) findViewById(R.id.editText_lider);
        editText.setText(accLeader, TextView.BufferType.EDITABLE);

        // Co Leader
        accLeader = this.mySettings.getString("pref_colider", "");
        editText = (EditText) findViewById(R.id.editText_colider);
        editText.setText(accLeader, TextView.BufferType.EDITABLE);

        // LT1
        accLeader = this.mySettings.getString("pref_lt1", "");
        editText = (EditText) findViewById(R.id.editText_lt1);
        editText.setText(accLeader, TextView.BufferType.EDITABLE);

        // Host
        accLeader = this.mySettings.getString("pref_host", "");
        editText = (EditText) findViewById(R.id.editText_host);
        editText.setText(accLeader, TextView.BufferType.EDITABLE);

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
        /* Shared Preferences */
        EditText editText;
        String leaderName, coleaderName, lt1Name, hostName;

        /* Getting User Input */
        editText = (EditText) findViewById(R.id.editText_lider);
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
    }
}

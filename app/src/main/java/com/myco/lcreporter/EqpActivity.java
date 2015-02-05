package com.myco.lcreporter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EqpActivity extends ActionBarActivity {

    private SharedPreferences mySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eqp);
        getSettingsBoxes();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_eqp, menu);
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

    /* ------------------------------------------------------------------------------------------ */
    /* Button Methods */
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

    /* ------------------------------------------------------------------------------------------ */
    /* My Methods */

    /**
     * Read content int the Preferences File to update in the Text Box*/
    public void getSettingsBoxes() {
        // Getting the preferences object
        this.mySettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String name = this.mySettings.getString("pref_eqp", "");

        // Putting the Settings Text in the Box
        // Equipe
        EditText editText = (EditText) findViewById(R.id.editText_eqp);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Master
        name = this.mySettings.getString("pref_master", "");
        editText = (EditText) findViewById(R.id.editText_colider);
        editText.setText(name, TextView.BufferType.EDITABLE);

        // Rede
        name = this.mySettings.getString("pref_rede", "");
        editText = (EditText) findViewById(R.id.editText_lt1);
        editText.setText(name, TextView.BufferType.EDITABLE);
    }
}

package com.myco.lcreporter;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by nakagaki on 30/01/2015.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the Fragment as the main content
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    }
}

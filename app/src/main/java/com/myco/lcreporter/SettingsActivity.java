package com.myco.lcreporter;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

/**
 * Created by nakagaki on 30/01/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the Fragment as the main content
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();

        PreferenceCategory phones = (PreferenceCategory) findPreference("pref_catPhone");
        phones.removeAll();

        Preference config = (Preference) findPreference("pref_config");
        PreferenceScreen preferenceScreen = (PreferenceScreen) findPreference("pref_screen");

        preferenceScreen.removePreference(config);
    }
}

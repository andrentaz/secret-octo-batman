package com.myco.lcreporter;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by andre on 1/30/15.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // Load the preferences from an XML resources
        addPreferencesFromResource(R.xml.preferences);
    }
}

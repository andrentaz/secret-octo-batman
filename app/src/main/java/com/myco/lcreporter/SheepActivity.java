package com.myco.lcreporter;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by nakagaki on 11/02/2015.
 */
public class SheepActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheep);

        // Check that the activity is using the layout version with the
        // fragment_container FrameLayout
        if (findViewById(R.id.contacts_list_container) != null) {
            // In case it is being restored
            if (savedInstanceState != null)
                return;
        }

        ContactsFragment fragment = new ContactsFragment();

        // Pass arguments to the fragment
        fragment.setArguments(getIntent().getExtras());

        // Add the fragment to the container FrameLayout
        getFragmentManager().beginTransaction()
                .add(R.id.contacts_list_container, fragment).commit();
    }
}

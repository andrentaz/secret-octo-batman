package com.myco.lcreporter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by nakagaki on 30/03/2015.
 */
public class ContactsPickerActivity extends FragmentActivity implements
        ContactsListFragment.OnContactSelectedListener {

    public static final String SHEEP_ARRAY = "com.myco.lcreporter.ContactsPickerActivity.SARRAY";

    private ContactsListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListFragment = new ContactsListFragment();

        setContentView(R.layout.activity_picker_contacts);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ContactsListFragment mListFragment = new ContactsListFragment();

        fragmentTransaction.add(R.id.picker_container, mListFragment);
        fragmentTransaction.commit();
    }

    /**
     * Creates the sheep and add to the list of the fragment
     * @param sheep String with the name of the contact.
     * @param view  of the contact.
     */
    @Override
    public void onContactNameSelected(Sheep sheep, View view) {
        /* Confirm the CheckBox state */
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.selectedCheckBox);

        if (!checkBox.isChecked()) {    // add the contact to the map
            mListFragment.add(sheep);
            checkBox.setChecked(true);
        } else {    // get it out the contact from the map
            mListFragment.remove(sheep);
            checkBox.setChecked(false);
        }
    }

    /* Pass the results to the caller and finish the activity */
    public void addListOfContacts(View view) {
        Intent data = new Intent();
        Map<String, Sheep> hash = mListFragment.retrieveList();
        Iterator it = hash.entrySet().iterator();
        List<Sheep> list = new ArrayList<>();

        /* Iterate over the elements of the map */
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            list.add((Sheep) pair.getValue());
        }

        data.putExtra(SHEEP_ARRAY, (Serializable) list);
        setResult(RESULT_OK, data);
        finish();
    }
}

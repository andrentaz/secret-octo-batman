package com.myco.lcreporter;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 14/02/2015.
 */
public class ContactListFragment extends ListFragment {
    private List<Contact> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicialize the item list
        mItems = new ArrayList<Contact>();
        mItems.add(new Contact("Andre", "+55 11 981545569"));

        // Inicialize and set the adapter
        setListAdapter(new ContactsAdapter(getActivity(), mItems));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_layout, container, false);
    }
}

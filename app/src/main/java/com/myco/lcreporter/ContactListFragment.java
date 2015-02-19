package com.myco.lcreporter;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 14/02/2015.
 * Class that inflates the list layout
 */
public class ContactListFragment extends ListFragment {
    public static final String ARG_POSITION = "com.myco.lcreporter.ContactsListFragment.POSITION";
    private List<Sheep> mItems;
    private ContactAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicialize the item list
        mItems = new ArrayList<Sheep>();

        // Inicialize and set the adapter
        adapter = new ContactAdapter(getActivity(), mItems);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_layout, container, false);
    }

    public void addSheep(Sheep sheep) {
        mItems.add(sheep);
        adapter.notifyDataSetChanged();
    }

    public void removeSheep(int position) {
        mItems.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the position
        Bundle bundle = new Bundle();
        bundle.putInt(this.ARG_POSITION, position );

        // Creates the fragment dialog
        DeletionDialogFragment newfragg = new DeletionDialogFragment();
        newfragg.setArguments(bundle);

        // Show the Dialog
        newfragg.show(getFragmentManager(), "DeletionDialogTag");
    }
}

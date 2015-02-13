package com.myco.lcreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by nakagaki on 13/02/2015.
 */
public class ContactsAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] name, number;

    /**
     * Creates an instance adapter, set all the values in the list
     * @param context
     * @param name
     * @param number
     */
    public ContactsAdapter(Context context, String[] name, String[] number) {
        super(context, R.layout.contact_list_item);
        this.context = context;
        this.name = name;
        this.number = number;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Create the view
        View itemView = inflater.inflate(R.layout.contact_list_item, parent, false);
        TextView contactName = (TextView) itemView.findViewById(R.id.item_firstLine);
        TextView contactNumber = (TextView) itemView.findViewById(R.id.item_secondLine);

        // Set the values
        contactName.setText(this.name[position]);
        contactNumber.setText(this.number[position]);

        // Return
        return itemView;
    }
}

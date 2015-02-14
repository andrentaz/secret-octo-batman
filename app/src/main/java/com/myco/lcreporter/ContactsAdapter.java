package com.myco.lcreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by nakagaki on 13/02/2015.
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {

    /**
     * Creates an instance adapter, set all the values in the list
     */
    public ContactsAdapter(Context context, List<Contact> items) {
        super(context, R.layout.contact_list_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the GridView Item Layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contact_list_item, parent, false);

            // Inicialize the View Holder
            viewHolder = new ViewHolder();
            viewHolder.setTvName((TextView) convertView.findViewById(R.id.item_firstLine));
            viewHolder.setTvNumber((TextView) convertView.findViewById(R.id.item_secondLine));
            convertView.setTag(viewHolder);
        } else {
            // Recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Muda os valores do item
        Contact item = getItem(position);
        viewHolder.getTvName().setText(item.getName());
        viewHolder.getTvNumber().setText(item.getNumber());

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private class ViewHolder {
        private TextView tvName;
        private TextView tvNumber;

        public TextView getTvName() {
            return tvName;
        }

        public void setTvName(TextView tvName) {
            this.tvName = tvName;
        }

        public TextView getTvNumber() {
            return tvNumber;
        }

        public void setTvNumber(TextView tvNumber) {
            this.tvNumber = tvNumber;
        }
    }
}

package com.myco.lcreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nakagaki on 13/02/2015.
 */
public class SheepAdapter extends ArrayAdapter<SimpleItem> {

    /**
     * Creates an instance adapter, set all the values in the list
     */
    public SheepAdapter(Context context, List<SimpleItem> items) {
        super(context, R.layout.item_sheep_list, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the View Item Layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_sheep_list, parent, false);

            // Initialize the View Holder
            viewHolder = new ViewHolder();
            viewHolder.setTvName((TextView) convertView.findViewById(R.id.item_firstLine));
            viewHolder.setTvNumber((TextView) convertView.findViewById(R.id.item_secondLine));
            convertView.setTag(viewHolder);
        } else {
            // Recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Muda os valores do item
        SimpleItem item = getItem(position);
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

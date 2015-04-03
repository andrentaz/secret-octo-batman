package com.myco.lcreporter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andre on 4/2/15.
 */
public class ContactsAdapter extends ArrayAdapter<Sheep> {

    public ContactsAdapter(Context context, List<Sheep> list) {
        super(context, R.layout.item_contact, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the View Item Layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_contact, parent, false);

            // Initialize the View Holder
            viewHolder = new ViewHolder();
            viewHolder.setTvName((TextView) convertView.findViewById(R.id.tvName));
            viewHolder.setTvNumber((TextView) convertView.findViewById(R.id.tvNumber));
            viewHolder.setIvThumb((ImageView) convertView.findViewById(R.id.ivThumb));
            convertView.setTag(viewHolder);
        } else {
            // Recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Muda os valores do item
        Sheep item = getItem(position);
        viewHolder.getTvName().setText(item.getName());
        viewHolder.getTvNumber().setText(item.getNumber());
        viewHolder.getIvThumb().setImageBitmap(item.getThumb());

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
        private ImageView ivThumb;

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

        public ImageView getIvThumb() {
            return ivThumb;
        }

        public void setIvThumb(ImageView ivThumb) {
            this.ivThumb = ivThumb;
        }
    }
}

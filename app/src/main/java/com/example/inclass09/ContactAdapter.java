package com.example.inclass09;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) { //if no view to re-use then inflate a new one
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_contact_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fullNameTextView = (TextView) convertView.findViewById(R.id.fullNameTextView);
            viewHolder.phoneTextView = (TextView) convertView.findViewById(R.id.phoneTextView);
            viewHolder.emailTextView = (TextView) convertView.findViewById(R.id.emailTextView);
            viewHolder.contactListImage = convertView.findViewById(R.id.contactListImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fullNameTextView.setText(contact.fullName);
        viewHolder.phoneTextView.setText(String.valueOf(contact.phone));
        viewHolder.emailTextView.setText(contact.email);
        Picasso.get().load(contact.getUrlImage()).into(viewHolder.contactListImage);

        return convertView;
    }

    private static class ViewHolder {
        TextView fullNameTextView;
        TextView phoneTextView;
        TextView emailTextView;
        ImageView contactListImage;
    }
}

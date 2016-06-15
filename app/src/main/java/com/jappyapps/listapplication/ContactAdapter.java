package com.jappyapps.listapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by harash on 27/05/16.
 */
public class ContactAdapter extends BaseAdapter {
    Context mContext;
    JSONArray json;
    Set<String> selected;


    public ContactAdapter(Context context) throws JSONException {
        mContext = context;
        json = new JSONObject(CityAdapter.readFileAsString("contacts.json", context)).getJSONArray("contacts");
        selected = new TreeSet<>();

    }

    @Override
    public int getCount() {
        return json.length();
    }

    @Override
    public Object getItem(int position) {

        try {
            return json.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_list_view, parent, false);
        }

        try {
            //populate view

            TextView contact_name = (TextView) convertView.findViewById(R.id.contact_name);
            TextView contact_number = (TextView) convertView.findViewById(R.id.contact_number);
            ImageButton delete = (ImageButton) convertView.findViewById(R.id.delete_contact);
            ImageView contact_image = (ImageView) convertView.findViewById(R.id.contact_image);
            contact_name.setText((json.getJSONObject(position).get("name")).toString());
            contact_number.setText((json.getJSONObject(position).get("Phone Number")).toString());
            delete.setFocusable(false);
            delete.setFocusableInTouchMode(false);
            delete.setClickable(true);
            Picasso.with(mContext).load(json.getJSONObject(position).get("image").toString()).transform(new ToCircle()).placeholder(R.drawable.loading).into(contact_image);
            if (selected.contains(contact_name.getText())) {
                convertView.setBackgroundColor(Color.LTGRAY);
                delete.setVisibility(View.VISIBLE);

            } else {
                convertView.setBackgroundColor(Color.WHITE);
                delete.setVisibility(View.INVISIBLE);
            }
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        removeItem(position);
                        notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return view
        return convertView;

    }

    public void toggle(String name) {
        if (selected.contains(name)) {
            selected.remove(name);
        } else {
            selected.add(name);
        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) throws JSONException {
        json.remove(position);
        selected.remove(json.getJSONObject(position).getString("name").toString());
        notifyDataSetChanged();
    }

    public void setMenuItem(Menu m) {
        if (selected.size() > 0) {
            m.getItem(R.id.delete_contact).setVisible(true);
        } else {
            m.getItem(R.id.delete_contact).setVisible(false);
        }
    }

}

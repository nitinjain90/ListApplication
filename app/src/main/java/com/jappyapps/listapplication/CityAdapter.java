package com.jappyapps.listapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by harash on 10/05/16.
 */
public class CityAdapter extends BaseAdapter {

    Context mContext;
    JSONArray json;

    public CityAdapter(Context context) throws JSONException {
        mContext = context;
        json = new JSONObject(readFileAsString("cities.json", mContext)).getJSONArray("cities");
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
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.city_list_view, parent, false);
        }

        try {
            //populate view
            TextView city_name = (TextView) convertView.findViewById(R.id.city_name);
            TextView city_population = (TextView) convertView.findViewById(R.id.city_population);
            TextView city_country = (TextView) convertView.findViewById(R.id.city_country);

            city_name.setText((json.getJSONObject(position).get("city")).toString());
            city_population.setText((json.getJSONObject(position).get("population")).toString());
            city_country.setText((json.getJSONObject(position).get("country")).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return view
        return convertView;
    }

    public static  String readFileAsString(String fileName, Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream input;
        String text = "";

        try {
            input = assetManager.open(fileName);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            text = new String(buffer);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return text;
    }

    public void addCity(String name, String country, long population) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("city", name);
        obj.put("country", country);
        obj.put("population", population);
        json.put(obj);
        notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void removeCityByName(String name) throws JSONException {
        for (int i = 0; i < json.length(); i++) {
             if(json.getJSONObject(i).get("city").equals(name)){
                 json.remove(i);
             }
        }
        notifyDataSetChanged();
    }


}

package com.jappyapps.listapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by harash on 18/05/16.
 */
public class TOCAdapter extends BaseAdapter {
    Context mContext;
    JSONArray json;
    Set<Integer> opened;

    public TOCAdapter(Context context) throws JSONException {
        mContext = context;
        json = new JSONObject(CityAdapter.readFileAsString("book.json", context)).getJSONObject("book").getJSONArray("chapters");
        opened = new TreeSet<>();
    }

    @Override
    public int getCount() {
        int count = 0;
        for (int i = 0; i < json.length(); i++) {
            try {
                count++;
                 if(opened.contains(i)) {
                     JSONArray temp = json.getJSONObject(i).getJSONArray("sections");
                     count = count + temp.length();
                 }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        JSONArray temp = new JSONArray();
        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject chapter = json.getJSONObject(i);
                temp.put(new JSONObject().put("type", "chapter").put("name", chapter.getString("name")).put("index", i));
                if (opened.contains(i)) {
                    JSONArray sections = chapter.getJSONArray("sections");
                    for (int j = 0; j < sections.length(); j++) {
                        JSONObject section = sections.getJSONObject(j);
                        temp.put(new JSONObject().put("type", "section").put("name", section.getString("name")));
                    }
                }
            }

            return temp.get(position);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject object = (JSONObject) getItem(position);
        try {
            if (object.getString("type").equals("chapter")) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.chapter_list_view, parent, false);

                TextView chapter_name = (TextView) convertView.findViewById(R.id.chapter_name);
                chapter_name.setText(object.get("name").toString());
                return convertView;

            } else {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.section_list_view, parent, false);

                TextView chapter_name = (TextView) convertView.findViewById(R.id.section_name);
                chapter_name.setText(object.get("name").toString());
                return convertView;

            }
        } catch (JSONException e) {

        }
        return null;
    }

    public void toggle(int chapter) throws JSONException {
        if(opened.contains(chapter)){
            opened.remove(chapter);
        }else{
            opened.add(chapter);
        }
        notifyDataSetChanged();
    }



}

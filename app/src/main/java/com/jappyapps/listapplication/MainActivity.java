package com.jappyapps.listapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {


    ContactAdapter tAdapter;
    private MenuItem m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView mylist = (ListView) findViewById(R.id.mylist);
        try {
            tAdapter = new ContactAdapter(this);
            mylist.setAdapter(tAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mylist.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              Log.i("Toggle" , "Onclick Executed");
                JSONObject object = (JSONObject) tAdapter.getItem(position);
                try {
                    String name = object.getString("name");
//                    Log.i("Toggle" , "About to execute toggle");
                    tAdapter.toggle(name);
                    if (tAdapter.selected.size() > 0) {
                        m.setVisible(true);
                        System.out.println("Item Selected");
                    } else {
                        m.setVisible(false);
                        System.out.println("Item not selected");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        m = menu.findItem(R.id.remove_contacts);
        m.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clicked = item.getItemId();

        switch (clicked) {
            case R.id.remove_contacts:
                for (Iterator<String> itr = tAdapter.selected.iterator(); itr.hasNext(); ) {
                    String s = itr.next();
                    for (int i = 0; i < tAdapter.json.length(); i++) {
                        try {
                            if (tAdapter.json.getJSONObject(i).getString("name").equals(s)) {
                                tAdapter.json.remove(i);
                                itr.remove();
                                m.setVisible(false);
                                tAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

        }
        return super.onOptionsItemSelected(item);
    }


}

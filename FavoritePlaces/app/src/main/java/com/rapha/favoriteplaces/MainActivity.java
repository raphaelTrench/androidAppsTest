package com.rapha.favoriteplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places = new ArrayList<>();
    static ArrayList<String> lats = new ArrayList<>();
    static ArrayList<String> lngs = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    public void onBackPressed()
    {


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.list);

        places.add("Add a location...");

        final Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.rapha.favoriteplaces", Context.MODE_PRIVATE);

        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<String>())));

            lats = (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.serialize(new ArrayList<String>())));
            lngs = (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("lngs",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(places.size()>0 && lats.size()>0 && lngs.size()>0)
        {
            System.out.println("load data sucessfully check");
        }

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
                    intent.putExtra("coisa",position);
                    startActivity(intent);
                }
                else
                {
                    intent.putExtra("coisa",position);
                    String lat = ((lats.get(position-1)));
                    String lon = ((lngs.get(position-1)));
                    intent.putExtra("lat",lat);
                    intent.putExtra("lon",lon);
                    intent.putExtra("ad",places.get(position));

                    startActivity(intent);
                }
            }
        });
    }
}

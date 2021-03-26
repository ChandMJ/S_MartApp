package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;


public class SearchhomeActivity extends AppCompatActivity {
    String num;
    ListView listView;
    ArrayList list;
    ArrayAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchhome);

        Intent intent=getIntent();
        num=intent.getStringExtra("Phone");
        searchView = findViewById(R.id.searchView);

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        list.add("Books");
        list.add("Vehicles");
        list.add("Miscellaneous");
        list.add("Snacks");
        list.add("Music Instruments");
        list.add("Electronics");
        list.add("Sports Equipment");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                String[] splitted = (""+list.get(position)).split(" ");
                System.out.println(splitted[0]+"   "+splitted[1]);
//                if(splitted[2] .equals("Pouch") ){
//                    Intent i=new Intent(SearchhomeActivity.this,MilkActivity.class);
//                    i.putExtra("content","0");
//                    i.putExtra("Phone",num);
//                    startActivity(i);
//                    finish();
//                }
//                if(splitted[2].equals("Tetra")){
//                    Intent i=new Intent(SearchhomeActivity.this,MilkActivity.class);
//                    i.putExtra("content","1");
//                    i.putExtra("Phone",num);
//                    startActivity(i);
//                    finish();
//                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{

                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
}
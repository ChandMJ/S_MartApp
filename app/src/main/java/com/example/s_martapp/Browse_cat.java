package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Browse_cat extends AppCompatActivity {

    ImageView back;
    String num;

    CardView book,electronic,sport,misc,music,vehicles,snacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_cat);
        Intent intent=getIntent();
        num=intent.getStringExtra("Phone");

        book=findViewById(R.id.books);
        sport=findViewById(R.id.sport);
        electronic=findViewById(R.id.electronics);
        vehicles=findViewById(R.id.vehicle);
        snacks=findViewById(R.id.snacks);
        misc=findViewById(R.id.misc);
        music=findViewById(R.id.music);


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Browse_cat.this,BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Books");
                startActivity(i);
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Browse_cat.this,BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Sports Equipment");
                startActivity(i);
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Browse_cat.this,BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Electronics");
                startActivity(i);
            }
        });
        vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Browse_cat.this,BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Vehicles");
                startActivity(i);
            }
        });
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Browse_cat.this,BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Snacks");
                startActivity(i);
            }
        });
        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Browse_cat.this,BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Miscellaneous");
                startActivity(i);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Browse_cat.this,BrowsedCategoryActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category","Music Instruments");
                startActivity(i);

            }
        });

        back=findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
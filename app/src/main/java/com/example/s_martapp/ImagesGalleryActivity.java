package com.example.s_martapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ImagesGalleryActivity extends AppCompatActivity {

    String category,user;

    ArrayList<String> mArrayUrl = new ArrayList<String>();
    //firebase
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_gallery);

        Intent intent1 = getIntent();
        category=intent1.getStringExtra("category");
        user=intent1.getStringExtra("user");

        ref=database.getReference("User").child(user).child("MyAd").child(category);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if((dataSnapshot1.getKey().equals("Images")) )
                    {
                        for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            for(DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
                                mArrayUrl.add(dataSnapshot3.getValue().toString());
                            }
                        }
                    }
                }

                addImagesToThegallery();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addImagesToThegallery() {
        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.imageGallery);
        for (String image : mArrayUrl) {
            imageGallery.addView(getImageView(image));
        }
    }


    private View getImageView(String image) {
        ImageView imageView = new ImageView(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 10, 0);
        imageView.setLayoutParams(lp);
        Glide.with(ImagesGalleryActivity.this).load(image).into(imageView);
        return imageView;
    }

}
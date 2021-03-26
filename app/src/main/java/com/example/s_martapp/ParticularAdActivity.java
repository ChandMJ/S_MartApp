package com.example.s_martapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
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

public class ParticularAdActivity extends AppCompatActivity {

    String num,category,user,day;

    TextView pprice,pname,iloc,date,desc,name,sem,hostel;
    Toolbar title;

    LinearLayout donate;

    ArrayList<String> mArrayUrl = new ArrayList<String>();

    ImageView gv,viewall;
    Button call;
    TextView moreimg;

    //firebase
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref,ref1,databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_ad);

        Intent intent1 = getIntent();
        num = intent1.getStringExtra("Phone");
        category=intent1.getStringExtra("category");
        user=intent1.getStringExtra("Sellerphone");
        day=intent1.getStringExtra("date");

        donate=findViewById(R.id.donate);

        pprice=findViewById(R.id.pprice);
        pname=findViewById(R.id.pname);
        iloc=findViewById(R.id.iloc);
        date=findViewById(R.id.date);
        desc=findViewById(R.id.desc);
        name=findViewById(R.id.name);
        sem=findViewById(R.id.sem);
        hostel=findViewById(R.id.hostel);

        call=findViewById(R.id.call);
        title=findViewById(R.id.toolbar);

        moreimg=findViewById(R.id.moreimg);
        viewall=findViewById(R.id.viewall);

        gv=findViewById(R.id.gv);

        databaseReference=database.getReference("User").child(num);

        ref=database.getReference("User").child(user).child("MyAd").child(category);
        ref1=database.getReference("User").child(user);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Donate").exists()){
                    donate.setVisibility(View.VISIBLE);
                }
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if((dataSnapshot1.getKey().equals("Date")) )
                    {
                        date.setText(dataSnapshot1.getValue().toString());
                    }
                    if((dataSnapshot1.getKey().equals("Price")) )
                    {
                        pprice.setText("₹ "+dataSnapshot1.getValue().toString());
                    }
                    if((dataSnapshot1.getKey().equals("Title")) )
                    {
                        title.setTitle("        "+dataSnapshot1.getValue().toString());
                        pname.setText(dataSnapshot1.getValue().toString());
                    }
                    if((dataSnapshot1.getKey().equals("Desc")) )
                    {
                        desc.setText(dataSnapshot1.getValue().toString());
                    }
                    if((dataSnapshot1.getKey().equals("Images")) )
                    {
                        for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                            for(DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()){
                                mArrayUrl.add(dataSnapshot3.getValue().toString());
                            }
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if((dataSnapshot1.getKey().equals("name")) )
                    {
                        name.setText(dataSnapshot1.getValue().toString());
                    }
                    if((dataSnapshot1.getKey().equals("semester")) )
                    {
                        sem.setText("Semester: "+dataSnapshot1.getValue().toString());
                    }
                    if((dataSnapshot1.getKey().equals("hostel")) )
                    {
                        hostel.setText(dataSnapshot1.getValue().toString());
                        iloc.setText(dataSnapshot.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(mArrayUrl.size()>0) {
            Glide.with(ParticularAdActivity.this).load(mArrayUrl.get(0)).into(gv);
        }
        if(mArrayUrl.size()>1){
            moreimg.setText((mArrayUrl.size()-1)+" more Images. View All");
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                final String currentdate = sdf.format(cal.getTime());

                databaseReference.child("Call History").child(currentdate+" "+name.getText().toString()).child("Caller Name").setValue(name.getText().toString());
                databaseReference.child("Call History").child(currentdate+" "+name.getText().toString()).child("Caller Phone").setValue(user);
                databaseReference.child("Call History").child(currentdate+" "+name.getText().toString()).child("Caller Sem").setValue(sem.getText().toString());
                databaseReference.child("Call History").child(currentdate+" "+name.getText().toString()).child("Date").setValue(currentdate);
                databaseReference.child("Call History").child(currentdate+" "+name.getText().toString()).child("Product Title").setValue(pname.getText().toString());

                callPhoneNumber();
            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ParticularAdActivity.this,ImagesGalleryActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("category",category);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
        }
    }
    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ParticularAdActivity.this, new String[]{
                            Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0" + user));
                startActivity(callIntent);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0" + user));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
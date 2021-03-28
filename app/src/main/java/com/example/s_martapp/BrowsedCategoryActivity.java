package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BrowsedCategoryActivity extends AppCompatActivity {

    ImageView back;
    String num,category;

    ConstraintLayout cv;

    Toolbar tool;

    String sellernum;
    RecyclerView Shows;
    ShowBrowsAdapter showMySubsAdapter;
    ArrayList<BrowsShow> shows;

    //firebase
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref1,ref2,ref;


    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> desc = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> postedby = new ArrayList<String>();
    ArrayList<String> postedon = new ArrayList<String>();

    ArrayList<String> mArrayUrl = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsed_category);

        Intent intent=getIntent();
        num=intent.getStringExtra("Phone");
        category=intent.getStringExtra("category");

        cv=findViewById(R.id.cv1);
        tool=findViewById(R.id.toolbar);

        Shows=(RecyclerView)findViewById(R.id.shows);

        back=findViewById(R.id.imageView);

        shows=new ArrayList<BrowsShow>();
        ref1=database.getReference("Category").child(category);
        System.out.println("cat: "+category);
        tool.setTitle("        "+category);

        ref=database.getReference("Category");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(category).exists()){
                    cv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            ref1.child("Images").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            System.out.println(dataSnapshot2.getValue().toString() + "     hi");
                            mArrayUrl.add(dataSnapshot2.getValue().toString());
                            break;
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
                int i=0;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            System.out.println("entered: "+dataSnapshot1.getKey().toString());
                            if(dataSnapshot1.getKey().toString().equals("Date")){
                                postedon.add(dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().toString().equals("Price")){
                                price.add(dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().toString().equals("Title")){
                                title.add(dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().toString().equals("Desc")){
                                desc.add(dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().toString().equals("Username")){
                                postedby.add(dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().toString().equals("User")){
                                sellernum=(dataSnapshot1.getValue().toString());
                            }

                    }
                System.out.println(title+"  "+desc+"   "+"   "+price+"   "+postedby+"    "+postedon+"    "+mArrayUrl);
                for(i=0;i<title.size();i++){
                    BrowsShow show=new BrowsShow(title.get(i),desc.get(i),price.get(i),postedby.get(i),postedon.get(i),mArrayUrl.get(i));
                    shows.add(show);
                }
                showMySubsAdapter= new ShowBrowsAdapter(BrowsedCategoryActivity.this,shows,num,sellernum,category);
                Shows.setLayoutManager(new LinearLayoutManager(BrowsedCategoryActivity.this));
                Shows.setItemAnimator(new DefaultItemAnimator());
                Shows.setAdapter(showMySubsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

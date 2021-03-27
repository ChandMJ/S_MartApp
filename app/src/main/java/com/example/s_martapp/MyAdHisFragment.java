package com.example.s_martapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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

public class MyAdHisFragment extends Fragment {


    String num;

    ConstraintLayout cv;

    RecyclerView Shows;
    ShowMyAdHisAdapter showAdapter;
    ArrayList<MyAdHisShow> shows=new ArrayList<MyAdHisShow>();

    //firebase
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref1;

    ArrayList<String> category = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();

    public MyAdHisFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_ad_his, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomeActivity activity = (HomeActivity) getActivity();
        num = activity.sendData();


        Shows=(RecyclerView)view.findViewById(R.id.shows);
        cv=view.findViewById(R.id.cv1);
        ref1=database.getReference("User").child(num);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                if(dataSnapshot.child("MyAd").exists()){
                    cv.setVisibility(View.INVISIBLE);
                    for(DataSnapshot vinesnapshot:dataSnapshot.child("MyAd").getChildren()) {
                        category.add(vinesnapshot.getKey().toString());
                        for(DataSnapshot dataSnapshot1:vinesnapshot.getChildren()){
                            if(dataSnapshot1.getKey().toString().equals("Title")){
                                title.add(dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().toString().equals("Price")){
                                price.add(dataSnapshot1.getValue().toString());
                            }
                            if(dataSnapshot1.getKey().toString().equals("Date")){
                                date.add(dataSnapshot1.getValue().toString());
                            }
                        }
                    }
                }
                for(i=0;i<category.size();i++){
                    MyAdHisShow show=new MyAdHisShow(category.get(i),title.get(i),price.get(i),date.get(i));
                    shows.add(show);
                }
                showAdapter=new ShowMyAdHisAdapter(getContext(),shows,num);
                Shows.setLayoutManager(new LinearLayoutManager(getContext()));
                Shows.setItemAnimator(new DefaultItemAnimator());
                Shows.setAdapter(showAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        shows=new ArrayList<MyAdHisShow>();

    }
}

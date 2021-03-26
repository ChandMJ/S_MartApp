package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellFragment extends Fragment {

    String num;

    CardView book,electronic,sport,misc,music,vehicles,snacks;

    //firebase
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;

    public SellFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeActivity activity = (HomeActivity) getActivity();
        num = activity.sendData();

        book=view.findViewById(R.id.books);
        sport=view.findViewById(R.id.sport);
        electronic=view.findViewById(R.id.electronics);
        vehicles=view.findViewById(R.id.vehicle);
        snacks=view.findViewById(R.id.snacks);
        misc=view.findViewById(R.id.misc);
        music=view.findViewById(R.id.music);

        ref=database.getReference("User").child(num).child("MyAd");

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Books").exists()){
                            Toast.makeText(getContext(),"You already have posted an Ad in this category!!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent i=new Intent(getActivity(),ItemdetailsActivity.class);
                            i.putExtra("Phone",num);
                            i.putExtra("category","Books");
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Sports Equipment").exists()){
                            Toast.makeText(getContext(),"You already have posted an Ad in this category!!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent i=new Intent(getActivity(),ItemdetailsActivity.class);
                            i.putExtra("Phone",num);
                            i.putExtra("category","Sports Equipment");
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Electronics").exists()){
                            Toast.makeText(getContext(),"You already have posted an Ad in this category!!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent i=new Intent(getActivity(),ItemdetailsActivity.class);
                            i.putExtra("Phone",num);
                            i.putExtra("category","Electronics");
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Vehicles").exists()){
                            Toast.makeText(getContext(),"You already have posted an Ad in this category!!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent i=new Intent(getActivity(),ItemdetailsActivity.class);
                            i.putExtra("Phone",num);
                            i.putExtra("category","Vehicles");
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Snacks").exists()){
                            Toast.makeText(getContext(),"You already have posted an Ad in this category!!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent i=new Intent(getActivity(),ItemdetailsActivity.class);
                            i.putExtra("Phone",num);
                            i.putExtra("category","Snacks");
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Miscellaneous").exists()){
                            Toast.makeText(getContext(),"You already have posted an Ad in this category!!!",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent i=new Intent(getActivity(),ItemdetailsActivity.class);
                            i.putExtra("Phone",num);
                            i.putExtra("category","Miscellaneous");
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Music Instruments").exists()){
                            Toast.makeText(getContext(),"You already have posted an Ad in this category!!!",Toast.LENGTH_LONG).show();
                        }
                        else{

                            Intent i=new Intent(getActivity(),ItemdetailsActivity.class);
                            i.putExtra("Phone",num);
                            i.putExtra("category","Music Instruments");
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
}

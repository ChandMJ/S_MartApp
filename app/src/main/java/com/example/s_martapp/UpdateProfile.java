package com.example.s_martapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    ImageView back;
    Button save;
    AppCompatEditText name,email,phone;
    String num;

    //firebase
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Intent intent=getIntent();
        num=intent.getStringExtra("Phone");

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);

        ref=database.getReference("User").child(num);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    System.out.println(dataSnapshot1.getKey());
                    if(dataSnapshot1.getKey().equals("Name"))
                    {
                        name.setText(dataSnapshot1.getValue().toString());
                    }
                    if(dataSnapshot1.getKey().equals("Mobile"))
                    {
                        phone.setText(dataSnapshot1.getValue().toString());
                    }
                    if(dataSnapshot1.getKey().equals("Email"))
                    {
                        email.setText(dataSnapshot1.getValue().toString());
                    }
                }

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

        save=findViewById(R.id.login);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(name.getText().toString().isEmpty() || email.getText().toString().isEmpty())) {

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                System.out.println(dataSnapshot1.getKey());
                                if (dataSnapshot1.getKey().equals("Name")) {
                                    ref.child(dataSnapshot1.getKey()).setValue(name.getText().toString());
                                }
                                if (dataSnapshot1.getKey().equals("Email")) {
                                    ref.child(dataSnapshot1.getKey()).setValue(email.getText().toString());
                                }
                            }
                            Toast.makeText(UpdateProfile.this, "Profile Update Successful!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(UpdateProfile.this,"Fill the details",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
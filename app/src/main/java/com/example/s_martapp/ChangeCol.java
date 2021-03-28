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

public class ChangeCol extends AppCompatActivity {

    ImageView back;
    Button save;
    String num;
    AppCompatEditText sem, branch, hostel;

    //firebase
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_col);
        Intent intent = getIntent();
        num = intent.getStringExtra("Phone");

        sem = findViewById(R.id.sem);
        branch = findViewById(R.id.name);
        hostel = findViewById(R.id.hostel);

        ref = database.getReference("User").child(num);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    System.out.println(dataSnapshot1.getKey());
                    if (dataSnapshot1.getKey().equals("semester")) {
                        sem.setText(dataSnapshot1.getValue().toString());
                    }
                    if (dataSnapshot1.getKey().equals("branch")) {
                        branch.setText(dataSnapshot1.getValue().toString());
                    }
                    if (dataSnapshot1.getKey().equals("hostel")) {
                        hostel.setText(dataSnapshot1.getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save = findViewById(R.id.login);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(sem.getText().toString().isEmpty() || branch.getText().toString().isEmpty() || hostel.getText().toString().isEmpty())) {

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ref.child("semester").setValue(sem.getText().toString());
                            ref.child("branch").setValue(branch.getText().toString());
                            ref.child("hostel").setValue(hostel.getText().toString());

                            Toast.makeText(ChangeCol.this, "Details Update Successful!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(ChangeCol.this, "Fill the details", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
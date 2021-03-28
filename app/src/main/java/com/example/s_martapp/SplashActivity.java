package com.example.s_martapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class
SplashActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView slogan;
    FirebaseUser firebaseUser;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hooks
        image= findViewById(R.id.imageView3);
        slogan= findViewById(R.id.textView7);

        image.setAnimation(topAnim);
        slogan.setAnimation(topAnim);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        Boolean flag = sharedPreferences.getBoolean("Flag", false);

        //check if user is not null
        if (firebaseUser != null) {
            System.out.println(firebaseUser.getPhoneNumber() + "  " + firebaseUser.getDisplayName() + "  " + firebaseUser.getEmail() + "  " + firebaseUser.getUid());
            String num = firebaseUser.getPhoneNumber();
            Toast.makeText(SplashActivity.this, "User logged in ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.putExtra("Phone", num.substring(3));
            startActivity(intent);
            finish();
        } else if (flag) {
            String phone = sharedPreferences.getString("Phone", null);

            Toast.makeText(SplashActivity.this, "User logged in ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.putExtra("Phone", phone);
            startActivity(intent);
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 1800);
        }
    }
}
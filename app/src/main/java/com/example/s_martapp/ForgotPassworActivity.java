package com.example.s_martapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgotPassworActivity extends AppCompatActivity {

    Button sendotp;
    EditText mobile;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    String mVerificationId, emailsend;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference().child("User");
    DatabaseReference ref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        sendotp = findViewById(R.id.sendotp);
        mobile = findViewById(R.id.mobile);

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobile.getText().toString().isEmpty()) {
                    mobile.setError("Provide your Phone number first!");
                    mobile.requestFocus();
                } else if (!(mobile.getText().toString().isEmpty())) {
                    //check if phon exists
                    final ProgressDialog mdialog = new ProgressDialog(ForgotPassworActivity.this);
                    mdialog.setMessage("Please wait...");
                    mdialog.show();
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Chcek if phone exists
                            if (dataSnapshot.child(mobile.getText().toString()).exists()) {
                                mdialog.dismiss();
                                Toast.makeText(ForgotPassworActivity.this, "OTP sent to registered mobile number", Toast.LENGTH_SHORT).show();
                                checkforotp(mobile.getText().toString());
                            } else {
                                mdialog.dismiss();
                                Toast.makeText(ForgotPassworActivity.this, "Mobile number not registered!!!" + mobile.getText().toString(), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } else {
                    Toast.makeText(ForgotPassworActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void checkforotp(final String mobile) {


        sendVerificationCode(mobile);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgotPassworActivity.this);

        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("OTP verification");

        // Setting Dialog Message
        alertDialog.setMessage("Enter OTP");

        final EditText editText = new EditText(ForgotPassworActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        alertDialog.setView(editText);


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        String code = editText.getText().toString().trim();
                        if (code.isEmpty() || code.length() < 6) {
                            editText.setError("Enter valid code");
                            editText.requestFocus();
                            return;
                        }

                        //verifying the code entered manually
                        verifyVerificationCode(code);

                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // closed

        // Showing Alert Message
        alertDialog.show();

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            // Creating alert Dialog with one Button
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgotPassworActivity.this);

            //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("OTP verification");

            // Setting Dialog Message
            alertDialog.setMessage("Enter OTP");

            final EditText editText = new EditText(ForgotPassworActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            editText.setLayoutParams(lp);
            alertDialog.setView(editText);

            if (code != null) {
                editText.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            String code = editText.getText().toString().trim();
                            if (code.isEmpty() || code.length() < 6) {
                                editText.setError("Enter valid code");
                                editText.requestFocus();
                                return;
                            }

                            //verifying the code entered manually
                            verifyVerificationCode(code);
                            //Toast.makeText(getApplicationContext(),"OTP Matched", Toast.LENGTH_SHORT).show();

                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });

            // closed

            // Showing Alert Message
            alertDialog.show();


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ForgotPassworActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(ForgotPassworActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgotPassworActivity.this);

                            //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                            // Setting Dialog Title
                            alertDialog.setTitle("Reset Password");

                            // Setting Dialog Message
                            alertDialog.setMessage("Enter a new password");

                            final EditText editText = new EditText(ForgotPassworActivity.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            editText.setLayoutParams(lp);
                            alertDialog.setView(editText);


                            // Setting Positive "Yes" Button
                            alertDialog.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to execute after dialog
                                            //System.out.println("-------------------------------------------------------------------------"+mobile.getText().toString());
                                            if (!(editText.getText().toString().equals(null))) {
                                                final ProgressDialog mdialog = new ProgressDialog(ForgotPassworActivity.this);
                                                mdialog.setMessage("Please wait...");
                                                mdialog.show();
                                                ref1 = database.getReference("User").child(mobile.getText().toString());
                                                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                            System.out.println(dataSnapshot1.getKey());
                                                            if (dataSnapshot1.getKey().equals("password")) {
                                                                System.out.println("             " + dataSnapshot1.getKey() + "          " + editText.getText().toString());
                                                                ref1.child(dataSnapshot1.getKey()).setValue(editText.getText().toString());
                                                            }
                                                        }
                                                        Toast.makeText(ForgotPassworActivity.this, "Reset Password successful!", Toast.LENGTH_LONG).show();
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            } else {
                                                Toast.makeText(ForgotPassworActivity.this, "Enter a password to update", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                            // Setting Negative "NO" Button
                            alertDialog.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to execute after dialog
                                            dialog.cancel();
                                        }
                                    });

                            // closed

                            // Showing Alert Message
                            AlertDialog alert = alertDialog.create();
                            alert.setCanceledOnTouchOutside(false);
                            alert.show();



                           /* Intent intent = new Intent(ActivityLogin.this, UserActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("email",emailsend);
                            startActivity(intent);*/

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Toast.makeText(ForgotPassworActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
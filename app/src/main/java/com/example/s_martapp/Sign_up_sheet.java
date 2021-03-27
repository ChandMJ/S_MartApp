package com.example.s_martapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
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

public class Sign_up_sheet extends BottomSheetDialogFragment {
    //RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;
    Button signup;
    AppCompatEditText name, phone, email, branch, sem, hostel;
    TextInputLayout passw;

    String mVerificationId;

    //firebase
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(bottomSheet.getHeight());
                coordinatorLayout.getParent().requestLayout();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.register_sheet, null);
        dialog.setContentView(view);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //if(((FullscreenActivity) getContext()).vid!=null) ((FullscreenActivity) getContext()).vid.start();
            }
        });

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        branch = view.findViewById(R.id.branch);
        sem = view.findViewById(R.id.sem);
        hostel = view.findViewById(R.id.hostel);

        passw = view.findViewById(R.id.passcont);

        signup = view.findViewById(R.id.sign_up_wel);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phone.getText().toString().isEmpty() || phone.getText().toString().length() != 10) {
                    phone.setError("Enter a valid mobile");
                    phone.requestFocus();
                }

                if (email.getText().toString().isEmpty()) {
                    email.setError("Provide your Email first!");
                    email.requestFocus();
                } else if (passw.getEditText().getText().toString().isEmpty()) {
                    passw.setError("Set your password");
                    passw.requestFocus();
                } else if (email.getText().toString().isEmpty() && passw.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.getText().toString().isEmpty() || passw.getEditText().getText().toString().isEmpty() || phone.getText().toString().isEmpty() || branch.getText().toString().isEmpty() || sem.getText().toString().isEmpty() || hostel.getText().toString().isEmpty() || name.getText().toString().isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), passw.getEditText().getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                sendVerificationCode(phone.getText().toString());

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                alertDialog.setTitle("OTP verification");
                                alertDialog.setMessage("Enter OTP");

                                alertDialog.setCancelable(false);

                                final EditText editText = new EditText(getActivity());
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


                                //startActivity(new Intent(MainActivity.this, UserActivity.class).putExtra("email",For_split_email[0]+For_split_email[1]));
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Fill all the details!", Toast.LENGTH_SHORT).show();
                }


            }
        });


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
            final String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            // Creating alert Dialog with one Button
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());


            // Setting Dialog Title
            alertDialog.setTitle("OTP verification");

            // Setting Dialog Message
            alertDialog.setMessage("Enter OTP");
            alertDialog.setCancelable(false);

            final EditText editText = new EditText(getActivity());
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
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final ProgressDialog mdialog = new ProgressDialog(getActivity());
                            mdialog.setMessage("Please wait...");
                            mdialog.show();
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //Chcek if phone exists
                                    if (dataSnapshot.child(phone.getText().toString()).exists()) {
                                        mdialog.dismiss();
                                        Toast.makeText(getContext(), "Mobile number already exists!", Toast.LENGTH_LONG).show();
                                    } else {
                                        mdialog.dismiss();
                                        User user = new User(name.getText().toString(), passw.getEditText().getText().toString(), email.getText().toString(), phone.getText().toString(), sem.getText().toString(), hostel.getText().toString(), branch.getText().toString());
                                        ref.child("User").child(phone.getText().toString()).setValue(user);
                                        Toast.makeText(getActivity(), "Sign up successful!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                        intent.putExtra("Phone", phone.getText().toString());
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(getView().findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }


}

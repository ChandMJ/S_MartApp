package com.example.s_martapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sign_in_sheet extends BottomSheetDialogFragment {

    private static final int RC_SIGN_IN = 1;

    Button login;
    TextView forgot;
    EditText loginmob;
    TextInputLayout loginpass;

    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("User");

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String mVerificationId;

    public static final String MyPREFERENCES = "login";
    public static final String Password = "passwordKey";
    public static final String Phone = "phoneKey";

    SharedPreferences sharedpreferences;


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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.sign_in_sheet, null);
        dialog.setContentView(view);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //if(((FullscreenActivity) getContext()).vid!=null) ((FullscreenActivity) getContext()).vid.start();
            }
        });

        login = view.findViewById(R.id.login);
        loginmob = view.findViewById(R.id.loginmob);
        loginpass = view.findViewById(R.id.login_pass);
        forgot = view.findViewById(R.id.textView3);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ForgotPassworActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(loginmob.getText().toString().isEmpty() || loginpass.getEditText().getText().toString().isEmpty())) {

                    final ProgressDialog mdialog = new ProgressDialog(getActivity());
                    mdialog.setMessage("Please wait...");
                    mdialog.show();

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //check if user not exist in db
                            if (dataSnapshot.child(loginmob.getText().toString()).exists()) {
                                //get user info
                                mdialog.dismiss();
                                User user = dataSnapshot.child(loginmob.getText().toString()).getValue(User.class);
                                System.out.println(user.getPassword() + "    " + loginpass.getEditText().getText());
                                if (user.getPassword().equals(loginpass.getEditText().getText().toString())) {
                                    sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                    //store in shared preference

                                    String pass = loginpass.getEditText().getText().toString();
                                    String ph = loginmob.getText().toString();
                                    Boolean flag = true;

                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString("Password", pass);
                                    editor.putString("Phone", ph);
                                    editor.putBoolean("Flag", flag);
                                    editor.commit();


                                    Toast.makeText(getActivity(), "Login successful !", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    intent.putExtra("Phone", loginmob.getText().toString());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Sign in failed!!!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                mdialog.dismiss();
                                Toast.makeText(getActivity(), "Mobile Number doesnot exist", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Enter the details!", Toast.LENGTH_LONG).show();
                    if ((loginmob.getText().toString().isEmpty())) {
                        loginmob.setError("Enter phone number");
                        loginmob.requestFocus();
                    }

                    if ((loginpass.getEditText().getText().toString().isEmpty())) {
                        loginpass.setError("Enter Password");
                        loginpass.requestFocus();
                    }
                }
            }
        });

    }

}

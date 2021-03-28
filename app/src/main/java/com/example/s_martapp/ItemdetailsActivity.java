package com.example.s_martapp;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ItemdetailsActivity extends AppCompatActivity {

    String num,category;
    ImageView back;

    String username;

    ProgressDialog progressDialog;

    TextView scrolltxt,choosetxt;

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    GridView gvGallery;
    GalleryAdapter galleryAdapter;
    int uplad_count=0;

    CardView camera;
    AppCompatEditText title,desc,cat,price;
    CheckBox free;

    Button save;

    //firebase
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);
        Intent intent =getIntent();
        num=intent.getStringExtra("Phone");
        category=intent.getStringExtra("category");

        ref1=database.getReference("User").child(num);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Image Uploading Please wait............");

        camera=findViewById(R.id.camera);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        cat=findViewById(R.id.category);
        price=findViewById(R.id.price);

        scrolltxt=findViewById(R.id.scrolltext);
        choosetxt=findViewById(R.id.choosetext);

        gvGallery = (GridView)findViewById(R.id.gv);

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().toString().equals("name")){
                        username=dataSnapshot1.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        free=findViewById(R.id.checkBox);

        save=findViewById(R.id.submit);

        cat.setText("Category: "+category);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.getText().toString().isEmpty()){
                    title.setError("Give a title");
                    title.requestFocus();
                }
                else {
                    progressDialog.show();
                    UplaodImage();
                    SaveItemDetails();
                    progressDialog.dismiss();

                    Toast.makeText(ItemdetailsActivity.this, "Your ad is successfully added!", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
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

    private void SaveItemDetails(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Getting current date
        Calendar cal = Calendar.getInstance();
        //Displaying current date in the desired format
        final String currentdate = sdf.format(cal.getTime());

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(num).child("MyAd").child(category);
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Category").child(category).child(num);

        if(free.isChecked()){
            databaseReference.child("Donate").setValue("Yes");
            databaseReference1.child("Donate").setValue("Yes");
        }

        databaseReference.child("Title").setValue(title.getText().toString());
        databaseReference.child("Desc").setValue(desc.getText().toString());
        databaseReference.child("Date").setValue(currentdate);
        databaseReference.child("Price").setValue(price.getText().toString());

        databaseReference1.child("Title").setValue(title.getText().toString());
        databaseReference1.child("Desc").setValue(desc.getText().toString());
        databaseReference1.child("Date").setValue(currentdate);
        databaseReference1.child("Price").setValue(price.getText().toString());
        databaseReference1.child("User").setValue(num);
        databaseReference1.child("Username").setValue(username);

    }

    private  void StoreLink(String url){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(num).child("MyAd").child(category).child("Images");
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Category").child(category).child(num).child("Images");
        HashMap<String,String > hashMap=new HashMap<>();
        hashMap.put("Imglink",url);
        databaseReference.push().setValue(hashMap);
        databaseReference1.push().setValue(hashMap);

    }

    public void UplaodImage(){
        StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for(uplad_count = 0; uplad_count< mArrayUri.size();uplad_count++){

            Uri IndividualImage=mArrayUri.get(uplad_count);
            final StorageReference ImageName= ImageFolder.child("Images"+IndividualImage.getLastPathSegment());

            ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            System.out.println("Enter"+uri);
                            String url = String.valueOf(uri);
                            StoreLink(url);
                        }
                    });
                }
            });

            System.out.println("Not successful");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    mArrayUri.add(mImageUri);
                    galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                            galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
                scrolltxt.setVisibility(View.VISIBLE);
                choosetxt.setVisibility(View.GONE);
                camera.setVisibility(View.GONE);

            }
            else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
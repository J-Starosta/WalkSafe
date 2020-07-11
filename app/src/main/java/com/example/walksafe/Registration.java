package com.example.walksafe;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.widget.Toast.LENGTH_SHORT;

public class Registration extends AppCompatActivity {

    private Object Tag;
    EditText editTextEmail, editTextPassword, editTextName, editTextPasswordConfirm;
    Button RegisterBtn, selectImageBtn;
    ImageView userImageView;
    private static final int GALLERY_REQUEST_CODE = 1;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    StorageReference mImageStorage;
    String UserID;
    public Uri imageData;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextName =  findViewById(R.id.editTextName);
        editTextPassword =  findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        progressBar = findViewById(R.id.loading);

        RegisterBtn = findViewById(R.id.RegisterBtn);
        selectImageBtn = findViewById(R.id.selectImageBtn);
        userImageView = findViewById(R.id.userImageView);
        mImageStorage = FirebaseStorage.getInstance().getReference("Images");
        fAuth = FirebaseAuth.getInstance();
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                ImageDisplay();
            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name;
                final String email;
                final String password;
                final String password_confirm;

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                name = editTextName.getText().toString();
                password_confirm = editTextPasswordConfirm.getText().toString();

                if(email.equals("")){
                    Toast.makeText(Registration.this, "Email Required", LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(Registration.this, "Password Required", LENGTH_SHORT).show();
                }
                else if(name.equals("")){
                    Toast.makeText(Registration.this, "Name Required", LENGTH_SHORT).show();
                }
                else if(password_confirm.equals("")){
                    Toast.makeText(Registration.this, "Password Confirmation Required", LENGTH_SHORT).show();
                }
                else if(!password.equals(password_confirm)){
                    Toast.makeText(Registration.this, "Password Mismatch", LENGTH_SHORT).show();
                }
                else if(password.length() < 6){
                    Toast.makeText(Registration.this, "Password Must Have 6 Or More Characters", LENGTH_SHORT).show();
                }
                else if(userImageView.getDrawable() == null) {
                    Toast.makeText(Registration.this, "Select the user photo", LENGTH_SHORT).show();
                }
                //registration of the user in firebase
                else{
                    RegisterUser(email, password, name);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public void RegisterUser(final String email, final String password, final String name) {
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserID = fAuth.getCurrentUser().getUid();
                    DatabaseReference mUserReference = mDatabase.getReference("Users");
                    DatabaseReference mCurrentUserReference = mUserReference.child("Current user");
                    mCurrentUserReference.setValue(UserID);
                    DatabaseReference mChildReference = mUserReference.child(UserID);
                    mUserReference.keepSynced(true);
                    mChildReference.child("ImageUrl").setValue("https://icons.iconarchive.com/icons/paomedia/small-n-flat/512/user-male-icon.png");
                    mChildReference.child("Password").setValue(password);
                    mChildReference.child("Email").setValue(email);
                    mChildReference.child("Name").setValue(name);
                    mChildReference.child("Points").setValue(0);
                    mChildReference.child("Milestone_1").setValue(0);
                    mChildReference.child("Milestone_2").setValue(0);
                    mChildReference.child("Milestone_3").setValue(0);
                    FileUploader();
                    Toast.makeText(Registration.this, "User Created", LENGTH_SHORT).show();
                    Intent menuIntent = new Intent(getApplicationContext(), menu.class);
                    menuIntent.putExtra("EXTRA_USER_ID", UserID);
                    startActivity(menuIntent);
                }
                else {
                    Toast.makeText(Registration.this, "Error! " + task.getException().getMessage(), LENGTH_SHORT).show();
                }

            }
        });
    }
    public void ImageDisplay(){
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(imageIntent, GALLERY_REQUEST_CODE);
    }
    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void FileUploader() {
        if(imageData != null){
            final StorageReference fileReference = mImageStorage.child(UserID + "." + getExtension(imageData));
            fileReference.putFile(imageData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            String imageURL = UserID + "." + getExtension(imageData);
                            //fileReference.getDownloadUrl().toString();
                            DatabaseReference mUserReference = mDatabase.getReference("Users");
                            DatabaseReference mChildReference = mUserReference.child(UserID);
                            Log.d((String) Tag, "URL value: " +imageURL);
                            mChildReference.child("ImageUrl").setValue(imageURL);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
        else {
            Toast.makeText(this, "No file selected", LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageData = data.getData();
            userImageView.setImageURI(imageData);
        }
    }
}

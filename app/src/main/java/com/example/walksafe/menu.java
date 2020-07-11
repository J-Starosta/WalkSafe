package com.example.walksafe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class menu extends AppCompatActivity {

    TextView username, email, pointView, discountView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = firebaseDatabase.getReference("Users");
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    Integer discountStep = 100;
    ImageView userPhoto;
    private Object Tag;
    String userID, user, emailAddress, imageUrl, password, points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final String[] image = new String[1];
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        pointView = (TextView) findViewById(R.id.points);
        discountView = (TextView) findViewById(R.id.discount);
        mDatabaseReference.keepSynced(true);
        userID = getIntent().getStringExtra("EXTRA_USER_ID");
        Log.d((String) Tag, "User ID = " + userID);



        userPhoto = findViewById(R.id.user_photo);
        Button milestonesBtn = (Button) findViewById(R.id.milestonesBtn);
        milestonesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent milestoneIntent = new Intent(getApplicationContext(), Milestones.class);
                milestoneIntent.putExtra("EXTRA_USER_ID", userID);
                startActivity(milestoneIntent);
            }
        });

        Button instructionBan = (Button) findViewById(R.id.instructionBtn);
        instructionBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructionIntent = new Intent(getApplicationContext(), instructionPage.class);
                startActivity(instructionIntent);
            }
        });

        Button aboutUsBtn = (Button) findViewById(R.id.aboutUsBtn);
        aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutUsWebpage();
            }
        });

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getKey();
                    Log.d((String) Tag, "Snapshot value: " + snapshot);
                    Log.d((String) Tag, "Key value: " + key);
                    if(key.equals(userID)) {
                        imageUrl = snapshot.child("ImageUrl").getValue().toString();
                        Log.d((String) Tag, "imageUrl value: " + imageUrl);
                        emailAddress = snapshot.child("Email").getValue().toString();
                        Log.d((String) Tag, "email value: " + emailAddress);
                        user = snapshot.child("Name").getValue().toString();
                        Log.d((String) Tag, "user value: " + user);
                        points = snapshot.child("Points").getValue().toString();
                        Integer pointsInt = Integer.parseInt(points);
                        username.setText(user);
                        email.setText(emailAddress);
                        pointView.setText("Points collected: " + points);
                        while(discountStep - pointsInt <= 0){
                            discountStep += 100;
                        }
                        discountView.setText("Next discount in " + (discountStep-pointsInt) + " points");
                       // if(!imageUrl.isEmpty())
                            StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://walksafeapp-2020.appspot.com/Images/")
                                    .child(imageUrl);
                            try {
                                final File localFile = File.createTempFile("images", "png");
                                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        userPhoto.setImageBitmap(bitmap);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                    }
                                });
                            } catch (IOException e ) {}
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(menu.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    public void AboutUsWebpage(){
        String andante = "https://en.metrodoporto.pt/pages/397";
        Uri webaddress = Uri.parse(andante);

        Intent goToAndante = new Intent(Intent.ACTION_VIEW, webaddress);
        if(goToAndante.resolveActivity(getPackageManager()) != null){
            startActivity(goToAndante);
        }
    }
}

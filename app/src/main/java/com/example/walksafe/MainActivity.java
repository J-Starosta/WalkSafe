package com.example.walksafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Object Tag;
    EditText editTextEmail, editTextPassword;
    Button LoginBtn;
    TextView RegisterTextView;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mUserReference = mDatabase.getReference();
    String username;
    public String UserID;
    //public static final String EXTRA_EMAIL = "com.example.walksafe.example.EXTRA_EMAIL";
    //public static final String EXTRA_USERNAME = "com.example.walksafe.example.EXTRA_USERNAME";
    //public static final String EXTRA_USER_ID = "com.example.walksafe.example.EXTRA_USER_ID";
    String EXTRA_EMAIL, EXTRA_USERNAME, EXTRA_USER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mUserReference.keepSynced(true);
        progressBar = findViewById(R.id.loading);

        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        RegisterTextView = (TextView) findViewById(R.id.RegisterTextView);
        fAuth = FirebaseAuth.getInstance();
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email, password;

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(email.equals("")){
                    Toast.makeText(MainActivity.this, "Email Required", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(MainActivity.this, "Password Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    authorization(email, password);
                }
            }

        });

        RegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), Registration.class);
                startActivity(registerIntent);
            }
        });
    }
    public void authorization(final String email, final String password) {
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser userLogged = fAuth.getCurrentUser();
                    UserID = userLogged.getUid();
                    mUserReference.child("Users").child("Current user").setValue(UserID);
                    Log.d((String) Tag, "User ID = " + UserID);
                    Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    Intent menuIntent = new Intent(getApplicationContext(), menu.class);
                    menuIntent.putExtra("EXTRA_USER_ID", UserID);
                    startActivity(menuIntent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Error! " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

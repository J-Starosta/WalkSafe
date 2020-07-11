package com.example.walksafe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Milestones extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = firebaseDatabase.getReference("Users");

    ProgressBar Milestone1ProgressBar;
    ProgressBar Milestone2ProgressBar;
    ProgressBar Milestone3ProgressBar;
    TextView ProgressBarTextView1;
    TextView ProgressBarTextView2;
    TextView ProgressBarTextView3;
    ImageView EmptyStar1Milestone1;
    ImageView EmptyStar2Milestone1;
    ImageView EmptyStar3Milestone1;
    ImageView EmptyStar4Milestone1;
    ImageView EmptyStar5Milestone1;
    ImageView EmptyStar1Milestone2;
    ImageView EmptyStar2Milestone2;
    ImageView EmptyStar3Milestone2;
    ImageView EmptyStar4Milestone2;
    ImageView EmptyStar5Milestone2;
    ImageView EmptyStar1Milestone3;
    ImageView EmptyStar2Milestone3;
    ImageView EmptyStar3Milestone3;
    ImageView EmptyStar4Milestone3;
    ImageView EmptyStar5Milestone3;
    ImageView Star1Milestone1;
    ImageView Star2Milestone1;
    ImageView Star3Milestone1;
    ImageView Star4Milestone1;
    ImageView Star5Milestone1;
    ImageView Star1Milestone2;
    ImageView Star2Milestone2;
    ImageView Star3Milestone2;
    ImageView Star4Milestone2;
    ImageView Star5Milestone2;
    ImageView Star1Milestone3;
    ImageView Star2Milestone3;
    ImageView Star3Milestone3;
    ImageView Star4Milestone3;
    ImageView Star5Milestone3;


    String userID;
    Integer progress1, progress2, progress3, points, milestone_1, milestone_2, milestone_3;
    private Object Tag;
    int numberOfDiscounts;
    int tableMilestones[][] = {
            {0, 100, 250, 500, 800, 1500},
            {0, 10, 21, 62, 124, 200},
            {0, 10, 50, 120, 180, 250}
    };
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestones);

        userID = getIntent().getStringExtra("EXTRA_USER_ID");



        //Setting display
       mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getKey();
                    Log.d((String) Tag, "Snapshot value: " + snapshot);
                    Log.d((String) Tag, "Key value: " + key);
                    if(key.equals(userID)) {

                        /*############## Initialization of variables ##############*/
                        //Progress bars
                        Milestone1ProgressBar = (ProgressBar) findViewById(R.id.Milestone1ProgressBar);
                        Milestone2ProgressBar = (ProgressBar) findViewById(R.id.Milestone2ProgressBar);
                        Milestone3ProgressBar = (ProgressBar) findViewById(R.id.Milestone3ProgressBar);

                        ProgressBarTextView1 = (TextView) findViewById(R.id.ProgressBarTextView1);
                        ProgressBarTextView2 = (TextView) findViewById(R.id.ProgressBarTextView2);
                        ProgressBarTextView3 = (TextView) findViewById(R.id.ProgressBarTextView3);

                        //Text view
                        TextView PointsTextView = (TextView) findViewById(R.id.PointsTextView);
                        TextView Milestone1TextView = (TextView) findViewById(R.id.Milestone1TextView);
                        TextView Milestone2TextView = (TextView) findViewById(R.id.Milestone2TextView);
                        TextView Milestone3TextView = (TextView) findViewById(R.id.Milestone3TextView);

                        EmptyStar1Milestone1 = (ImageView) findViewById(R.id.EmptyStar1Milestone1);
                        EmptyStar2Milestone1 = (ImageView) findViewById(R.id.EmptyStar2Milestone1);
                        EmptyStar3Milestone1 = (ImageView) findViewById(R.id.EmptyStar3Milestone1);
                        EmptyStar4Milestone1 = (ImageView) findViewById(R.id.EmptyStar4Milestone1);
                        EmptyStar5Milestone1 = (ImageView) findViewById(R.id.EmptyStar5Milestone1);
                        EmptyStar1Milestone2 = (ImageView) findViewById(R.id.EmptyStar1Milestone2);
                        EmptyStar2Milestone2 = (ImageView) findViewById(R.id.EmptyStar2Milestone2);
                        EmptyStar3Milestone2 = (ImageView) findViewById(R.id.EmptyStar3Milestone2);
                        EmptyStar4Milestone2 = (ImageView) findViewById(R.id.EmptyStar4Milestone2);
                        EmptyStar5Milestone2 = (ImageView) findViewById(R.id.EmptyStar5Milestone2);
                        EmptyStar1Milestone3 = (ImageView) findViewById(R.id.EmptyStar1Milestone3);
                        EmptyStar2Milestone3 = (ImageView) findViewById(R.id.EmptyStar2Milestone3);
                        EmptyStar3Milestone3 = (ImageView) findViewById(R.id.EmptyStar3Milestone3);
                        EmptyStar4Milestone3 = (ImageView) findViewById(R.id.EmptyStar4Milestone3);
                        EmptyStar5Milestone3 = (ImageView) findViewById(R.id.EmptyStar5Milestone3);
                        Star1Milestone1 = (ImageView) findViewById(R.id.Star1Milestone1);
                        Star2Milestone1 = (ImageView) findViewById(R.id.Star2Milestone1);
                        Star3Milestone1 = (ImageView) findViewById(R.id.Star3Milestone1);
                        Star4Milestone1 = (ImageView) findViewById(R.id.Star4Milestone1);
                        Star5Milestone1 = (ImageView) findViewById(R.id.Star5Milestone1);
                        Star1Milestone2 = (ImageView) findViewById(R.id.Star1Milestone2);
                        Star2Milestone2 = (ImageView) findViewById(R.id.Star2Milestone2);
                        Star3Milestone2 = (ImageView) findViewById(R.id.Star3Milestone2);
                        Star4Milestone2 = (ImageView) findViewById(R.id.Star4Milestone2);
                        Star5Milestone2 = (ImageView) findViewById(R.id.Star5Milestone2);
                        Star1Milestone3 = (ImageView) findViewById(R.id.Star1Milestone3);
                        Star2Milestone3 = (ImageView) findViewById(R.id.Star2Milestone3);
                        Star3Milestone3 = (ImageView) findViewById(R.id.Star3Milestone3);
                        Star4Milestone3 = (ImageView) findViewById(R.id.Star4Milestone3);
                        Star5Milestone3 = (ImageView) findViewById(R.id.Star5Milestone3);





                        /*############## Getting values from Firebase ##############*/

                        milestone_1 = Integer.valueOf(String.valueOf(snapshot.child("Milestone_1").getValue()));
                        Log.d(String.valueOf((Integer) Tag), "Milestone_1 value:" + milestone_1);
                        milestone_2 = Integer.valueOf(String.valueOf(snapshot.child("Milestone_2").getValue()));
                        Log.d(String.valueOf((Integer) Tag), "Milestone_2 value:" + milestone_2);
                        milestone_3 = Integer.valueOf(String.valueOf(snapshot.child("Milestone_3").getValue()));
                        Log.d(String.valueOf((Integer) Tag), "Milestone_3 value:" + milestone_3);
                        points = Integer.valueOf(String.valueOf(snapshot.child("Points").getValue()));
                        Log.d(String.valueOf((Integer) Tag), "Points value:" + points);

                        int milestone[] = new int[3];
                        milestone[0] = (int) milestone_1;
                        milestone[1] = (int) milestone_2;
                        milestone[2] = (int) milestone_3;
                        /*############## Setting the range on progress bars ##############*/

                        for(int j = 0; j < 3; j++){
                            for(int i = 1; i < 6; i++){
                                int stars = 0;
                                    if(milestone[j] < tableMilestones[j][i] && milestone[j] > tableMilestones[j][i-1]){
                                        stars = i - 1;
                                        getProgressBarNumber(j).setMax(tableMilestones[j][i]);
                                        getProgressBarTextViewNumber(j).setText(milestone[j] + "/" + getProgressBarNumber(j).getMax());
                                        progressOfMilestonesInStars(j,stars);
                                    }
                                    else if(milestone[j] == tableMilestones[j][i] && milestone[j] < tableMilestones[j][5]){
                                        stars = i;
                                        getProgressBarNumber(j).setMax(tableMilestones[j][i+1]);
                                        getProgressBarTextViewNumber(j).setText(milestone[j] + "/" + getProgressBarNumber(j).getMax());
                                        progressOfMilestonesInStars(j,stars);
                                    }
                                    else if (milestone[j] >= tableMilestones[j][5]){
                                        stars = i;
                                        getProgressBarNumber(j).setMax(tableMilestones[j][5]);
                                        milestone[j] = tableMilestones[j][5];
                                        getProgressBarTextViewNumber(j).setText("Achievement completed");
                                        progressOfMilestonesInStars(j,stars);
                                    }
                                    else if(milestone[j] == 0){
                                        stars = 0;
                                        getProgressBarNumber(j).setMax(tableMilestones[j][1]);
                                        getProgressBarTextViewNumber(j).setText(milestone[j] + "/" + getProgressBarNumber(j).getMax());
                                        progressOfMilestonesInStars(j,stars);
                                    }
                            }
                        }
                        /*############## Setting data for progress bars ##############*/

                        Milestone1ProgressBar.setProgress(milestone[0]);
                        Milestone2ProgressBar.setProgress(milestone[1]);
                        Milestone3ProgressBar.setProgress(milestone[2]);

                        Milestone1TextView.setText("Cross the road "  + Milestone1ProgressBar.getMax()+" times.");
                        Milestone2TextView.setText(Milestone2ProgressBar.getMax() + " days in a row.");
                        Milestone3TextView.setText("Use " + Milestone3ProgressBar.getMax() + " different crossings.");

                        numberOfDiscounts = (int) points/100;
                        PointsTextView.setText("Number of discounts: " + numberOfDiscounts);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        }
    ProgressBar getProgressBarNumber(int j) {
        if(j == 0 ) return Milestone1ProgressBar;
        else if(j == 1) return Milestone2ProgressBar;
        else return Milestone3ProgressBar;
    }

    TextView getProgressBarTextViewNumber(int j) {
        if(j == 0 ) return ProgressBarTextView1;
        else if(j == 1) return ProgressBarTextView2;
        else return ProgressBarTextView3;
    }
    void progressOfMilestonesInStars(int j, int i) {
        if(j == 0) {
            if(i >= 1) Star1Milestone1.setVisibility(View.VISIBLE);
            if(i >= 2) Star2Milestone1.setVisibility(View.VISIBLE);
            if(i >= 3) Star3Milestone1.setVisibility(View.VISIBLE);
            if(i >= 4) Star4Milestone1.setVisibility(View.VISIBLE);
            if(i >= 5) Star5Milestone1.setVisibility(View.VISIBLE);
        }
        else if(j == 1) {
            if(i >= 1) Star1Milestone2.setVisibility(View.VISIBLE);
            if(i >= 2) Star2Milestone2.setVisibility(View.VISIBLE);
            if(i >= 3) Star3Milestone2.setVisibility(View.VISIBLE);
            if(i >= 4) Star4Milestone2.setVisibility(View.VISIBLE);
            if(i >= 5) Star5Milestone2.setVisibility(View.VISIBLE);
        }
        else if(j == 2) {
            if(i >= 1) Star1Milestone3.setVisibility(View.VISIBLE);
            if(i >= 2) Star2Milestone3.setVisibility(View.VISIBLE);
            if(i >= 3) Star3Milestone3.setVisibility(View.VISIBLE);
            if(i >= 4) Star4Milestone3.setVisibility(View.VISIBLE);
            if(i >= 5) Star5Milestone3.setVisibility(View.VISIBLE);
        }
    }
}

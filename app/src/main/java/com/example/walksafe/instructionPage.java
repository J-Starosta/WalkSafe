package com.example.walksafe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class instructionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_page);

        TextView Step1TextView = (TextView) findViewById(R.id.Step1textView);
        TextView Step2TextView = (TextView) findViewById(R.id.Step2textView);
        TextView Step3TextView = (TextView) findViewById(R.id.Step3textView);
        TextView Step4TextView = (TextView) findViewById(R.id.Step4textView);

        Step1TextView.setText("Step 1: \nScan your card with Andante card \nor scan your phone using \nWalkSafe app.");
        Step2TextView.setText("Step 2: \nCross the road.");
        Step3TextView.setText("Step 3: \nAgain scan your card with Andante \ncard or scan your phone using \nWalkSafe app.");
        Step4TextView.setText("Step 4: \nCollect points to get a discount \non public transport.");
    }
}

package com.example.itlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MCQFinalPage extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView testResult;
    Button shareButton, homeButton;
    int rightAnswerCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqfinal_page);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        testResult = findViewById(R.id.testResult);
        shareButton = findViewById(R.id.share_button);
        homeButton = findViewById(R.id.home_button);

        Intent prevIntent = getIntent();
        rightAnswerCount = prevIntent.getIntExtra("rightAnswerCount", 0);
        circularProgressBar.setProgress(rightAnswerCount * 20);
        testResult.setText(rightAnswerCount + "/5");

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "IT Learning");
                    String shareMessage = "\nI got " + rightAnswerCount + " out of 5, " + "How much can you score?";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }catch (Exception e){
                    //e.toString();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MCQFinalPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
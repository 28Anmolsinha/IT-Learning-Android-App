package com.example.itlearning;

import static com.example.itlearning.HomePage.questions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MCQTestPage extends AppCompatActivity {

    CountDownTimer countDownTimer;
    ProgressBar progressBar;
    int timerValue = 60;
    List<Question> allQuestions;
    Question questionClass;
    int index = 0;
    TextView questionTextView;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    Button nextButton;
    int rightAnswerCount, wrongAnswerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqtest_page);

        //calling a method for finding all the ids
        findingIds();

        //accessing the questions from prev activity
        allQuestions = questions;
        questionClass = allQuestions.get(index);

        //calling a method for setting up all the text data for mcq
        setAllData();

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                timerValue = timerValue - 1;
                progressBar.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                // What should happen when the time is up
                Toast.makeText(MCQTestPage.this, "Time up", Toast.LENGTH_SHORT).show();
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    wrongAnswer();
                }else{
                    String userAnswer = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    if(userAnswer.equals(questionClass.getAnswer())){
                        rightAnswer();
                    }else {
                        wrongAnswer();
                    }
                }

            }
        }.start();

        //Next button and check if the answer is correct or not
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(MCQTestPage.this, "Select Your Answer", Toast.LENGTH_SHORT).show();
                }else{
                    String userAnswer = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    if(userAnswer.equals(questionClass.getAnswer())){
                        rightAnswer();
                    }else {
                        wrongAnswer();
                    }
                }
            }
        });

    }

    private void findingIds(){
        progressBar = findViewById(R.id.progress_bar);
        questionTextView = findViewById(R.id.question);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton_1);
        radioButton2 = findViewById(R.id.radioButton_2);
        radioButton3 = findViewById(R.id.radioButton_3);
        radioButton4 = findViewById(R.id.radioButton_4);
        nextButton = findViewById(R.id.next_button);
    }

    private void setAllData(){
        questionTextView.setText(questionClass.getQuestion());
        //setting text view to be scrollable
        questionTextView.setMovementMethod(new ScrollingMovementMethod());
        radioButton1.setText(questionClass.getoA());
        radioButton2.setText(questionClass.getoB());
        radioButton3.setText(questionClass.getoC());
        radioButton4.setText(questionClass.getoD());
    }

    //what should happen if the ans is correct
    private void rightAnswer(){
        rightAnswerCount++;
        if(index < allQuestions.size() - 1){
            index++;
            questionClass = allQuestions.get(index);
            setAllData();
            radioGroup.clearCheck();
            timerValue = 60;
            countDownTimer.cancel();
            countDownTimer.start();
        }else {
            countDownTimer.cancel();
            gameComplete();
        }

    }

    //what should happen if the answer is wrong
    private void wrongAnswer(){
        wrongAnswerCount++;
        if(index < allQuestions.size() - 1){
            index++;
            questionClass = allQuestions.get(index);
            setAllData();
            radioGroup.clearCheck();
            timerValue = 60;
            countDownTimer.cancel();
            countDownTimer.start();
        }else{
            countDownTimer.cancel();
            gameComplete();
        }
    }

    private void gameComplete(){
        Intent intent = new Intent(MCQTestPage.this, MCQFinalPage.class);
        intent.putExtra("rightAnswerCount", rightAnswerCount);
        intent.putExtra("wrongAnswerCount", wrongAnswerCount);
        startActivity(intent);
    }
}
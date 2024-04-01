package com.nftapp.nftmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Quiz_Level_2 extends AppCompatActivity {

    private int presCounter = 0;

    private String[][] ans1 = {{"Book","Chair"},{"Hue","Hanoi"},{"Pizza","Bread"}};
    private String[][] ans2 = {{"Chicken","Cup"},{"Penguin","Nghe An"},{"Zebra","Rice"}};

    private String[] trueAnswer = {"Chicken","Penguin","Zebra"};




    TextView textQuestion, textTitle;
    Animation smallbigforth;

    public int value =  0;
    public String[] keys1 = ans1[value];
    public String[] keys2 = ans2[value];
    public String textAnswer = trueAnswer[value];
    public int maxPresCounter = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        value = intent.getIntExtra("ii",0);
         keys1 = ans1[value];
         keys2 = ans2[value];
         textAnswer = trueAnswer[value];
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        keys1 = shuffleArray(keys1);
        keys2 = shuffleArray(keys2);


        for (String key : keys1) {
            addView(((LinearLayout) findViewById(R.id.layoutParent1)), key, ((EditText) findViewById(R.id.editText)));
        }
        for (String key : keys2) {
            addView(((LinearLayout) findViewById(R.id.layoutParent2)), key, ((EditText) findViewById(R.id.editText)));
        }
        CountDownTimer cdt = new CountDownTimer(15 * 1000 + 500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int)millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                TextView textViewCountDown = findViewById(R.id.time);
                textViewCountDown.setText("Time Out");
                EditText editText = findViewById(R.id.editText);
                editText.setText(textAnswer);
            }
        }.start();

    }

    private void updateTimer(int i) {
        TextView textViewCountDown = findViewById(R.id.time);
        textViewCountDown.setText(String.valueOf(i));
    }


    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }


    private void addView(LinearLayout viewParent1,  String text1,  EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutParams.rightMargin = 30;
        System.out.println(text1);

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text1);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(16);


        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textTitle = (TextView) findViewById(R.id.textTitle);


        textView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        editText.setText("");

                    editText.setText(text1);
                    textView.startAnimation(smallbigforth);
                    textView.animate().alpha(0).setDuration(300);
                    presCounter++;

                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });


        viewParent1.addView(textView);


    }


    private void doValidate() {
        presCounter = 0;


        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout1 = findViewById(R.id.layoutParent1);
        LinearLayout linearLayout2 = findViewById(R.id.layoutParent2);


        if(editText.getText().toString().equals(textAnswer)) {
            Toast.makeText(Quiz_Level_2.this, "Correct", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(Quiz_Level_2.this, BossAct.class);
            a.putExtra("ll", value+1);
            if (value == 2) {
                Intent end = new Intent(Quiz_Level_2.this, DoneAllQuiz.class);
                startActivity(end);
            } else {
                startActivity(a);
            }

            editText.setText("");
        } else {
            Toast.makeText(Quiz_Level_2.this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }

        keys1 = shuffleArray(keys1);
        keys2 = shuffleArray(keys2);

        linearLayout1.removeAllViews();
        for (String key : keys1) {
            addView(linearLayout1, key, editText);
        }
        linearLayout2.removeAllViews();
        for (String key : keys1) {
            addView(linearLayout2, key, editText);
        }

    }
}
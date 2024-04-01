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


public class MainActivity_Quiz extends AppCompatActivity {

    private int presCounter = 0;
    private int[] generalPressCounter = {4,3,4};
    private String[] genralTextAnswer = {"BIRD","CAT","LION"};
    private String[][] generalKey = {{"R", "I", "B", "D","O"},{"C", "A", "T", "D","X"},{"L", "I", "O", "N", "U"}};

    private String[][] ans1 = {{"Book","Chair"},{"Hue","Hanoi"},{"Pizza","Bread"}};
    private String[][] ans2 = {{"Chicken","Cup"},{"Penguin","Nghe An"},{"Zebra","Rice"}};
    private String[] ans = {"Chicken", "Penguin", "Zebra"};




    TextView textQuestion, textTitle;
    Animation smallbigforth;

    public int value =  0;
    public String[] keys;
    public String textAnswer;
    public int maxPresCounter;

    public int maxPressCounter1 = 1;
    public String[] ans_key1 ;
    public String[] ans_key2 ;
    public String ansText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        value = intent.getIntExtra("i",0);

        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        if (value == 1 || value == 2 || value == 0) {
            keys = generalKey[value];
            textAnswer = genralTextAnswer[value];
            maxPresCounter = generalPressCounter[value];
            keys = shuffleArray(keys);
            for (String key : keys) {
                addView(((LinearLayout) findViewById(R.id.layoutParent1)), key, ((EditText) findViewById(R.id.editText)));
            }
        } else if (value == 3 || value == 4 || value == 5){
            ans_key1 = ans1[value - 3];
            ans_key2 = ans2[value - 3];
            textAnswer = ans[value -3];
            maxPresCounter = 1;
            for (String key : ans_key1) {
                addView(((LinearLayout) findViewById(R.id.layoutParent1)), key, ((EditText) findViewById(R.id.editText)));
            }
            for (String key : ans_key2) {
                addView(((LinearLayout) findViewById(R.id.layoutParent2)), key, ((EditText) findViewById(R.id.editText)));
            }

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


    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayoutParams.rightMargin = 30;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(16);


        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textTitle = (TextView) findViewById(R.id.textTitle);

        if (value == 0 || value == 1 || value ==2) {
            textView.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    if(presCounter < maxPresCounter) {
                        if (presCounter == 0)
                            editText.setText("");

                        editText.setText(editText.getText().toString() + text);
                        textView.startAnimation(smallbigforth);
                        textView.animate().alpha(0).setDuration(300);
                        presCounter++;

                        if (presCounter == maxPresCounter)
                            doValidate();
                    }
                }
            });


            viewParent.addView(textView);


        } else if (value == 3 || value == 4 || value == 5) {
            textView.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                        if (presCounter == 0)
                            editText.setText("");
                        presCounter++;
                        editText.setText(text);
                        textView.startAnimation(smallbigforth);
                        textView.animate().alpha(0).setDuration(300);

                        if (presCounter == 1) {
                            doValidate();
                            presCounter = 0;
                        }
                    }

            });


            viewParent.addView(textView);
        }

    }


    private void doValidate() {
        presCounter = 0;


        EditText editText = findViewById(R.id.editText);

        if(editText.getText().toString().equals(textAnswer)) {
            Toast.makeText(MainActivity_Quiz.this, "Correct", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(MainActivity_Quiz.this, BossAct.class);
            a.putExtra("l", value+1);
            if (value == 5) {
                Intent end = new Intent(MainActivity_Quiz.this, DoneAllQuiz.class);
                startActivity(end);
            } else {
                startActivity(a);

            }


            editText.setText("");
        } else {
            Toast.makeText(MainActivity_Quiz.this, "Wrong", Toast.LENGTH_SHORT).show();
            editText.setText("");
        }


    }




}

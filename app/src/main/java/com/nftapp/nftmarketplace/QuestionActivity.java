package com.nftapp.nftmarketplace;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.nftapp.nftmarketplace.api.ApiService;
import com.nftapp.nftmarketplace.model.Item;
import com.nftapp.nftmarketplace.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionActivity extends AppCompatActivity {
    private TextView countdown_timer, option1, option2, option3, option4, question_text;
    private ImageView close_button, question_image;
    private ConstraintLayout exit_quizz_layout, a_layout, b_layout, c_layout, d_layout;
    private CountDownTimer timer;
    private CardView option1_layout, option2_layout, option3_layout, option4_layout;
    private View option1_blur, option2_blur, option3_blur, option4_blur;
    private String quizz_package, quizz_level, key;
    private int wrongNumberAnswer = 0, correctNumberAnswer = 0, score = 0;
    private List<Question> questionList;
    private int number_question = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        Bundle bundle = getIntent().getExtras();
        quizz_package = bundle.getString("quizz_package");
        quizz_level = bundle.getString("quizz_level");
        getQuestion(quizz_level, quizz_package);
        question_text = findViewById(R.id.question_text);
        option1_layout = findViewById(R.id.option1_layout);
        option2_layout = findViewById(R.id.option2_layout);
        option3_layout = findViewById(R.id.option3_layout);
        option4_layout = findViewById(R.id.option4_layout);
        countdown_timer = findViewById(R.id.countdown_timer);

        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);

        option1_blur = findViewById(R.id.option1_blur);
        option2_blur = findViewById(R.id.option2_blur);
        option3_blur = findViewById(R.id.option3_blur);
        option4_blur = findViewById(R.id.option4_blur);
        question_image = findViewById(R.id.question_image);
        a_layout = findViewById(R.id.a_layout);
        b_layout = findViewById(R.id.b_layout);
        c_layout = findViewById(R.id.c_layout);
        d_layout = findViewById(R.id.d_layout);


        close_button = findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.close_effect);
                mediaPlayer.start();
                timer.cancel();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        View view = LayoutInflater.from(QuestionActivity.this).inflate(R.layout.exit_quizz, exit_quizz_layout);
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
        builder.setView(view);
        Button cancel_button = view.findViewById(R.id.cancel_button);
        Button accept = view.findViewById(R.id.accept_button);
        final AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
            }
        });
    }

    public void getQuestion(String quizz_level, String quizz_package) {
        ApiService.apiService.sendPOST_question(quizz_level, quizz_package).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                List<Question> list = response.body();
                getListQuestion(list);
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
            }
        });
    }
    public void getListQuestion(List<Question> mListQuestion) {
        questionList = mListQuestion;
        setQuestion();
    }



    public void setQuestion() {
        a_layout.setBackgroundColor(Color.rgb(225, 27, 60));
        b_layout.setBackgroundColor(Color.rgb(18, 104, 205));
        c_layout.setBackgroundColor(Color.rgb(214, 158, 0));
        d_layout.setBackgroundColor(Color.rgb(38, 137, 11));
        option1_blur.setVisibility(View.INVISIBLE);
        option1_layout.setEnabled(true);
        option2_blur.setVisibility(View.INVISIBLE);
        option2_layout.setEnabled(true);
        option3_blur.setVisibility(View.INVISIBLE);
        option3_layout.setEnabled(true);
        option4_blur.setVisibility(View.INVISIBLE);
        option4_layout.setEnabled(true);
        Glide.with(this).load(questionList.get(number_question).getQuestion_image()).into(question_image);
        question_text.setText(questionList.get(number_question).getQuestion_text());
        option1.setText(questionList.get(number_question).getQuestion_option1());
        option2.setText(questionList.get(number_question).getQuestion_option2());
        option3.setText(questionList.get(number_question).getQuestion_option3());
        option4.setText(questionList.get(number_question).getQuestion_option4());
        Handler handler = new Handler();
        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%01d", seconds);
                countdown_timer.setText(timeFormatted);
                option1_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.click_effect);
                        mediaPlayer.start();
                        option1_layout.setEnabled(false);
                        option2_blur.setVisibility(View.VISIBLE);
                        option2_layout.setEnabled(false);
                        option3_blur.setVisibility(View.VISIBLE);
                        option3_layout.setEnabled(false);
                        option4_blur.setVisibility(View.VISIBLE);
                        option4_layout.setEnabled(false);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (option1.getText().equals(questionList.get(number_question).getQuestion_key())) {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.correct_answer_effect);
                                    mediaPlayer.start();
                                    a_layout.setBackgroundColor(Color.rgb(0, 255, 0));
                                    correctNumberAnswer++;
                                    score += 10;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);
                                } else {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.wrong_answer_effect);
                                    mediaPlayer.start();
                                    a_layout.setBackgroundColor(Color.rgb(255, 0, 0));
                                    wrongNumberAnswer++;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);

                                }
                            }
                        }, 1000);
                    }
                });
                option2_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.click_effect);
                        mediaPlayer.start();
                        option1_layout.setEnabled(false);
                        option1_blur.setVisibility(View.VISIBLE);
                        option2_layout.setEnabled(false);
                        option3_layout.setEnabled(false);
                        option3_blur.setVisibility(View.VISIBLE);
                        option4_layout.setEnabled(false);
                        option4_blur.setVisibility(View.VISIBLE);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (option2.getText().equals(questionList.get(number_question).getQuestion_key())) {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.correct_answer_effect);
                                    mediaPlayer.start();
                                    b_layout.setBackgroundColor(Color.rgb(0, 255, 0));
                                    correctNumberAnswer++;
                                    score += 10;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);
                                } else {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.wrong_answer_effect);
                                    mediaPlayer.start();
                                    b_layout.setBackgroundColor(Color.rgb(255, 0, 0));
                                    wrongNumberAnswer++;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);

                                }
                            }
                        }, 1000);
                    }
                });
                option3_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.click_effect);
                        mediaPlayer.start();
                        option1_layout.setEnabled(false);
                        option1_blur.setVisibility(View.VISIBLE);
                        option2_layout.setEnabled(false);
                        option2_blur.setVisibility(View.VISIBLE);
                        option3_layout.setEnabled(false);
                        option4_layout.setEnabled(false);
                        option4_blur.setVisibility(View.VISIBLE);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (option3.getText().equals(questionList.get(number_question).getQuestion_key())) {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.correct_answer_effect);
                                    mediaPlayer.start();
                                    c_layout.setBackgroundColor(Color.rgb(0, 255, 0));
                                    correctNumberAnswer++;
                                    score += 10;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);
                                } else {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.wrong_answer_effect);
                                    mediaPlayer.start();
                                    c_layout.setBackgroundColor(Color.rgb(255, 0, 0));
                                    wrongNumberAnswer++;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);

                                }
                            }
                        }, 1000);
                    }
                });
                option4_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.click_effect);
                        mediaPlayer.start();
                        option1_layout.setEnabled(false);
                        option1_blur.setVisibility(View.VISIBLE);
                        option2_layout.setEnabled(false);
                        option2_blur.setVisibility(View.VISIBLE);
                        option3_layout.setEnabled(false);
                        option3_blur.setVisibility(View.VISIBLE);
                        option4_layout.setEnabled(false);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (option4.getText().equals(questionList.get(number_question).getQuestion_key())) {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.correct_answer_effect);
                                    mediaPlayer.start();
                                    d_layout.setBackgroundColor(Color.rgb(0, 255, 0));
                                    correctNumberAnswer++;
                                    score += 10;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);

                                } else {
                                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.wrong_answer_effect);
                                    mediaPlayer.start();
                                    d_layout.setBackgroundColor(Color.rgb(255, 0, 0));
                                    wrongNumberAnswer++;
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            timer.onFinish();
                                        }
                                    }, 1000);

                                }
                            }
                        }, 1000);

                    }
                });
                if (seconds <= 5) {
                    final MediaPlayer mediaPlayer = MediaPlayer.create(QuestionActivity.this, R.raw.timer);
                    mediaPlayer.start();
                }
            }

            @Override
            public void onFinish() {
                timer.cancel();
                number_question++;
                if (number_question == questionList.size()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("quizz_package", quizz_package);
                    bundle.putString("quizz_level", quizz_level);
                    bundle.putInt("correctNumberAnswer", correctNumberAnswer);
                    bundle.putInt("wrongNumberAnswer", wrongNumberAnswer);
                    bundle.putInt("score", score);
                    Intent intent = new Intent(QuestionActivity.this, QuizzResult.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    setQuestion();
                }

            }
        }.start();

    }
}
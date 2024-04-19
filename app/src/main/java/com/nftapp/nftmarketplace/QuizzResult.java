package com.nftapp.nftmarketplace;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nftapp.nftmarketplace.model.QuizzPackage;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class QuizzResult extends AppCompatActivity {
    private ImageView close_button;
    private RelativeLayout replay_button;
    private ConstraintLayout timerConstraintLayout;
    private String quizz_package,  quizz_level;
    private TextView wrongScore,correctScore,resultScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_result);
        final MediaPlayer mediaPlayer = MediaPlayer.create(QuizzResult.this,R.raw.win_effect);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        Shape.DrawableShape drawableShape = new Shape.DrawableShape(AppCompatResources.getDrawable(this,R.drawable.ic_android),true);
        KonfettiView konfettiView = findViewById(R.id.congratulation_effect);
        EmitterConfig emitterConfig = new Emitter(300, TimeUnit.MILLISECONDS).max(300);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .shapes(Shape.Circle.INSTANCE, Shape.Square.INSTANCE, drawableShape)
                        .spread(360)
                        .position(0.5, 0.25,1,1)
                        .sizes(new Size(8,50,10))
                        .timeToLive(10000)
                        .fadeOutEnabled(true)
                        .build());
        Bundle bundle = getIntent().getExtras();
        quizz_package = bundle.getString("quizz_package");
        quizz_level = bundle.getString("quizz_level");
        int correctNumberAnswer = bundle.getInt("correctNumberAnswer");
        int wrongNumberAnswer = bundle.getInt("wrongNumberAnswer");
        int score = bundle.getInt("score");
        wrongScore = findViewById(R.id.wrongScore);
        correctScore = findViewById(R.id.correctScore);
        resultScore = findViewById(R.id.resultScore);
        wrongScore.setText(String.valueOf(wrongNumberAnswer));
        correctScore.setText(String.valueOf(correctNumberAnswer));
        resultScore.setText(String.valueOf(score)+"/100");
        close_button = findViewById(R.id.close_button);
        replay_button = findViewById(R.id.replay_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final MediaPlayer mediaPlayer = MediaPlayer.create(QuizzResult.this,R.raw.close_effect);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("level",quizz_level);
                Intent intent = new Intent(QuizzResult.this,QuizzPackageSelector.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        replay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(QuizzResult.this,R.raw.click_effect);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                View view1 = LayoutInflater.from(QuizzResult.this).inflate(R.layout.timer_dialog, timerConstraintLayout);
                Button cancel_timer = view1.findViewById(R.id.cancel_timer);
                TextView countdown_timer = view1.findViewById(R.id.countdown_timer);
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizzResult.this);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();

                if(alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
                CountDownTimer timer = new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(QuizzResult.this,R.raw.timer);
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                        long seconds = (millisUntilFinished / 1000) % 60;
                        String timeFormatted = String.format(Locale.getDefault(),"%01d",seconds);
                        countdown_timer.setText(timeFormatted);
                    }
                    @Override
                    public void onFinish() {
                        alertDialog.dismiss();
                        onClickGoToPlay();
                    }
                }.start();
                cancel_timer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(QuizzResult.this,R.raw.close_effect);
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                        alertDialog.dismiss();
                        timer.cancel();
                    }
                });

            }
        });
    }

    public void onClickGoToPlay(){
        Bundle bundle1 = new Bundle();
        bundle1.putString("quizz_package",quizz_package);
        bundle1.putString("quizz_level",quizz_level);
        Intent intent = new Intent(QuizzResult.this, QuestionActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}

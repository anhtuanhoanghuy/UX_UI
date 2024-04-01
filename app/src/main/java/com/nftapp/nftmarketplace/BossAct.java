package com.nftapp.nftmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BossAct extends AppCompatActivity {

    TextView textScreen, textQuestion, textTitle, textBtn;
    ImageView bigboss;
    Animation smalltobig;

    public int level = 1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        Intent intent = getIntent();
        level = intent.getIntExtra("l",0);


        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        textQuestion = (TextView) findViewById(R.id.textQuestion);
        textTitle = (Button) findViewById(R.id.textTitle);

        bigboss = (ImageView) findViewById(R.id.bigboss);
        bigboss.startAnimation(smalltobig);

        textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(BossAct.this, MainActivity_Quiz.class);
                    intent.putExtra("i", level);
                    startActivity(intent);

            }
        });

    }
}

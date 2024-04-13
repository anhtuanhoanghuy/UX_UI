package com.nftapp.nftmarketplace.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nftapp.nftmarketplace.QuestionActivity;
import com.nftapp.nftmarketplace.R;
import com.nftapp.nftmarketplace.model.QuizzPackage;

import java.util.List;
import java.util.Locale;

public class QuizzPackageAdapter extends RecyclerView.Adapter<QuizzPackageAdapter.QuizzPackageViewHolder>{
    private ConstraintLayout timerConstraintLayout;

    private Context mContext;
    private List<QuizzPackage> mListQuizzPackage;
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public QuizzPackageAdapter(List<QuizzPackage> mListQuizzPackage) {
        this.mListQuizzPackage = mListQuizzPackage;
    }

    public QuizzPackageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<QuizzPackage> list) {
        this.mListQuizzPackage = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuizzPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quizz_package_item,parent,false);
        return new QuizzPackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizzPackageViewHolder holder, int position) {
        QuizzPackage quizzPackage = mListQuizzPackage.get(position);
        if (quizzPackage == null) {
            return;
        }
        holder.package_number.setText(quizzPackage.getPackage_number());

        holder.package_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.click_effect);
                mediaPlayer.start();
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.timer_dialog, timerConstraintLayout);
                Button cancel_timer = view1.findViewById(R.id.cancel_timer);
                TextView countdown_timer = view1.findViewById(R.id.countdown_timer);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();

                if(alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();
                CountDownTimer timer = new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.timer);
                        mediaPlayer.start();
                        long seconds = (millisUntilFinished / 1000) % 60;
                        String timeFormatted = String.format(Locale.getDefault(),"%01d",seconds);
                        countdown_timer.setText(timeFormatted);

                    }

                    @Override
                    public void onFinish() {
                        alertDialog.dismiss();
                        onClickGoToPlay(quizzPackage);
                    }
                }.start();
                cancel_timer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(mContext,R.raw.close_effect);
                        mediaPlayer.start();
                        alertDialog.dismiss();
                        timer.cancel();
                    }
                });

            }
        });

    }

    public void onClickGoToPlay(QuizzPackage quizzPackage){
        Bundle bundle = new Bundle();
        bundle.putString("quizz_package",quizzPackage.getPackage_number());
        bundle.putString("quizz_level",level);
        Intent intent = new Intent(mContext, QuestionActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public void release() {
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if(mListQuizzPackage != null) {
            return mListQuizzPackage.size();
        }
        return 0;
    }


    public class QuizzPackageViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout package_layout;


        private TextView package_number;



        public QuizzPackageViewHolder(@NonNull View itemView) {
            super(itemView);
            package_layout = itemView.findViewById(R.id.package_layout);
            package_number = itemView.findViewById(R.id.package_number);


        }
    }

}

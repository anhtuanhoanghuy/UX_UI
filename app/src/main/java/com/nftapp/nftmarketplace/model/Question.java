package com.nftapp.nftmarketplace.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private String question_image;
    private String question_text;
    private String question_option1;
    private String question_option2;
    private String question_option3;
    private String question_option4;
    private String question_key;

    public Question(String question_image, String question_text, String question_option1, String question_option2, String question_option3, String question_option4, String question_key) {
        this.question_image = question_image;
        this.question_text = question_text;
        this.question_option1 = question_option1;
        this.question_option2 = question_option2;
        this.question_option3 = question_option3;
        this.question_option4 = question_option4;
        this.question_key = question_key;
    }

    public String getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestion_option1() {
        return question_option1;
    }

    public void setQuestion_option1(String question_option1) {
        this.question_option1 = question_option1;
    }

    public String getQuestion_option2() {
        return question_option2;
    }

    public void setQuestion_option2(String question_option2) {
        this.question_option2 = question_option2;
    }

    public String getQuestion_option3() {
        return question_option3;
    }

    public void setQuestion_option3(String question_option3) {
        this.question_option3 = question_option3;
    }

    public String getQuestion_option4() {
        return question_option4;
    }

    public void setQuestion_option4(String question_option4) {
        this.question_option4 = question_option4;
    }

    public String getQuestion_key() {
        return question_key;
    }

    public void setQuestion_key(String question_key) {
        this.question_key = question_key;
    }

    public static class PaintView extends View {

        Bitmap bitmap;
        public PaintView(Context context) {
            super(context);
        }

        public PaintView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            if(bitmap == null) {
                Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), Common.PICTURE_SELECTED);
                bitmap = Bitmap.createScaledBitmap(srcBitmap, w, h, false);

                for(int i=0; i<bitmap.getWidth();i++){

                    for (int j=0;j<bitmap.getHeight();j++){
                        int alpha = 255 - brightness(bitmap.getPixel(i,j));

                        if(alpha  < 200){
                            bitmap.setPixel(i,j, Color.WHITE);
                        }else {
                            bitmap.setPixel(i,j, Color.BLACK);
                        }
                    }
                }
                if (defaultBitmap == null)
                    defaultBitmap = Bitmap.createBitmap(bitmap);

            }
        }

        private int brightness(int color) {

            return (color >> 16) & 0xff;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(bitmap,0,0,null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            paint((int) event.getX(),(int) event.getY());
            return super.onTouchEvent(event);
        }

        private void paint(int x, int y) {

            int targetColor = bitmap.getPixel(x,y);
            if(targetColor != Color.BLACK) {
                QuizzPackage.FloodFill.floodFill(bitmap, new Point(x, y), targetColor, Common.COLOR_SELECTED);

                addLastAction(Bitmap.createBitmap(getBitmap()));

                invalidate();
            }
        }

        public Bitmap getBitmap() {
            return bitmap;
        }


        private List<Bitmap> bitmapList = new ArrayList<>();
        private Bitmap defaultBitmap = null;
        public void undoLastAction() {

            if(bitmapList.size() > 0){

                bitmapList.remove(bitmapList.size() - 1);
                    if(bitmapList.size() > 0){
                        bitmap = bitmapList.get(bitmapList.size() - 1);
                    }else {
                        bitmap = Bitmap.createBitmap(defaultBitmap);
                }
                    invalidate();
            }
        }

        private void addLastAction(Bitmap b){
            bitmapList.add(b);
        }
    }
}

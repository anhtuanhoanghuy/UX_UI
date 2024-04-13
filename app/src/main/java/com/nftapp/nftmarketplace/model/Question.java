package com.nftapp.nftmarketplace.model;

import java.io.Serializable;

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
}

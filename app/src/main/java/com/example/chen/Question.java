package com.example.chen;

public abstract class Question {
    private String question;
    private int type;

    private String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int type() {
        return type;
    }
}


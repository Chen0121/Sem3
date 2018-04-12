package com.example.chen;

public abstract class abstractQuestion {
    private String question;
    private int questionType;

    private String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int questionType() {
        return questionType;
    }
}


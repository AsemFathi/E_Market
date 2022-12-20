package com.egtactile.e_market;

public class Feedback {

    String feedback;
    Float rate;
    String username;

    public Feedback(String feedback, Float rate , String username) {
        this.feedback = feedback;
        this.rate = rate;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}

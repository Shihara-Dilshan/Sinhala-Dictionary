package com.example.sinhaladictionary.models;

public class SinhalaMeaning {
    private String mean;

    public SinhalaMeaning() {
    }

    public SinhalaMeaning(String mean) {
        this.mean = mean;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    @Override
    public String toString() {
        return "SinhalaMeaning{" +
                "mean='" + mean + '\'' +
                '}';
    }
}

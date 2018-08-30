package com.droid.khosbayar.droidfitnesspal.models;

import com.google.gson.annotations.SerializedName;

public class ArticleHeadline {

    @SerializedName("main")
    private String main;
    @SerializedName("kicker")
    private String kicker;

    public ArticleHeadline(String main, String kicker) {
        this.main = main;
        this.kicker = kicker;
    }

    public ArticleHeadline() {
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getKicker() {
        return kicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }
}


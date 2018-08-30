package com.droid.khosbayar.droidfitnesspal.models;

import com.google.gson.annotations.SerializedName;

public class ArticleMultiMedia {
    @SerializedName("url")
    private String url;

    public ArticleMultiMedia() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

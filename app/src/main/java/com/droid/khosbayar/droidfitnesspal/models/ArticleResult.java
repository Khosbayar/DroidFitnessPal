package com.droid.khosbayar.droidfitnesspal.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticleResult {

    @SerializedName("status")
    private String status;
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("response")
    private ArticleResponse response;

    public ArticleResult(String status, String copyright, ArticleResponse response) {
        this.status = status;
        this.copyright = copyright;
        this.response = response;
    }

    public ArticleResult() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public ArticleResponse getResponse() {
        return response;
    }

    public void setResponse(ArticleResponse response) {
        this.response = response;
    }
}

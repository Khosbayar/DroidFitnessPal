package com.droid.khosbayar.droidfitnesspal.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticleResponse {
    @SerializedName("docs")
    private ArrayList<ArticleDocument> docs;

    public ArticleResponse() {
        docs = new ArrayList<>();

    }

    public ArrayList<ArticleDocument> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<ArticleDocument> docs) {
        this.docs = docs;
    }
}

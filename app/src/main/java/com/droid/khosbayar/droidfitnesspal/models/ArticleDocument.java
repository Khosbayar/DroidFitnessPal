package com.droid.khosbayar.droidfitnesspal.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticleDocument {

    @SerializedName("web_url")
    private String web_url;
    @SerializedName("snippet")
    private String snippet;
    @SerializedName("lead_paragraph")
    private String lead_paragraph;
    @SerializedName("print_page")
    private String print_page;
    @SerializedName("source")
    private String source;
    @SerializedName("multimedia")
    private ArrayList<ArticleMultiMedia> multimedia;
    @SerializedName("headline")
    private ArticleHeadline headline;
    @SerializedName("pub_date")
    private String pub_date;
    @SerializedName("document_type")
    private String document_type;
    @SerializedName("section_name")
    private String section_name;
    @SerializedName("type_of_material")
    private String type_of_material;
    @SerializedName("_id")
    private String _id;

    public ArticleDocument() {
        multimedia = new ArrayList<>();
    }


    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLead_paragraph() {
        return lead_paragraph;
    }

    public void setLead_paragraph(String lead_paragraph) {
        this.lead_paragraph = lead_paragraph;
    }

    public String getPrint_page() {
        return print_page;
    }

    public void setPrint_page(String print_page) {
        this.print_page = print_page;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<ArticleMultiMedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(ArrayList<ArticleMultiMedia> multimedia) {
        this.multimedia = multimedia;
    }

    public ArticleHeadline getHeadline() {
        return headline;
    }

    public void setHeadline(ArticleHeadline headline) {
        this.headline = headline;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getType_of_material() {
        return type_of_material;
    }

    public void setType_of_material(String type_of_material) {
        this.type_of_material = type_of_material;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

package com.droid.khosbayar.droidfitnesspal.services;

import com.droid.khosbayar.droidfitnesspal.models.ArticleResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/svc/search/v2/articlesearch.json")
    Call<ArticleResult> getSearchResults(@Query("q") String query, @Query("page") int page,@Query("api-key") String api_key);

    @GET("/svc/search/v2/articlesearch.json?sort=newest")
    Call<ArticleResult> getNewestResults(@Query("page") int page, @Query("api-key") String api_key);
}

package com.droid.khosbayar.droidfitnesspal.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.droid.khosbayar.droidfitnesspal.R;
import com.droid.khosbayar.droidfitnesspal.adapters.SearchAdapter;
import com.droid.khosbayar.droidfitnesspal.models.ArticleDocument;
import com.droid.khosbayar.droidfitnesspal.models.ArticleResult;
import com.droid.khosbayar.droidfitnesspal.services.GetDataService;
import com.droid.khosbayar.droidfitnesspal.utils.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private int pagecounter;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SearchAdapter mSearchAdapter;
    ProgressDialog progressDoalog;
    List<ArticleDocument> mArticleDocuments;
    private LinearLayoutManager layoutManager;
    private boolean loading = true;
    private String mQuery;
    int pastVisiblesItems, visibleItemCount, totalItemCount, previousTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pagecounter = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressDoalog = new ProgressDialog(SearchActivity.this);
        progressDoalog.setMessage("Loading....");

        mArticleDocuments = new ArrayList<>();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.search_results_recycler);
        mSearchAdapter = new SearchAdapter(this, mArticleDocuments);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mSearchAdapter);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount) <= pastVisiblesItems) {
                        pagecounter++;
                        doPagination();
                        loading = true;
                    }
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(getApplicationContext().SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                clearDataList();
                sendRequest(query, 0);
                pagecounter = 0;
                previousTotal = 0;
                mQuery = query;
                loading = true;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText.length() > 2) {
//                    sendRequest(newText);
//                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    private void sendRequest(String newText, int page) {
        if (newText.isEmpty()) {
            clearDataList();
        } else {
            progressDoalog.show();


            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<ArticleResult> call = service.getSearchResults(newText, page, getString(R.string.api_key));
            call.enqueue(new Callback<ArticleResult>() {
                @Override
                public void onResponse(Call<ArticleResult> call, Response<ArticleResult> response) {
                    progressDoalog.dismiss();
                    if (response.body() != null)
                        generateDataList(response.body());
                    else
                        Toast.makeText(SearchActivity.this, "Something went wrong when getting response body!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ArticleResult> call, Throwable t) {
                    progressDoalog.dismiss();
                    Toast.makeText(SearchActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        mSearchAdapter.notifyDataSetChanged();
    }

    private void doPagination() {
        sendRequest(mQuery, pagecounter);
    }

    private void generateDataList(ArticleResult articleResult) {
        mSearchAdapter.setDocs(articleResult.getResponse().getDocs());
    }

    private void clearDataList() {
        mSearchAdapter.clear();
    }


}

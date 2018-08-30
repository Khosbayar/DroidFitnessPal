package com.droid.khosbayar.droidfitnesspal.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.droid.khosbayar.droidfitnesspal.R;
import com.droid.khosbayar.droidfitnesspal.activities.SearchActivity;
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

public class MainFragment extends Fragment {

    private CardView mCardView;
    private RecyclerView mRecyclerView;
    private SearchAdapter mSearchAdapter;
    List<ArticleDocument> mArticleDocuments;
    private LinearLayoutManager layoutManager;
    ProgressDialog progressDoalog;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount, previousTotal = 0;
    private int pagecounter;
    SwipeRefreshLayout mRefresh;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pagecounter = 0;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        mArticleDocuments = new ArrayList<>();

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mCardView = view.findViewById(R.id.search_card_view);
        mRecyclerView = view.findViewById(R.id.explore_article_recycler);

        mSearchAdapter = new SearchAdapter(getActivity(), mArticleDocuments);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mSearchAdapter);

        mRefresh = view.findViewById(R.id.refresher);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearDataList();
                fetchData();
                pagecounter = 0;
                previousTotal = 0;
                loading = true;
                mRefresh.setRefreshing(false);

            }
        });

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

        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
            }
        });
        fetchData();
        return view;
    }

    private void doPagination() {
        progressDoalog.show();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ArticleResult> call = service.getNewestResults(pagecounter, getString(R.string.api_key));
        call.enqueue(new Callback<ArticleResult>() {
            @Override
            public void onResponse(Call<ArticleResult> call, Response<ArticleResult> response) {
                progressDoalog.dismiss();
                if (response.body() != null)
                    generateDataList(response.body());
                else
                    Toast.makeText(getActivity(), "Something went wrong when getting response body!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArticleResult> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        mSearchAdapter.notifyDataSetChanged();
    }

    private void fetchData() {
        progressDoalog.show();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ArticleResult> call = service.getNewestResults(0, getString(R.string.api_key));
        call.enqueue(new Callback<ArticleResult>() {
            @Override
            public void onResponse(Call<ArticleResult> call, Response<ArticleResult> response) {
                progressDoalog.dismiss();
                if (response.body() != null)
                    generateDataList(response.body());
                else
                    Toast.makeText(getActivity(), "Something went wrong when getting response body!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArticleResult> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        mSearchAdapter.notifyDataSetChanged();
    }

    private void generateDataList(ArticleResult articleResult) {
        mSearchAdapter.setDocs(articleResult.getResponse().getDocs());
    }

    private void clearDataList() {
        mSearchAdapter.clear();
    }
}

package com.droid.khosbayar.droidfitnesspal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.khosbayar.droidfitnesspal.R;
import com.droid.khosbayar.droidfitnesspal.models.ArticleDocument;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<ArticleDocument> docs;
    private Context context;


    public SearchAdapter(Context context, List<ArticleDocument> docs) {
        this.context = context;
        this.docs = docs;
    }

    public void setDocs(List<ArticleDocument> docs) {
//        this.docs = docs;
        this.docs.addAll(docs);
        notifyDataSetChanged();
    }

    public void clear() {
        this.docs.clear();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.article_list_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        holder.mResultText.setText(docs.get(position).getHeadline().getMain());

        if (docs.get(position).getMultimedia().size() > 0) {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load("https://www.nytimes.com/" + docs.get(position).getMultimedia().get(0).getUrl())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.mResultImg);
        }
    }

    @Override
    public int getItemCount() {
        return docs.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        TextView mResultText;
        private ImageView mResultImg;

        SearchViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mResultText = mView.findViewById(R.id.result_title);
            mResultImg = mView.findViewById(R.id.result_icon);
        }
    }
}

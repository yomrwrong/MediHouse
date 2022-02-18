package com.example.medihouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.medihouse.databinding.ArticleItemBinding;
import com.example.medihouse.model.Articles;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    ArrayList<Articles> articleList = new ArrayList<>();
    onItemClickListener listener;

    public ArticleAdapter(ArrayList<Articles> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticleItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.article_item, parent, false);
        return new MyViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Articles item = articleList.get(position);
        holder.itemView.setArticleItem(item);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ArticleItemBinding itemView;

        public MyViewHolder(@NonNull ArticleItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION && listener != null) {
                        listener.itemClicked(getItemAt(pos));
                    }
                }
            });

        }
    }

    private Articles getItemAt(int pos) {
        return articleList.get(pos);
    }

    public interface onItemClickListener {
        void itemClicked(Articles item);
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }
}

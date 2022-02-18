package com.example.medihouse.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Articles implements Serializable {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("url")
    String url;

    @SerializedName("imageUrl")
    String imageUrl;

    @SerializedName("newsSite")
    String newsSite;

    @SerializedName("summary")
    String summary;

    @SerializedName("publishedAt")
    String publishedAt;

    @SerializedName("updatedAt")
    String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNewsSite() {
        return newsSite;
    }

    public void setNewsSite(String newsSite) {
        this.newsSite = newsSite;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @BindingAdapter("loadImg")
    public static void loadImgUrl(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }
}

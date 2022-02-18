package com.example.medihouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.medihouse.databinding.ActivityInfoBinding;
import com.example.medihouse.model.Articles;

public class InfoActivity extends AppCompatActivity {

    public static final String KEY_ITEM = "com.example.mediblog.item";

    ActivityInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitle("Article Info");

        Articles item = (Articles) getIntent().getSerializableExtra(KEY_ITEM);
        binding.setArticleItem(item);
    }



}
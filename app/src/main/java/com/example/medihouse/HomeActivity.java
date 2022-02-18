package com.example.medihouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.medihouse.login.LoginActivity;
import com.example.medihouse.login.SIgnUpActivity;
import com.example.medihouse.model.Articles;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_MOBILE_NUMBER = "com.example.medihouse.mobile.number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_list);
        TextView textViewUsername = findViewById(R.id.text_view_username);

        if (getIntent() != null) {
            textViewUsername.setText("Logged In with : " + getIntent().getStringExtra(EXTRA_MOBILE_NUMBER));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        ArrayList<Articles> articleList = new ArrayList<>();
        ArticleAdapter adapter = new ArticleAdapter(articleList);
        recyclerView.setAdapter(adapter);

        viewModel.getAllArticles().observe(this, articles -> {
            articleList.clear();
            articleList.addAll(articles);
            adapter.notifyDataSetChanged();
        });

        adapter.setListener(item -> {
            //MOVE TO OTHER ACTIVITY TO VIEW INFO
            Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
            intent.putExtra(InfoActivity.KEY_ITEM, item);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_logout) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharePrefs.setBooleanPref(HomeActivity.this, "isLoggedIn", false);
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
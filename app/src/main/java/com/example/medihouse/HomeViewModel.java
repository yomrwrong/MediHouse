package com.example.medihouse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.medihouse.model.Articles;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    HomeRepository repository;

    public HomeViewModel() {
        repository = new HomeRepository();
    }

    public LiveData<ArrayList<Articles>> getAllArticles(){
        return repository.getAllArticles();
    }
}

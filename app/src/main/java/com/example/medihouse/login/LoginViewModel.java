package com.example.medihouse.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.medihouse.db.User;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;
    private LiveData<List<User>> userList;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository(application);
        userList = repository.getAllProfiles();
    }

    public void registerUser(User user){
        repository.insertProfile(user);
    }

    public LiveData<List<User>> getAllUsers(){
        return userList;
    }
}

package com.example.medihouse.login;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.medihouse.db.ProfileDao;
import com.example.medihouse.db.ProfileDb;
import com.example.medihouse.db.User;

import java.util.List;

public class LoginRepository {

    private ProfileDao profileDao;

    private LiveData<List<User>> userList;

    public LoginRepository(Application application) {
        ProfileDb db = ProfileDb.getInstance(application);
        profileDao = db.profileDao();
        userList = profileDao.getAllUsers();
    }

    public LiveData<List<User>> getAllProfiles(){
        return userList;
    }

    public void insertProfile(User user){
        new InsertAsyncTask(profileDao).execute(user);
    }

    public static class InsertAsyncTask extends AsyncTask<User,Void,Void> {
        private ProfileDao profileDao;

        public InsertAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            profileDao.insertNewUser(users[0]);
            return null;
        }
    }

}

package com.ndroid.retrofitmvvm.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ndroid.retrofitmvvm.model.User;

public class UserViewModel extends ViewModel {
    MutableLiveData<User> mData;
    UserRepository mRepo;
    int pageIndex = 1;

    public void init(Context context) {
        if (mRepo == null)
            mRepo = UserRepository.getInstance(context);
        mData = mRepo.getUsers(pageIndex);
    }

    public LiveData<User> getUser() {
        return mData;
    }

    public void callNextUserSet() {
        pageIndex++;
        mRepo.getUsers(pageIndex);
    }
}

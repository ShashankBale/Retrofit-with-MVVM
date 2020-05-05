package com.ndroid.retrofitmvvm.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.ndroid.retrofitmvvm.model.User;
import com.ndroid.retrofitmvvm.retrofit.ApiInterface;
import com.ndroid.retrofitmvvm.retrofit.RetrofitUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepository {

    private static UserRepository instance;
    private static ApiInterface apiInterface;
    final MutableLiveData<User> userMLD = new MutableLiveData<>();

    private UserRepository() {
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null)
            instance = new UserRepository();

        if (apiInterface == null)
            apiInterface = RetrofitUtil.getRetrofitInstance(context).create(ApiInterface.class);

        return instance;
    }

    public MutableLiveData<User> getUsers(int pageNo) {
        apiInterface.getUser(pageNo)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            userMLD.setValue(response.body());
                        } else {
                            userMLD.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        userMLD.setValue(null);
                    }
                });
        return userMLD;
    }
}

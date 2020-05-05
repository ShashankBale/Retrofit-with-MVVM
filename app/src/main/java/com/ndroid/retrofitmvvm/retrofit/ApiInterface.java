package com.ndroid.retrofitmvvm.retrofit;

import com.ndroid.retrofitmvvm.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users")
    Call<User> getUser(
            @Query("page") int pageNo
    );
}

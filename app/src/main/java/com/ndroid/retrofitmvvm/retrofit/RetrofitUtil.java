package com.ndroid.retrofitmvvm.retrofit;

import android.content.Context;
import com.readystatesoftware.chuck.ChuckInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://reqres.in/api/";

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(context))
                .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

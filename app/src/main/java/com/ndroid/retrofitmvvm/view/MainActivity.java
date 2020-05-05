package com.ndroid.retrofitmvvm.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ndroid.retrofitmvvm.R;
import com.ndroid.retrofitmvvm.view.adapter.RecyclerAdapter;
import com.ndroid.retrofitmvvm.model.UserDetail;
import com.ndroid.retrofitmvvm.model.User;
import com.ndroid.retrofitmvvm.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private UserViewModel mUserViewModel;
    private List<UserDetail> alUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        populateValues();
        registerMvvm();
        addListeners();
        initRecyclerView();
    }

    private void initUi() {
        mFab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void registerMvvm() {
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.init(getApplicationContext());
        mUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user == null) {
                    toast("Something went wrong...!!");
                    return;
                }
                if (user.getPage() == 1)
                    hideProgressBar();
                else
                    toast("Refresh..!!");

                if (user.getData().isEmpty())
                    toast("Nothing to show more.");

                List<UserDetail> newList = user.getData();
                alUsers.addAll(newList);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void toast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void populateValues() {
        showProgressBar();
    }

    private void addListeners() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserViewModel.callNextUserSet();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new RecyclerAdapter(this, alUsers);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}

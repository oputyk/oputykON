package com.example.kamil.otomotonotifier.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kamil.otomotonotifier.R;

import butterknife.ButterKnife;

public class ClientEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit);
        ButterKnife.bind(this);
    }
}

package com.example.kamil.otomotonotifier.UI.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.Converters.EntityConverter;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.AdEntity;
import com.example.kamil.otomotonotifier.R;
import com.example.kamil.otomotonotifier.Services.SmsSender;
import com.example.kamil.otomotonotifier.Services.SimpleSmsSender;
import com.example.kamil.otomotonotifier.UI.Adapters.AdArrayAdapter;
import com.example.kamil.otomotonotifier.SearchersCallerReceiver;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdListActivity extends AppCompatActivity implements OnItemClickListener {
    private List<AdEntity> adEntities;
    private ListView searchedAdListView;
    @BindView(R.id.clearAllAdsButton) public Button clearAllAdsButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_list);
        ButterKnife.bind(this);
        initsearchedAdListView();
    }



    protected void onResume() {
        super.onResume();
        update();
    }

    public void goToSearcherListActivity(View view) {
        startActivity(new Intent(this, SearcherListActivity.class));
    }

    public void clearAllAds(View view) {
        AppDatabase.getDatabase(getApplicationContext()).getAdDao().deleteAllAdEntities();
        update();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((Ad) parent.getItemAtPosition(position)).getLink())));
    }

    private void initsearchedAdListView() {
        searchedAdListView = (ListView) findViewById(R.id.searchedAdListView);
        searchedAdListView.setOnItemClickListener(this);
    }

    private void update() {
        downloadAds();
        updateListView();
        updateClearAllAdsButton();
    }


    private void updateClearAllAdsButton() {
        clearAllAdsButton.setText(makeClearAllAdsButttonText());
    }

    private void updateListView() {
        searchedAdListView.setAdapter(new AdArrayAdapter(this, EntityConverter.AdEntitiesToAds(adEntities)));
    }

    private void downloadAds() {
        adEntities = AppDatabase.getDatabase(getApplicationContext()).getAdDao().getAllAdEntities();
        Collections.reverse(adEntities);
    }

    private String makeClearAllAdsButttonText() {
        StringBuilder stringBuilder = new StringBuilder("");
        String baseText = getResources().getString(R.string.clear_all_ads);
        stringBuilder.append(baseText);
        stringBuilder.append(" (");
        stringBuilder.append(adEntities.size());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

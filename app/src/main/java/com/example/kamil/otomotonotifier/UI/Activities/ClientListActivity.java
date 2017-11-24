package com.example.kamil.otomotonotifier.UI.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kamil.otomotonotifier.Data.Daos.ClientDao;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.Client;
import com.example.kamil.otomotonotifier.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.clientListView) ListView clientListView;
    ClientDao clientDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        ButterKnife.bind(this);
        initClientDao();
    }

    private void initClientDao() {
        clientDao = AppDatabase.getDatabase(this).getClientDao();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Client client = (Client) parent.getItemAtPosition(position);
        Intent intent = new Intent();
        intent.putExtra("clientId", client.getPhoneNumber());
        startActivity(intent);
    }
}

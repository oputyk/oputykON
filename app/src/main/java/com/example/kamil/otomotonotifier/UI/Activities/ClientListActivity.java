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
import com.example.kamil.otomotonotifier.UI.Adapters.ClientArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.clientListView) ListView clientListView;
    ClientDao clientDao;
    List<Client> clientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        ButterKnife.bind(this);
        initClientDao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    void update() {
        downloadClientList();
        updateClientListView();
    }

    private void updateClientListView() {
        clientListView.setAdapter(new ClientArrayAdapter(this, clientList));
    }

    private void downloadClientList() {
        clientList = clientDao.getAllClients();
    }

    private void initClientDao() {
        clientDao = AppDatabase.getDatabase(this).getClientDao();
    }

    @OnClick(R.id.addClientButton)
    public void addNewClient() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        Client client = (Client) parent.getItemAtPosition(position);
        Intent intent = new Intent(ClientListActivity.this, SearcherListActivity.class);
        intent.putExtra("clientId", client.getPhoneNumber());
        startActivity(intent);
    }
}

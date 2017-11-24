package com.example.kamil.otomotonotifier.UI.Activities;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kamil.otomotonotifier.Converters.EntityConverter;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;
import com.example.kamil.otomotonotifier.R;
import com.example.kamil.otomotonotifier.UI.Adapters.SearcherArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearcherListActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener {
    @BindView(R.id.dataUsageTextView) public TextView dataUsageTextView;
    @BindView(R.id.searcherListView) public ListView searcherListView;
    List<SearcherEntity> searcherEntities;
    private int clientId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher_list);
        ButterKnife.bind(this);
        retrieveClientId();
        initSearcherListView();
    }

    private void retrieveClientId() {
        clientId = getIntent().getIntExtra("clientId", -1);
        if(clientId == -1) {
            throw new RuntimeException("No clientId in SearcherListActivity.");
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goToSearcherEditActivity(((SearcherEntity) parent.getItemAtPosition(position)).getId());
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final SearcherEntity searcherEntity = (SearcherEntity) parent.getItemAtPosition(position);
        Builder alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setMessage("Czy chcesz usunąć tego obserwatora?");
        alertDialogBuilder.setPositiveButton("Tak", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AppDatabase.getDatabase(SearcherListActivity.this.getApplicationContext()).getSearcherDao().deleteSearcherEntity(searcherEntity.getId());
                SearcherListActivity.this.update();
            }
        });
        alertDialogBuilder.setNegativeButton("Nie", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.create().show();
        return true;
    }

    protected void onResume() {
        super.onResume();
        update();
    }

    private void goToSearcherEditActivity(int searcherId) {
        Intent intent = new Intent(this, SearcherEditActivity.class);
        intent.putExtra("searcherId", searcherId);
        startActivity(intent);
    }

    public void goToSearcherEditActivity(View view) {
        startActivity(new Intent(this, SearcherEditActivity.class));
    }

    private void initSearcherListView() {
        searcherListView.setOnItemClickListener(this);
        searcherListView.setOnItemLongClickListener(this);
    }

    private void update() {
        downloadSearchers();
        updateListView();
    }

    private void updateListView() {
        searcherListView.setAdapter(new SearcherArrayAdapter(this, EntityConverter.SearcherEntitiesToSearchers(searcherEntities)));
    }

    private void downloadSearchers() {
        searcherEntities = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getAllSearcherEntities();
    }
}

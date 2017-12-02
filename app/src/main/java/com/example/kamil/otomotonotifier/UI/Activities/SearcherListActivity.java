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
import android.widget.Button;
import android.widget.ListView;

import com.example.kamil.otomotonotifier.Converters.EntityConverter;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;
import com.example.kamil.otomotonotifier.R;
import com.example.kamil.otomotonotifier.UI.Adapters.SearcherArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearcherListActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener {
    @BindView(R.id.searcherListView) public ListView searcherListView;
    @BindView(R.id.addSearcher) public Button addSearcherButton;
    List<SearcherEntity> searcherEntities;
    private int phoneNumber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher_list);
        ButterKnife.bind(this);
        retrieveClientId();
        if(phoneNumber != -1) {
            enableSearcherButtonClicking();
        } else {
            disableSearcherButtonClicking();
        }
        initSearcherListView();
    }

    private void disableSearcherButtonClicking() {
        addSearcherButton.setClickable(false);
    }

    private void enableSearcherButtonClicking() {
        addSearcherButton.setClickable(true);
    }

    private void retrieveClientId() {
        phoneNumber = getIntent().getIntExtra("phoneNumber", -1);
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
        if(phoneNumber == -1) {
            searcherEntities = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getAllSearcherEntities();
        } else{
            searcherEntities = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getSearchersEntitiesByClientPhoneNumber(phoneNumber);
        }
    }
}

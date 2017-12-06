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
import butterknife.OnClick;

public class SearcherListActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener {
    @BindView(R.id.searcherListView) public ListView searcherListView;
    @BindView(R.id.addSearcher) public Button addSearcherButton;
    List<SearcherEntity> searcherEntities;
    private String phoneNumber = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher_list);
        ButterKnife.bind(this);
        retrievePhoneNumber();
        if(phoneNumber != null) {
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

    private void retrievePhoneNumber() {
        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goToSearcherEditActivityWithSearcherId(((SearcherEntity) parent.getItemAtPosition(position)).getId());
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

    @OnClick(R.id.addSearcher)
    public void goToSearcherEditActivity() {
        Intent intent = new Intent(this, SearcherEditActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
    }

    private void goToSearcherEditActivityWithSearcherId(int searcherId) {
        Intent intent = new Intent(this, SearcherEditActivity.class);
        intent.putExtra("searcherId", searcherId);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);
    }

    public void goToSearcherEditActivityWithSearcherId(View view) {
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
        searcherListView.setAdapter(new SearcherArrayAdapter(this, searcherEntities));
    }

    private void downloadSearchers() {
        if(phoneNumber == null) {
            searcherEntities = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getAllSearcherEntities();
        } else{
            searcherEntities = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getSearchersEntitiesByClientPhoneNumber(phoneNumber);
        }
    }
}

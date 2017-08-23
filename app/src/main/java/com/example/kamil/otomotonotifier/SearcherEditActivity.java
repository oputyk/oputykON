package com.example.kamil.otomotonotifier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;

public class SearcherEditActivity extends AppCompatActivity {
    Searcher searcher = new Searcher();
    Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher_edit);
        if (isSearcherIdSaved()) {
            downloadSearcher();
            updateUIWithSearcher();
        }
        initComponents();
    }

    public void confirmChanges(View view) {
        searcher.setCategory(spinner.getSelectedItem().toString());
        AppSearchersDatabase.getDatabase(getApplicationContext()).getSearcherDao().addSearcher(searcher);
    }

    private void initComponents() {
        spinner = (Spinner) findViewById(R.id.categorySpinner);
    }

    private boolean isSearcherIdSaved() {
        return getIntent().getIntExtra("searcherId", -1) != -1;
    }

    private void downloadSearcher() {
        searcher = AppSearchersDatabase.getDatabase(getApplicationContext()).getSearcherDao().getSearcher((long) getIntent().getIntExtra("searcherId", -1));
    }

    private void updateUIWithSearcher() {

    }
}

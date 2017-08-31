package com.example.kamil.otomotonotifier;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearcherEditActivity extends AppCompatActivity {
    Searcher searcher = new Searcher();
    Spinner spinner;
    static List<Category> categoryList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher_edit);
        initComponents();
        downloadCategoryList();
        if (isSearcherIdSaved()) {
            downloadSearcher();
            updateUIWithSearcher();
        }
    }

    public void confirmChanges(View view) {
        searcher.setCategory(spinner.getSelectedItem().toString());
        AppSearchersDatabase.getDatabase(getApplicationContext()).getSearcherDao().addSearcher(searcher);
    }


    private void downloadCategoryList() {
        if(categoryList.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        categoryList = CategoryDownloader.downloadCategories();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fillInCategoryList();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void initComponents() {
        spinner = (Spinner) findViewById(R.id.categorySpinner);
    }


    private void fillInCategoryList() {
        List<String> categoryStrings = getCategoryString();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @NonNull
    private List<String> getCategoryString() {
        List<String> categoryStrings = new ArrayList<>();
        for(Category category : categoryList) {
            categoryStrings.add(category.getName());
        }
        return categoryStrings;
    }

    private boolean isSearcherIdSaved() {
        return getIntent().getIntExtra("searcherId", -1) != -1;
    }

    private void downloadSearcher() {
        searcher = AppSearchersDatabase.getDatabase(getApplicationContext()).getSearcherDao().getSearcher((long) getIntent().getIntExtra("searcherId", -1));
    }

    private void updateUIWithSearcher() {
        spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(searcher.getCategory()));
    }
}

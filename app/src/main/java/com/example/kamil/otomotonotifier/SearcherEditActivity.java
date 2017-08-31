package com.example.kamil.otomotonotifier;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearcherEditActivity extends AppCompatActivity {
    Searcher searcher = new Searcher();
    Spinner spinner;
    Spinner subspinner;
    static List<Category> categoryList = new ArrayList<>();
    List<Category> subcategoryList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher_edit);
        initComponents();
        downloadCategoryList();
        fillInCategoryList();
        if (isSearcherIdSaved()) {
            downloadSearcher();
            updateUIWithSearcher();
        }
    }

    public void confirmChanges(View view) {
        searcher.setCategory(spinner.getSelectedItem().toString());
        if(subspinner.getVisibility() != Spinner.GONE && !subspinner.getSelectedItem().toString().equalsIgnoreCase("dowolny")) {
            searcher.setSubcategory(subspinner.getSelectedItem().toString());
        }
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
                                removeAllSubcategories();
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

    private void removeAllSubcategories() {
        for(Category category : categoryList) {
            if(category.getSubCategories() != null) {
                for (int i = 0; i < category.getSubCategories().size(); i++) {
                    List<Category> subcategories = category.getSubCategories();
                    if (subcategories.get(i).getName().toLowerCase().contains("wszystkie")) {
                        category.getSubCategories().remove(i);
                    }
                }
            }
        }
    }

    private void initComponents() {
        spinner = (Spinner) findViewById(R.id.categorySpinner);
        subspinner = (Spinner) findViewById(R.id.subcategorySpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = categoryList.get(i);
                searcher.setCategoryCode(category.getCode());
                if(category.getSubCategories() == null) {
                    subspinner.setVisibility(Spinner.GONE);
                    subspinner.setSelection(0);
                }
                else {
                    subspinner.setVisibility(Spinner.VISIBLE);
                    subcategoryList = categoryList.get(i).getSubCategories();
                    fillInSubcategoryList();
                    subspinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        subspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category subcategory = subcategoryList.get(i + 1);
                searcher.setSubcategoryCode(subcategory.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void fillInCategoryList() {
        List<String> categoryStrings = getCategoryStrings();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void fillInSubcategoryList() {
        List<String> subcategoryStrings = getSubcategoryStrings();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subcategoryStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subspinner.setAdapter(adapter);
    }

    @NonNull
    private List<String> getCategoryStrings() {
        List<String> categoryStrings = new ArrayList<>();
        for(Category category : categoryList) {
            categoryStrings.add(category.getName());
        }
        return categoryStrings;
    }

    private List<String> getSubcategoryStrings() {
        List<String> subcategoryStrings = new ArrayList<>();
        subcategoryStrings.add("Dowolny");
        for(Category subcategory : subcategoryList) {
            subcategoryStrings.add(subcategory.getName());
        }
        return subcategoryStrings;
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

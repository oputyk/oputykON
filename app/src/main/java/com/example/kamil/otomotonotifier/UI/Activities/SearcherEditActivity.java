package com.example.kamil.otomotonotifier.UI.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamil.otomotonotifier.AdEngine.Models.Category;
import com.example.kamil.otomotonotifier.AdEngine.Downloaders.CategoryDownloader;
import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.SearcherEntity;
import com.example.kamil.otomotonotifier.R;
import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearcherEditActivity extends AppCompatActivity {
    @BindView(R.id.categorySpinner) public Spinner categorySpinner;
    @BindView(R.id.subcategorySpinner) public Spinner subcategorySpinner;
    @BindView(R.id.makeEdit) public EditText makeEdit;
    @BindView(R.id.modelEdit) public EditText modelEdit;
    @BindView(R.id.versionEdit) public EditText versionEdit;
    @BindView(R.id.typeEdit) public EditText typeEdit;
    @BindView(R.id.minPriceEdit) public EditText minPriceEdit;
    @BindView(R.id.maxPriceEdit) public EditText maxPriceEdit;
    @BindView(R.id.minYearEdit) public EditText minYearEdit;
    @BindView(R.id.maxYearEdit) public EditText maxYearEdit;
    @BindView(R.id.fuelTypeEdit) public EditText fuelTypeEdit;

    Searcher searcher = new Searcher();
    private String phoneNumber = null;
    static List<Category> categoryList = new ArrayList<>();
    List<Category> subcategoryList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher_edit);
        ButterKnife.bind(this);
        retrievePhoneNumber();
        if(phoneNumber == null) {
            throw new RuntimeException("No phoneNumber in SearcherEditActivity!!");
        }
        initComponents();
        downloadCategoryList();
        fillInCategoryList();
        if (isSearcherIdSaved()) {
            downloadSearcher();
            updateUIBySearcher();
        }
    }

    private void retrievePhoneNumber() {
        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    public void confirmChanges(View view) {
        updateSearcherByUI();
        if (!searcher.getCategory().equalsIgnoreCase("osobowe") || searcher.getMake() != null) {
            if (isOsoboweOk()) {
                AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().addSearcherEntity(new SearcherEntity(0, phoneNumber, searcher));
                finish();
            } else {
                Toast.makeText(this, "Nie ma takiej marki samochodu osobowego!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Dodaj markę samochodu osobowego!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOsoboweOk() {
        if (searcher.getCategory().equalsIgnoreCase("osobowe")) {
            String[] osoboweMakes = getResources().getStringArray(R.array.osobowe_makes_string_array);
            boolean contained = false;
            for (String str : osoboweMakes) { // containing
                if (searcher.getMake().equalsIgnoreCase(str)) {
                    contained = true;
                }
            }
            return contained;
        } else {
            return true;
        }
    }

    private void updateSearcherByUI() {
        updateSearcherCategory();
        updateSearcherSubcategory();
        updateSearcherMake();
        updateSearcherModel();
        updateSearcherVersion();
        updateSearcherType();
        updateSearcherMinPrice();
        updateSearcherMaxPrice();
        updateSearcherMinYear();
        updateSearcherMaxYear();
        updateSearcherFuelType();
    }


    private void updateSearcherFuelType() {
        if(!fuelTypeEdit.getText().toString().isEmpty()) {
            searcher.setFuelType(fuelTypeEdit.getText().toString());
        } else {
            searcher.setFuelType(null);
        }
    }

    private void updateSearcherMaxYear() {
        if (!maxYearEdit.getText().toString().isEmpty()) {
            searcher.setMaxYear(Integer.parseInt(maxYearEdit.getText().toString()));
        } else {
            searcher.setMaxYear(null);
        }
    }

    private void updateSearcherMinYear() {
        if (!minYearEdit.getText().toString().isEmpty()) {
            searcher.setMinYear(Integer.parseInt(minYearEdit.getText().toString()));
        } else {
            searcher.setMinYear(null);
        }
    }

    private void updateSearcherMaxPrice() {
        if (!maxPriceEdit.getText().toString().isEmpty()) {
            searcher.setMaxPrice(Integer.parseInt(maxPriceEdit.getText().toString()));
        } else {
            searcher.setMaxPrice(null);
        }
    }

    private void updateSearcherMinPrice() {
        if (!minPriceEdit.getText().toString().isEmpty()) {
            searcher.setMinPrice(Integer.parseInt(minPriceEdit.getText().toString()));
        } else {
            searcher.setMinPrice(null);
        }
    }

    private void updateSearcherType() {
        if (!typeEdit.getText().toString().isEmpty()) {
            searcher.setType(typeEdit.getText().toString());
        } else {
            searcher.setType(null);
        }
    }

    private void updateSearcherVersion() {
        if (!versionEdit.getText().toString().isEmpty()) {
            searcher.setVersion(versionEdit.getText().toString());
        } else {
            searcher.setVersion(null);
        }
    }

    private void updateSearcherModel() {
        if (!modelEdit.getText().toString().isEmpty()) {
            searcher.setModel(modelEdit.getText().toString());
        } else {
            searcher.setModel(null);
        }
    }

    private void updateSearcherMake() {
        if (!makeEdit.getText().toString().isEmpty()) {
            searcher.setMake(makeEdit.getText().toString());
        } else {
            searcher.setMake(null);
        }
    }

    private void updateSearcherSubcategory() {
        if (subcategorySpinner.getVisibility() != Spinner.GONE && !subcategorySpinner.getSelectedItem().toString().equalsIgnoreCase("dowolny")) {
            searcher.setSubcategory(subcategorySpinner.getSelectedItem().toString());
        } else {
            searcher.setSubcategory(null);
        }
    }

    private void updateSearcherCategory() {
        searcher.setCategory(categorySpinner.getSelectedItem().toString());
    }


    private void downloadCategoryList() {
        if (categoryList.isEmpty()) {
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
                                fillInCategorySpinner();
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
        for (Category category : categoryList) {
            if (category.getSubCategories() != null) {
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
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = categoryList.get(i);
                searcher.setCategoryCode(category.getCode());
                if (category.getSubCategories() == null) {
                    subcategorySpinner.setVisibility(Spinner.GONE);
                    subcategorySpinner.setSelection(0);
                } else {
                    subcategorySpinner.setVisibility(Spinner.VISIBLE);
                    subcategoryList = categoryList.get(i).getSubCategories();
                    fillInSubcategoryList();
                    subcategorySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        subcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        categorySpinner.setAdapter(adapter);
    }

    private void fillInSubcategoryList() {
        List<String> subcategoryStrings = getSubcategoryStrings();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subcategoryStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(adapter);
    }

    @NonNull
    private List<String> getCategoryStrings() {
        List<String> categoryStrings = new ArrayList<>();
        for (Category category : categoryList) {
            categoryStrings.add(category.getName());
        }
        return categoryStrings;
    }

    private List<String> getSubcategoryStrings() {
        List<String> subcategoryStrings = new ArrayList<>();
        subcategoryStrings.add("Dowolny");
        for (Category subcategory : subcategoryList) {
            subcategoryStrings.add(subcategory.getName());
        }
        return subcategoryStrings;
    }

    private boolean isSearcherIdSaved() {
        return getIntent().getIntExtra("searcherId", -1) != -1;
    }

    private void downloadSearcher() {
        searcher = AppDatabase.getDatabase(getApplicationContext()).getSearcherDao().getSearcherEntity((long) getIntent().getIntExtra("searcherId", -1)).getSearcher();
    }

    private void updateUIBySearcher() {
        fillInCategorySpinner();
        fillInSubcategorySpinner();
        fillInMakeEdit();
        fillInModelEdit();
        fillInVersionEdit();
        fillInTypeEdit();
        fillInMinPriceEdit();
        fillInMaxPriceEdit();
        fillInMinYearEdit();
        fillInMaxYearEdit();
        fillInFuelType();
    }

    private void fillInCategorySpinner() {
        if (searcher.getCategory() != null) {
            categorySpinner.setSelection(getItemPositionInSpinnerByString(categorySpinner, searcher.getCategory()));
        }
    }

    private void fillInSubcategorySpinner() {
        if (searcher.getSubcategory() != null) {
            subcategorySpinner.setSelection(getItemPositionInSpinnerByString(subcategorySpinner, searcher.getSubcategory()));
        }
    }

    private void fillInMakeEdit() {
        if (searcher.getMake() != null) {
            makeEdit.setText(searcher.getMake());
        }
    }

    private void fillInModelEdit() {
        if (searcher.getModel() != null) {
            modelEdit.setText(searcher.getModel());
        }
    }

    private void fillInVersionEdit() {
        if (searcher.getVersion() != null) {
            versionEdit.setText(searcher.getVersion());
        }
    }

    private void fillInTypeEdit() {
        if (searcher.getType() != null) {
            typeEdit.setText(searcher.getType());
        }
    }

    private void fillInMinPriceEdit() {
        if (searcher.getMinPrice() != null) {
            minPriceEdit.setText(searcher.getMinPrice().toString());
        }
    }

    private void fillInMaxPriceEdit() {
        if (searcher.getMaxPrice() != null) {
            maxPriceEdit.setText(searcher.getMaxPrice().toString());
        }
    }

    private void fillInMinYearEdit() {
        if (searcher.getMinYear() != null) {
            minYearEdit.setText(searcher.getMinYear().toString());
        }
    }

    private void fillInMaxYearEdit() {
        if (searcher.getMaxYear() != null) {
            maxYearEdit.setText(searcher.getMaxYear().toString());
        }
    }

    private void fillInFuelType() {
        if(searcher.getFuelType() != null) {
            fuelTypeEdit.setText(searcher.getFuelType());
        }
    }

    private int getItemPositionInSpinnerByString(Spinner spinner, String string) {
        return ((ArrayAdapter) spinner.getAdapter()).getPosition(string);
    }
}

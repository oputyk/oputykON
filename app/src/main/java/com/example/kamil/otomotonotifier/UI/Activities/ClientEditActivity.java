package com.example.kamil.otomotonotifier.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kamil.otomotonotifier.Data.Databases.AppDatabase;
import com.example.kamil.otomotonotifier.Models.Client;
import com.example.kamil.otomotonotifier.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientEditActivity extends AppCompatActivity {
    @BindView(R.id.phoneNumberEdit)
    public EditText phoneNumberEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.saveClient)
    public void saveClient() {
        try {
            String phoneNumber = phoneNumberEdit.getText().toString();
            AppDatabase.getDatabase(this).getClientDao().addClient(new Client(phoneNumber));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Taki numer telefonu już istnieje!", Toast.LENGTH_LONG).show();
        }
    }
}

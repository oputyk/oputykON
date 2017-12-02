package com.example.kamil.otomotonotifier.UI.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kamil.otomotonotifier.AdEngine.Models.Searcher;
import com.example.kamil.otomotonotifier.Models.Client;
import com.example.kamil.otomotonotifier.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kamil on 24/11/2017.
 */

public class ClientArrayAdapter extends ArrayAdapter<Client> {

    public class ViewHolder {
        @BindView(R.id.clientPhoneNumber) TextView clientPhoneNumber;

        ViewHolder(View view) {
            ButterKnife.bind(view);
        }

        void updateByClient(Client client) {
            clientPhoneNumber.setText(client.getPhoneNumber());
        }
    }

    public ClientArrayAdapter(Activity context, List<Client> clients) {
        super(context, R.layout.client, clients);
    }

    private View inflateAndReturnViewByParent(ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.client, parent, false);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = inflateAndReturnViewByParent(parent);
            convertView.setTag(new ViewHolder(convertView));
        }
        ((ViewHolder)convertView.getTag()).updateByClient(getItem(position));
        return convertView;
    }
}

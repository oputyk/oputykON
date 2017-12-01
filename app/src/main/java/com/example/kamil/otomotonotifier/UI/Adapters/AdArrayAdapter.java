package com.example.kamil.otomotonotifier.UI.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.R;

import java.util.List;

public class AdArrayAdapter extends ArrayAdapter<Ad> {
    private ViewGroup parent;
    private View view;

    private class ViewHolder {
        public TextView adInfo;
        public TextView category;
        public TextView price;

        private ViewHolder() {
        }

        public void updateByAd(Ad ad) {
            category.setText(ad.getCategory());
            adInfo.setText(ad.toString());
            price.setText(ad.moneyToString());
        }
    }

    public AdArrayAdapter(Activity context, List<Ad> ads) {
        super(context, R.layout.ad_basic_info, ads);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parentView) {
        view = convertView;
        parent = parentView;
        if (view == null) {
            view = inflateAndReturnView();
            view.setTag(makeAndReturnViewHolder());
        }
        ((ViewHolder) view.getTag()).updateByAd((Ad) getItem(position));
        return view;
    }

    private View inflateAndReturnView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.ad_basic_info, parent, false);
        return view;
    }

    @NonNull
    private ViewHolder makeAndReturnViewHolder() {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.price = (TextView) view.findViewById(R.id.priceTextView);
        viewHolder.adInfo = (TextView) view.findViewById(R.id.adInfoTextView);
        viewHolder.category = (TextView) view.findViewById(R.id.categoryTextView);
        return viewHolder;
    }
}

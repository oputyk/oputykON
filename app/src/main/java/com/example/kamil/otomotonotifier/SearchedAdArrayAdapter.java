package com.example.kamil.otomotonotifier;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

class SearchedAdArrayAdapter extends ArrayAdapter<Ad> {
    private ViewGroup parent;
    private View view;

    private class ViewHolder {
        public TextView adInfo;
        public TextView category;
        public TextView price;

        private ViewHolder() {
        }

        public void updateByAd(Ad ad) {
            this.category.setText(ad.getCategory());
            this.adInfo.setText(ad.toString());
        }
    }

    public SearchedAdArrayAdapter(Activity context, List<Ad> ads) {
        super(context, R.layout.ad_basic_info, ads);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parentView) {
        this.view = convertView;
        this.parent = parentView;
        if (this.view == null) {
            this.view = inflateAndReturnView();
            this.view.setTag(makeAndReturnViewHolder());
        }
        ((ViewHolder) this.view.getTag()).updateByAd((Ad) getItem(position));
        return this.view;
    }

    private View inflateAndReturnView() {
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.ad_basic_info, this.parent, false);
        return this.view;
    }

    @NonNull
    private ViewHolder makeAndReturnViewHolder() {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.price = (TextView) this.view.findViewById(R.id.priceTextView);
        viewHolder.adInfo = (TextView) this.view.findViewById(R.id.adInfoTextView);
        viewHolder.category = (TextView) this.view.findViewById(R.id.categoryTextView);
        return viewHolder;
    }
}

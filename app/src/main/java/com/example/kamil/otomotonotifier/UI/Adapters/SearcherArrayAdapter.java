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
import com.example.kamil.otomotonotifier.R;

import java.util.List;

public class SearcherArrayAdapter extends ArrayAdapter<Searcher> {
    private ViewGroup parent;
    private View view;

    private class ViewHolder {
        public TextView adInfo;
        public TextView category;
        public TextView price;

        private ViewHolder() {
        }

        public void updateBySearcher(Searcher searcher) {
            this.price.setText(makeAndReturnSearcherPricesString(searcher));
            this.category.setText(searcher.getCategory());
            this.adInfo.setText(searcher.toString());
        }

        private String makeAndReturnSearcherPricesString(Searcher searcher) {
            String minPriceString = "0";
            String maxPriceString = "∞";
            if(searcher.getMinPrice() != null) {
                minPriceString = Integer.toString(searcher.getMinPrice());
            }
            if(searcher.getMaxPrice() != null) {
                maxPriceString = Integer.toString(searcher.getMaxPrice());
            }
            return minPriceString + " - " + maxPriceString + " zł";
        }
    }

    public SearcherArrayAdapter(Activity context, List<Searcher> searchers) {
        super(context, R.layout.ad_basic_info, searchers);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parentView) {
        this.view = convertView;
        this.parent = parentView;
        if (this.view == null) {
            this.view = inflateAndReturnView();
            this.view.setTag(makeAndReturnViewHolder());
        }
        ((ViewHolder) this.view.getTag()).updateBySearcher(getItem(position));
        return this.view;
    }

    private View inflateAndReturnView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.ad_basic_info, this.parent, false);
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

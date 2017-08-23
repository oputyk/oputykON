package com.example.kamil.otomotonotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SearchersCaller extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, SearchersCallerService.class));
    }
}

package com.example.kamil.otomotonotifier.Services;

import android.telephony.SmsManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kamil on 24/11/2017.
 */

public class SimpleSmsSender implements SmsSender {
    private SmsManager smsManager;

    public SimpleSmsSender() {
        smsManager = SmsManager.getDefault();
    }

    @Override
    public void sendMessage(String message, String phoneNumber) {
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}

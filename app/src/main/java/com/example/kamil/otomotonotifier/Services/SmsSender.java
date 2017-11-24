package com.example.kamil.otomotonotifier.Services;

/**
 * Created by kamil on 24/11/2017.
 */

public interface SmsSender {
    void sendMessage(String message, String phoneNumber);
}

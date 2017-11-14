package com.example.kamil.otomotonotifier.AdEngine.RemoteServices;

import org.json.JSONException;

/**
 * Created by kamil on 14/11/2017.
 */
public interface ExchangeRates {
    public double getExchangeRateToPLNFrom(String currencyName) throws JSONException;
    public void periodUpdate();
    void setUpdatePeriod(long milisecondsPeriod);
}

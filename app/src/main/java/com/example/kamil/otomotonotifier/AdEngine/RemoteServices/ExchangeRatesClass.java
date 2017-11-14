package com.example.kamil.otomotonotifier.AdEngine.RemoteServices;
import java.util.Date;

import org.json.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ExchangeRatesClass implements ExchangeRates {
    JSONObject jsonObject;
    Date dateOfLastUpdate;
    long updateMilisecondsPeriod;

    static private ExchangeRatesClass instance = null;

    public static ExchangeRatesClass getInstance() {
        if(instance == null)
            instance = new ExchangeRatesClass();
        return instance;
    }

    private ExchangeRatesClass() {
        downloadExchangeRates();
        updateMilisecondsPeriod = getMilisecondsTime(1, 0, 0, 0, 0);
    }

    private long getMilisecondsTime(int days, int hours, int minutes, int seconds, int miliseconds) {
        long daysToMiliseconds = 86400000 * days;
        long hoursToMiliseconds = 3600000 * hours;
        long minutesToMiliseconds = 60000 * minutes;
        long secondsToMiliseconds = 1000 * seconds;

        return daysToMiliseconds + hoursToMiliseconds
                + minutesToMiliseconds + secondsToMiliseconds + miliseconds;
    }

    @Override
    public void periodUpdate() {
        Date now = new Date();
        Date dateOfNextUpdate = new Date(dateOfLastUpdate.getTime() + updateMilisecondsPeriod);
        if(now.after(dateOfNextUpdate))
            downloadExchangeRates();
    }

    @Override
    public void setUpdatePeriod(long milisecondsPeriod) {
        updateMilisecondsPeriod = milisecondsPeriod;
    }

    private void downloadExchangeRates() {
        getJsonObject();
    }

    private void getJsonObject() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder().url("http://api.fixer.io/latest?base=PLN").build();
                    Response response = client.newCall(request).execute();
                    String bodyString = response.body().string();
                    jsonObject = new JSONObject(bodyString);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public double getExchangeRateToPLNFrom(String currencyName) throws JSONException {
        if(isPLN(currencyName))
            return 1;

        double exchangeRateBasePLN = getExchangeRateBasePLNFrom(currencyName);
        double inverseExchangeRateBasePLN = 0.0;

        if(exchangeRateBasePLN != 0)
            inverseExchangeRateBasePLN = 1.0 / exchangeRateBasePLN;

        return inverseExchangeRateBasePLN;
    }

    private boolean isPLN(String currencyName) {
        return currencyName.equals("PLN");
    }

    private double getExchangeRateBasePLNFrom(String currencyName) throws JSONException {
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        if(ratesObject.has(currencyName))
            return ratesObject.getDouble(currencyName);
        return 0;
    }
}
package com.example.kamil.otomotonotifier.AdEngine.Parsers;

import com.example.kamil.otomotonotifier.AdEngine.Models.Ad;
import com.example.kamil.otomotonotifier.AdEngine.RemoteServices.ExchangeRates;
import com.example.kamil.otomotonotifier.AdEngine.RemoteServices.ExchangeRatesClass;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdListJsonParser implements JsonParser<List<Ad>> {
    JSONArray adJsonArray = new JSONArray();
    List<Ad> ads = new ArrayList();
    int allAdsCount;
    int goodAdsCount = 10;
    JSONObject jsonAdObject = new JSONObject();
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonParamsArray = new JSONArray();
    private boolean kmNotMth = true;
    String nextPageUrl;
    boolean onlyLastAdsParsing = true;
    ExchangeRates exchangeRates = ExchangeRatesClass.getInstance();

    public List<Ad> getAds() {
        return this.ads;
    }

    public String getNextPageUrl() {
        return this.nextPageUrl;
    }

    public int getGoodAdsCount() {
        return this.goodAdsCount;
    }

    public int getAllAdsCount() {
        return this.allAdsCount;
    }

    public boolean getOnlyLastAdsParsing() {
        return this.onlyLastAdsParsing;
    }

    public void setOnlyLastAdsParsing(boolean onlyLastAdsParsing) {
        this.onlyLastAdsParsing = onlyLastAdsParsing;
    }

    public List<Ad> parse(JSONObject jsonObject) throws JSONException {
        this.jsonObject = jsonObject;
        parseAds();
        return this.ads;
    }

    private void parseAds() throws JSONException {
        parseNextPageUrl();
        parseAllAdsCount();
        parseAdJsonArray();
        parseAdsCount();
        if (this.onlyLastAdsParsing) {
            parseOnlyLastAds();
        } else {
            parseAllAds();
        }
    }

    private void parseNextPageUrl() throws JSONException {
        this.nextPageUrl = this.jsonObject.getString("next_page_url");
    }

    private void parseAllAdsCount() throws JSONException {
        this.allAdsCount = this.jsonObject.getInt("total_ads");
    }

    private void parseAdJsonArray() throws JSONException {
        this.adJsonArray = this.jsonObject.getJSONArray("ads");
    }

    private void parseAdsCount() throws JSONException {
        this.goodAdsCount = this.jsonObject.getInt("ads_on_page");
    }

    private void parseOnlyLastAds() throws JSONException {
        for (int i = this.adJsonArray.length() - this.goodAdsCount; i < this.adJsonArray.length(); i++) {
            this.jsonAdObject = this.adJsonArray.getJSONObject(i);
            parseAd();
        }
    }

    private void parseAllAds() throws JSONException {
        for (int i = 0; i < this.adJsonArray.length(); i++) {
            this.jsonAdObject = this.adJsonArray.getJSONObject(i);
            parseAd();
        }
    }

    private void parseAd() throws JSONException {
        parseJsonParamsArray();
        Ad ad = new Ad();
        ad.setAdId(parseAndReturnId());
        ad.setCity(parseAndReturnCity());
        ad.setCategory(parseAndReturnCategory());
        ad.setSubcategory(parseAndReturnSubcategory());
        ad.setMake(parseAndReturnMake());
        ad.setModel(parseAndReturnModel());
        ad.setVersion(parseAndReturnVersion());
        ad.setType(parseAndReturnType());
        ad.setFuelType(parseAndReturnFuelType());
        ad.setDamaged(parseAndReturnDamaged());
        ad.setPower(parseAndReturnPower());
        ad.setEngine(parseAndReturnEngine());
        ad.setPrice(parseAndReturnPrice());
        ad.setYear(parseAndReturnYear());
        ad.setMileage(parseAndReturnMileage());
        ad.setKmNotMth(this.kmNotMth);
        ad.setLink(parseAndReturnLink());
        this.ads.add(ad);
    }

    private void parseJsonParamsArray() throws JSONException {
        this.jsonParamsArray = this.jsonAdObject.getJSONArray("params");
    }

    private String parseAndReturnLink() throws JSONException {
        return this.jsonAdObject.getString("url");
    }

    private int parseAndReturnYear() throws JSONException {
        return Integer.parseInt(parseAndReturnParamByName("Rok produkcji").trim());
    }

    private int parseAndReturnMileage() throws JSONException {
        String mileageString = null;
        this.kmNotMth = true;
        try {
            return Integer.parseInt(formatAndReturnMileageString(parseAndReturnParamByName("Przebieg")));
        } catch (Exception e) {
            e.printStackTrace();
            mileageString = parseAndReturnParamByName("Motogodziny");
            this.kmNotMth = false;
            try {
                return Integer.parseInt(formatAndReturnMileageString(mileageString));
            } catch (Exception ex) {
                ex.printStackTrace();
                return -1;
            }
        }
    }

    private int parseAndReturnPrice() throws JSONException {
        String priceString = this.jsonAdObject.getString("list_label");
        if(priceString.contains("EUR")) {
            return (int) (exchangeRates.getExchangeRateToPLNFrom("EUR") * Float.parseFloat(formatAndReturnPriceString(priceString)));
        }
        return (int) Float.parseFloat(formatAndReturnPriceString(priceString));
    }

    private String parseAndReturnModel() throws JSONException {
        return parseAndReturnParamByName("Model");
    }

    private String parseAndReturnVersion() throws JSONException {
        return parseAndReturnParamByName("Wersja");
    }

    private String parseAndReturnType() throws JSONException {
        return parseAndReturnParamByName("Typ");
    }

    private String parseAndReturnFuelType() throws JSONException {
        return parseAndReturnParamByName("Rodzaj paliwa");
    }

    private int parseAndReturnPower() throws JSONException {
        String powerString = formatAndReturnPowerString(parseAndReturnParamByName("Moc"));
        if(powerString.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(powerString);
    }

    private int parseAndReturnEngine() throws JSONException {
        String engineString = formatAndReturnEngineString(parseAndReturnParamByName("Pojemność skokowa"));
        if(engineString.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(engineString);
    }

    private boolean parseAndReturnDamaged() throws JSONException {
        return !parseAndReturnParamByName("Bezwypadkowy").contains("Tak");
    }

    private String parseAndReturnMake() throws JSONException {
        return parseAndReturnParamByName("Marka");
    }

    private String parseAndReturnCategory() throws JSONException {
        return parseAndReturnParamByName("Kategoria");
    }

    private String parseAndReturnSubcategory() throws JSONException {
        return parseAndReturnParamByName("Podkategoria");
    }

    private String parseAndReturnCity() throws JSONException {
        return this.jsonAdObject.getString("city_name");
    }

    private String parseAndReturnId() throws JSONException {
        return this.jsonAdObject.getString("id");
    }

    private String parseAndReturnParamByName(String name) throws JSONException {
        for (int i = 0; i < this.jsonParamsArray.length(); i++) {
            if (parseAndReturnParameterNameByIndex(i).equals(name)) {
                return parseParameterValueByIndex(i);
            }
        }
        return "";
    }

    private String formatAndReturnPowerString(String powerString) {
        return powerString.replaceAll(" ", "").replaceAll("KM", "");
    }

    private String formatAndReturnEngineString(String engineString) {
        return engineString.replaceAll(" ", "").replaceAll("cm3", "");
    }

    private String formatAndReturnMileageString(String mileageString) {
        return mileageString.replaceAll(" ", "").replaceAll("km", "");
    }

    private String formatAndReturnPriceString(String priceString) {
        if(priceString.contains("EUR")) {
            return priceString.replaceAll(" ", "").replaceAll(",", ".").replaceAll("EUR", "");
        }
        return priceString.replaceAll(" ", "").replaceAll(",", ".").replaceAll("PLN", "");
    }

    private String parseParameterValueByIndex(int index) throws JSONException {
        return this.jsonParamsArray.getJSONArray(index).getString(1);
    }

    private String parseAndReturnParameterNameByIndex(int index) throws JSONException {
        return this.jsonParamsArray.getJSONArray(index).getString(0);
    }
}

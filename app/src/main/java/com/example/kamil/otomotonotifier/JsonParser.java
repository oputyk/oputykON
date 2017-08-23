package com.example.kamil.otomotonotifier;

import org.json.JSONException;
import org.json.JSONObject;

interface JsonParser<T> {
    T parse(JSONObject jSONObject) throws JSONException;
}

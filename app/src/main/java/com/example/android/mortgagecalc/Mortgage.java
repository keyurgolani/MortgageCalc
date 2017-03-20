package com.example.android.mortgagecalc;

import android.util.Log;

import com.example.android.utility.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Rushin Naik on 3/17/2017.
 */

public class Mortgage implements Serializable {

    private long id = 0;
    private int type =0;
    private String address = "";
    private String city = "";
    private String state = "";
    private int zip = 0;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private int period = 0;
    private double downpayment = 0.0;
    private double interest = 0.0;
    private double price = 0.0;
    private boolean isValid = true;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(double downpayment) {
        this.downpayment = downpayment;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public double getMortgageAmount() {
        return (price - downpayment)
                * (interest / 1200)
                * Math.pow(1 + (interest / 1200), (period * 12))
                / (Math.pow(1 + (interest / 1200), (period * 12)) - 1);
    }

    public void validate() {
        RequestParams rp = new RequestParams();
        rp.add("address", address + ", " + city + ", " + state + " " + zip);
        rp.add("sensor", "false");

        HttpUtils.post(HttpUtils.getAbsoluteUrl(""), rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    if(serverResp.getString("status").equalsIgnoreCase("OK")) {
                        latitude = serverResp.getJSONArray("results")
                                .getJSONObject(0)
                                .getJSONObject("geometry")
                                .getJSONObject("location")
                                .getDouble("lat");

                        longitude = serverResp.getJSONArray("results")
                                .getJSONObject(0)
                                .getJSONObject("geometry")
                                .getJSONObject("location")
                                .getDouble("lng");
                        isValid = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String toString() {
        return "Street: " + address +
                "\nCity: " + city +
                "\nState: " + state +
                "\nZip: " + zip +
                "\nType: " + type +
                "\nLongitude: " + longitude +
                "\nLatitude: " + latitude +
                "\nPeriod: " + period +
                "\nDownPayment: " + downpayment +
                "\nInterest: " + interest +
                "\nPrice: " + price;
    }
}

package com.example.android.mortgagecalc;

/**
 * Created by Rushin Naik on 3/17/2017.
 */

public class Mortgage {

    private long id = 0;
    private String type ="";
    private String street = "";
    private String city = "";
    private String state = "";
    private int zip = 0;
    private double latitude = 0.0;
    private double longitude = 0.0;


    private int period = 0;
    private double downpayment = 0.0;
    private double interest = 0.0;
    private double price = 0.0;
    private double monthlypayment = 0.0;



    public long getId() {
        return id;
    }

    public double getMonthlypayment(){
        return monthlypayment;
    }

    public String getType(){
        return type;
    }

    public int getZip(){
        return zip;
    }
    public String getStreet(){
        return street;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getPeriod() {
        return period;
    }

    public double getDownpayment() {
        return downpayment;
    }

    public double getInterest() {
        return interest;
    }

    public double getPrice() {
        return price;
    }


    //SETTER

    public void setId(long id) {
        this.id = id;
    }

    public void setMonthlypayment(double xmonthly){
        this.monthlypayment = xmonthly;
    }

    public void setType(String xtype){
        this.type = xtype;

    }

    public void setZip(int xzip){
        this.zip = xzip;
    }

    public void setStreet(String xstreet){
        this.street = xstreet;
    }

    public void setCity(String xcity){
        this.city = xcity;
    }



    public void setState(String xstate){
        this.state = xstate;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setDownpayment(double downpayment) {
        this.downpayment = downpayment;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMortgageAmount() {
        return price * (interest / 12) * Math.pow(1 + (interest / 12), (period * 12) / (Math.pow(1 + (interest / 12), (period * 12)) - 1));
    }

    @Override
    public String toString() {
        return "Street: " + street +
                "\nCity: " + city +
                "\nState: " + state +
                "\nZip: " + zip +
                "\nType: " + type +
                "\nLongitude: " + longitude +
                "\nLatitude: " + latitude +
                "\nPeriod: " + period +
                "\nDownPayment: " + downpayment +
                "\nInterest: " + interest +
                "\nPrice: " + price +
                "\nEMI: " + monthlypayment;
    }
}

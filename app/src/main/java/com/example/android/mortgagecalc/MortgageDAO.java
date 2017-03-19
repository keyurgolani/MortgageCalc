package com.example.android.mortgagecalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.utility.SQLHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keyurgolani on 3/5/17.
 */

public class MortgageDAO {

    private SQLiteDatabase database;
    private SQLHandler dbHelper;
    private String[] allColumns = { SQLHandler.COLUMN_ID,
                                    SQLHandler.COLUMN_ADDRESS,
                                    SQLHandler.COLUMN_TYPE,
                                    SQLHandler.COLUMN_CITY,
                                    SQLHandler.COLUMN_STATE,
                                    SQLHandler.COLUMN_ZIP,
                                    SQLHandler.COLUMN_LATITUDE,
                                    SQLHandler.COLUMN_LONGITUDE,
                                    SQLHandler.COLUMN_PERIOD,
                                    SQLHandler.COLUMN_DOWNPAYMENT,
                                    SQLHandler.COLUMN_INTEREST,
                                    SQLHandler.COLUMN_PRICE
                                    };

    public MortgageDAO(Context context) {
        dbHelper = new SQLHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Mortgage createMortgage(String address,
                                   int type,
                                   String city,
                                   String state,
                                   int zip,
                                   double latitude,
                                   double longitude,
                                   int period,
                                   double downpayment,
                                   double interest,
                                   double price) {
        open();
        ContentValues values = new ContentValues();
        values.put(SQLHandler.COLUMN_ADDRESS, address);
        values.put(SQLHandler.COLUMN_TYPE, type);
        values.put(SQLHandler.COLUMN_CITY, city);
        values.put(SQLHandler.COLUMN_STATE, state);
        values.put(SQLHandler.COLUMN_ZIP, zip);
        values.put(SQLHandler.COLUMN_LATITUDE, latitude);
        values.put(SQLHandler.COLUMN_LONGITUDE, longitude);
        values.put(SQLHandler.COLUMN_PERIOD, period);
        values.put(SQLHandler.COLUMN_DOWNPAYMENT, downpayment);
        values.put(SQLHandler.COLUMN_INTEREST, interest);
        values.put(SQLHandler.COLUMN_PRICE, price);
        long insertId = database.insert(SQLHandler.TABLE_MORTGAGES, null,
                values);
        Cursor cursor = database.query(SQLHandler.TABLE_MORTGAGES,
                allColumns, SQLHandler.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Mortgage newMortgage = cursorToMortgage(cursor);
        cursor.close();
        close();
        return newMortgage;
    }

    public Mortgage createMortgage(Mortgage mortgage) {
        open();
        ContentValues values = new ContentValues();
        values.put(SQLHandler.COLUMN_ADDRESS, mortgage.getAddress());
        values.put(SQLHandler.COLUMN_TYPE, mortgage.getType());
        values.put(SQLHandler.COLUMN_CITY, mortgage.getCity());
        values.put(SQLHandler.COLUMN_STATE, mortgage.getState());
        values.put(SQLHandler.COLUMN_ZIP, mortgage.getZip());
        values.put(SQLHandler.COLUMN_LATITUDE, mortgage.getLatitude());
        values.put(SQLHandler.COLUMN_LONGITUDE, mortgage.getLongitude());
        values.put(SQLHandler.COLUMN_PERIOD, mortgage.getPeriod());
        values.put(SQLHandler.COLUMN_DOWNPAYMENT, mortgage.getDownpayment());
        values.put(SQLHandler.COLUMN_INTEREST, mortgage.getInterest());
        values.put(SQLHandler.COLUMN_PRICE, mortgage.getPrice());
        long insertId = database.insert(SQLHandler.TABLE_MORTGAGES, null,
                values);
        Cursor cursor = database.query(SQLHandler.TABLE_MORTGAGES,
                allColumns, SQLHandler.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Mortgage newMortgage = cursorToMortgage(cursor);
        cursor.close();
        close();
        return newMortgage;
    }

    public void deleteMortgage(Mortgage mortgage) {
        open();
        long id = mortgage.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SQLHandler.TABLE_MORTGAGES, SQLHandler.COLUMN_ID
                + " = " + id, null);
        close();
    }

    public List<Mortgage> getAllMortgages() {
        open();
        List<Mortgage> mortgages = new ArrayList<>();

        Cursor cursor = database.query(SQLHandler.TABLE_MORTGAGES,
                allColumns, null, null,
                null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Mortgage mortgage = cursorToMortgage(cursor);
            mortgages.add(mortgage);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        close();
        return mortgages;
    }

    private Mortgage cursorToMortgage(Cursor cursor) {
        Mortgage mortgage = new Mortgage();
        mortgage.setId(cursor.getLong(0));
        mortgage.setAddress(cursor.getString(1));
        mortgage.setType(cursor.getInt(2));
        mortgage.setCity(cursor.getString(3));
        mortgage.setState(cursor.getString(4));
        mortgage.setZip(cursor.getInt(5));
        mortgage.setLatitude(cursor.getDouble(6));
        mortgage.setLongitude(cursor.getDouble(7));
        mortgage.setPeriod(cursor.getInt(8));
        mortgage.setDownpayment(cursor.getDouble(9));
        mortgage.setInterest(cursor.getDouble(10));
        mortgage.setPrice(cursor.getDouble(11));
        return mortgage;
    }

    public void updateMortgage(Mortgage mortgage) {
        open();
        String strFilter = SQLHandler.COLUMN_ID + "=" + mortgage.getId();
        ContentValues values = new ContentValues();
        values.put(SQLHandler.COLUMN_ADDRESS, mortgage.getAddress());
        values.put(SQLHandler.COLUMN_TYPE, mortgage.getType());
        values.put(SQLHandler.COLUMN_CITY, mortgage.getCity());
        values.put(SQLHandler.COLUMN_STATE, mortgage.getState());
        values.put(SQLHandler.COLUMN_ZIP, mortgage.getZip());
        values.put(SQLHandler.COLUMN_LATITUDE, mortgage.getLatitude());
        values.put(SQLHandler.COLUMN_LONGITUDE, mortgage.getLongitude());
        values.put(SQLHandler.COLUMN_PERIOD, mortgage.getPeriod());
        values.put(SQLHandler.COLUMN_DOWNPAYMENT, mortgage.getDownpayment());
        values.put(SQLHandler.COLUMN_INTEREST, mortgage.getInterest());
        values.put(SQLHandler.COLUMN_PRICE, mortgage.getPrice());
        database.update(SQLHandler.TABLE_MORTGAGES, values, strFilter, null);
        close();
    }
}

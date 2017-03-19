package com.example.android.utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by keyurgolani on 3/5/17.
 */

public class SQLHandler extends SQLiteOpenHelper {

    public static final String TABLE_MORTGAGES = "mortgages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_ZIP = "zip";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_PERIOD = "period";
    public static final String COLUMN_DOWNPAYMENT = "downpayment";
    public static final String COLUMN_INTEREST = "interest";
    public static final String COLUMN_PRICE = "price";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_MORTGAGES + "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_ADDRESS + " text not null, " +
            COLUMN_TYPE + " integer not null, " +
            COLUMN_CITY + " text not null, " +
            COLUMN_STATE + " text not null, " +
            COLUMN_ZIP + " integer not null, " +
            COLUMN_LATITUDE + " real not null, " +
            COLUMN_LONGITUDE + " real not null, " +
            COLUMN_PERIOD + " integer not null, " +
            COLUMN_DOWNPAYMENT + " real not null, " +
            COLUMN_INTEREST + " real not null, " +
            COLUMN_PRICE + " real not null" +
            ");";

    private static final String DATABASE_NAME = "mortgages";
    private static final int DATABASE_VERSION = 1;

    public SQLHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MORTGAGES);
        onCreate(db);
    }
}

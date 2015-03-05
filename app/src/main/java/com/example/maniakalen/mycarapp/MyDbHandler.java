package com.example.maniakalen.mycarapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 167;
    private static final String DATABASE_NAME = "expenses.db";
    public static final String TABLE_EXPENSES = "expenses";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_PROFILE_ID = "profile_id";
    public static final String COLUMN_MILEAGE = "mileage";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_DATETIME = "done_on";
    public static final String COLUMN_AMOUNT = "amount";

    public static final String TABLE_PROFILES = "profiles";
    public static final String COLUMN_PROFILE_ID = "_id";
    public static final String COLUMN_PROFILE_NAME = "name";
    public static final String COLUMN_PROFILE_BRAND = "brand";
    public static final String COLUMN_PROFILE_MODEL = "model";
    public static final String COLUMN_PROFILE_YEAR = "year";
    public static final String COLUMN_PROFILE_PLATE = "plate";
    public static final String COLUMN_PROFILE_PHOTO = "photo";
    public static final String COLUMN_PROFILE_DATE_ADD = "added_on";

    public MyDbHandler(Context context, String name,
                       CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String pt = "CREATE TABLE " +
                TABLE_EXPENSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ID_PROFILE_ID + " REAL,"
                + COLUMN_MILEAGE + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_QUANTITY + " REAL,"
                + COLUMN_AMOUNT + " REAL,"
                + COLUMN_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(pt);

        pt = "CREATE TABLE " +
                TABLE_PROFILES + "("
                + COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PROFILE_NAME + " TEXT,"
                + COLUMN_PROFILE_BRAND + " TEXT,"
                + COLUMN_PROFILE_MODEL + " TEXT,"
                + COLUMN_PROFILE_PLATE + " TEXT,"
                + COLUMN_PROFILE_PHOTO + " TEXT,"
                + COLUMN_PROFILE_YEAR + " REAL,"
                + COLUMN_PROFILE_DATE_ADD + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

        db.execSQL(pt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        onCreate(db);
    }
}
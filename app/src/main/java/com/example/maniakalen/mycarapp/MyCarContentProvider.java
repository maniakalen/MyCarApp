package com.example.maniakalen.mycarapp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyCarContentProvider extends ContentProvider {
    private MyDbHandler myDB;
    private static final String AUTHORITY =
            "com.example.maniakalen.mycarapp";
    private static final String PRODUCTS_TABLE = "expenses";
    private static final String PROFILES_TABLE = MyDbHandler.TABLE_PROFILES;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PRODUCTS_TABLE);
    public static final Uri PROFILE_URI = Uri.parse("content://" + AUTHORITY + "/" + PROFILES_TABLE);

    public static final int PRODUCTS = 1;
    public static final int PRODUCTS_ID = 2;

    public static final int PROFILES = 3;
    public static final int PROFILE_ID = 4;
    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, PRODUCTS_TABLE, PRODUCTS);
        sURIMatcher.addURI(AUTHORITY, PRODUCTS_TABLE + "/#",
                PRODUCTS_ID);
        sURIMatcher.addURI(AUTHORITY, PROFILES_TABLE, PROFILES);
        sURIMatcher.addURI(AUTHORITY, PROFILES_TABLE + "/#",
                PROFILE_ID);
    }
    public MyCarContentProvider() {
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsDeleted = sqlDB.delete(MyDbHandler.TABLE_EXPENSES,
                        selection,
                        selectionArgs);
                break;

            case PRODUCTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MyDbHandler.TABLE_EXPENSES,
                            MyDbHandler.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(MyDbHandler.TABLE_EXPENSES,
                            MyDbHandler.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            case PROFILES:
                rowsDeleted = sqlDB.delete(MyDbHandler.TABLE_PROFILES, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        String table = null;
        long id = 0;
        switch (uriType) {
            case PRODUCTS:
                setValuesTime(values);
                id = sqlDB.insert(MyDbHandler.TABLE_EXPENSES,
                        null, values);
                table = PRODUCTS_TABLE;
                break;
            case PROFILES:
                id = sqlDB.insert(MyDbHandler.TABLE_PROFILES, null, values);
                table = PROFILES_TABLE;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(table + "/" + id);
    }

    @Override
    public boolean onCreate() {
        myDB = new MyDbHandler(getContext(), null, null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();


        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case PRODUCTS_ID:
                queryBuilder.appendWhere(MyDbHandler.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                queryBuilder.setTables(MyDbHandler.TABLE_EXPENSES);
                break;
            case PROFILE_ID:
                queryBuilder.setTables(MyDbHandler.TABLE_PROFILES);
                queryBuilder.appendWhere(MyDbHandler.COLUMN_PROFILE_ID + "=" + uri.getLastPathSegment());
                break;
            case PRODUCTS:
                queryBuilder.setTables(MyDbHandler.TABLE_EXPENSES);
                break;
            case PROFILES:
                queryBuilder.setTables(MyDbHandler.TABLE_PROFILES);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsUpdated = 0;
        String id;
        switch (uriType) {
            case PRODUCTS:
                rowsUpdated = sqlDB.update(MyDbHandler.TABLE_EXPENSES,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PROFILES:
                rowsUpdated = sqlDB.update(MyDbHandler.TABLE_PROFILES,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PRODUCTS_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated =
                            sqlDB.update(MyDbHandler.TABLE_EXPENSES,
                                    values,
                                    MyDbHandler.COLUMN_ID + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(MyDbHandler.TABLE_EXPENSES,
                                    values,
                                    MyDbHandler.COLUMN_ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;

            case PROFILE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated =
                            sqlDB.update(MyDbHandler.TABLE_PROFILES,
                                    values,
                                    MyDbHandler.COLUMN_PROFILE_ID + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(MyDbHandler.TABLE_PROFILES,
                                    values,
                                    MyDbHandler.COLUMN_PROFILE_ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case PRODUCTS:
            case PROFILES:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.peter.cars.mycar";
            case PROFILE_ID:
            case PRODUCTS_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.peter.cars.mycar";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);

        }
    }

    private String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    private void setValuesTime(ContentValues values) {
        if (!values.containsKey(MyDbHandler.COLUMN_DATETIME)) {
            values.put(MyDbHandler.COLUMN_DATETIME, getDateTime(new Date()));
        }
    }
}

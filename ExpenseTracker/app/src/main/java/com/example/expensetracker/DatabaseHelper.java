package com.example.expensetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "mySQLiteHelper";

    //column names for the table.
    public static final String KEY_NAME = "Name";
    public static final String KEY_CATEGORY = "Category";
    public static final String KEY_DATE = "Date";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_NOTE = "Note";


    public static final String KEY_ROWID = "_id";   //required field for the cursorAdapter

    public static final String DATABASE_NAME = "myExpenses.db";
    public static final String TABLE_NAME = "Expenses";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +  //this line is required for the cursorAdapter.
                    KEY_NAME + " TEXT, " +
                    KEY_CATEGORY + " TEXT, " +
                    KEY_DATE + " TEXT, " +
                    KEY_AMOUNT + " REAL, " +
                    KEY_NOTE + " TEXT );";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion
                + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

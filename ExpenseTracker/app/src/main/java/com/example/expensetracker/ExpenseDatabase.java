package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.IOException;

public class ExpenseDatabase {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public ExpenseDatabase(Context ctx){
        helper= new DatabaseHelper(ctx);
    }

    public void open() throws SQLException{
        db = helper.getWritableDatabase();
    }

    public boolean isOpen() throws SQLException{
        return db.isOpen();
    }

    //---closes the database---
    public void close() {
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---insert method---
    public long insertRow(String name, String category, String date, Float amount, String note){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_NAME, name);
        cv.put(DatabaseHelper.KEY_CATEGORY, category);
        cv.put(DatabaseHelper.KEY_DATE, date);
        cv.put(DatabaseHelper.KEY_AMOUNT, amount);
        cv.put(DatabaseHelper.KEY_NOTE, note);
        return db.insert(DatabaseHelper.TABLE_NAME, null, cv);
    }

    public long cpInsertRow(String tableName, ContentValues cv){
        return db.insert(tableName, null, cv);
    }

    public Cursor getAllRows(){
        Cursor c = db.query(DatabaseHelper.TABLE_NAME,
                new String[]{DatabaseHelper.KEY_ROWID,
                        DatabaseHelper.KEY_NAME,
                        DatabaseHelper.KEY_CATEGORY,
                        DatabaseHelper.KEY_DATE,
                        DatabaseHelper.KEY_AMOUNT,
                        DatabaseHelper.KEY_NOTE},
                null, null, null, null,
                DatabaseHelper.KEY_ROWID);  //sort by name.

        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRowWithId(String id){
        Cursor c =
                db.query(true, DatabaseHelper.TABLE_NAME,
                        new String[]{DatabaseHelper.KEY_ROWID,
                                DatabaseHelper.KEY_NAME,
                                DatabaseHelper.KEY_CATEGORY,
                                DatabaseHelper.KEY_DATE,
                                DatabaseHelper.KEY_AMOUNT,
                                DatabaseHelper.KEY_NOTE},
                        DatabaseHelper.KEY_ROWID + "=\'" + id + "\'",
                        null,
                        null,
                        null,
                        null,
                        null);
        if(c!=null){
            c.moveToFirst();
        }
        return c;
    }

    public ExpenseItem getRowArrayAtPosition(int pos){
        Cursor c = getAllRows();
        if(c!=null){
            c.moveToPosition(pos);
        }else {
            Log.d("ExpenseDatabase", "Cursor c is null");
            return null;
        }
        ExpenseItem result = new ExpenseItem(c.getString(c.getColumnIndex(DatabaseHelper.KEY_NAME)),
                c.getString(c.getColumnIndex(DatabaseHelper.KEY_CATEGORY)),
                c.getString(c.getColumnIndex(DatabaseHelper.KEY_DATE)),
                c.getFloat(c.getColumnIndex(DatabaseHelper.KEY_AMOUNT)),
                c.getString(c.getColumnIndex(DatabaseHelper.KEY_NOTE))
        );
        return result;
    }

    public String getIdAtPos(int pos){
        Cursor c = getAllRows();
        if(c!=null){
            c.moveToPosition(pos);
        }else {
            Log.d("ExpenseDatabase", "Cursor c is null");
            return "";
        }
        return c.getString(c.getColumnIndex(DatabaseHelper.KEY_ROWID));
    }

    public int updateRow(String id, String name, String category, String date, Float amount, String note){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.KEY_NAME, name);
        cv.put(DatabaseHelper.KEY_CATEGORY, category);
        cv.put(DatabaseHelper.KEY_DATE, date);
        cv.put(DatabaseHelper.KEY_AMOUNT, amount);
        cv.put(DatabaseHelper.KEY_NOTE, note);
        int result = db.update(DatabaseHelper.TABLE_NAME, cv, DatabaseHelper.KEY_ROWID + "= \'" + id + "\'", null);
        return result;
    }

    public int
    cpDelete(String TableName, String selection, String[] selectionArgs) {
        return db.delete(TableName, selection, selectionArgs);
    }

    //remove all entries from the CurrentBoard
    public void emptyName() {
        db.delete(DatabaseHelper.TABLE_NAME, null, null);
    }


}

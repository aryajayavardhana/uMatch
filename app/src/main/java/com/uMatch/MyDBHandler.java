package com.uMatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "umatch.db";
    private static final String TABLE_NAME = "paymentInfo";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CC = "creditCardNumber";
    private static final String COLUMN_CVV = "CVV";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MATCH = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CC + " VARCHAR(50) NOT NULL, " +
                COLUMN_CVV + " VARCHAR(50) NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_MATCH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private SQLiteDatabase database;

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    private String[] allColumns =
            {COLUMN_ID,COLUMN_CC,COLUMN_CVV};

    public void insertInfo(String CC, String CVV){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CC, CC);
        values.put(COLUMN_CVV, CVV);
        database.insert(TABLE_NAME, null, values);
    }

    public void deleteInfo(long id){
        String filter = "_id="+id;
        database.delete(TABLE_NAME, filter, null);
    }

    public String getCC(long id){
        database = this.getWritableDatabase();
        Cursor result = database.query(TABLE_NAME,allColumns,"_id="+id,null,null,null,null);
        result.moveToFirst();
        String CC = result.getString(1);
        result.close();
        return CC;
    }

    public String getCVV(long id){
        database = this.getWritableDatabase();
        Cursor result = database.query(TABLE_NAME,allColumns,"_id="+id,null,null,null,null);
        result.moveToFirst();
        String CVV = result.getString(2);
        result.close();
        return CVV;
    }

    public String checkDB(){
        database = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = database.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount > 0){
            String filled = "yes";
            return  filled;
        } else {
            String filled = "yno";
            return  filled;
        }
    }
}

package com.anlia.androidbase.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

/**
 * Created by anlia on 2018/1/18.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String FILE_DIR = Environment.getExternalStorageDirectory().getPath() + "/SQLiteTest/";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "test";

    public MySQLiteOpenHelper(Context context, String name){
        this(context, name, null, DATABASE_VERSION);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        File dir = new File(FILE_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try{
            db.execSQL("create table if not exists " + TABLE_NAME +
                    "(id text primary key,name text)");
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(sql);
            onCreate(db);
        }
    }
}

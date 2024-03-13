package com.example.collegeschedulerapp.ui.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VER = 1;
    private static final String DB_NAME = "ToDoDataBase";
    public DBHelper(Context context){
        super(context, DB_NAME,null,DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlDDL = "CREATE TABLE Todo (id INTEGER PRIMARY KEY AUTOINCREMENT, taskname TEXT, duedate TEXT, course TEXT, status INTEGER)";
        db.execSQL(sqlDDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlRecreate = "DROP TABLE IF EXISTS Todo";
        db.execSQL(sqlRecreate);
        onCreate(db);
    }
}
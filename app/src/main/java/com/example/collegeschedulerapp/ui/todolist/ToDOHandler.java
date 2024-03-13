package com.example.collegeschedulerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.collegeschedulerapp.Models.TD;
import com.example.collegeschedulerapp.ui.todolist.DBHelper;

import java.util.ArrayList;

public class ToDOHandler extends DBHelper {

    public ToDOHandler(Context context) {
        super(context);
    }

    //CRUD Operations Create, Read, Update and Delete

    public boolean create (TD td) {
        ContentValues values = new ContentValues();
        values.put("taskname",td.getTaskname());
        values.put("duedate",td.getDuedate());
        values.put("course",td.getCourse());
        values.put("status",td.getStatus());

        SQLiteDatabase Dbase = this.getWritableDatabase();
        boolean isInserted = Dbase.insert("Todo",null,values) > 0 ;
        Dbase.close();
        return isInserted;
    }
    public ArrayList<TD> readtds(int sortby){
        ArrayList<TD> tds = new ArrayList<>();
        String sqlQ = "SELECT * FROM ToDo ORDER BY id ASC";
        if (sortby == 1) {
            sqlQ = "SELECT * FROM ToDo ORDER BY status ASC";
        } else if (sortby == 2) {
            sqlQ = "SELECT * FROM ToDo ORDER BY course ASC";
        } else if(sortby == 3) {
            sqlQ = "SELECT * FROM ToDo ORDER BY duedate ASC";
        } else if(sortby == 4) {
            sqlQ = "SELECT * FROM ToDo ORDER BY taskname ASC";
        }
        //Log.d("sortby", "sortby val - "+sortby);

        SQLiteDatabase Dbase = this.getWritableDatabase();
        Cursor cursor = Dbase.rawQuery(sqlQ,null);

        if(cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String taskname = cursor.getString(cursor.getColumnIndex("taskname"));
                String duedate = cursor.getString(cursor.getColumnIndex("duedate"));
                String course = cursor.getString(cursor.getColumnIndex("course"));
                String statustext = cursor.getString(cursor.getColumnIndex("status"));
                int status = Integer.parseInt(statustext);
                TD td = new TD(taskname,duedate,course,status);
                td.setId(id);
                tds.add(td);
            } while (cursor.moveToNext());
            cursor.close();
            Dbase.close();
        }
        return tds;
    }
    public TD readatd(int id){
        TD td = null;
        String sqlQ = "SELECT * FROM Todo WHERE id = "+id;
        SQLiteDatabase Dbase = this.getWritableDatabase();
        Cursor cursor = Dbase.rawQuery(sqlQ,null);
        if(cursor.moveToFirst()){
            do {
                int tdid = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String taskname = cursor.getString(cursor.getColumnIndex("taskname"));
                String duedate = cursor.getString(cursor.getColumnIndex("duedate"));
                String course = cursor.getString(cursor.getColumnIndex("course"));
                String statustext = cursor.getString(cursor.getColumnIndex("status"));
                //Log.d("readatd",statustext);
                int status = Integer.parseInt(statustext);
                td = new TD(taskname,duedate,course,status);
                td.setId(tdid);
                td.setStatus(status);
            } while (cursor.moveToNext());
            cursor.close();
            Dbase.close();
        }
        return td;
    }
    public boolean upd(TD td){
        ContentValues val = new ContentValues();
        val.put("taskname",td.getTaskname());
        val.put("duedate",td.getDuedate());
        val.put("course",td.getCourse());
        val.put("status",td.getStatus());

        SQLiteDatabase Dbase = this.getWritableDatabase();
        boolean isUpdated = Dbase.update("Todo",val,"id='"+td.getId()+"'",null) > 0;
        Dbase.close();
        return isUpdated;
    }

    public boolean del(int id) {
        boolean isDeleted;
        SQLiteDatabase Dbase = this.getWritableDatabase();
        isDeleted = Dbase.delete("Todo","id='"+id+"'",null) > 0;
        Dbase.close();
        return isDeleted;
    }
}





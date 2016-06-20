package com.example.jegansbeast.fazt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jegansbeast.fazt.subject.Subject;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class Database extends SQLiteOpenHelper {

    public enum DAYS{MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY}
    public Database(Context context) {
        super(context, "fastnote", null, 1);
        Log.d("dbname",getDatabaseName());
    }

    public void recreate(){
        dropTables();
        onCreate(getWritableDatabase());
    }

    public int insertSubject(Subject subject){
        String name = subject.getTitle(), code = subject.getCode();
        if(name!=null && code!=null) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("code", code);
            db.insert("subjects", null, values);

            Cursor cursor = db.rawQuery("select subject_id from subjects where name ='" + name + "' and code='" + code+"'", null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex("subject_id"));
                cursor.close();
                return id;
            }
        }
        return -1;
    }

    public List<Subject> getAllSubjects(){
        List<Subject> subjects = new LinkedList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from subjects";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("subject_id"));
                String sub_name = cursor.getString(cursor.getColumnIndex("name"));
                String sub_code = cursor.getString(cursor.getColumnIndex("code"));
                subjects.add(new Subject(id, sub_name, sub_code));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return subjects;
    }

    public boolean insertSubjectWithDay(DAYS day,Subject subject,int periodno){
        Log.d("insert",subject.getId()+"");
        int id =-1;

        if(subject.getId()!=null)
            id = subject.getId();

        if(id!=-1) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            switch (day){
                case MONDAY:
                    values.put("day_row_id",1);
                    break;
                case TUESDAY:
                    values.put("day_row_id",2);
                    break;
                case WEDNESDAY:
                    values.put("day_row_id",3);
                    break;
                case THURSDAY:
                    values.put("day_row_id",4);
                    break;
                case FRIDAY:
                    values.put("day_row_id",5);
                    break;
                case SATURDAY:
                    values.put("day_row_id",6);
            }
            values.put("subject_id",id );
            values.put("periodno",periodno);

            db.insert("timetable_entry",null,values);
        }
        return false;
    }

    public Subject getSubjectByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from subjects where name ='"+name+"'",null);
        if(cursor!=null) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("subject_id"));
            String sub_name = cursor.getString(cursor.getColumnIndex("name"));
            String sub_code = cursor.getString(cursor.getColumnIndex("code"));
            return new Subject(id,sub_name,sub_code);
        }
        return null;
    }

    public List<Subject> getSubjectsAssigned(DAYS day){
        if(day!=null) {
            List<Subject> subjects = null;
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = null;
            String select = "select subjects.subject_id, name,code,periodno from subjects join " +
                    "timetable_entry on subjects.subject_id=timetable_entry.subject_id and timetable_entry.day_row_id=(select day_row_id from timetable where day='";
            switch (day) {
                case MONDAY:
                    cursor = db.rawQuery(select + "monday')" , null);
                    break;
                case TUESDAY:
                    cursor = db.rawQuery(select + "tuesday')", null);
                    break;
                case WEDNESDAY:
                    cursor = db.rawQuery(select + "wednesday')", null);
                    break;
                case THURSDAY:
                    cursor = db.rawQuery(select + "thursday')" , null);
                    break;
                case FRIDAY:
                    cursor = db.rawQuery(select + "friday')" , null);
                    break;
                case SATURDAY:
                    cursor = db.rawQuery(select + "saturday')", null);
            }

            Log.d("dbday",day.name());

            if (cursor != null && cursor.getCount() > 0) {
                subjects = new LinkedList<>();
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("subject_id"));
                    String sub_name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String sub_code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                    int period = cursor.getInt(cursor.getColumnIndexOrThrow("periodno"));
                    Log.d("db","adding subject "+sub_name+" "+period);
                    Subject subject = new Subject(id, sub_name, sub_code);
                    subject.setPeriodno(period);
                    subjects.add(subject);
                } while (cursor.moveToNext());
                cursor.close();
            }
            if (subjects != null) {
                Collections.sort(subjects, new Comparator<Subject>() {
                    @Override
                    public int compare(Subject lhs, Subject rhs) {
                        return lhs.getPeriodno()<rhs.getPeriodno()?-1:lhs.getPeriodno()==rhs.getPeriodno()?0:1;
                    }
                });
            }
            return subjects;
        }
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table subjects(subject_id integer primary key autoincrement,name text not null,code text)");
        db.execSQL("create table timetable(day text primary key,day_row_id integer )");
        db.execSQL("create table timetable_entry(day_row_id integer ,subject_id integer,periodno integer)");


        ContentValues values = new ContentValues();
        values.put("day","monday");
        values.put("day_row_id",1);
        db.insert("timetable",null,values);

        values.put("day","tuesday");
        values.put("day_row_id",2);
        db.insert("timetable",null,values);

        values.put("day","wednesday");
        values.put("day_row_id",3);
        db.insert("timetable",null,values);

        values.put("day","thursday");
        values.put("day_row_id",4);
        db.insert("timetable",null,values);

        values.put("day","friday");
        values.put("day_row_id",5);
        db.insert("timetable",null,values);

        values.put("day","saturday");
        values.put("day_row_id",6);
        db.insert("timetable",null,values);
    }

    private void dropTables(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + "timetable_entry");
        db.execSQL("DROP TABLE IF EXISTS " + "timetable");
        db.execSQL("DROP TABLE IF EXISTS " + "subjects");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables();
        onCreate(db);
    }


}

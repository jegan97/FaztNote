package com.example.jegansbeast.fazt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.jegansbeast.fazt.subject.Subject;
import com.example.jegansbeast.fazt.timetable.OtherItem;
import com.example.jegansbeast.fazt.timetable.PositionComparable;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public enum DAYS {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}


    public Database(Context context) {
        super(context, "fastnote", null, 1);
        Log.d("dbname", getDatabaseName());
    }

    public void recreate() {
        dropTables();
        onCreate(getWritableDatabase());
    }

    public int insertSubject(Subject subject) {
        String name = subject.getTitle(), code = subject.getCode();
        if (name != null && code != null) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("code", code);
            db.insert("subjects", null, values);

            Cursor cursor = db.rawQuery("select subject_id from subjects where name ='" + name + "' and code='" + code + "'", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndex("subject_id"));
                cursor.close();
                return id;
            }
        }
        return -1;
    }

    public int deleteSubject(int id, String name) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from subjects where subject_id = ? and name = ?";
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, id);
        statement.bindString(2, name);
        return statement.executeUpdateDelete();
    }


    public int deleteSubjectFromTimeTable(Subject subject,int periodno,DAYS day) {
        SQLiteDatabase db = getWritableDatabase();
        int id = subject.getId();
        String query = "delete from timetable_entry where subject_id = ? and periodno = ? and day_row_id = ? ";
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, id);
        statement.bindLong(2,periodno);
        statement.bindLong(3,getDayId(day));
        return statement.executeUpdateDelete();
    }

    public int deleteSubjectFromTimeTable(Subject subject) {
        SQLiteDatabase db = getWritableDatabase();
        int id = subject.getId();
        String query = "delete from timetable_entry where subject_id = ?";
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, id);
        return statement.executeUpdateDelete();
    }

    public int replaceSubject(int id,int periodno,DAYS day){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("update timetable_entry set subject_id = ? where periodno = ? and day_row_id = ?");
        statement.bindLong(1,id);
        statement.bindLong(2,periodno);
        statement.bindLong(3,getDayId(day));

        return statement.executeUpdateDelete();
    }

    public int replaceOtherItem(OtherItem item,int pos,DAYS day){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("update otheritems set otheritem_id = ?,duration=? where pos = ? and day_row_id = ?");
        statement.bindLong(1,item.getId());
        statement.bindLong(2,item.getDuration());
        statement.bindLong(3,pos);
        statement.bindLong(4,getDayId(day));
        return statement.executeUpdateDelete();
    }

    public int getDayId(DAYS day){
        int id;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select day_row_id from timetable where day=?",new String[]{day.name().toLowerCase()},null);
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndexOrThrow("day_row_id"));
            cursor.close();
            return id;
        }

        return -1;
    }

    public int deleteOtherItemFromTimeTable(OtherItem item,DAYS day){
        SQLiteDatabase db = getWritableDatabase();
        int pos = item.getPos();
        String query = "delete from otheritems where pos = ? and day_row_id = ?";
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindLong(1, pos);
        statement.bindLong(2,getDayId(day));
        return statement.executeUpdateDelete();
    }

    public int updateSubject(Subject subject) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("update subjects set name = ?, code = ? where subject_id = ? ");
        statement.bindString(1, subject.getTitle());
        statement.bindString(2, subject.getCode());
        statement.bindLong(3, subject.getId());
        return statement.executeUpdateDelete();
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new LinkedList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from subjects";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
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


    public boolean insertOtherItemWithDay(DAYS day, OtherItem item, int pos) {

        Log.d("insert", item.getName());
        int id = item.getId();

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        switch (day) {
            case MONDAY:
                values.put("day_row_id", 1);
                break;
            case TUESDAY:
                values.put("day_row_id", 2);
                break;
            case WEDNESDAY:
                values.put("day_row_id", 3);
                break;
            case THURSDAY:
                values.put("day_row_id", 4);
                break;
            case FRIDAY:
                values.put("day_row_id", 5);
                break;
            case SATURDAY:
                values.put("day_row_id", 6);
        }

        values.put("otheritem_id", id);
        values.put("pos", pos);
        values.put("duration", item.getDuration());

        db.insert("otheritems", null, values);

        return false;
    }

    public boolean insertSubjectWithDay(DAYS day, Subject subject, int periodno) {
        Log.d("insert", subject.getId() + "");
        int id = -1;

        if (subject.getId() != null)
            id = subject.getId();

        if (id != -1) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            switch (day) {
                case MONDAY:
                    values.put("day_row_id", 1);
                    break;
                case TUESDAY:
                    values.put("day_row_id", 2);
                    break;
                case WEDNESDAY:
                    values.put("day_row_id", 3);
                    break;
                case THURSDAY:
                    values.put("day_row_id", 4);
                    break;
                case FRIDAY:
                    values.put("day_row_id", 5);
                    break;
                case SATURDAY:
                    values.put("day_row_id", 6);
            }
            values.put("subject_id", id);
            values.put("periodno", periodno);

            db.insert("timetable_entry", null, values);
        }
        return false;
    }

    public Subject getSubjectByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from subjects where name ='" + name + "'", null);
        if (cursor != null) {
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex("subject_id"));
            String sub_name = cursor.getString(cursor.getColumnIndex("name"));
            String sub_code = cursor.getString(cursor.getColumnIndex("code"));
            return new Subject(id, sub_name, sub_code);
        }
        return null;
    }


    public List<PositionComparable> getItemsAssigned(DAYS day) {
        List<PositionComparable> itemList = null;

        itemList = new LinkedList<>();
            List<Subject> subjects = getSubjectsAssigned(day);

            if (subjects != null) {
                itemList.addAll(subjects);
                T.logDatabase(itemList.toString());
            }

            List<OtherItem> otherItems = getOtherItemsAssigned(day);

            if (otherItems != null) {
                itemList.addAll(otherItems);
                T.logDatabase("otheritems: " + itemList.toString());
            }

            Collections.sort(itemList, new Comparator<PositionComparable>() {
                @Override
                public int compare(PositionComparable lhs, PositionComparable rhs) {
                    return lhs.getPos() < rhs.getPos() ? -1 : lhs.getPos() == rhs.getPos() ? 0 : 1;
                }
            });

            return itemList;

    }

    public List<OtherItem> getOtherItemsAssigned(DAYS day) {
        if (day != null) {
            List<OtherItem> items = null;
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = null;
            String select = "select otheritem_id,duration,pos from otheritems where day_row_id = (select day_row_id from timetable where day = ? )";

            cursor = db.rawQuery(select, new String[]{day.name().toLowerCase()});

            if (cursor != null && cursor.getCount() > 0) {

                items = new LinkedList<>();

                cursor.moveToFirst();

                do {
                    OtherItem item = new OtherItem();

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("otheritem_id"));
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
                    int pos = cursor.getInt(cursor.getColumnIndexOrThrow("pos"));

                    item.setId(id);
                    item.setDuration(duration);
                    item.setPos(pos);

                    switch (id) {
                        case 1:
                            item.setName("Break");
                            break;
                        case 2:
                            item.setName("Lunch");
                            break;
                    }
                    items.add(item);
                } while (cursor.moveToNext());

                cursor.close();
            }

            if (items != null) {
                Collections.sort(items, new Comparator<OtherItem>() {
                    @Override
                    public int compare(OtherItem lhs, OtherItem rhs) {
                        return lhs.getPos() < rhs.getPos() ? -1 : lhs.getPos() == rhs.getPos() ? 0 : 1;
                    }
                });

                return items;
            }
        }

        return null;
    }

    public List<Subject> getSubjectsAssigned(DAYS day) {
        if (day != null) {
            List<Subject> subjects = null;
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = null;
            String select = "select subjects.subject_id, name,code,periodno from subjects join " +
                    "timetable_entry on subjects.subject_id=timetable_entry.subject_id and timetable_entry.day_row_id=(select day_row_id from timetable where day='";
            switch (day) {
                case MONDAY:
                    cursor = db.rawQuery(select + "monday')", null);
                    break;
                case TUESDAY:
                    cursor = db.rawQuery(select + "tuesday')", null);
                    break;
                case WEDNESDAY:
                    cursor = db.rawQuery(select + "wednesday')", null);
                    break;
                case THURSDAY:
                    cursor = db.rawQuery(select + "thursday')", null);
                    break;
                case FRIDAY:
                    cursor = db.rawQuery(select + "friday')", null);
                    break;
                case SATURDAY:
                    cursor = db.rawQuery(select + "saturday')", null);
            }

            Log.d("dbday", day.name());

            if (cursor != null && cursor.getCount() > 0) {
                subjects = new LinkedList<>();
                cursor.moveToFirst();
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("subject_id"));
                    String sub_name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String sub_code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                    int period = cursor.getInt(cursor.getColumnIndexOrThrow("periodno"));
                    Log.d("db", "adding subject " + sub_name + " " + period);
                    Subject subject = new Subject(id, sub_name, sub_code);
                    subject.setPos(period);
                    subjects.add(subject);
                } while (cursor.moveToNext());
                cursor.close();
            }
            if (subjects != null) {
                Collections.sort(subjects, new Comparator<Subject>() {
                    @Override
                    public int compare(Subject lhs, Subject rhs) {
                        return lhs.getPos() < rhs.getPos() ? -1 : lhs.getPos() == rhs.getPos() ? 0 : 1;
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
        db.execSQL("create table otheritems(otheritem_id integer,duration integer,pos integer,day_row_id integer)");


        ContentValues values = new ContentValues();
        values.put("day", "monday");
        values.put("day_row_id", 1);
        db.insert("timetable", null, values);

        values.put("day", "tuesday");
        values.put("day_row_id", 2);
        db.insert("timetable", null, values);

        values.put("day", "wednesday");
        values.put("day_row_id", 3);
        db.insert("timetable", null, values);

        values.put("day", "thursday");
        values.put("day_row_id", 4);
        db.insert("timetable", null, values);

        values.put("day", "friday");
        values.put("day_row_id", 5);
        db.insert("timetable", null, values);

        values.put("day", "saturday");
        values.put("day_row_id", 6);
        db.insert("timetable", null, values);
    }

    private void dropTables() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + "timetable_entry");
        db.execSQL("DROP TABLE IF EXISTS " + "timetable");
        db.execSQL("DROP TABLE IF EXISTS " + "subjects");
        db.execSQL("DROP TABLE IF EXISTS " + "otheritems");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables();
        onCreate(db);
    }


}

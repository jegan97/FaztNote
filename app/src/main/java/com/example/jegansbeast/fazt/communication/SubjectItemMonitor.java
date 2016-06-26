package com.example.jegansbeast.fazt.communication;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;

import com.example.jegansbeast.fazt.Database;
import com.example.jegansbeast.fazt.subject.Subject;
import com.example.jegansbeast.fazt.timetable.OtherItem;
import com.example.jegansbeast.fazt.timetable.PositionComparable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class SubjectItemMonitor {

    private List<Subject> subjectlist;
    private List<SubjectItemObserver> observers;
    private Database db;

    AlarmManager alarmManager;
    NotificationManager notificationManager;

    private static SubjectItemMonitor subjectmonitor = null;

    public static SubjectItemMonitor getInstance() {
        return subjectmonitor;
    }

    public SubjectItemMonitor(Context c) {
        observers = new LinkedList<>();
        subjectlist = new LinkedList<>();
        db = new Database(c);
        subjectmonitor = this;
        subjectlist = db.getAllSubjects();
        alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        updatelist();
    }


    public void subjectInserted(int pos) {
        int id = db.insertSubject(subjectlist.get(pos));
        subjectlist.get(pos).setId(id);

        for (SubjectItemObserver o : observers) {
            o.subjectInserted(pos);
        }
    }

    public void subjectRemoved(int pos) {
        for (SubjectItemObserver o : observers) {
            o.subjectRemoved(pos);
        }
    }

    public void subjectChanged(int pos) {

        for (SubjectItemObserver o : observers) {
            o.subjectChanged(pos);
        }
    }

    public void deleteSubject(Subject subject) {
        int res = db.deleteSubject(subject.getId(), subject.getTitle());

        if (res > 0) {
            int removedpos = -1;
            db.deleteSubjectFromTimeTable(subject);

            for (int i = 0; i < subjectlist.size(); i++) {
                if (subjectlist.get(i).getId() == subject.getId()) {
                    removedpos = i;
                    subjectlist.remove(i);
                    break;
                }
            }

            if (removedpos != -1) {
                for (SubjectItemObserver observer : observers) {

                    observer.subjectRemoved(removedpos);
                }
            }
        }
    }

    public void deleteItem(PositionComparable object, Database.DAYS day){

        if(object instanceof Subject){
            db.deleteSubjectFromTimeTable((Subject) object, object.getPos(),day);
        }
        else if(object instanceof OtherItem){
            db.deleteOtherItemFromTimeTable((OtherItem) object,day);
        }

    }

    public void updateItem(PositionComparable o, PositionComparable n, Database.DAYS day){

        if(o instanceof Subject && n instanceof Subject){
            db.replaceSubject(((Subject) n).getId(),o.getPos(),day);
        }
        else if(o instanceof OtherItem && n instanceof OtherItem){
            db.replaceOtherItem((OtherItem) n,o.getPos(),day);
        }
        else if(o instanceof Subject && n instanceof OtherItem){
            db.deleteSubjectFromTimeTable((Subject) o,o.getPos(),day);
            db.insertOtherItemWithDay(day, (OtherItem) n,o.getPos());
        }
        else if(o instanceof OtherItem && n instanceof Subject){
            db.deleteOtherItemFromTimeTable((OtherItem) o,day);
            db.insertSubjectWithDay(day, (Subject) n,o.getPos());
        }
    }


    public void updateSubject(Subject subject){
        int res = db.updateSubject(subject);
        if(res>0){
            int changedpos = -1;
            for (int i = 0; i < subjectlist.size(); i++) {
                if(subjectlist.get(i).getId()==subject.getId()){
                     changedpos = i;
                }
            }
            for (SubjectItemObserver observer : observers) {
             observer.subjectChanged(subject);
            observer.subjectChanged(changedpos);
            }
        }
    }

    public List<Subject> getSubjectlist() {
        return subjectlist;
    }

    public void attachObserver(SubjectItemObserver observer) {
        observers.add(observer);
    }

    public void detachObserver(SubjectItemObserver observer) {
        observers.remove(observer);
    }

    public void updatelist() {
        for (SubjectItemObserver o : observers) {
            o.updateList(subjectlist);
        }
    }


    public Database getDb() {
        return db;
    }

    public Subject getSubjectByName(String name) {
        for (Subject subject : subjectlist) {
            if (subject.getTitle().equals(name)) {
                return subject;
            }
        }
        return null;
    }

    public ArrayList<String> getAllSubjectNames() {
        ArrayList<String> list = new ArrayList<>();
        for (Subject subject : subjectlist) {
            if (subject.getTitle() != null) {
                list.add(subject.getTitle());
            }
        }
        return list;

    }

    public void closeDB() {
        db.close();
    }

}

package com.example.jegansbeast.fazt.communication;

import android.content.Context;
import android.util.Log;

import com.example.jegansbeast.fazt.Database;
import com.example.jegansbeast.fazt.subject.Subject;

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

    private static SubjectItemMonitor subjectmonitor = null;

    public static SubjectItemMonitor getInstance() {
        return subjectmonitor;
    }

    public SubjectItemMonitor(Context c){
        observers = new LinkedList<>();
        subjectlist = new LinkedList<>();
        db = new Database(c);
        subjectmonitor = this;
        subjectlist = db.getAllSubjects();
        updatelist();
    }

    public void subjectInserted(int pos){
        int id = db.insertSubject(subjectlist.get(pos));
        Log.d("id",id+" set to "+subjectlist.get(pos).getTitle());
        subjectlist.get(pos).setId(id);

        for (SubjectItemObserver o:observers) {
            o.subjectInserted(pos);
        }
    }

    public void subjectRemoved(int pos){
        for (SubjectItemObserver o:observers) {
            o.subjectRemoved(pos);
        }
    }

    public void subjectChanged(int pos){

        for (SubjectItemObserver o:observers) {
            o.subjectChanged(pos);
        }
    }

    public List<Subject> getSubjectlist() {
        Log.d("size","sublist size "+subjectlist.size());
//        for (Subject subject : subjectlist) {
//          Log.d("adding",subject.getName());
//        }
        return subjectlist;
    }

    public void attachObserver(SubjectItemObserver observer){
        observers.add(observer);
    }

    public void detachObserver(SubjectItemObserver observer){
        observers.remove(observer);
    }

    public void updatelist() {
        for (SubjectItemObserver o:observers) {
            o.updateList(subjectlist);
        }
    }

    public Database getDb() {
        return db;
    }

    public Subject getSubjectByName(String name){
        for (Subject subject : subjectlist) {
            if(subject.getTitle().equals(name)){
                return subject;
            }
        }
        return null;
    }

    public ArrayList<String> getAllSubjectNames(){
        ArrayList<String> list = new ArrayList<>();
        for (Subject subject : subjectlist) {
            if(subject.getTitle()!=null) {
                list.add(subject.getTitle());
                Log.d("getallnames",subject.getTitle());
            }
        }
        return list;

    }

    public void closeDB(){
        db.close();
    }

}

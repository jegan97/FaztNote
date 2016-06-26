package com.example.jegansbeast.fazt.timetable.inputdialog;

import com.example.jegansbeast.fazt.subject.Subject;
import com.example.jegansbeast.fazt.timetable.OtherItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JEGAN'S BEAST on 6/23/2016.
 */
public class ItemDumper {

    private List<Subject> subjectList;
    private List<OtherItem> otherItems;

    public ItemDumper() {
        subjectList = new ArrayList<>();
        otherItems = new ArrayList<>();
    }

    void putSubjectList(List<Subject> list){
        subjectList.addAll(list);
    }

    void putOtherItemsList(List<OtherItem> list){
        otherItems.addAll(list);
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public List<OtherItem> getOtherItemsList() {
        return otherItems;
    }
}

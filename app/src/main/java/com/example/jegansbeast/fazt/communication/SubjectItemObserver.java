package com.example.jegansbeast.fazt.communication;

import com.example.jegansbeast.fazt.subject.Subject;

import java.util.List;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public interface SubjectItemObserver {
    void subjectChanged(int pos);
    void subjectInserted(int pos);
    void subjectRemoved(int pos);
    void updateList(List<Subject> subjectlist);
}

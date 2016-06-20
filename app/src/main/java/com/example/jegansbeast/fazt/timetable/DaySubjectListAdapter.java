package com.example.jegansbeast.fazt.timetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jegansbeast.fazt.Database;
import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.example.jegansbeast.fazt.subject.Subject;

import java.util.List;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class DaySubjectListAdapter extends RecyclerView.Adapter<DaySubjectListHolder> {

    SubjectItemMonitor monitor;
    List<Subject> subjectList;
    Database.DAYS day;

    public DaySubjectListAdapter(Database.DAYS day) {
        this.day = day;
        monitor = SubjectItemMonitor.getInstance();
//        Log.d("db",day.name());
        this.subjectList = monitor.getDb().getSubjectsAssigned(day) ;
    }


    @Override
    public DaySubjectListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_subject_row,parent,false);
        return new DaySubjectListHolder(view);
    }

    @Override
    public void onBindViewHolder(DaySubjectListHolder holder, int position) {
        holder.setSubjectDetails(subjectList.get(position));
    }

    @Override
    public int getItemCount() {
        if(subjectList!=null)
            return subjectList.size();

        return 0;
    }

    public void refreshList(){
        subjectList = monitor.getDb().getSubjectsAssigned(day);
        notifyDataSetChanged();
    }
}

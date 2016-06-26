package com.example.jegansbeast.fazt.timetable.inputdialog;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private SubjectItemMonitor monitor;
    private List<Subject> subjectList;
    Database.DAYS day;
    SubjectSelectionInteraction interactor;


    public ViewAdapter(SubjectSelectionInteraction interactor,Database.DAYS day){
        monitor = SubjectItemMonitor.getInstance();
        subjectList = monitor.getSubjectlist();
        Log.d("subject",subjectList.size()+"");
        this.day = day;
        this.interactor = interactor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_subject_input_row,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactor.selected(holder.subject);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setSubjectDetails(subjectList.get(position));
        Subject subject = subjectList.get(position);
        Log.d("subject added",subject.getTitle());
        holder.setSubjectDetails(subject);
    }


    @Override
    public int getItemCount() {
        return subjectList.size();
    }
    public interface SubjectSelectionInteraction{
        void selected(Object subject);
    }
}



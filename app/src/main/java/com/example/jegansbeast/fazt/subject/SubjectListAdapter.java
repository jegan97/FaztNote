package com.example.jegansbeast.fazt.subject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.communication.SubjectItemObserver;

import java.util.List;

/**
 * Created by JEGAN'S BEAST on 6/17/2016.
 */
public class SubjectListAdapter extends RecyclerView.Adapter<SubjectRowHolder> implements SubjectItemObserver {

    private List<Subject> list;

    public SubjectListAdapter(List<Subject> subjectList){
        list = subjectList;
    }

    @Override
    public SubjectRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_row,parent,false);
        return new SubjectRowHolder(parent.getContext(),v);
    }

    @Override
    public void onBindViewHolder(SubjectRowHolder holder, int position) {
        holder.setTitle(list.get(position).getTitle());
        holder.setCode(list.get(position).getCode());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void subjectChanged(int pos) {

    }

    @Override
    public void subjectInserted(int pos) {

    }

    @Override
    public void subjectRemoved(int pos) {

    }

    @Override
    public void updateList(List<Subject> subjectlist) {

    }
}

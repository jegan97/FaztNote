package com.example.jegansbeast.fazt.subject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.example.jegansbeast.fazt.communication.SubjectItemObserver;

import java.util.List;

/**
 * Created by JEGAN'S BEAST on 6/17/2016.
 */
public class SubjectListAdapter extends RecyclerView.Adapter<SubjectRowHolder> implements SubjectItemObserver,View.OnLongClickListener,View.OnClickListener {

    private List<Subject> list;

    public SubjectListAdapter(List<Subject> subjectList){
        SubjectItemMonitor.getInstance().attachObserver(this);
        list = subjectList;
    }

    @Override
    public SubjectRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_row,parent,false);
        SubjectRowHolder holder = new SubjectRowHolder(parent.getContext(),v);
        v.setTag(holder);
        v.setOnLongClickListener(this);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SubjectRowHolder holder, int position) {
        holder.setTitle(list.get(position).getTitle());
        holder.setCode(list.get(position).getCode());
        holder.setSubject(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void subjectChanged(int pos) {
        notifyItemChanged(pos);
    }

    @Override
    public void subjectChanged(Subject subject) {

    }


    @Override
    public void subjectInserted(int pos) {

    }

    @Override
    public void subjectRemoved(int pos) {
        notifyItemRemoved(pos);
    }

    @Override
    public void removedSubjectId(int id) {

    }

    @Override
    public void updateList(List<Subject> subjectlist) {

    }

    @Override
    public boolean onLongClick(View view) {
        SubjectRowHolder holder = (SubjectRowHolder) view.getTag();
        holder.setSelected(true);
        return true;
    }

    @Override
    public void onClick(View view) {
            SubjectRowHolder holder = (SubjectRowHolder) view.getTag();
            if (holder.isSelected())
                holder.setSelected(false);
            else {

            }
    }
}

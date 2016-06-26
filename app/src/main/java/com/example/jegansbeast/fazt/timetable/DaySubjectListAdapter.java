package com.example.jegansbeast.fazt.timetable;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jegansbeast.fazt.Database;
import com.example.jegansbeast.fazt.MainActivity;
import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.T;
import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.example.jegansbeast.fazt.communication.SubjectItemObserver;
import com.example.jegansbeast.fazt.subject.Subject;

import java.util.List;
import java.util.Objects;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class DaySubjectListAdapter extends SelectableAdapter implements SubjectItemObserver
{

    SubjectItemMonitor monitor;
    public List<PositionComparable> itemList;
    Database.DAYS day;
    Context context;
    String start_time;
    int duration;
    TimePrefs prefs;

    ClickListener clickListener;

    Toolbar toolbar ;



    SharedPreferences preferences;

    public DaySubjectListAdapter(Context context, Database.DAYS day, ClickListener clickListener) {

        this.clickListener = clickListener;
        this.day = day;
        this.context = context;

        monitor = SubjectItemMonitor.getInstance();

        refreshList();

        T.logDatabase(itemList.toString());
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        start_time = preferences.getString("start_time", "empty");
        duration = Integer.parseInt(preferences.getString("duration", "e"));


        toolbar = (Toolbar) ((MainActivity)context).findViewById(R.id.toolbar);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_subject_row, parent, false);
                DaySubjectListHolder daySubjectListHolder =  new DaySubjectListHolder(view,clickListener);
                view.setTag(daySubjectListHolder);
                return daySubjectListHolder;
            case 2:
                View otheritemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_otheritems_row, parent, false);
                OtherItemHolder otherItemHolder =  new OtherItemHolder(otheritemview,clickListener);
                otheritemview.setTag(otherItemHolder);
                return otherItemHolder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = itemList.get(position);

        if (obj instanceof Subject) {

            Subject subject = (Subject) obj;

            if (position == 0) {
                subject.setStartTime(start_time);
                subject.setEndTime(duration);
            }
            else{
                subject.setStartTime(itemList.get(position-1).getEndTime());
                subject.setEndTime(duration);
            }

            ((DaySubjectListHolder) holder).setSubjectDetails(subject);
            ((DaySubjectListHolder) holder).setSelected(isSelected(position));

        }

        else if (obj instanceof OtherItem) {
            OtherItem item = (OtherItem) obj;

            if (position == 0) {
                item.setStartTime(start_time);
                item.setEndTime(item.getDuration());
            }
            else{
                item.setStartTime(itemList.get(position-1).getEndTime());
                item.setEndTime(item.getDuration());
            }

            ((OtherItemHolder) holder).setItem(item);
            ((OtherItemHolder) holder).setSelected(isSelected(position));
        }
    }

    @Override
    public int getItemCount() {
        if (itemList != null)
            return itemList.size();

        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Subject)
            return 1;
        else
            return 2;
    }

    public void refreshList() {
        itemList = monitor.getDb().getItemsAssigned(day);
        int posbinder =0;
        for (int i = 0; i < itemList.size(); i++) {
            Object obj = itemList.get(i);
            if(obj instanceof Subject){
                Subject subject = (Subject) obj;
                subject.setListpos(i+1-posbinder);
            }
            else if(obj instanceof  OtherItem){
                posbinder++;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void subjectChanged(int pos) {

    }

    @Override
    public void subjectChanged(Subject subject) {

        for (int i = 0; i < itemList.size(); i++) {
            Object o = itemList.get(i);
            if (o instanceof Subject) {
                Subject subject1 = (Subject) o;
                if (Objects.equals(subject1.getId(), subject.getId())) {
                    subject1.setTitle(subject.getTitle());
                    subject1.setCode(subject.getCode());
                    notifyItemChanged(i);
                    break;
                }
            }
        }

    }

    @Override
    public void subjectInserted(int pos) {

    }

    @Override
    public void subjectRemoved(int pos) {
    }

    @Override
    public void removedSubjectId(int id) {
        int expiredsubject = -1;
        for (int i = 0; i < itemList.size(); i++) {
            Object o = itemList.get(i);
            if (o instanceof Subject) {
                Subject subject = (Subject) o;
                if (subject.getId() == id) {
                    expiredsubject = i;
                    break;
                }
            }
        }
        if (expiredsubject != -1) {
            itemList.remove(expiredsubject);
            notifyItemRemoved(expiredsubject);
        }
    }

    @Override
    public void updateList(List<Subject> subjectlist) {

    }




}

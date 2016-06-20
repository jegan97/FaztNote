package com.example.jegansbeast.fazt.timetable;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jegansbeast.fazt.Database;
import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.example.jegansbeast.fazt.subject.Subject;
import com.example.jegansbeast.fazt.timetable.inputdialog.ListItemDecoration;
import com.example.jegansbeast.fazt.timetable.inputdialog.ViewAdapter;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class DayFragment extends Fragment implements ViewAdapter.SubjectSelectionInteraction {

    private Database.DAYS day;

    RecyclerView list;
    FloatingActionButton add;
    Dialog dialog = null;
    DayFragment dayFragment = this;
    DaySubjectListAdapter adapter;


    public DayFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day,container,false);
        list = (RecyclerView) view.findViewById(R.id.day_list);
        add = (FloatingActionButton) view.findViewById(R.id.day_add);

        adapter = new DaySubjectListAdapter(day);
        list.setAdapter(adapter);
        list.addItemDecoration(new ListItemDecoration(getContext(),R.drawable.subjects_divider));
        list.setLayoutManager(new LinearLayoutManager(getContext()));



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View input = LayoutInflater.from(getContext()).inflate(R.layout.day_subject_input,null);
                RecyclerView recyclerView = (RecyclerView) input.findViewById(R.id.input_day_subject_list);
                recyclerView.setAdapter(new ViewAdapter(dayFragment,day));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                recyclerView.addItemDecoration(new ListItemDecoration(getContext(),R.drawable.input_subjects_divider));

                builder.setView(input);
                builder.setTitle("Select Subject");

                builder.setCancelable(true);
                dialog = builder.create();
                dialog.show();
            }
        });
        return view;

    }

    public Database.DAYS getDay() {
        return day;
    }

    public void setDay(Database.DAYS day) {
        this.day = day;

    }

    @Override
    public void selected(Subject subject) {
        if(dialog!=null){
            if(subject!=null){
                SubjectItemMonitor.getInstance().getDb().insertSubjectWithDay(day,subject,adapter.getItemCount()+1);
            }
            adapter.refreshList();
            dialog.cancel();
        }
        dialog = null;
    }
}

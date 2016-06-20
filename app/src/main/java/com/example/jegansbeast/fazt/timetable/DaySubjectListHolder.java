package com.example.jegansbeast.fazt.timetable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.subject.Subject;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class DaySubjectListHolder extends RecyclerView.ViewHolder {
   TextView pos,name,code;
    public DaySubjectListHolder(View itemView) {
        super(itemView);
        pos = (TextView) itemView.findViewById(R.id.pos);
        name = (TextView) itemView.findViewById(R.id.name);
        code = (TextView) itemView.findViewById(R.id.code);
    }

    public void setSubjectDetails(Subject subject){
        pos.setText(String.valueOf(subject.getPeriodno()));
        name.setText(subject.getTitle());
        code.setText(subject.getCode());
    }

}

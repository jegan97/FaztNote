package com.example.jegansbeast.fazt.timetable.inputdialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.subject.Subject;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    Subject subject;
    private int periodno;
    TextView textView, code;


    public ViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.sub_name);
        code = (TextView) itemView.findViewById(R.id.sub_code);
    }

    public void setSubjectDetails(Subject subject) {
        this.subject = subject;
        textView.setText(subject.getTitle());
        code.setText(subject.getCode());
    }

    public int getPeriodno() {
        return periodno;
    }

    public void setPeriodno(int periodno) {
        this.periodno = periodno;
    }
}

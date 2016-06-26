package com.example.jegansbeast.fazt.timetable;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.subject.Subject;

/**
 * Created by JEGAN'S BEAST on 6/18/2016.
 */
public class DaySubjectListHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
   TextView pos,name,code,start,end;
    ClickListener clickListener;
    LinearLayout layout;

    public DaySubjectListHolder(View itemView,ClickListener clickListener) {
        super(itemView);
        layout = (LinearLayout) itemView.findViewById(R.id.layout);
        pos = (TextView) itemView.findViewById(R.id.pos);
        name = (TextView) itemView.findViewById(R.id.name);
        code = (TextView) itemView.findViewById(R.id.code);
        start = (TextView) itemView.findViewById(R.id.sub_end);
        end = (TextView) itemView.findViewById(R.id.sub_start);
        this.clickListener = clickListener;
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
    }

    public void setSubjectDetails(Subject subject){

        name.setText(subject.getTitle());

        code.setText(subject.getCode());

        TimePrefs timePrefs = new TimePrefs();


        start.setText(timePrefs.getReadableTime(subject.getStarthour(),subject.getStartmin()));

        end.setText(timePrefs.getReadableTime(subject.getEndhour(),subject.getEndmin()));

        pos.setText(String.valueOf(subject.getListpos()));
    }

    public void setSelected(boolean selected){
        if(selected)
            layout.setBackgroundColor(Color.parseColor("#5990DE"));
        else
            layout.setBackgroundColor(Color.parseColor("#E1BEE7"));
    }

    @Override
    public void onClick(View v) {
        if(clickListener!=null){
            clickListener.onItemClicked(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(clickListener!=null){
            return clickListener.onItemLongClicked(getAdapterPosition());
        }
        return false;
    }


}

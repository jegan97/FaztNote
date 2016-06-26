package com.example.jegansbeast.fazt.timetable;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jegansbeast.fazt.R;

/**
 * Created by JEGAN'S BEAST on 6/25/2016.
 */
public class OtherItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    TextView title,start,end;
    OtherItem item;
    ClickListener clickListener;
    RelativeLayout layout;

    public OtherItemHolder(View itemView ,ClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        title = (TextView) itemView.findViewById(R.id.title);
        end = (TextView) itemView.findViewById(R.id.sub_end);
        start = (TextView) itemView.findViewById(R.id.sub_start);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItem(OtherItem item){
        this.item = item;
        title.setText(item.getName());
        TimePrefs timePrefs = new TimePrefs();
        start.setText(timePrefs.getReadableTime(item.getStarthour(),item.getStartmin()));
        end.setText(timePrefs.getReadableTime(item.getEndhour(),item.getEndmin()));
    }

    public void setSelected(boolean selected){
        if(selected)
            layout.setBackgroundColor(Color.parseColor("#5990DE"));
        else
            layout.setBackgroundColor(Color.parseColor("#5a6890"));
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

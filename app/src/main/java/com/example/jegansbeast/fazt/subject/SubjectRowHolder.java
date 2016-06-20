package com.example.jegansbeast.fazt.subject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jegansbeast.fazt.R;

/**
 * Created by JEGAN'S BEAST on 6/17/2016.
 */
public class SubjectRowHolder extends RecyclerView.ViewHolder {
private Context context;

    TextView icon,title,code;
    public SubjectRowHolder(Context context,View itemView) {
        super(itemView);

        this.context =context;

        title = (TextView) itemView.findViewById(R.id.subject_title);
        code = (TextView) itemView.findViewById(R.id.subject_code);
        icon = (TextView) itemView.findViewById(R.id.icon);

    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setCode(String code){
        this.code.setText(code);
    }

}

package com.example.jegansbeast.fazt.subject;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by JEGAN'S BEAST on 6/17/2016.
 */
public class SubjectRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private Context context;

     TextView icon,title,code,message;
     ImageButton edit,delete;
    private LinearLayout layout;
    boolean selected=false;
    Subject subject;

    public SubjectRowHolder(Context context,View itemView) {
        super(itemView);
        this.context =context;
        layout = (LinearLayout) itemView.findViewById(R.id.layout);
        title = (TextView) itemView.findViewById(R.id.subject_title);
        code = (TextView) itemView.findViewById(R.id.subject_code);
        icon = (TextView) itemView.findViewById(R.id.icon);
        message = (TextView) itemView.findViewById(R.id.message);
        edit = (ImageButton) itemView.findViewById(R.id.edit);
        delete = (ImageButton) itemView.findViewById(R.id.delete);

        edit.setImageDrawable(new IconicsDrawable(context).icon(FontAwesome.Icon.faw_pencil).sizeDp(30).color(Color.BLACK));
        delete.setImageDrawable(new IconicsDrawable(context).icon(FontAwesome.Icon.faw_times).sizeDp(30).color(Color.RED));
        edit.setTag("edit");
        delete.setTag("delete");
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);

        message.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setCode(String code){
        this.code.setText(code);
    }

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
        if(selected){
            layout.setBackgroundColor(Color.parseColor("#F0F4C3"));
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }
        else{
            layout.setBackgroundColor(Color.parseColor("#ffffff"));
            edit.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }
    }

    public void setMessageCount(int count){
        if(count==0)
            message.setVisibility(View.INVISIBLE);
        else {
            message.setText(String.valueOf(count));
            message.setVisibility(View.VISIBLE);
        }
    }

    public boolean isSelected(){
        return selected;
    }


    @Override
    public void onClick(View v) {
        if(v.getTag() instanceof String){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            if(v.getTag().equals("edit")){

                Dialog dialog;

                builder.setCancelable(true);
                View view = LayoutInflater.from(context).inflate(R.layout.subject_input_dialog,null);
                final EditText title = (EditText) view.findViewById(R.id.sub_title);
                final EditText code = (EditText) view.findViewById(R.id.sub_code);

                title.setText(subject.getTitle());
                code.setText(subject.getCode());
                builder.setView(view);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newtitle = title.getText().toString();
                        String newcode = code.getText().toString();

                        if(!subject.getTitle().equals(newtitle)||!subject.getCode().equals(newcode)){
                           subject.setTitle(newtitle);
                            subject.setCode(newcode);
                            SubjectItemMonitor.getInstance().updateSubject(subject);
                        }
                        if(dialog!=null) {
                            setSelected(false);
                            dialog.dismiss();
                        }
                    }

                });

                dialog = builder.create();
                dialog.show();


            }
            else if(v.getTag().equals("delete")){
                builder.setTitle("Are you sure?");
                builder.setMessage("This will also delete this subject from timetable");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubjectItemMonitor.getInstance().deleteSubject(subject);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }


        }
    }

}

package com.example.jegansbeast.fazt.timetable.inputdialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.T;
import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.example.jegansbeast.fazt.communication.SubjectItemObserver;
import com.example.jegansbeast.fazt.subject.Subject;
import com.example.jegansbeast.fazt.timetable.OtherItem;
import com.example.jegansbeast.fazt.timetable.PositionComparable;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;


public class ItemSelectorAdapter extends BaseExpandableListAdapter implements AdapterView.OnItemSelectedListener
        ,ExpandableListView.OnChildClickListener
        ,RadioGroup.OnCheckedChangeListener
        ,SubjectItemObserver
        ,View.OnClickListener{

    private Context context;
    private ItemDumper dumper;
    private SubjectItemMonitor monitor;
    ArrayAdapter<CharSequence> durationlist;
    Drawable tick;
    int other_item_duration = -1;
    RadioGroup radioGroup;
    ItemSelectionInteraction interactor;
    int mode;//insert or update

    public ItemSelectorAdapter(Context context,ItemSelectionInteraction interactor,int mode) {
        monitor = SubjectItemMonitor.getInstance();
        monitor.attachObserver(this);
        this.mode = mode;
        this.interactor = interactor;
        this.context = context;
        dumper = new ItemDumper();
        tick = new IconicsDrawable(context).icon(FontAwesome.Icon.faw_check).color(Color.parseColor("#4caf50")).sizeDp(35);

        dumper.putSubjectList(monitor.getSubjectlist());

        List<OtherItem> list = new ArrayList<>();
        list.add(new OtherItem("Break"));
        list.add(new OtherItem("Lunch"));

        dumper.putOtherItemsList(list);

        durationlist = ArrayAdapter.createFromResource(context, R.array.interval_duration_list, android.R.layout.simple_spinner_item);
        durationlist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return dumper.getSubjectList().size();
            case 1:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return dumper.getSubjectList();
            case 1:
                return dumper.getOtherItemsList();
            default:
                return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                return dumper.getSubjectList().get(childPosition);
            case 1:
                return dumper.getOtherItemsList().get(childPosition);
            default:
                return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (groupPosition * 10) + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.day_input_parent_view, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);

        switch (groupPosition) {
            case 0:
                title.setText("Subjects");
                break;
            case 1:
                title.setText("Others");
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        switch (groupPosition) {
            case 0:
                if(convertView==null)
                    convertView = LayoutInflater.from(context).inflate(R.layout.day_subject_input_row, parent, false);
                TextView name = (TextView) convertView.findViewById(R.id.sub_name);
                TextView code = (TextView) convertView.findViewById(R.id.sub_code);
                Subject subject = dumper.getSubjectList().get(childPosition);
                name.setText(subject.getTitle());
                code.setText(subject.getCode());
                break;
            case 1:
                if(convertView == null)
                    convertView = LayoutInflater.from(context).inflate(R.layout.day_otheritems_input_row, parent, false);

                if(radioGroup==null) {
                    radioGroup = (RadioGroup) convertView.findViewById(R.id.itemgroup);
                }

                radioGroup.setOnCheckedChangeListener(this);
                Button button = (Button) convertView.findViewById(R.id.okbutton);
                button.setOnClickListener(this);
                Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);
                spinner.setAdapter(durationlist);
                spinner.setOnItemSelectedListener(this);
                break;

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        if(groupPosition==0)
            return true;

        return true;
    }

    @Override
    public int getChildTypeCount() {
        return 2;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            case 0:
                other_item_duration =-1;
                break;
            case 1:
                other_item_duration = 5;
                break;
            case 2:
                other_item_duration = 10;
                break;
            case 3:
                other_item_duration = 15;
                break;
            case 4:
                other_item_duration = 20;
                break;
            case 5:
                other_item_duration = 25;
                break;
            case 6:
                other_item_duration = 30;
                break;
            case 7:
                other_item_duration = 60;
                break;
            case 8:
                other_item_duration = 120;
                break;
        }

        T.toastShort(context,"item selected "+position+" duration set to "+other_item_duration);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        T.toastShort(context,"clicked "+groupPosition+" "+childPosition);
        if(groupPosition==0){
            interactor.selected(dumper.getSubjectList().get(childPosition),mode);
            T.toastShort(context,dumper.getSubjectList().get(childPosition).getTitle()+" is inserted");
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        radioGroup = group;
    }

    @Override
    public void subjectChanged(int pos) {
        notifyDataSetChanged();
    }

    @Override
    public void subjectChanged(Subject subject) {
        notifyDataSetChanged();
    }


    @Override
    public void subjectInserted(int pos) {
        notifyDataSetChanged();
    }

    @Override
    public void subjectRemoved(int pos) {
        notifyDataSetChanged();
    }

    @Override
    public void removedSubjectId(int id) {

    }

    @Override
    public void updateList(List<Subject> subjectlist) {
        dumper.putSubjectList(subjectlist);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(radioGroup!=null){
            int id = radioGroup.getCheckedRadioButtonId();

            if(id!=-1) {
                if (other_item_duration != -1) {
                    OtherItem item = new OtherItem();
                    switch (id){
                        case R.id.day_break:
                            item.setName("Break");
                            break;
                        case R.id.lunch:
                            item.setName("Lunch");
                            break;
                    }
                    item.setDuration(other_item_duration);
                    interactor.selected(item,mode);
                }
                else {
                    T.toastShort(context, "select duration");
                }
            }
            else {
                T.toastShort(context,"select any item");
            }
        }
    }

    public interface ItemSelectionInteraction{
        void selected(PositionComparable object, int mode);
    }
}

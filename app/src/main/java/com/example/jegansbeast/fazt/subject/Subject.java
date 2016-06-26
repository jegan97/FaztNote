package com.example.jegansbeast.fazt.subject;

import com.example.jegansbeast.fazt.timetable.PositionComparable;
import com.example.jegansbeast.fazt.timetable.TimePrefs;

/**
 * Created by JEGAN'S BEAST on 6/17/2016.
 */
public class Subject implements PositionComparable{
    private String title,code;
    private Integer id=-1;
    private Integer periodno = 0,listpos=0;

    private String starttime,endtime;
    private int starthour,endhour;
    private int startmin,endmin;
    TimePrefs prefs;

    public Subject(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public Subject(int id, String sub_name, String sub_code) {
        this.id = id;
        title = sub_name;
        code = sub_code;
    }

    public Subject(int id, String sub_name, String sub_code,int period) {
        this.id = id;
        title = sub_name;
        code = sub_code;
        periodno = period;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getPos() {
        return periodno;
    }

    public String getStarttime() {
        return starttime;
    }

    @Override
    public String getEndTime() {
        return endtime;
    }

    public void setPos(int periodno) {
        this.periodno = periodno;
    }

    public int getStarthour() {
        return starthour;
    }

    public int getEndhour() {
        return endhour;
    }

    public int getEndmin() {
        return endmin;
    }

    public int getStartmin() {
        return startmin;
    }

    public void setStartTime(String time) {
        if(time!=null){
            prefs= new TimePrefs(time);

            starthour = prefs.getHour();
            startmin = prefs.getMin();

//            String z = " am";
//            if(starthour>12){
//                starthour -= 12;
//                z=" pm";
//            }
                starttime = pad(starthour)+":"+pad(startmin);

        }
    }

    public void setEndTime(int duration){
        endmin = startmin + duration;
        int h=0;
        if(endmin>59){
            h = endmin/60;
            endmin = endmin - (h*60);
        }
        endhour = starthour + h;


        endtime =  pad(endhour)+":"+pad(endmin);
    }

    protected String pad(int time){
        if(time>9)
            return String.valueOf(time);
        else
            return "0"+time;
    }

    public Integer getListpos() {
        return listpos;
    }

    public void setListpos(Integer listpos) {
        this.listpos = listpos;
    }
}

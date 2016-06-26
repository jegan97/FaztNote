package com.example.jegansbeast.fazt.timetable;

/**
 * Created by JEGAN'S BEAST on 6/23/2016.
 */
public class OtherItem implements PositionComparable{
    private String name;
    private int duration=-1;
    private Integer id;
    private Integer pos;


    private String starttime,endtime;
    private int starthour,endhour;
    private int startmin,endmin;
    TimePrefs prefs;

    public OtherItem(){

    }

    public OtherItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.equals("Break"))
            setId(1);
        else if(name.equals("Lunch"))
            setId(2);
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getPos() {
        return pos;
    }

    public String getStarttime(){
        return starttime;
    }

    @Override
    public String getEndTime() {
        return endtime;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
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
        this.starttime = time;
        if(time!=null){
            prefs= new TimePrefs(starttime);
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
//        String z = " am";
//        if(endhour>12){
//            endhour -= 12;
//            z=" pm";
//        }
        endtime =  pad(endhour)+":"+pad(endmin);
    }

    protected String pad(int time){
        if(time>9)
            return String.valueOf(time);
        else
            return "0"+time;
    }
}

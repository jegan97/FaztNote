package com.example.jegansbeast.fazt.timetable;

/**
 * Created by JEGAN'S BEAST on 6/25/2016.
 */
public class TimePrefs {
    private int hour,min;


    public TimePrefs(){}


    public TimePrefs(String time){
        if(time!=null){
            parseTime(time);
        }
    }

    public int getHour() {
        return hour;
    }


    public int getMin() {
        return min;
    }



    protected void parseTime(String time){
        String[] parts = time.split(":");
       try {
           int h = Integer.parseInt(parts[0]);
           int m = Integer.parseInt(parts[1]);
           hour = h;
           min = m;
       }catch (NumberFormatException e){
           hour = -1;
           min = -1;
       }
    }


    public String getReadableTime(int h,int m){
        StringBuilder stringBuilder = new StringBuilder();

        String z=" am";

        if(m>59){
            int eh = m/60;
            h += eh;
            m -= (eh*60);
        }
        if(h>11){
            if(h>12)
                h -= 12;
            z = " pm";
        }

        stringBuilder.append(pad(h));
        stringBuilder.append(":");
        stringBuilder.append(pad(m));
        stringBuilder.append(z);

        return stringBuilder.toString();
    }

    public String pad(int t){
        if(t<10)
            return "0"+t;
        else
            return String.valueOf(t);
    }
}

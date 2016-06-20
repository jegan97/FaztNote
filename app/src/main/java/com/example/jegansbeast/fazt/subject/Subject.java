package com.example.jegansbeast.fazt.subject;

/**
 * Created by JEGAN'S BEAST on 6/17/2016.
 */
public class Subject {
    private String title,code;
    private Integer id=-1;
    private int periodno = 0;

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

    public int getPeriodno() {
        return periodno;
    }

    public void setPeriodno(int periodno) {
        this.periodno = periodno;
    }
}

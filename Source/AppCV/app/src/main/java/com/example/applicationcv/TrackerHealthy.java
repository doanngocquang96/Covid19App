package com.example.applicationcv;

public class TrackerHealthy {
    private int type;
    private String name;
    private String time;
    private String relate;
    private String desc;

    public TrackerHealthy() {
    }

    public TrackerHealthy(int iType, String iName, String iTime, String iRelate, String iDesc) {
        this.type = iType;
        this.name = iName;
        this.time = iTime;
        this.relate = iRelate;
        this.desc = iDesc;

    }

    public int getType(){
        return this.type;
    }

    public void setType(int iType){
        this.type = iType;
    }

    public String getRelate(){
        return this.relate;
    }

    public void setRelate(String iRelate){
        this.time = iRelate;
    }

    public String getTime(){
        return this.time;
    }

    public void setTime(String iTime){
        this.time = iTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String iName) {
        this.name = iName;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String iDesc) {
        this.desc = iDesc;
    }
}

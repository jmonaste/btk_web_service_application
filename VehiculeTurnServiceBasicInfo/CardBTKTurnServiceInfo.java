package com.introtoandroid.samplematerial.VehiculeTurnServiceBasicInfo;


public class CardBTKTurnServiceInfo {

    //Turn Service Info
    private long id;
    private String subLine;
    private String serviceCode;
    private String startTime;
    private String finishTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubLine() {
        return subLine;
    }

    public void setSubLine(String subLine) {
        this.subLine = subLine;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}

package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class SubLine implements Parcelable{

    //SubLine information
    private int subLineID;
    private String subLineCode;
    private String subLineName;
    private String subLineColor;
    private String subLineKM;
    private Line line;
    private List<SubLineRoutePoints> subLineRoutePoints;

    public SubLine(int subLineID, String subLineCode, String subLineName, String subLineColor, String subLineKM, Line line, List<SubLineRoutePoints> subLineRoutePoints) {
        this.subLineID = subLineID;
        this.subLineCode = subLineCode;
        this.subLineName = subLineName;
        this.subLineColor = subLineColor;
        this.subLineKM = subLineKM;
        this.line = line;
        this.subLineRoutePoints = subLineRoutePoints;
    }
    public SubLine() {
        this.subLineID = 0;
        this.subLineCode = "";
        this.subLineName = "";
        this.subLineColor = "";
        this.subLineKM = "";
        this.line = null;
        this.subLineRoutePoints = null;
    }
    public SubLine(Parcel input){
        subLineID = input.readInt();
        subLineCode = input.readString();
        subLineName = input.readString();
        subLineColor = input.readString();
        subLineKM = input.readString();
        line = input.readParcelable(Line.class.getClassLoader());
        //subLineRoutePoints = (List<SubLineRoutePoints>) input.readParcelable(SubLineRoutePoints.class.getClassLoader());
        //input.readList(subLineRoutePoints, SubLineRoutePoints.class.getClassLoader());
        subLineRoutePoints = new ArrayList<SubLineRoutePoints>();
        input.readTypedList(subLineRoutePoints, SubLineRoutePoints.CREATOR);
    }


    public int getSubLineID() {
        return subLineID;
    }
    public void setSubLineID(int subLineID) {
        this.subLineID = subLineID;
    }
    public String getSubLineCode() {
        return subLineCode;
    }
    public void setSubLineCode(String subLineCode) {
        this.subLineCode = subLineCode;
    }
    public String getSubLineName() {
        return subLineName;
    }
    public void setSubLineName(String subLineName) {
        this.subLineName = subLineName;
    }
    public String getSubLineColor() {
        return subLineColor;
    }
    public void setSubLineColor(String subLineColor) {
        this.subLineColor = subLineColor;
    }
    public String getSubLineKM() {
        return subLineKM;
    }
    public void setSubLineKM(String subLineKM) {
        this.subLineKM = subLineKM;
    }
    public Line getLine() {
        return line;
    }
    public void setLine(Line line) {
        this.line = line;
    }
    public List<SubLineRoutePoints> getSubLineRoutePoints() {
        return subLineRoutePoints;
    }
    public void setSubLineRoutePoints(List<SubLineRoutePoints> subLineRoutePoints) {
        this.subLineRoutePoints = subLineRoutePoints;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subLineID);
        dest.writeString(subLineCode);
        dest.writeString(subLineName);
        dest.writeString(subLineColor);
        dest.writeString(subLineKM);
        dest.writeParcelable(line, 0);
        dest.writeTypedList(subLineRoutePoints);
    }

    public static final Parcelable.Creator<SubLine> CREATOR
            = new Parcelable.Creator<SubLine>(){
        public SubLine createFromParcel(Parcel in){
            return new SubLine(in);
        }
        public SubLine[] newArray(int size){
            return new SubLine[size];
        }
    };
}




package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class Line implements Parcelable{

    //SubLine information
    private int lineID;
    private String lineCode;
    private String lineName;
    private String lineDescription;
    private Company lineCompany;

    public Line(int lineID, String lineCode, String lineName, String lineDescription, Company lineCompany) {
        this.lineID = lineID;
        this.lineCode = lineCode;
        this.lineName = lineName;
        this.lineDescription = lineDescription;
        this.lineCompany = lineCompany;
    }
    public Line() {
        this.lineID = 0;
        this.lineCode = "0";
        this.lineName = "";
        this.lineDescription = "";
        this.lineCompany = null;
    }
    public Line(Parcel input){
        lineID = input.readInt();
        lineCode = input.readString();
        lineName = input.readString();
        lineDescription = input.readString();
        lineCompany = input.readParcelable(Company.class.getClassLoader());
    }

    public int getLineID() {
        return lineID;
    }
    public void setLineID(int lineID) {
        this.lineID = lineID;
    }
    public String getLineCode() {
        return lineCode;
    }
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }
    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    public String getLineDescription() {
        return lineDescription;
    }
    public void setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
    }
    public Company getLineCompany() {
        return lineCompany;
    }
    public void setLineCompany(Company lineCompany) {
        this.lineCompany = lineCompany;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lineID);
        dest.writeString(lineCode);
        dest.writeString(lineName);
        dest.writeString(lineDescription);
        dest.writeParcelable(lineCompany, 0);
    }

    public static final Parcelable.Creator<Line> CREATOR
            = new Parcelable.Creator<Line>(){
        public Line createFromParcel(Parcel in){
            return new Line(in);
        }
        public Line[] newArray(int size){
            return new Line[size];
        }
    };
}




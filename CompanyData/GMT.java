package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmonaste on 15/03/2017.
 */
public class GMT implements Parcelable{

    //GMT information
    private int gmtID;
    private String gmtValue;

    //CONSTRUCTORS
    public GMT() {
        this.gmtID = 0;
        this.gmtValue = "";
    }
    public GMT(int gmtID, String gmtValue) {
        this.gmtID = gmtID;
        this.gmtValue = gmtValue;
    }
    public GMT(Parcel input){
        gmtID = input.readInt();
        gmtValue = input.readString();
    }

    //GET & SET METHODS
    public int getGmtID() {

        return gmtID;
    }
    public String getGmtValue() {

        return gmtValue;
    }
    public void setGmtID(int gmtID) {

        this.gmtID = gmtID;
    }
    public void setGmtValue(String gmtValue) {

        this.gmtValue = gmtValue;
    }


    //PARCELABLE INTERFACE METHODS
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gmtID);
        dest.writeString(gmtValue);
    }
    public static final Parcelable.Creator<GMT> CREATOR
            = new Parcelable.Creator<GMT>(){
        public GMT createFromParcel(Parcel in){
            return new GMT(in);
        }

        public GMT[] newArray(int size){
            return new GMT[size];
        }
    };
}




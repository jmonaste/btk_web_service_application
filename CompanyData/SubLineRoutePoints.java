package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class SubLineRoutePoints implements Parcelable{

    //SubLine information
    private int subLineRoutePointsID;
    private String latitude;
    private String longitude;
    private String subLineID;

    public SubLineRoutePoints(int subLineRoutePointsID, String latitude, String longitude, String subLineID) {
        this.subLineRoutePointsID = subLineRoutePointsID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.subLineID = subLineID;
    }
    public SubLineRoutePoints() {
        this.subLineRoutePointsID = 0;
        this.latitude = "0";
        this.longitude = "0";
        this.subLineID = "0";
    }

    public SubLineRoutePoints(Parcel input){
        subLineRoutePointsID = input.readInt();
        latitude = input.readString();
        longitude = input.readString();
        subLineID = input.readString();
    }

    public int getSubLineRoutePointsID() {
        return subLineRoutePointsID;
    }
    public void setSubLineRoutePointsID(int subLineRoutePointsID) {
        this.subLineRoutePointsID = subLineRoutePointsID;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getSubLineID() {
        return subLineID;
    }
    public void setSubLineID(String subLineID) {
        this.subLineID = subLineID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(subLineRoutePointsID);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(subLineID);
    }

    public static final Parcelable.Creator<SubLineRoutePoints> CREATOR
            = new Parcelable.Creator<SubLineRoutePoints>(){
        public SubLineRoutePoints createFromParcel(Parcel in){
            return new SubLineRoutePoints(in);
        }

        public SubLineRoutePoints[] newArray(int size){
            return new SubLineRoutePoints[size];
        }
    };

}




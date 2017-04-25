package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class Service implements Parcelable{

    private int serviceID;
    private String ServiceCode;
    private String serviceStartTime;
    private String serviceEndTime;
    private SubLine subLine;
    private boolean mondayService;
    private boolean tuesdayService;
    private boolean wednesdayService;
    private boolean thursdayService;
    private boolean fridayService;
    private boolean saturdayService;
    private boolean sundayService;

    public Service(int serviceID, String ServiceCode, String serviceStartTime,
                   String serviceEndTime, SubLine subLine, boolean mondayService,
                   boolean tuesdayService, boolean wednesdayService, boolean thursdayService,
                   boolean fridayService, boolean saturdayService, boolean sundayService) {

        this.serviceID = serviceID;
        this.ServiceCode = ServiceCode;
        this.serviceStartTime = serviceStartTime;
        this.serviceEndTime = serviceEndTime;
        this.subLine = subLine;
        this.mondayService = mondayService;
        this.tuesdayService = tuesdayService;
        this.wednesdayService = wednesdayService;
        this.thursdayService = thursdayService;
        this.fridayService = fridayService;
        this.saturdayService = saturdayService;
        this.sundayService = sundayService;
    }
    public Service() {

        this.serviceID = 0;
        this.ServiceCode = "";
        this.serviceStartTime = "";
        this.serviceEndTime = "";
        this.subLine = null;
        this.mondayService = false;
        this.tuesdayService = false;
        this.wednesdayService = false;
        this.thursdayService = false;
        this.fridayService = false;
        this.saturdayService = false;
        this.sundayService = false;
    }
    public Service(Parcel input){
        serviceID = input.readInt();
        ServiceCode = input.readString();
        serviceStartTime = input.readString();
        serviceEndTime = input.readString();
        subLine = input.readParcelable(SubLine.class.getClassLoader());
        mondayService = input.readByte() != 0; //mondayService == true if byte != 0
        tuesdayService = input.readByte() != 0;
        wednesdayService = input.readByte() != 0;
        thursdayService = input.readByte() != 0;
        fridayService = input.readByte() != 0;
        saturdayService = input.readByte() != 0;
        sundayService = input.readByte() != 0;

    }

    public int getServiceID() {
        return serviceID;
    }
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }
    public String getServiceCode() {
        return ServiceCode;
    }
    public void setServiceCode(String serviceCode) {
        this.ServiceCode = serviceCode;
    }
    public String getServiceStartTime() {
        return serviceStartTime;
    }
    public void setServiceStartTime(String serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }
    public String getServiceEndTime() {
        return serviceEndTime;
    }
    public void setServiceEndTime(String serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }
    public SubLine getSubLine() {
        return subLine;
    }
    public void setSubLine(SubLine subLine) {
        this.subLine = subLine;
    }
    public boolean isMondayService() {
        return mondayService;
    }
    public void setMondayService(boolean mondayService) {
        this.mondayService = mondayService;
    }
    public boolean isTuesdayService() {
        return tuesdayService;
    }
    public void setTuesdayService(boolean tuesdayService) {
        this.tuesdayService = tuesdayService;
    }
    public boolean isWednesdayService() {
        return wednesdayService;
    }
    public void setWednesdayService(boolean wednesdayService) {
        this.wednesdayService = wednesdayService;
    }
    public boolean isThursdayService() {
        return thursdayService;
    }
    public void setThursdayService(boolean thursdayService) {
        this.thursdayService = thursdayService;
    }
    public boolean isFridayService() {
        return fridayService;
    }
    public void setFridayService(boolean fridayService) {
        this.fridayService = fridayService;
    }
    public boolean isSaturdayService() {
        return saturdayService;
    }
    public void setSaturdayService(boolean saturdayService) {
        this.saturdayService = saturdayService;
    }
    public boolean isSundayService() {
        return sundayService;
    }
    public void setSundayService(boolean sundayService) {
        this.sundayService = sundayService;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(serviceID);
        dest.writeString(ServiceCode);
        dest.writeString(serviceStartTime);
        dest.writeString(serviceEndTime);
        dest.writeParcelable(subLine, 0);

        dest.writeByte((byte) (mondayService ? 1 : 0)); //if mondayService == true, byte == 1
        dest.writeByte((byte) (tuesdayService ? 1 : 0));
        dest.writeByte((byte) (wednesdayService ? 1 : 0));
        dest.writeByte((byte) (thursdayService ? 1 : 0));
        dest.writeByte((byte) (fridayService ? 1 : 0));
        dest.writeByte((byte) (saturdayService ? 1 : 0));
        dest.writeByte((byte) (sundayService ? 1 : 0));
    }

    public static final Parcelable.Creator<Service> CREATOR
            = new Parcelable.Creator<Service>(){
        public Service createFromParcel(Parcel in){
            return new Service(in);
        }
        public Service[] newArray(int size){
            return new Service[size];
        }
    };
}




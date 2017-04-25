package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class VehicleGroup implements Parcelable{

    //Vehicle Group information
    private int groupID;
    private String groupName;
    private String groupFollowerProfile;
    private int groupCompanyID;
    private GMT groupGMT;


    public VehicleGroup() {
        this.groupID = 0;
        this.groupName = "";
        this.groupFollowerProfile = "";
        this.groupCompanyID = 0;
        this.groupGMT = null;

    }
    public VehicleGroup(int groupID, String groupName, String groupFollowerProfile,
                        int groupCompanyID, GMT groupGMT) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupFollowerProfile = groupFollowerProfile;
        this.groupCompanyID = groupCompanyID;
        this.groupGMT = groupGMT;
    }
    public VehicleGroup(Parcel input){
        groupID = input.readInt();
        groupName = input.readString();
        groupFollowerProfile = input.readString();
        groupCompanyID = input.readInt();
        groupGMT = input.readParcelable(GMT.class.getClassLoader());
    }

    //GET & SET METHODS
    public int getGroupID() {

        return groupID;
    }
    public void setGroupID(int groupID) {

        this.groupID = groupID;
    }
    public String getGroupName() {

        return groupName;
    }
    public void setGroupName(String groupName) {

        this.groupName = groupName;
    }
    public String getGroupFollowerProfile() {

        return groupFollowerProfile;
    }
    public void setGroupFollowerProfile(String groupFollowerProfile) {

        this.groupFollowerProfile = groupFollowerProfile;
    }
    public int getGroupCompanyID() {

        return groupCompanyID;
    }
    public void setGroupCompanyID(int groupCompanyID) {

        this.groupCompanyID = groupCompanyID;
    }
    public GMT getGroupGMT() {

        return groupGMT;
    }
    public void setGroupGMT(GMT groupGMT) {

        this.groupGMT = groupGMT;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groupID);
        dest.writeString(groupName);
        dest.writeString(groupFollowerProfile);
        dest.writeInt(groupCompanyID);
        dest.writeParcelable(groupGMT, 0);
    }
    public static final Parcelable.Creator<VehicleGroup> CREATOR
            = new Parcelable.Creator<VehicleGroup>(){
        public VehicleGroup createFromParcel(Parcel in){
            return new VehicleGroup(in);
        }

        public VehicleGroup[] newArray(int size){
            return new VehicleGroup[size];
        }
    };
}




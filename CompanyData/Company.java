package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class Company implements Parcelable{

    private int companyID;
    private String companyCode;
    private String companyName;
    private String companyDDBBScheme;
    private String companyServerDir;
    private String companyDDBBUser;
    private String companyDDBBPasword;
    private String companyDDBBPort;
    private List<VehicleGroup> companyGroups;

    public Company() {
        this.companyID = 0;
        this.companyCode = "";
        this.companyName = "";
        this.companyDDBBScheme = "";
        this.companyServerDir = "";
        this.companyDDBBUser = "";
        this.companyDDBBPasword = "";
        this.companyDDBBPort = "";
        this.companyGroups = null;
    }

    public Company(int companyID, String companyCode, String companyName,
                   String companyDDBBScheme, String companyServerDir,
                   String companyDDBBUser, String companyDDBBPasword, String companyDDBBPort,
                   List<VehicleGroup> companyGroups) {

        this.companyID = companyID;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.companyDDBBScheme = companyDDBBScheme;
        this.companyServerDir = companyServerDir;
        this.companyDDBBUser = companyDDBBUser;
        this.companyDDBBPasword = companyDDBBPasword;
        this.companyDDBBPort = companyDDBBPort;
        this.companyGroups = companyGroups;
    }


    public Company(Parcel input){
        companyID = input.readInt();
        companyCode = input.readString();
        companyName = input.readString();
        companyDDBBScheme = input.readString();
        companyServerDir = input.readString();
        companyDDBBUser = input.readString();
        companyDDBBPasword = input.readString();
        companyDDBBPort = input.readString();

        companyGroups = new ArrayList<VehicleGroup>();
        input.readTypedList(companyGroups, VehicleGroup.CREATOR);

        //input.readList(companyGroups, VehicleGroup.class.getClassLoader());
    }

    //GET & SET METHODS
    public int getCompanyID() {

        return companyID;
    }
    public String getCompanyCode() {

        return companyCode;
    }
    public String getCompanyName() {

        return companyName;
    }
    public String getCompanyDDBBScheme() {

        return companyDDBBScheme;
    }
    public String getCompanyServerDir() {

        return companyServerDir;
    }
    public String getCompanyDDBBUser() {

        return companyDDBBUser;
    }
    public String getCompanyDDBBPasword() {

        return companyDDBBPasword;
    }
    public String getCompanyDDBBPort() {

        return companyDDBBPort;
    }
    public List<VehicleGroup> getCompanyGroups() {

        return companyGroups;
    }
    public void setCompanyID(int companyID) {

        this.companyID = companyID;
    }
    public void setCompanyCode(String companyCode) {

        this.companyCode = companyCode;
    }
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
    public void setCompanyDDBBScheme(String companyDDBBScheme) {

        this.companyDDBBScheme = companyDDBBScheme;
    }
    public void setCompanyServerDir(String companyServerDir) {

        this.companyServerDir = companyServerDir;
    }
    public void setCompanyDDBBUser(String companyDDBBUser) {

        this.companyDDBBUser = companyDDBBUser;
    }
    public void setCompanyDDBBPasword(String companyDDBBPasword) {

        this.companyDDBBPasword = companyDDBBPasword;
    }
    public void setCompanyDDBBPort(String companyDDBBPort) {

        this.companyDDBBPort = companyDDBBPort;
    }
    public void setCompanyGroups(List<VehicleGroup> companyGroups) {

        this.companyGroups = companyGroups;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(companyID);
        dest.writeString(companyCode);
        dest.writeString(companyName);
        dest.writeString(companyDDBBScheme);
        dest.writeString(companyServerDir);
        dest.writeString(companyDDBBUser);
        dest.writeString(companyDDBBPasword);
        dest.writeString(companyDDBBPort);
        dest.writeTypedList(companyGroups);
    }

    public static final Parcelable.Creator<Company> CREATOR
            = new Parcelable.Creator<Company>(){
        public Company createFromParcel(Parcel in){
            return new Company(in);
        }
        public Company[] newArray(int size){
            return new Company[size];
        }
    };
}




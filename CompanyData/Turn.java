package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import static java.io.FileDescriptor.in;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class Turn implements Parcelable{

    //Turn information
    private int turnID;
    private String turnCode;
    private String turnDescription;
    private List<TurnService> turnServices;
    private Company turnCompany;

    public Turn() {

        //Turn information
        this.turnID = 0;
        this.turnCode = "";
        this.turnDescription = "";
        this.turnServices = null;
        this.turnCompany = null;

    }

    public Turn(int turnID, String turnCode, String turnDescription,
                List<TurnService> turnServices, Company turnCompany) {

        //Turn information
        this.turnID = turnID;
        this.turnCode = turnCode;
        this.turnDescription = turnDescription;
        this.turnServices = turnServices;
        this.turnCompany = turnCompany;
    }

    public Turn(Parcel input){
        turnID = input.readInt();
        turnCode = input.readString();
        turnDescription = input.readString();

        turnServices = new ArrayList<TurnService>();
        input.readTypedList(turnServices, TurnService.CREATOR);
        //input.readList(turnServices, TurnService.class.getClassLoader());
        turnCompany = input.readParcelable(Company.class.getClassLoader());
    }

    public int getTurnID() {

        return turnID;
    }
    public String getTurnCode() {

        return turnCode;
    }
    public String getTurnDescription() {

        return turnDescription;
    }
    public List<TurnService> getTurnServices() {

        return turnServices;
    }
    public Company getTurnCompany() {

        return turnCompany;
    }
    public void setTurnID(int turnID) {

        this.turnID = turnID;
    }
    public void setTurnCode(String turnCode) {

        this.turnCode = turnCode;
    }
    public void setTurnDescription(String turnDescription) {

        this.turnDescription = turnDescription;
    }
    public void setTurnServices(List<TurnService> turnServices) {

        this.turnServices = turnServices;
    }
    public void setTurnCompany(Company turnCompany) {

        this.turnCompany = turnCompany;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(turnID);
        dest.writeString(turnCode);
        dest.writeString(turnDescription);
        dest.writeTypedList(turnServices);
        dest.writeParcelable(turnCompany, 0);
    }

    public static final Parcelable.Creator<Turn> CREATOR
            = new Parcelable.Creator<Turn>(){
        public Turn createFromParcel(Parcel in){
            return new Turn(in);
        }
        public Turn[] newArray(int size){
            return new Turn[size];
        }
    };
}




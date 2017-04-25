package com.introtoandroid.samplematerial.CompanyData;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jmonaste on 15/03/2017.
 */
public class TurnService implements Parcelable{

    //Turn Service information
    private int turnServiceID;
    private Service turnService;
    private int serviceOrder;

    public TurnService(int turnServiceID, Service turnService, int serviceOrder) {
        this.turnServiceID = turnServiceID;
        this.turnService = turnService;
        this.serviceOrder = serviceOrder;
    }
    public TurnService() {
        turnServiceID = 0;
        this.turnService = null;
        serviceOrder = 0;
    }
    public TurnService(Parcel input){
        turnServiceID = input.readInt();
        turnService = input.readParcelable(Service.class.getClassLoader());
        serviceOrder = input.readInt();
    }

    public int getTurnServiceID() {
        return turnServiceID;
    }
    public void setTurnServiceID(int turnServiceID) {
        this.turnServiceID = turnServiceID;
    }
    public Service getTurnService() {
        return turnService;
    }
    public void setTurnService(Service turnService) {
        this.turnService = turnService;
    }
    public int getServiceOrder() {
        return serviceOrder;
    }
    public void setServiceOrder(int serviceOrder) {
        this.serviceOrder = serviceOrder;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(turnServiceID);
        dest.writeParcelable(turnService, 0);
        dest.writeInt(serviceOrder);
    }

    public static final Parcelable.Creator<TurnService> CREATOR
            = new Parcelable.Creator<TurnService>(){
        public TurnService createFromParcel(Parcel in){
            return new TurnService(in);
        }
        public TurnService[] newArray(int size){
            return new TurnService[size];
        }
    };
}




package com.introtoandroid.samplematerial;


public class CardBTKVehiculeBasicInfo {

    private long id;
    private String vehiculeCode;
    private String vehiculeName;
    private String vehiculeRegNumber;
    private String vehiculeType;
    private int colorResource;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVehiculeCode() {
        return vehiculeCode;
    }

    public void setVehiculeCode(String vehiculeCode) {
        this.vehiculeCode = vehiculeCode;
    }

    public String getVehiculeName() {
        return vehiculeName;
    }

    public void setVehiculeName(String vehiculeName) {
        this.vehiculeName = vehiculeName;
    }

    public String getVehiculeRegNumber() {
        return vehiculeRegNumber;
    }

    public void setVehiculeRegNumber(String vehiculeRegNumber) {
        this.vehiculeRegNumber = vehiculeRegNumber;
    }

    public String getVehiculeType() {
        return vehiculeType;
    }

    public void setVehiculeType(String vehiculeType) {
        this.vehiculeType = vehiculeType;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }
}

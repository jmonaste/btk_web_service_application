package com.introtoandroid.samplematerial.VehiculeTurnInfo;


import com.introtoandroid.samplematerial.CompanyData.Company;
import com.introtoandroid.samplematerial.CompanyData.Turn;
import com.introtoandroid.samplematerial.CompanyData.TurnService;

import java.util.List;

public class CardBTKVehiculeTurnInfo {

    //Vehicule Info
    private long id;
    private String vehiculeCode;
    private String vehiculeName;
    private String vehiculeRegNumber;
    private String vehiculeType;
    private int colorResource;
    //Turn information
    private int Identificador_Turno;
    private String Codigo_Turno;
    private String Descripcion_Turno;
    private Turn Turno;
    private String Empresa_Turno;

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

    public int getIdentificador_Turno() {
        return Identificador_Turno;
    }

    public void setIdentificador_Turno(int identificador_Turno) {
        Identificador_Turno = identificador_Turno;
    }

    public String getCodigo_Turno() {
        return Codigo_Turno;
    }

    public void setCodigo_Turno(String codigo_Turno) {
        Codigo_Turno = codigo_Turno;
    }

    public String getDescripcion_Turno() {
        return Descripcion_Turno;
    }

    public void setDescripcion_Turno(String descripcion_Turno) {
        Descripcion_Turno = descripcion_Turno;
    }

    public String getEmpresa_Turno() {
        return Empresa_Turno;
    }

    public void setEmpresa_Turno(String empresa_Turno) {
        Empresa_Turno = empresa_Turno;
    }

    public Turn getTurn() {
        return Turno;
    }

    public void setTurn(Turn turn) {
        Turno = turn;
    }
}

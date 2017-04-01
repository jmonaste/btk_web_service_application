package com.introtoandroid.samplematerial.CompanyData;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class VehicleType {

    //Vehicle Type information
    private int Identificador_TipoVehiculo;
    private Company Empresa_TipoVehiculo;
    private String Nombre_TipoVehiculo;

    public VehicleType() {

        //Vehicle Type information
        this.Identificador_TipoVehiculo = 0;
        this.Empresa_TipoVehiculo = null;
        this.Nombre_TipoVehiculo = "";

    }

    public VehicleType(int Identificador_TipoVehiculo, Company Empresa_TipoVehiculo, String Nombre_TipoVehiculo) {

        //Vehicle Type information
        this.Identificador_TipoVehiculo = Identificador_TipoVehiculo;
        this.Empresa_TipoVehiculo = Empresa_TipoVehiculo;
        this.Nombre_TipoVehiculo = Nombre_TipoVehiculo;
    }

    //Métodos GET y SET

    public int getIdentificador_TipoVehiculo() {
        return Identificador_TipoVehiculo;
    }

    public void setIdentificador_TipoVehiculo(int identificador_TipoVehiculo) {
        Identificador_TipoVehiculo = identificador_TipoVehiculo;
    }

    public Company getEmpresa_TipoVehiculo() {
        return Empresa_TipoVehiculo;
    }

    public void setEmpresa_TipoVehiculo(Company empresa_TipoVehiculo) {
        Empresa_TipoVehiculo = empresa_TipoVehiculo;
    }

    public String getNombre_TipoVehiculo() {
        return Nombre_TipoVehiculo;
    }

    public void setNombre_TipoVehiculo(String nombre_TipoVehiculo) {
        Nombre_TipoVehiculo = nombre_TipoVehiculo;
    }
}




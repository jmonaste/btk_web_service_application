package com.introtoandroid.samplematerial.CompanyData;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class Vehicle {

    //Vehicle information
    private int Identificador_Vehiculo;
    private VehicleGroup Grupo_Vehiculo;
    private String Nombre_Vehiculo;
    private String Codigo_Vehiculo;
    private String Matricula_Vehiculo;
    private VehicleType TipoVehiculo_Vehiculo;

    public Vehicle() {

        //Vehicle information
        this.Identificador_Vehiculo = 0;
        this.Grupo_Vehiculo = null;
        this.Nombre_Vehiculo = "";
        this.Codigo_Vehiculo = "";
        this.Matricula_Vehiculo = "";
        this.TipoVehiculo_Vehiculo = null;

    }

    public Vehicle(int Identificador_Vehiculo, VehicleGroup Grupo_Vehiculo, String Nombre_Vehiculo,
                   String Codigo_Vehiculo, String Matricula_Vehiculo, VehicleType TipoVehiculo_Vehiculo) {

        //Vehicle information
        this.Identificador_Vehiculo = Identificador_Vehiculo;
        this.Grupo_Vehiculo = Grupo_Vehiculo;
        this.Nombre_Vehiculo = Nombre_Vehiculo;
        this.Codigo_Vehiculo = Codigo_Vehiculo;
        this.Matricula_Vehiculo = Matricula_Vehiculo;
        this.TipoVehiculo_Vehiculo = TipoVehiculo_Vehiculo;
    }


    //Métodos GET y SET

    public String getVehiculeCompany(){
        return this.getTipoVehiculo_Vehiculo().getEmpresa_TipoVehiculo().getCompanyName();
    }

    public int getIdentificador_Vehiculo() {
        return Identificador_Vehiculo;
    }

    public void setIdentificador_Vehiculo(int identificador_Vehiculo) {
        this.Identificador_Vehiculo = identificador_Vehiculo;
    }

    public VehicleGroup getGrupo_Vehiculo() {
        return Grupo_Vehiculo;
    }

    public void setGrupo_Vehiculo(VehicleGroup grupo_Vehiculo) {
        this.Grupo_Vehiculo = grupo_Vehiculo;
    }

    public String getNombre_Vehiculo() {
        return Nombre_Vehiculo;
    }

    public void setNombre_Vehiculo(String nombre_Vehiculo) {
        this.Nombre_Vehiculo = nombre_Vehiculo;
    }

    public String getCodigo_Vehiculo() {
        return Codigo_Vehiculo;
    }

    public void setCodigo_Vehiculo(String Codigo_Vehiculo) {
        this.Codigo_Vehiculo = Codigo_Vehiculo;
    }

    public String getMatricula_Vehiculo() {
        return Matricula_Vehiculo;
    }

    public void setMatricula_Vehiculo(String matricula_Vehiculo) {
        this.Matricula_Vehiculo = matricula_Vehiculo;
    }

    public VehicleType getTipoVehiculo_Vehiculo() {
        return TipoVehiculo_Vehiculo;
    }

    public void setTipoVehiculo_Vehiculo(VehicleType tipoVehiculo_Vehiculo) {
        this.TipoVehiculo_Vehiculo = tipoVehiculo_Vehiculo;
    }
}




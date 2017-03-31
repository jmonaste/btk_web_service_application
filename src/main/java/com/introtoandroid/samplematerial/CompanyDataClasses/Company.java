package com.introtoandroid.samplematerial.CompanyDataClasses;

import java.util.List;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class Company {

    private int Identificador_Empresa;
    private String Codigo_Empresa;
    private String Nombre_Empresa;
    private String EsquemaBBDD_Empresa;
    private String DireccionServidor_BBDD_Empresa;
    private String UsuarioBBDD_Empresa;
    private String ContraseniaBBDD_Empresa;
    private String PuertoBBDD_Empresa;
    private List<VehicleGroup> Grupos_Empresa;

    public Company() {

        //Company information
        this.Identificador_Empresa = 0;
        this.Codigo_Empresa = "";
        this.Nombre_Empresa = "";
        this.EsquemaBBDD_Empresa = "";
        this.DireccionServidor_BBDD_Empresa = "";
        this.UsuarioBBDD_Empresa = "";
        this.ContraseniaBBDD_Empresa = "";
        this.PuertoBBDD_Empresa = "";
        this.Grupos_Empresa = null;
    }

    public Company(int Identificador_Empresa, String Codigo_Empresa, String Nombre_Empresa,
                   String EsquemaBBDD_Empresa, String DireccionServidor_BBDD_Empresa,
                   String UsuarioBBDD_Empresa, String ContraseniaBBDD_Empresa, String PuertoBBDD_Empresa,
                   List<VehicleGroup> Grupos_Empresa) {

        //Company information
        this.Identificador_Empresa = Identificador_Empresa;
        this.Codigo_Empresa = Codigo_Empresa;
        this.Nombre_Empresa = Nombre_Empresa;
        this.EsquemaBBDD_Empresa = EsquemaBBDD_Empresa;
        this.DireccionServidor_BBDD_Empresa = DireccionServidor_BBDD_Empresa;
        this.UsuarioBBDD_Empresa = UsuarioBBDD_Empresa;
        this.ContraseniaBBDD_Empresa = ContraseniaBBDD_Empresa;
        this.PuertoBBDD_Empresa = PuertoBBDD_Empresa;
        this.Grupos_Empresa = Grupos_Empresa;
    }

    //Métodos GET y SET

    public int getIdentificador_Empresa() {

        return Identificador_Empresa;
    }

    public String getCodigo_Empresa() {

        return Codigo_Empresa;
    }

    public String getNombre_Empresa() {

        return Nombre_Empresa;
    }

    public String getEsquemaBBDD_Empresa() {

        return EsquemaBBDD_Empresa;
    }

    public String getDireccionServidor_BBDD_Empresa() {

        return DireccionServidor_BBDD_Empresa;
    }

    public String getUsuarioBBDD_Empresa() {

        return UsuarioBBDD_Empresa;
    }

    public String getContraseniaBBDD_Empresa() {

        return ContraseniaBBDD_Empresa;
    }

    public String getPuertoBBDD_Empresa() {

        return PuertoBBDD_Empresa;
    }

    public List<VehicleGroup> getGrupos_Empresa() {

        return Grupos_Empresa;
    }

    public void setIdentificador_Empresa(int identificador_Empresa) {

        Identificador_Empresa = identificador_Empresa;
    }

    public void setCodigo_Empresa(String codigo_Empresa) {

        Codigo_Empresa = codigo_Empresa;
    }

    public void setNombre_Empresa(String nombre_Empresa) {

        Nombre_Empresa = nombre_Empresa;
    }

    public void setEsquemaBBDD_Empresa(String esquemaBBDD_Empresa) {

        EsquemaBBDD_Empresa = esquemaBBDD_Empresa;
    }

    public void setDireccionServidor_BBDD_Empresa(String direccionServidor_BBDD_Empresa) {

        DireccionServidor_BBDD_Empresa = direccionServidor_BBDD_Empresa;
    }

    public void setUsuarioBBDD_Empresa(String usuarioBBDD_Empresa) {

        UsuarioBBDD_Empresa = usuarioBBDD_Empresa;
    }

    public void setContraseniaBBDD_Empresa(String contraseniaBBDD_Empresa) {

        ContraseniaBBDD_Empresa = contraseniaBBDD_Empresa;
    }

    public void setPuertoBBDD_Empresa(String puertoBBDD_Empresa) {

        PuertoBBDD_Empresa = puertoBBDD_Empresa;
    }

    public void setGrupos_Empresa(List<VehicleGroup> grupos_Empresa) {

        Grupos_Empresa = grupos_Empresa;
    }
}




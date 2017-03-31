package com.introtoandroid.samplematerial.CompanyDataClasses;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class VehicleGroup {

    //Vehicle Group information
    private int Identificador_Grupo;
    private String Nombre_Grupo;
    private String PerfilSeguimiento_Grupo;
    private int Identificador_Empresa;
    private GMT GMT;

    /**
     * Constructor de la clase VehiculeGroup
     */
    public VehicleGroup() {

        //Vehicle Group information
        this.Identificador_Grupo = 0;
        this.Nombre_Grupo = "";
        this.PerfilSeguimiento_Grupo = "";
        this.Identificador_Empresa = 0;
        this.GMT = null;

    }

    /**
     * Constructor de la clase VehiculeGroup.
     * @param Identificador_Grupo
     * @param Nombre_Grupo
     * @param PerfilSeguimiento_Grupo
     * @param Identificador_Empresa
     * @param GMT
     */
    public VehicleGroup(int Identificador_Grupo, String Nombre_Grupo, String PerfilSeguimiento_Grupo,
                        int Identificador_Empresa, GMT GMT) {

        //Vehicle Group information
        this.Identificador_Grupo = Identificador_Grupo;
        this.Nombre_Grupo = Nombre_Grupo;
        this.PerfilSeguimiento_Grupo = PerfilSeguimiento_Grupo;
        this.Identificador_Empresa = Identificador_Empresa;
        this.GMT = GMT;
    }

    //Métodos GET y SET

    public int getIdentificador_Grupo() {

        return Identificador_Grupo;
    }

    public void setIdentificador_Grupo(int identificador_Grupo) {

        Identificador_Grupo = identificador_Grupo;
    }

    public String getNombre_Grupo() {

        return Nombre_Grupo;
    }

    public void setNombre_Grupo(String nombre_Grupo) {

        Nombre_Grupo = nombre_Grupo;
    }

    public String getPerfilSeguimiento_Grupo() {

        return PerfilSeguimiento_Grupo;
    }

    public void setPerfilSeguimiento_Grupo(String perfilSeguimiento_Grupo) {

        PerfilSeguimiento_Grupo = perfilSeguimiento_Grupo;
    }

    public int getIdentificador_Empresa() {

        return Identificador_Empresa;
    }

    public void setIdentificador_Empresa(int identificador_Empresa) {

        Identificador_Empresa = identificador_Empresa;
    }

    public com.example.jmonaste.btk_2.CompanyDataClasses.GMT getGMT() {

        return GMT;
    }

    public void setGMT(com.example.jmonaste.btk_2.CompanyDataClasses.GMT GMT) {

        this.GMT = GMT;
    }
}




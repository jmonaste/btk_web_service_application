package com.introtoandroid.samplematerial.CompanyDataClasses;

import java.util.List;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class Turn {

    //Turn information
    private int Identificador_Turno;
    private String Codigo_Turno;
    private String Descripcion_Turno;
    private List<TurnService> ServiciosTurno_Turno;
    private Company Empresa_Turno;

    public Turn() {

        //Turn information
        this.Identificador_Turno = 0;
        this.Codigo_Turno = "";
        this.Descripcion_Turno = "";
        this.ServiciosTurno_Turno = null;
        this.Empresa_Turno = null;

    }

    public Turn(int Identificador_Turno, String Codigo_Turno, String Descripcion_Turno,
                List<TurnService> ServiciosTurno_Turno, Company Empresa_Turno) {

        //Turn information
        this.Identificador_Turno = Identificador_Turno;
        this.Codigo_Turno = Codigo_Turno;
        this.Descripcion_Turno = Descripcion_Turno;
        this.ServiciosTurno_Turno = ServiciosTurno_Turno;
        this.Empresa_Turno = Empresa_Turno;
    }

    //Métodos GET y SET

    public int getIdentificador_Turno() {

        return Identificador_Turno;
    }

    public String getCodigo_Turno() {

        return Codigo_Turno;
    }

    public String getDescripcion_Turno() {

        return Descripcion_Turno;
    }

    public List<TurnService> getServiciosTurno_Turno() {

        return ServiciosTurno_Turno;
    }

    public Company getEmpresa_Turno() {

        return Empresa_Turno;
    }

    public void setIdentificador_Turno(int identificador_Turno) {

        Identificador_Turno = identificador_Turno;
    }

    public void setCodigo_Turno(String codigo_Turno) {

        Codigo_Turno = codigo_Turno;
    }

    public void setDescripcion_Turno(String descripcion_Turno) {

        Descripcion_Turno = descripcion_Turno;
    }

    public void setServiciosTurno_Turno(List<TurnService> serviciosTurno_Turno) {

        ServiciosTurno_Turno = serviciosTurno_Turno;
    }

    public void setEmpresa_Turno(Company empresa_Turno) {

        Empresa_Turno = empresa_Turno;
    }
}




package com.introtoandroid.samplematerial.CompanyData;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class TurnService {

    //Turn Service information
    private int IdentificadorTurno_ServicioTurno;
    private int IdentificadorServicio_ServicioTurno;
    private int OrdenServicio_ServicioTurno;

    public TurnService() {

        //Turn Service information
        this.IdentificadorTurno_ServicioTurno = 0;
        this.IdentificadorServicio_ServicioTurno = 0;
        this.OrdenServicio_ServicioTurno = 0;

    }

    public TurnService(int IdentificadorTurno_ServicioTurno, int IdentificadorServicio_ServicioTurno,
                       int OrdenServicio_ServicioTurno) {

        //Turn Service information
        this.IdentificadorTurno_ServicioTurno = IdentificadorTurno_ServicioTurno;
        this.IdentificadorServicio_ServicioTurno = IdentificadorServicio_ServicioTurno;
        this.OrdenServicio_ServicioTurno = OrdenServicio_ServicioTurno;
    }

    //Métodos GET y SET

    public int getIdentificadorTurno_ServicioTurno() {
        return IdentificadorTurno_ServicioTurno;
    }

    public void setIdentificadorTurno_ServicioTurno(int identificadorTurno_ServicioTurno) {
        IdentificadorTurno_ServicioTurno = identificadorTurno_ServicioTurno;
    }

    public int getIdentificadorServicio_ServicioTurno() {
        return IdentificadorServicio_ServicioTurno;
    }

    public void setIdentificadorServicio_ServicioTurno(int identificadorServicio_ServicioTurno) {
        IdentificadorServicio_ServicioTurno = identificadorServicio_ServicioTurno;
    }

    public int getOrdenServicio_ServicioTurno() {
        return OrdenServicio_ServicioTurno;
    }

    public void setOrdenServicio_ServicioTurno(int ordenServicio_ServicioTurno) {
        OrdenServicio_ServicioTurno = ordenServicio_ServicioTurno;
    }
}




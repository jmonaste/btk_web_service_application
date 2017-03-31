package com.introtoandroid.samplematerial.CompanyDataClasses;

/**
 * Created by jmonaste on 15/03/2017.
 */

public class GMT {

    //GMT information
    private int Identificador_GMT;
    private String Valor_GMT;

    public GMT() {

        //GMT information
        this.Identificador_GMT = 0;
        this.Valor_GMT = "";
    }

    //GMT information
    public GMT(int Identificador_GMT, String Valor_GMT) {
        this.Identificador_GMT = Identificador_GMT;
        this.Valor_GMT = Valor_GMT;
    }

    //MÉTODOS GET & SET

    public int getIdentificador_GMT() {

        return Identificador_GMT;
    }

    public String getValor_GMT() {

        return Valor_GMT;
    }

    public void setIdentificador_GMT(int identificador_GMT) {

        Identificador_GMT = identificador_GMT;
    }

    public void setValor_GMT(String valor_GMT) {

        Valor_GMT = valor_GMT;
    }
}




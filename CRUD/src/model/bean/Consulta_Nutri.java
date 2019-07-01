package model.bean;

import java.sql.Timestamp;

/**
 * Consulta_Nutri
 */
public class Consulta_Nutri {

    private int matricula_usuario;
    private int cod_nutri;
    private Timestamp data_hora;

    public int getMatricula_usuario() {
        return this.matricula_usuario;
    }

    public void setMatricula_usuario(int matricula_usuario) {
        this.matricula_usuario = matricula_usuario;
    }

    public int getCod_nutri() {
        return this.cod_nutri;
    }

    public void setCod_nutri(int cod_nutri) {
        this.cod_nutri = cod_nutri;
    }

    public Timestamp getData_hora() {
        return this.data_hora;
    }

    public void setData_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
    }

    public void Display() {
        String format = "%-15d%-15d%-20s\n";
        System.out.printf(format, matricula_usuario, cod_nutri, data_hora.toString());
    }
}
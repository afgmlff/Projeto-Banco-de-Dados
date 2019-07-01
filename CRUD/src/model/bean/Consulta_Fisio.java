package model.bean;

import java.sql.Timestamp;

/**
 * Consulta_Fisio
 */
public class Consulta_Fisio {

    private int matricula_usuario;
    private int cod_fisio;
    private Timestamp data_hora;

    public int getMatricula_usuario() {
        return this.matricula_usuario;
    }

    public void setMatricula_usuario(int matricula_usuario) {
        this.matricula_usuario = matricula_usuario;
    }

    public int getCod_fisio() {
        return this.cod_fisio;
    }

    public void setCod_fisio(int cod_fisio) {
        this.cod_fisio = cod_fisio;
    }

    public Timestamp getData_hora() {
        return this.data_hora;
    }

    public void setData_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
    }

    public void Display() {
        String format = "%-15d%-15d%-20s\n";
        System.out.printf(format, matricula_usuario, cod_fisio, data_hora.toString());
    }
}
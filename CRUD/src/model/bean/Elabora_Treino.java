package model.bean;

import java.sql.Timestamp;

/**
 * Elabora_Treino
 */
public class Elabora_Treino {

    private int matricula_usuario;
    private int cod_prof;
    private Timestamp data_hora;

	public int getMatricula_usuario() {
		return this.matricula_usuario;
	}

	public void setMatricula_usuario(int matricula_usuario) {
		this.matricula_usuario = matricula_usuario;
	}

	public int getCod_prof() {
		return this.cod_prof;
	}

	public void setCod_prof(int cod_prof) {
		this.cod_prof = cod_prof;
	}

	public Timestamp getData_hora() {
		return this.data_hora;
	}

	public void setData_hora(Timestamp data_hora) {
		this.data_hora = data_hora;
    }
    
    public void Display() {
        String format = "%-15d%-15d%-20s\n";
        System.out.printf(format, matricula_usuario, cod_prof, data_hora.toString());
    }

}
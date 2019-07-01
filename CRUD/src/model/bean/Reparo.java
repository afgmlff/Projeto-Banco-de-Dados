package model.bean;

import java.sql.Timestamp;

/**
 * Reparo
 */
public class Reparo {

    private int codigo_funcionario;
    private int codigo_equipamento;
    private String descricao_reparo;
    private Timestamp data_reparo;

	public int getCodigo_funcionario() {
		return this.codigo_funcionario;
	}

	public void setCodigo_funcionario(int codigo_funcionario) {
		this.codigo_funcionario = codigo_funcionario;
	}

	public int getCodigo_equipamento() {
		return this.codigo_equipamento;
	}

	public void setCodigo_equipamento(int codigo_equipamento) {
		this.codigo_equipamento = codigo_equipamento;
	}

	public String getDescricao_reparo() {
		return this.descricao_reparo;
	}

	public void setDescricao_reparo(String descricao_reparo) {
		this.descricao_reparo = descricao_reparo;
	}

	public Timestamp getData_reparo() {
		return this.data_reparo;
	}

	public void setData_reparo(Timestamp data_reparo) {
		this.data_reparo = data_reparo;
	}

    public void Display() {
        String format = "%-15d%-15d%-40s%-20s\n";
        System.out.printf(format, codigo_funcionario, codigo_equipamento, descricao_reparo, data_reparo.toString());
    }
}
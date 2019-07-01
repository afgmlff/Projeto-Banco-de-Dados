package model.bean;

/**
 * Modalidade
 */
public class Modalidade {

    private int codigo_modalidade;
    private String nome;
    private String descricao;
    private String dias;
    private String horarios;

	public int getCodigo_modalidade() {
		return this.codigo_modalidade;
	}

	public void setCodigo_modalidade(int codigo_modalidade) {
		this.codigo_modalidade = codigo_modalidade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDias() {
		return this.dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getHorarios() {
		return this.horarios;
	}

	public void setHorarios(String horarios) {
		this.horarios = horarios;
    }
    
    public void Display(){
        String format = "%-15d%-30s%-30s%-30s%-30s\n";
        System.out.printf(format, codigo_modalidade, nome, descricao, dias, horarios);
    }

}
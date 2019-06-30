package model.bean;

/**
 * Manutencao
 */
public class Manutencao {

    private int codigo_funcionario;
    private String area;

	public int getCodigo_funcionario() {
		return this.codigo_funcionario;
	}

	public void setCodigo_funcionario(int codigo_funcionario) {
		this.codigo_funcionario = codigo_funcionario;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
    }
    
    public void Display(){
        String format = "%-10d%-40s\n";
        System.out.printf(format, codigo_funcionario, area);
    }

}
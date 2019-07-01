package model.bean;

/**
 * Estagiario_Eh_Supervisionado
 */
public class Estagiario_Eh_Supervisionado {

    private int codigo_estagiario;
    private int codigo_supervisor;

	public int getCodigo_estagiario() {
		return this.codigo_estagiario;
	}

	public void setCodigo_estagiario(int codigo_estagiario) {
		this.codigo_estagiario = codigo_estagiario;
	}

	public int getCodigo_supervisor() {
		return this.codigo_supervisor;
	}

	public void setCodigo_supervisor(int codigo_professor) {
		this.codigo_supervisor = codigo_professor;
    }
    
    public void Display() {
        String format = "%-15d%-15d\n";
        System.out.printf(format, codigo_estagiario, codigo_supervisor);
    }

}
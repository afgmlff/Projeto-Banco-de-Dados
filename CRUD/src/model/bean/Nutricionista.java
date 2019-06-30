package model.bean;

/**
 * Nutricionista
 */
public class Nutricionista {

    private int codigo_funcionario;
    private int CFN;

	public int getCodigo_funcionario() {
		return this.codigo_funcionario;
	}

	public void setCodigo_funcionario(int codigo_funcionario) {
		this.codigo_funcionario = codigo_funcionario;
	}

	public int getCFN() {
		return this.CFN;
	}

	public void setCFN(int CFN) {
		this.CFN = CFN;
    }
    
    public void Display() {
        String format = "%-15d%-15d\n";
        System.out.printf(format, codigo_funcionario, CFN);
    }

}
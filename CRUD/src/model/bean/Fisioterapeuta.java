package model.bean;

/**
 * Fisioterapeuta
 */
public class Fisioterapeuta {

    private int codigo_funcionario;
    private int CREFITO;

	public int getCodigo_funcionario() {
		return this.codigo_funcionario;
	}

	public void setCodigo_funcionario(int codigo_funcionario) {
		this.codigo_funcionario = codigo_funcionario;
	}

	public int getCREFITO() {
		return this.CREFITO;
	}

	public void setCREFITO(int CFN) {
		this.CREFITO = CFN;
    }
    
    public void Display() {
        String format = "%-15d%-15d\n";
        System.out.printf(format, codigo_funcionario, CREFITO);
    }

}
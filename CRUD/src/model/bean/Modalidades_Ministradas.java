package model.bean;

/**
 * Modalidades_Ministradas
 */
public class Modalidades_Ministradas {

    private int codigo_professor;
    private int codigo_modalidade;

	public int getCodigo_professor() {
		return this.codigo_professor;
	}

	public void setCodigo_professor(int codigo_professor) {
		this.codigo_professor = codigo_professor;
	}

	public int getCodigo_modalidade() {
		return this.codigo_modalidade;
	}

	public void setCodigo_modalidade(int codigo_modalidade) {
		this.codigo_modalidade = codigo_modalidade;
    }
    
    public void Display() {
        String format = "%-15d%-15d\n";
        System.out.printf(format, codigo_professor, codigo_modalidade);
    }

}
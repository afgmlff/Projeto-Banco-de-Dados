package model.bean;

/**
 * Professor_Especialidade
 */
public class Professor_Especialidade {

    private int codigo_professor;
    private String especialidade;

    public int getCodigo_professor() {
        return this.codigo_professor;
    }

    public void setCodigo_professor(int codigo_professor) {
        this.codigo_professor = codigo_professor;
    }

    public String getEspecialidade() {
        return this.especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void Display() {
        String format = "%-10d%-40s\n";
        System.out.printf(format, codigo_professor, especialidade);
    }

}
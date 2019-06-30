package model.bean;

/**
 * Estagiario
 */
public class Estagiario {

    private int codigo_funcionario;
    private String area;
    private String instituicao_ensino;

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

    public String getInstituicao_ensino() {
        return this.instituicao_ensino;
    }

    public void setInstituicao_ensino(String instituicao_ensino) {
        this.instituicao_ensino = instituicao_ensino;
    }

    public void Display() {
        String format = "%-10d%-40s%-40s\n";
        System.out.printf(format, codigo_funcionario, area, instituicao_ensino);
    }

}
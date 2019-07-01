package model.bean;

/**
 * Inscricao
 */
public class Inscricao {

    private int matricula_usuario;
    private int codigo_modalidade;

    public int getMatricula_usuario() {
        return this.matricula_usuario;
    }

    public void setMatricula_usuario(int matricula_usuario) {
        this.matricula_usuario = matricula_usuario;
    }

    public int getCodigo_modalidade() {
        return this.codigo_modalidade;
    }

    public void setCodigo_modalidade(int codigo_modalidade) {
        this.codigo_modalidade = codigo_modalidade;
    }

    public void Display() {
        String format = "%-15d%-15d\n";
        System.out.printf(format, matricula_usuario, codigo_modalidade);
    }

}
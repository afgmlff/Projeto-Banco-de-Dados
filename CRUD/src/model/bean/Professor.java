package model.bean;

/**
 * Professor
 */
public class Professor {

    private int codigo_funcionario;

    public int getCodigo_funcionario() {
        return this.codigo_funcionario;
    }

    public void setCodigo_funcionario(int codigo_funcionario) {
        this.codigo_funcionario = codigo_funcionario;
    }

    public void Display() {
        String format = "%-10d\n";
        System.out.printf(format, codigo_funcionario);
    }

}
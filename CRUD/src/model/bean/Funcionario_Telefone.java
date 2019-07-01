package model.bean;

public class Funcionario_Telefone {

    private int codigo_funcionario;
    private int telefone;

    public int getCodigo_funcionario() {
        return this.codigo_funcionario;
    }

    public void setCodigo_funcionario(int codigo_funcionario) {
        this.codigo_funcionario = codigo_funcionario;
    }

    public int getTelefone() {
        return this.telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public void Display() {
        String format = "%-10d%-10d\n";
        System.out.printf(format, codigo_funcionario, telefone);
    }

}
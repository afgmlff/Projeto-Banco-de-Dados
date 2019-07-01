package model.bean;

/**
 * Funcionario
 */
public class Funcionario {

    private int registro_funcionario;
    private String nome;
    private String endereco;

    public int getRegistro_funcionario() {
        return this.registro_funcionario;
    }

    public void setRegistro_funcionario(int registro_funcionario) {
        this.registro_funcionario = registro_funcionario;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void Display() {
        String format = "%-10d%-30s%-30s\n";
        System.out.printf(format, registro_funcionario, nome, endereco);
    }

}
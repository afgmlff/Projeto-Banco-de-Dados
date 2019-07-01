package model.bean;

import java.sql.Blob;
import java.sql.Date;

public class Usuario {

    private int matricula;
    private String nome;
    private String endereco;
    private Date data_nascimento;
    private Date data_inicio;
    private Blob foto;

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public Date getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Date data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public void Display() {
        String format = "%-10d%-30s%-30s%-15s%-15s\n";
        System.out.printf(format, matricula, nome, endereco, data_nascimento.toString(), data_inicio.toString());
    }
}

package model.bean;

import java.sql.Blob;

/**
 * Equipamento
 */
public class Equipamento {

    private int codigo_equipamento;
    private String nome;
    private Blob video;

    public int getCodigo_equipamento() {
        return this.codigo_equipamento;
    }

    public void setCodigo_equipamento(int codigo_equipamento) {
        this.codigo_equipamento = codigo_equipamento;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Blob getVideo() {
        return this.video;
    }

    public void setVideo(Blob video) {
        this.video = video;
    }

    public void Display() {
        String format = "%-10d%-40s\n";
        System.out.printf(format, codigo_equipamento, nome);
    }

}
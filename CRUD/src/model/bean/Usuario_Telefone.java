package model.bean;

public class Usuario_Telefone {

    private int usuario;
    private int telefone;

    public int getUsuario() {
        return this.usuario;
    }

    public void setusuario(int usuario) {
        this.usuario = usuario;
    }

    public int getTelefone() {
        return this.telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public void Display() {
        String format = "%-10d%-10d\n";
        System.out.printf(format, usuario, telefone);
    }

}
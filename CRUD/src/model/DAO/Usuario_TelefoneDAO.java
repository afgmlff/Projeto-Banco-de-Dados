package model.DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.ArrayList;

import connection.ConnectionFactory;
import model.bean.Usuario_Telefone;

public class Usuario_TelefoneDAO {

    public void insereTelefone(Usuario_Telefone u_tel) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO `usuario_telefone`(`usuario`, `telefone`) VALUES (?, ?)");
            stmt.setInt(1, u_tel.getUsuario());
            stmt.setInt(2, u_tel.getTelefone());
            stmt.executeUpdate();
            System.out.println("Telefone inserido com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de telefone: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Usuario_Telefone> buscaTelefone(int telefone) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Usuario_Telefone> telefones = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `usuario_telefone` WHERE `telefone` = " + telefone);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario_Telefone u_tel = new Usuario_Telefone();
                u_tel.setTelefone(rs.getInt("telefone"));
                u_tel.setusuario(rs.getInt("usuario"));
                telefones.add(u_tel);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando telefone: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return telefones;
    }

    public List<Usuario_Telefone> buscaMatricula(int matricula) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Usuario_Telefone> telefones = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `usuario_telefone` WHERE `usuario` = " + matricula);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario_Telefone u_tel = new Usuario_Telefone();
                u_tel.setTelefone(rs.getInt("telefone"));
                u_tel.setusuario(rs.getInt("usuario"));
                telefones.add(u_tel);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando matrícula: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return telefones;
    }

    public void deletaTelefone(int telefone) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM ´usuario_telefone´ WHERE `telefone` = " + telefone);
            System.out.println("Telefone deletado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de telefone: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void MenuInsere() {

        Usuario_Telefone u_tel = new Usuario_Telefone();
        boolean quitRequested = false;

        if (!quitRequested) {
            String usuario = JOptionPane.showInputDialog("Insira a matrícula do usuário");
            if (usuario == null || usuario.length() == 0) {
                quitRequested = true;
            } else {
                u_tel.setusuario(Integer.parseInt(usuario));
            }
        }

        if (!quitRequested) {
            String telefone = JOptionPane.showInputDialog("Insira o telefone do usuário");
            if (telefone == null || telefone.length() == 0) {
                quitRequested = true;
            } else {
                u_tel.setTelefone(Integer.parseInt(telefone));
            }
        }

        if (!quitRequested) {
            insereTelefone(u_tel);
        }

    }

    public void MenuDeleta() {

        String tel = JOptionPane.showInputDialog("Digite o telefone a deletar");
        if (tel != null && tel.length() > 0) {
            deletaTelefone(Integer.parseInt(tel));
        }
    }

    public void MenuConsulta() {
        JFrame frame = new JFrame("Escolha o tipo de consulta");
        frame.setBounds(300, 300, 200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b1 = new JButton("Matrícula");
        b1.setActionCommand("mat");
        b1.setMnemonic('M');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("mat")) {
                    String mat = JOptionPane.showInputDialog("Digite a matrícula a consultar");
                    if (mat != null && mat.length() > 0) {
                        int matricula = Integer.parseInt(mat);
                        List<Usuario_Telefone> lista = buscaMatricula(matricula);

                        if (lista != null && lista.size() > 0) {
                            String format = "%-10s%-10s\n";
                            System.out.printf(format, "Matrícula", "Telefone");
                            for (int i = 0; i < lista.size(); i++) {
                                lista.get(i).Display();
                            }
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Telefone");
        b2.setActionCommand("tel");
        b2.setMnemonic('T');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("tel")) {
                    String tel = JOptionPane.showInputDialog("Digite o telefone a buscar");
                    if (tel != null && tel.length() > 0) {
                        List<Usuario_Telefone> resultado = buscaTelefone(Integer.parseInt(tel));
                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-10s%-10s\n";
                            System.out.printf(format, "Matrícula", "Telefone");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        frame.add(b1, BorderLayout.PAGE_START);
        frame.add(b2, BorderLayout.PAGE_END);
        frame.setVisible(true);
        frame.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dim.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
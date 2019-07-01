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
import model.bean.Funcionario_Telefone;

public class Funcionario_TelefoneDAO {

    public void insereTelefone(Funcionario_Telefone f_tel) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `funcionario_telefone`(`codigo_funcionario`, `telefone`) VALUES (?, ?)");
            stmt.setInt(1, f_tel.getCodigo_funcionario());
            stmt.setInt(2, f_tel.getTelefone());
            stmt.executeUpdate();
            System.out.println("Telefone inserido com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de telefone: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Funcionario_Telefone> buscaTelefone(int telefone) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Funcionario_Telefone> telefones = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `Funcionario_Telefone` WHERE `telefone` = " + telefone);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario_Telefone u_tel = new Funcionario_Telefone();
                u_tel.setTelefone(rs.getInt("telefone"));
                u_tel.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                telefones.add(u_tel);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando telefone: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return telefones;
    }

    public List<Funcionario_Telefone> buscaCodigo(int codigo) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Funcionario_Telefone> telefones = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `funcionario_telefone` WHERE `codigo_funcionario` = " + codigo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario_Telefone u_tel = new Funcionario_Telefone();
                u_tel.setTelefone(rs.getInt("telefone"));
                u_tel.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                telefones.add(u_tel);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return telefones;
    }

    public void deletaTelefone(int telefone) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM `funcionario_telefone` WHERE `telefone` = " + telefone);
            System.out.println("Telefone deletado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na deleção de telefone: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void MenuInsere() {

        Funcionario_Telefone u_tel = new Funcionario_Telefone();
        boolean quitRequested = false;

        if (!quitRequested) {
            String codigo_funcionario = JOptionPane.showInputDialog("Insira a matrícula do funcionário");
            if (codigo_funcionario == null || codigo_funcionario.length() == 0) {
                quitRequested = true;
            } else {
                u_tel.setCodigo_funcionario(Integer.parseInt(codigo_funcionario));
            }
        }

        if (!quitRequested) {
            String telefone = JOptionPane.showInputDialog("Insira o telefone do funcionário");
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
        JButton b1 = new JButton("Código");
        b1.setActionCommand("cod");
        b1.setMnemonic('C');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("cod")) {
                    String cod = JOptionPane.showInputDialog("Digite o código de funcionário a consultar");
                    if (cod != null && cod.length() > 0) {
                        int codigo = Integer.parseInt(cod);
                        List<Funcionario_Telefone> lista = buscaCodigo(codigo);

                        if (lista != null && lista.size() > 0) {
                            String format = "%-10s%-10s\n";
                            System.out.printf(format, "Matrícula", "Telefone");
                            for (int i = 0; i < lista.size(); i++) {
                                lista.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhum telefone encontrado para o registro");
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
                        List<Funcionario_Telefone> resultado = buscaTelefone(Integer.parseInt(tel));
                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-10s%-10s\n";
                            System.out.printf(format, "Matrícula", "Telefone");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhum telefone encontrado");
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
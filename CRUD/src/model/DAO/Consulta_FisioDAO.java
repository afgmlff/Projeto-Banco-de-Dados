package model.DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import model.bean.Consulta_Fisio;

/**
 * Consulta_FisioDAO
 */
public class Consulta_FisioDAO {

    public void insereConsulta(Consulta_Fisio cf) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `consulta_fisio`(`matricula_usuario`, `cod_fisio`, `data_hora`) VALUES (?, ?, ?)");
            stmt.setInt(1, cf.getMatricula_usuario());
            stmt.setInt(2, cf.getCod_fisio());
            stmt.setTimestamp(3, cf.getData_hora());
            stmt.executeUpdate();
            System.out.println("Consulta inserida com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de consulta a fisioterapeuta: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Consulta_Fisio> buscaMatricula(int matricula) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Consulta_Fisio> consultas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `consulta_fisio` WHERE `matricula_usuario` = " + matricula);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta_Fisio cons = new Consulta_Fisio();
                cons.setMatricula_usuario(rs.getInt("matricula_usuario"));
                cons.setCod_fisio(rs.getInt("cod_fisio"));
                cons.setData_hora(rs.getTimestamp("data_hora"));
                consultas.add(cons);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando consultas por matrícula: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return consultas;
    }

    public List<Consulta_Fisio> buscaCodFisio(int codFisio) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Consulta_Fisio> consultas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `consulta_fisio` WHERE `cod_fisio` = " + codFisio);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta_Fisio cons = new Consulta_Fisio();
                cons.setMatricula_usuario(rs.getInt("matricula_usuario"));
                cons.setCod_fisio(rs.getInt("cod_fisio"));
                cons.setData_hora(rs.getTimestamp("data_hora"));
                consultas.add(cons);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando consultas por código de fisioterapeutas: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return consultas;
    }

    public int deletaConsulta(int matricula, int codFisio, Date data_consulta) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement(
                    "DELETE FROM `consulta_fisio` WHERE `matricula_usuario` = " + matricula + " AND `cod_fisio` = "
                            + codFisio + " AND DATE(`data_hora`) LIKE '" + data_consulta.toString() + "%'");
            success = stmt.executeUpdate();
            System.out.println(success + " consulta(s) deletada(s)!");
        } catch (SQLException ex) {
            System.out.println("Erro deletando consulta: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {
        Consulta_Fisio con = new Consulta_Fisio();
        boolean quitRequested = false;

        if (!quitRequested) {
            String mat = JOptionPane.showInputDialog("Insira a matrícula do usuário");
            if (mat != null && mat.length() > 0) {
                con.setMatricula_usuario(Integer.parseInt(mat));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String cod = JOptionPane.showInputDialog("Insira o código do fisioterapeuta");
            if (cod != null && cod.length() > 0) {
                con.setCod_fisio(Integer.parseInt(cod));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String date = JOptionPane.showInputDialog("Insira a data e hora da consulta (yyyy-mm-dd hh:mm:ss)");
            if (date != null && date.length() > 0) {
                con.setData_hora(Timestamp.valueOf(date));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            insereConsulta(con);
        }
    }

    public void MenuDeleta() {

        String matricula = JOptionPane.showInputDialog("Insira a matrícula de usuário da consulta a deletar");
        if (matricula != null && matricula.length() > 0) {
            String codFisio = JOptionPane.showInputDialog("Insira o código do fisioterapeuta da consulta a deletar");
            if (codFisio != null && codFisio.length() > 0) {
                String data = JOptionPane.showInputDialog("Insira a data da consulta (yyyy-mm-dd)");
                if (data != null && data.length() > 0) {
                    deletaConsulta(Integer.parseInt(matricula), Integer.parseInt(codFisio), Date.valueOf(data));
                }
            }
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
                        List<Consulta_Fisio> reps = buscaMatricula(matricula);

                        if (reps != null && reps.size() > 0) {
                            String format = "%-15s%-15s%-20s\n";
                            System.out.printf(format, "Usuário", "Fisioterapeuta", "Data");
                            for (int i = 0; i < reps.size(); i++) {
                                reps.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma consulta encontrada");
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Código de Fisioterapeuta");
        b2.setActionCommand("fisio");
        b2.setMnemonic('F');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("fisio")) {
                    String cod = JOptionPane.showInputDialog("Digite o código de fisioterapeuta a consultar");
                    List<Consulta_Fisio> reparos = buscaCodFisio(Integer.parseInt(cod));
                    if (reparos != null && reparos.size() > 0) {
                        String format = "%-15s%-15s%-20s\n";
                        System.out.printf(format, "Usuário", "Fisioterapeuta", "Data");
                        for (int i = 0; i < reparos.size(); i++) {
                            reparos.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhuma consulta encontrada");
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
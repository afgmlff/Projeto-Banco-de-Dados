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
import model.bean.Consulta_Nutri;

/**
 * Consulta_NutriDAO
 */
public class Consulta_NutriDAO {

    public void insereConsulta(Consulta_Nutri cn) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `consulta_nutri`(`matricula_usuario`, `cod_nutri`, `data_hora`) VALUES (?, ?, ?)");
            stmt.setInt(1, cn.getMatricula_usuario());
            stmt.setInt(2, cn.getCod_nutri());
            stmt.setTimestamp(3, cn.getData_hora());
            stmt.executeUpdate();
            System.out.println("Consulta inserida com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de consulta a nutricionista: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Consulta_Nutri> buscaMatricula(int matricula) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Consulta_Nutri> consultas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `consulta_nutri` WHERE `matricula_usuario` = " + matricula);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta_Nutri cons = new Consulta_Nutri();
                cons.setMatricula_usuario(rs.getInt("matricula_usuario"));
                cons.setCod_nutri(rs.getInt("cod_nutri"));
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

    public List<Consulta_Nutri> buscaCodNutri(int codNutri) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Consulta_Nutri> consultas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `consulta_nutri` WHERE `cod_nutri` = " + codNutri);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta_Nutri cons = new Consulta_Nutri();
                cons.setMatricula_usuario(rs.getInt("matricula_usuario"));
                cons.setCod_nutri(rs.getInt("cod_nutri"));
                cons.setData_hora(rs.getTimestamp("data_hora"));
                consultas.add(cons);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando consultas por código de nutricionista: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return consultas;
    }

    public int deletaConsulta(int matricula, int codNutri, Date data_reparo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement(
                    "DELETE FROM `consulta_nutri` WHERE `matricula_usuario` = " + matricula + " AND `cod_nutri` = "
                            + codNutri + " AND DATE(`data_hora`) LIKE '" + data_reparo.toString() + "%'");
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
        Consulta_Nutri con = new Consulta_Nutri();
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
            String cod = JOptionPane.showInputDialog("Insira o código do nutricionista");
            if (cod != null && cod.length() > 0) {
                con.setCod_nutri(Integer.parseInt(cod));
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
            String codNutri = JOptionPane.showInputDialog("Insira o código do nutricionista da consulta a deletar");
            if (codNutri != null && codNutri.length() > 0) {
                String data = JOptionPane.showInputDialog("Insira a data da consulta (yyyy-mm-dd)");
                if (data != null && data.length() > 0) {
                    deletaConsulta(Integer.parseInt(matricula), Integer.parseInt(codNutri), Date.valueOf(data));
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
                        List<Consulta_Nutri> reps = buscaMatricula(matricula);

                        if (reps != null && reps.size() > 0) {
                            String format = "%-15s%-15s%-20s\n";
                            System.out.printf(format, "Usuário", "Nutricionista", "Data");
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
        JButton b2 = new JButton("Código de Nutricionista");
        b2.setActionCommand("nutri");
        b2.setMnemonic('N');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("nutri")) {
                    String cod = JOptionPane.showInputDialog("Digite o código de nutricionista a consultar");
                    List<Consulta_Nutri> reparos = buscaCodNutri(Integer.parseInt(cod));
                    if (reparos != null && reparos.size() > 0) {
                        String format = "%-15s%-15s%-20s\n";
                        System.out.printf(format, "Usuário", "Nutricionista", "Data");
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
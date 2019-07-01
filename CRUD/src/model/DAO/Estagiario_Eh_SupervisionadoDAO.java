package model.DAO;

import model.bean.Estagiario_Eh_Supervisionado;
import connection.ConnectionFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Estagiario_Eh_SupervisionadoDAO
 */

public class Estagiario_Eh_SupervisionadoDAO {

    public void insereEstagiario_Eh_Supervisionado(Estagiario_Eh_Supervisionado est) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `estagiario_eh_supervisionado`(`codigo_estagiario`, `codigo_supervisor`) VALUES (?, ?)");
            stmt.setInt(1, est.getCodigo_estagiario());
            stmt.setInt(2, est.getCodigo_supervisor());
            stmt.executeUpdate();
            System.out.println("Supervisão de estagiário inserida com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de supervisão de estagiário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Estagiario_Eh_Supervisionado> buscaEstagiario(int codigo) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Estagiario_Eh_Supervisionado> supervisores = new ArrayList<>();

        try {
            stmt = con
                    .prepareStatement("SELECT * FROM `estagiario_eh_supervisionado` WHERE `codigo_estagiario` = " + codigo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Estagiario_Eh_Supervisionado est = new Estagiario_Eh_Supervisionado();
                est.setCodigo_estagiario(rs.getInt("codigo_estagiario"));
                est.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
                supervisores.add(est);
            }
        } catch (SQLException ex) {
            System.out.println("Erro buscando por código de estagiário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return supervisores;
    }

    public List<Estagiario_Eh_Supervisionado> buscaSupervisor(int codigo) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Estagiario_Eh_Supervisionado> estagiarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `estagiario_eh_supervisionado` WHERE `codigo_supervisor` = " + codigo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Estagiario_Eh_Supervisionado est = new Estagiario_Eh_Supervisionado();
                est.setCodigo_estagiario(rs.getInt("codigo_estagiario"));
                est.setCodigo_supervisor(rs.getInt("codigo_supervisor"));
                estagiarios.add(est);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando por código de supervisor: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return estagiarios;
    }

    public int deletaEstagiario_Eh_Supervisionado(int codigo_estagiario, int codigo_supervisor) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement(
                    "DELETE FROM `estagiario_eh_supervisionado` WHERE (`codigo_estagiario`, `codigo_supervisor`) = ("
                            + codigo_estagiario + ", " + codigo_supervisor + ")");
            success = stmt.executeUpdate();
            System.out.println(success + " supervisão(ões) de estagiário deletada(s)!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar supervisão de estagiário: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Estagiario_Eh_Supervisionado est = new Estagiario_Eh_Supervisionado();
        boolean quitRequested = false;

        if (!quitRequested) {
            String cod_est = JOptionPane.showInputDialog("Insira o código do estagiário");
            if (cod_est == null || cod_est.length() == 0) {
                quitRequested = true;
            } else {
                est.setCodigo_estagiario(Integer.parseInt(cod_est));
            }
        }

        if (!quitRequested) {
            String cod_super = JOptionPane.showInputDialog("Insira o código do funcionário que o supervisiona");
            if (cod_super != null && cod_super.length() > 0) {
                est.setCodigo_supervisor(Integer.parseInt(cod_super));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            insereEstagiario_Eh_Supervisionado(est);
        }

    }

    public void MenuDeleta() {

        String cod_mod = JOptionPane.showInputDialog("Digite o código do estagiário a deletar");
        if (cod_mod != null && cod_mod.length() > 0) {
            String cod_prof = JOptionPane.showInputDialog("Digite o código do funcionário que o supervisiona");
            if (cod_prof != null && cod_prof.length() > 0) {
                deletaEstagiario_Eh_Supervisionado(Integer.parseInt(cod_mod), Integer.parseInt(cod_prof));
            }
        }
    }

    public void MenuConsulta() {
        JFrame frame = new JFrame("Escolha o tipo de consulta");
        frame.setBounds(300, 300, 200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b1 = new JButton("Código do estagiário");
        b1.setActionCommand("est");
        b1.setMnemonic('E');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("est")) {
                    String mat = JOptionPane.showInputDialog("Digite o código do estagiário a consultar");
                    if (mat != null && mat.length() > 0) {
                        int matricula = Integer.parseInt(mat);
                        List<Estagiario_Eh_Supervisionado> resultado = buscaEstagiario(matricula);

                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Estagiário", "Supervisor");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma supervisão de estagiário encontrada");
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Código do supervisor");
        b2.setActionCommand("sup");
        b2.setMnemonic('S');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("sup")) {
                    String cod_prof = JOptionPane.showInputDialog("Digite o código de funcionário a consultar");
                    if (cod_prof != null && cod_prof.length() > 0) {
                        List<Estagiario_Eh_Supervisionado> resultado = buscaSupervisor(Integer.parseInt(cod_prof));
                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Estagiário", "Supervisor");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma supervisão de estagiário encontrada");
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

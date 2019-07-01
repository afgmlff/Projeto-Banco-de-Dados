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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import model.bean.Estagiario;

/**
 * EstagiarioDAO
 */
public class EstagiarioDAO {

    public void insereEstagiario(Estagiario est) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `estagiario`(`codigo_funcionario`, `area`, `instituicao_de_ensino`) VALUES(?, ?, ?)");
            stmt.setInt(1, est.getCodigo_funcionario());
            stmt.setString(2, est.getArea());
            stmt.setString(3, est.getInstituicao_ensino());
            stmt.executeUpdate();
            System.out.println("Estagiário inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Estagiario buscaRegistro(int reg) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Estagiario est = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM `estagiario` WHERE `codigo_funcionario` = " + reg);
            rs = stmt.executeQuery();

            if (rs.next()) {
                est = new Estagiario();
                est.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                est.setArea(rs.getString("area"));
                est.setInstituicao_ensino(rs.getString("instituicao_de_ensino"));
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return est;
    }

    public List<Estagiario> buscaTodos() {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Estagiario> estagiarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `estagiario`");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Estagiario est = new Estagiario();
                est = new Estagiario();
                est.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                est.setArea(rs.getString("area"));
                est.setInstituicao_ensino(rs.getString("instituicao_de_ensino"));
                estagiarios.add(est);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando estagiários: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return estagiarios;
    }

    public int deletaEstagiario(int reg) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `estagiario` WHERE `codigo_funcionario` = " + reg);
            stmt.executeUpdate();
            success = 1;
            System.out.println("Estagiário deletado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar funcionário: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Estagiario man = new Estagiario();
        boolean quitRequested = false;

        if (!quitRequested) {
            String cod = JOptionPane.showInputDialog("Digite o código do funcionário");
            if (cod == null || cod.length() == 0) {
                quitRequested = true;
            } else {
                man.setCodigo_funcionario(Integer.parseInt(cod));
            }
        }

        if (!quitRequested) {
            String area = JOptionPane.showInputDialog("Digite a área do estagiário");
            if (area == null || area.length() == 0) {
                quitRequested = true;
            } else {
                man.setArea(area);
            }
        }

        if (!quitRequested) {
            String instituicao = JOptionPane.showInputDialog("Digite a instituição de ensino do estagiário");
            if (instituicao == null || instituicao.length() == 0) {
                quitRequested = true;
            } else {
                man.setInstituicao_ensino(instituicao);
            }
        }

        if (!quitRequested) {
            insereEstagiario(man);
        }
    }

    public void MenuDeleta() {

        String reg = JOptionPane.showInputDialog("Digite o registro do funcionário a deletar");
        if (reg != null && reg.length() > 0) {
            deletaEstagiario(Integer.parseInt(reg));
        }
    }

    public void MenuConsulta() {
        JFrame frame = new JFrame("Escolha o tipo de consulta");
        frame.setBounds(300, 300, 200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b1 = new JButton("Registro");
        b1.setActionCommand("reg");
        b1.setMnemonic('R');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("reg")) {
                    String reg = JOptionPane.showInputDialog("Digite o registro a consultar");
                    if (reg != null && reg.length() > 0) {
                        int registro = Integer.parseInt(reg);
                        Estagiario est = buscaRegistro(registro);

                        if (est != null) {
                            String format = "%-10s%-40s%-40s\n";
                            System.out.printf(format, "Registro", "Área", "Intituição de Ensino");
                            est.Display();
                        } else {
                            System.out.println("Nenhum estagiário encontrado");
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Todos");
        b2.setActionCommand("tudo");
        b2.setMnemonic('T');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("tudo")) {
                    List<Estagiario> resultado = buscaTodos();
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-10s%-40s%-40s\n";
                        System.out.printf(format, "Registro", "Área", "Intituição de Ensino");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhum estagiário encontrado");
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
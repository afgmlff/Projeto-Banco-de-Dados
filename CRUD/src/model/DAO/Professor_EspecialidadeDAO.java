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
import model.bean.Professor_Especialidade;

/**
 * Professor_EspecialidadeDAO
 */
public class Professor_EspecialidadeDAO {

    public void insereEspecialidade(Professor_Especialidade pe) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `professor_especialidade`(`codigo_professor`, `especialidade`) VALUES(?, ?)");
            stmt.setInt(1, pe.getCodigo_professor());
            stmt.setString(2, pe.getEspecialidade());
            stmt.executeUpdate();
            System.out.println("Especialidade inserida com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Professor_Especialidade> buscaRegistro(int reg) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Professor_Especialidade> especialidades = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `professor_especialidade` WHERE `codigo_professor` = " + reg);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Professor_Especialidade pe = new Professor_Especialidade();
                pe.setCodigo_professor(rs.getInt("codigo_professor"));
                pe.setEspecialidade(rs.getString("especialidade"));
                especialidades.add(pe);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return especialidades;
    }

    public List<Professor_Especialidade> buscaTodos() {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Professor_Especialidade> prof_esps = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `professor_especialidade`");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Professor_Especialidade pe = new Professor_Especialidade();
                pe.setCodigo_professor(rs.getInt("codigo_professor"));
                pe.setEspecialidade(rs.getString("especialidade"));
                prof_esps.add(pe);
            }
        } catch (SQLException ex) {
            System.out.println("Erro buscando especialidades de professores: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return prof_esps;
    }

    public int deletaEspecialidades(int reg, String esp) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `professor_especialidade` WHERE `especialidade` LIKE '%" + esp
                    + "%' AND `codigo_professor` = " + reg);
            stmt.executeUpdate();
            success = 1;
            System.out.println("Especialidades deletadas com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar especialidades: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Professor_Especialidade pe = new Professor_Especialidade();
        boolean quitRequested = false;

        if (!quitRequested) {
            String cod = JOptionPane.showInputDialog("Digite o código do professor");
            if (cod == null || cod.length() == 0) {
                quitRequested = true;
            } else {
                pe.setCodigo_professor(Integer.parseInt(cod));
            }
        }

        if (!quitRequested) {
            String espec = JOptionPane.showInputDialog("Digite a especialidade do professor");
            if (espec == null || espec.length() == 0) {
                quitRequested = true;
            } else {
                pe.setEspecialidade(espec);
            }
        }

        if (!quitRequested) {
            insereEspecialidade(pe);
        }
    }

    public void MenuDeleta() {

        Professor_Especialidade pe = new Professor_Especialidade();

        String reg = JOptionPane.showInputDialog("Digite o registro do professor");
        if (reg != null && reg.length() > 0) {
            pe.setCodigo_professor(Integer.parseInt(reg));

            String espec = JOptionPane.showInputDialog("Digite a especialidade a deletar");
            if (espec != null && espec.length() > 0) {
                pe.setEspecialidade(espec);

                deletaEspecialidades(pe.getCodigo_professor(), pe.getEspecialidade());
            }
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
                        List<Professor_Especialidade> resultado = buscaRegistro(registro);

                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-10s%-40s\n";
                            System.out.printf(format, "CodProf", "Especialidade");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma especialidade encontrada");
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
                    List<Professor_Especialidade> resultado = buscaTodos();
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-10s%-40s\n";
                        System.out.printf(format, "CodProf", "Especialidade");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhuma especialidade encontrada");
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
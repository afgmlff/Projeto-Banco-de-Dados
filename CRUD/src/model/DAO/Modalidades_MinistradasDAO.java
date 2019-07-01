package model.DAO;

import model.bean.Modalidades_Ministradas;
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
 * Modalidades_MinistradasDAO
 */

public class Modalidades_MinistradasDAO {
    String s = null;

    public void insereModalidades_Ministradas(Modalidades_Ministradas m) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `modalidades_ministradas`(`codigo_professor`, `codigo_modalidade`) VALUES (?, ?)");
            stmt.setInt(1, m.getCodigo_professor());
            stmt.setInt(2, m.getCodigo_modalidade());
            stmt.executeUpdate();
            System.out.println("Modalidade Ministrada inserida com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de modalidade ministrada: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Modalidades_Ministradas> buscaModalidade(int codigo) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Modalidades_Ministradas> m = new ArrayList<>();

        try {
            stmt = con
                    .prepareStatement("SELECT * FROM `modalidades_ministradas` WHERE `codigo_modalidade` = " + codigo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Modalidades_Ministradas mod = new Modalidades_Ministradas();
                mod.setCodigo_modalidade(rs.getInt("codigo_professor"));
                mod.setCodigo_professor(rs.getInt("codigo_modalidade"));
                m.add(mod);
            }
        } catch (SQLException ex) {
            System.out.println("Erro buscando por código de modalidade: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return m;
    }

    public List<Modalidades_Ministradas> buscaProfessor(int codigo) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Modalidades_Ministradas> modalidades_ministradass = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `modalidades_ministradas` WHERE `codigo_professor` = " + codigo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Modalidades_Ministradas m = new Modalidades_Ministradas();
                m.setCodigo_modalidade(rs.getInt("codigo_modalidade"));
                m.setCodigo_professor(rs.getInt("codigo_professor"));
                modalidades_ministradass.add(m);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando por código de professor: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return modalidades_ministradass;
    }

    public int deletaModalidades_Ministradas(int codigo_modalidade, int codigo_professor) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement(
                    "DELETE FROM `modalidades_ministradas` WHERE (`codigo_modalidade`, `codigo_professor`) = ("
                            + codigo_modalidade + ", " + codigo_professor + ")");
            success = stmt.executeUpdate();
            System.out.println(success + " modalidade(s) ministrada(s) deletada(s)!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar modalidades ministradas: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Modalidades_Ministradas m = new Modalidades_Ministradas();
        boolean quitRequested = false;

        if (!quitRequested) {
            String nome = JOptionPane.showInputDialog("Insira o código da modalidade ministrada");
            if (nome == null || nome.length() == 0) {
                quitRequested = true;
            } else {
                m.setCodigo_modalidade(Integer.parseInt(nome));
            }
        }

        if (!quitRequested) {
            String desc = JOptionPane.showInputDialog("Insira o código do professor que a ministra");
            if (desc != null && desc.length() > 0) {
                m.setCodigo_professor(Integer.parseInt(desc));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            insereModalidades_Ministradas(m);
        }

    }

    public void MenuDeleta() {

        String cod_mod = JOptionPane.showInputDialog("Digite o código da modalidade a deletar");
        if (cod_mod != null && cod_mod.length() > 0) {
            String cod_prof = JOptionPane.showInputDialog("Digite o código do professor que a ministra");
            if (cod_prof != null && cod_prof.length() > 0) {
                deletaModalidades_Ministradas(Integer.parseInt(cod_mod), Integer.parseInt(cod_prof));
            }
        }
    }

    public void MenuConsulta() {
        JFrame frame = new JFrame("Escolha o tipo de consulta");
        frame.setBounds(300, 300, 200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b1 = new JButton("Código de modalidade");
        b1.setActionCommand("mod");
        b1.setMnemonic('M');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("mod")) {
                    String mat = JOptionPane.showInputDialog("Digite o código da modalidade a consultar");
                    if (mat != null && mat.length() > 0) {
                        int matricula = Integer.parseInt(mat);
                        List<Modalidades_Ministradas> resultado = buscaModalidade(matricula);

                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Professor", "Modalidade");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma modalidade ministrada encontrada");
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Código de professor");
        b2.setActionCommand("prof");
        b2.setMnemonic('P');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("prof")) {
                    String cod_prof = JOptionPane.showInputDialog("Digite o código de professor a consultar");
                    if (cod_prof != null && cod_prof.length() > 0) {
                        List<Modalidades_Ministradas> resultado = buscaProfessor(Integer.parseInt(cod_prof));
                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Professor", "Modalidade");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma modalidade ministrada encontrada");
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

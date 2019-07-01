package model.DAO;

import model.bean.Inscricao;
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
 * InscricaoDAO
 */
public class InscricaoDAO {

    public void insereInscricao(Inscricao i) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `inscricao`(`matricula_usuario`, `codigo_modalidade`) VALUES (?, ?)");
            stmt.setInt(1, i.getMatricula_usuario());
            stmt.setInt(2, i.getCodigo_modalidade());
            stmt.executeUpdate();
            System.out.println("Inscrição inserida com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de inscrição: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Inscricao> buscaMatricula(int matricula) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Inscricao> inscricoes = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `inscricao` WHERE `matricula_usuario` = " + matricula);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Inscricao i = new Inscricao();
                i.setMatricula_usuario(rs.getInt("matricula_usuario"));
                i.setCodigo_modalidade(rs.getInt("codigo_modalidade"));
                inscricoes.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Erro buscando por matrícula: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return inscricoes;
    }

    public List<Inscricao> buscaModalidade(int cod_mod) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Inscricao> inscricoes = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `inscricao` WHERE `codigo_modalidade` = " + cod_mod);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Inscricao i = new Inscricao();
                i.setMatricula_usuario(rs.getInt("matricula_usuario"));
                i.setCodigo_modalidade(rs.getInt("codigo_modalidade"));
                inscricoes.add(i);
            }
        } catch (SQLException ex) {
            System.out.println("Erro buscando por matrícula: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return inscricoes;
    }

    public int deletaInscricao(int matricula, int cod_mod) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `inscricao` WHERE (`matricula_usuario`, `codigo_modalidade`) = ("
                    + matricula + ", " + cod_mod + ")");
            success = stmt.executeUpdate();
            System.out.println(success + " inscrição(ões) deletada(s)!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar inscrição: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Inscricao insc = new Inscricao();
        boolean quitRequested = false;

        if (!quitRequested) {
            String matricula = JOptionPane.showInputDialog("Insira a matrícula do usuário");
            if (matricula == null || matricula.length() == 0) {
                quitRequested = true;
            } else {
                insc.setMatricula_usuario(Integer.parseInt(matricula));
            }
        }

        if (!quitRequested) {
            String cod_mod = JOptionPane.showInputDialog("Insira o código da modalidade em que se inscreveu");
            if (cod_mod != null && cod_mod.length() > 0) {
                insc.setCodigo_modalidade(Integer.parseInt(cod_mod));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            insereInscricao(insc);
        }

    }

    public void MenuDeleta() {

        String matricula = JOptionPane.showInputDialog("Insira a matrícula do usuário");
        if (matricula != null && matricula.length() > 0) {
            String cod_mod = JOptionPane.showInputDialog("Insira o código da modalidade de que se desinscreveu");
            if (cod_mod != null && cod_mod.length() > 0) {
                deletaInscricao(Integer.parseInt(matricula), Integer.parseInt(cod_mod));
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
                        List<Inscricao> resultado = buscaMatricula(matricula);

                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Usuário", "Modalidade");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma inscrição encontrada");
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Modalidade");
        b2.setActionCommand("mod");
        b2.setMnemonic('O');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("mod")) {
                    String cod_prof = JOptionPane.showInputDialog("Digite o código de modalidade a consultar");
                    if (cod_prof != null && cod_prof.length() > 0) {
                        List<Inscricao> resultado = buscaModalidade(Integer.parseInt(cod_prof));
                        if (resultado != null && resultado.size() > 0) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Usuário", "Modalidade");
                            for (int i = 0; i < resultado.size(); i++) {
                                resultado.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhuma inscrição encontrada");
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
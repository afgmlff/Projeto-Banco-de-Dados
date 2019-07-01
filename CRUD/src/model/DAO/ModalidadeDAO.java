package model.DAO;

import model.bean.Modalidade;
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

public class ModalidadeDAO {
    String s = null;

    public void insereModalidade(Modalidade m) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `modalidade`(`nome`, `descricao`, `dias`, `horarios`) VALUES (?, ?, ?, ?)");
            stmt.setString(1, m.getNome());
            stmt.setString(2, m.getDescricao());
            stmt.setString(3, m.getDias());
            stmt.setString(4, m.getHorarios());
            stmt.executeUpdate();
            System.out.println("Modalidade inserida com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de modalidade: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Modalidade buscaCodigo(int codigo) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Modalidade m = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM `modalidade` WHERE `codigo_modalidade` = " + codigo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                m = new Modalidade();
                m.setCodigo_modalidade(rs.getInt("codigo_modalidade"));
                m.setNome(rs.getString("nome"));
                m.setDescricao(rs.getString("descricao"));
                m.setDias(rs.getString("dias"));
                m.setHorarios(rs.getString("horarios"));
            }
        } catch (SQLException ex) {
            System.out.println("Erro buscando código de modalidade: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return m;
    }

    public List<Modalidade> buscaNome(String nome) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Modalidade> modalidades = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `modalidade` WHERE `nome` LIKE '%" + nome + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Modalidade m = new Modalidade();
                m.setCodigo_modalidade(rs.getInt("codigo_modalidade"));
                m.setNome(rs.getString("nome"));
                m.setDescricao(rs.getString("descricao"));
                m.setDias(rs.getString("dias"));
                m.setHorarios(rs.getString("horarios"));
                modalidades.add(m);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando nome de modalidade: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return modalidades;
    }

    public int deletaModalidade(int matricula) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `modalidade` WHERE `codigo_modalidade` = " + matricula);
            success = stmt.executeUpdate();
            System.out.println(success + " modalidade(s) deletadas!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar modalidade: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Modalidade m = new Modalidade();
        boolean quitRequested = false;

        if (!quitRequested) {
            String nome = JOptionPane.showInputDialog("Insira o nome da modalidade");
            m.setNome(nome);
            if (nome == null || nome.length() == 0) {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String desc = JOptionPane.showInputDialog("Insira a descrição da modalidade");
            if (desc != null && desc.length() > 0) {
                m.setDescricao(desc);
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String dias = JOptionPane.showInputDialog("Insira os dias da modalidade");
            if (dias != null && dias.length() > 0) {
                m.setDias(dias);
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String horarios = JOptionPane.showInputDialog("Insira os horários da modalidade");
            m.setHorarios(horarios);
            if (horarios == null || horarios.length() == 0) {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            insereModalidade(m);
        }

    }

    public void MenuDeleta() {

        String cod = JOptionPane.showInputDialog("Digite o código da modalidade a deletar");
        if (cod != null && cod.length() > 0) {
            deletaModalidade(Integer.parseInt(cod));
        }
    }

    public void MenuConsulta() {
        JFrame frame = new JFrame("Escolha o tipo de consulta");
        frame.setBounds(300, 300, 200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b1 = new JButton("Código");
        b1.setActionCommand("cod");
        b1.setMnemonic('M');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("cod")) {
                    String mat = JOptionPane.showInputDialog("Digite o código a consultar");
                    if (mat != null && mat.length() > 0) {
                        int matricula = Integer.parseInt(mat);
                        Modalidade u = buscaCodigo(matricula);

                        if (u != null) {
                            String format = "%-15s%-30s%-30s%-30s%-30s\n";
                            System.out.printf(format, "Código", "Nome", "Descrição", "Dias", "Horários");
                            u.Display();
                        } else {
                            System.out.println("Nenhuma modalidade encontrada");
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Nome");
        b2.setActionCommand("nome");
        b2.setMnemonic('N');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("nome")) {
                    String nome = JOptionPane.showInputDialog("Digite o nome a buscar");
                    List<Modalidade> resultado = buscaNome(nome);
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-15s%-30s%-30s%-30s%-30s\n";
                        System.out.printf(format, "Código", "Nome", "Descrição", "Dias", "Horários");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhuma modalidade encontrada");
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

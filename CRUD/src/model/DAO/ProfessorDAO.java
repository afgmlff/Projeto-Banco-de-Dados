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
import model.bean.Professor;

/**
 * ProfessorDAO
 */
public class ProfessorDAO {

    public void insereProfessor(Professor man) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO `professor`(`codigo_funcionario`) VALUES(?)");
            stmt.setInt(1, man.getCodigo_funcionario());
            stmt.executeUpdate();
            System.out.println("Professor inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Professor buscaRegistro(int reg) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Professor man = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM `professor` WHERE `codigo_funcionario` = " + reg);
            rs = stmt.executeQuery();

            if (rs.next()) {
                man = new Professor();
                man.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return man;
    }

    public List<Professor> buscaTodos() {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Professor> funcs_manu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `professor`");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Professor man = new Professor();
                man = new Professor();
                man.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                funcs_manu.add(man);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando professores: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return funcs_manu;
    }

    public int deletaProfessor(int reg) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `professor` WHERE `codigo_funcionario` = " + reg);
            stmt.executeUpdate();
            success = 1;
            System.out.println("Professor deletado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar professor: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Professor man = new Professor();
        boolean quitRequested = false;

        if (!quitRequested) {
            String cod = JOptionPane.showInputDialog("Digite o código de funcionário");
            if (cod == null || cod.length() == 0) {
                quitRequested = true;
            } else {
                man.setCodigo_funcionario(Integer.parseInt(cod));
            }
        }

        if (!quitRequested) {
            insereProfessor(man);
        }
    }

    public void MenuDeleta() {

        String reg = JOptionPane.showInputDialog("Digite o registro do professor a deletar");
        if (reg != null && reg.length() > 0) {
            deletaProfessor(Integer.parseInt(reg));
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
                        Professor f = buscaRegistro(registro);

                        if (f != null) {
                            String format = "%-10s\n";
                            System.out.printf(format, "Registro");
                            f.Display();
                        } else {
                            System.out.println("Nenhum professor encontrado");
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
                    List<Professor> resultado = buscaTodos();
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-10s\n";
                        System.out.printf(format, "Registro");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhum professor encontrado");
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
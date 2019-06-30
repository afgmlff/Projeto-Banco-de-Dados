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
import model.bean.Manutencao;

/**
 * ManutencaoDAO
 */
public class ManutencaoDAO {

    
    public void insereManutencao(Manutencao man) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO `manutencao`(`codigo_funcionario`, `area`) VALUES(?, ?)");
            stmt.setInt(1, man.getCodigo_funcionario());
            stmt.setString(2, man.getArea());
            stmt.executeUpdate();
            System.out.println("Funcionário de manutenção inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Manutencao buscaRegistro(int reg) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Manutencao man = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM `manutencao` WHERE `codigo_funcionario` = " + reg);
            rs = stmt.executeQuery();

            if (rs.next()) {
                man = new Manutencao();
                man.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                man.setArea(rs.getString("area"));
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return man;
    }

    public List<Manutencao> buscaTodos() {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Manutencao> funcs_manu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `manutencao`");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Manutencao man = new Manutencao();
                man = new Manutencao();
                man.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                man.setArea(rs.getString("area"));
                funcs_manu.add(man);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return funcs_manu;
    }

    public int deletaManutencao(int reg) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `manutencao` WHERE `codigo_funcionario` = " + reg);
            stmt.executeUpdate();
            success = 1;
            System.out.println("Funcionário deletado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar funcionário: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Manutencao man = new Manutencao();
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
            String area = JOptionPane.showInputDialog("Digite a área do funcionário");
            if (area == null || area.length() == 0) {
                quitRequested = true;
            } else {
                man.setArea(area);
            }
        }

        if (!quitRequested) {
            insereManutencao(man);
        }
    }

    public void MenuDeleta() {

        String reg = JOptionPane.showInputDialog("Digite o registro do funcionário a deletar");
        if (reg != null && reg.length() > 0) {
            deletaManutencao(Integer.parseInt(reg));
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
                        Manutencao f = buscaRegistro(registro);

                        if (f != null) {
                            String format = "%-10s%-40s\n";
                            System.out.printf(format, "Registro", "Área");
                            f.Display();
                        } else {
                            System.out.println("Nenhum funcionário encontrado");
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
                    List<Manutencao> resultado = buscaTodos();
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-10s%-40s\n";
                        System.out.printf(format, "Registro", "Área");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhum funcionário encontrado");
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
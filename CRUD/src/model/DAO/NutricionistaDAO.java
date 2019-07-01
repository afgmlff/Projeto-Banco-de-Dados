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
import model.bean.Nutricionista;

/**
 * NutricionistaDAO
 */
public class NutricionistaDAO {

    
    public void insereNutricionista(Nutricionista nutri) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO `nutricionista`(`codigo_funcionario`, `CFN`) VALUES(?, ?)");
            stmt.setInt(1, nutri.getCodigo_funcionario());
            stmt.setInt(2, nutri.getCFN());
            stmt.executeUpdate();
            System.out.println("Nutricionista inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Nutricionista buscaRegistro(int reg) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Nutricionista nutri = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM `nutricionista` WHERE `codigo_funcionario` = " + reg);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nutri = new Nutricionista();
                nutri.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                nutri.setCFN(rs.getInt("CFN"));
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return nutri;
    }

    public List<Nutricionista> buscaTodos() {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Nutricionista> nutris = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `nutricionista`");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Nutricionista nutri = new Nutricionista();
                nutri = new Nutricionista();
                nutri.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                nutri.setCFN(rs.getInt("CFN"));
                nutris.add(nutri);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando nutricionistas: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return nutris;
    }

    public int deletaNutricionista(int reg) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `nutricionista` WHERE `codigo_funcionario` = " + reg);
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

        Nutricionista nutri = new Nutricionista();
        boolean quitRequested = false;

        if (!quitRequested) {
            String cod = JOptionPane.showInputDialog("Digite o código do funcionário");
            if (cod == null || cod.length() == 0) {
                quitRequested = true;
            } else {
                nutri.setCodigo_funcionario(Integer.parseInt(cod));
            }
        }

        if (!quitRequested) {
            String cfn = JOptionPane.showInputDialog("Digite o CFN do(a) nutricionista");
            if (cfn == null || cfn.length() == 0) {
                quitRequested = true;
            } else {
                nutri.setCFN(Integer.parseInt(cfn));
            }
        }

        if (!quitRequested) {
            insereNutricionista(nutri);
        }
    }

    public void MenuDeleta() {

        String reg = JOptionPane.showInputDialog("Digite o registro do funcionário a deletar");
        if (reg != null && reg.length() > 0) {
            deletaNutricionista(Integer.parseInt(reg));
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
                        Nutricionista f = buscaRegistro(registro);

                        if (f != null) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Registro", "CFN");
                            f.Display();
                        } else {
                            System.out.println("Nenhum nutricionista encontrado");
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
                    List<Nutricionista> resultado = buscaTodos();
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-15s%-15s\n";
                        System.out.printf(format, "Registro", "CFN");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhum nutricionista encontrado");
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
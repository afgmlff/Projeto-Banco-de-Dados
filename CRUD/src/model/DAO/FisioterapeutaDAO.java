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
import model.bean.Fisioterapeuta;

/**
 * FisioterapeutaDAO
 */
public class FisioterapeutaDAO {

    
    public void insereFisioterapeuta(Fisioterapeuta fisio) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO `fisioterapeuta`(`codigo_funcionario`, `CREFITO`) VALUES(?, ?)");
            stmt.setInt(1, fisio.getCodigo_funcionario());
            stmt.setInt(2, fisio.getCREFITO());
            stmt.executeUpdate();
            System.out.println("Fisioterapeuta inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Fisioterapeuta buscaRegistro(int reg) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Fisioterapeuta nutri = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM `fisioterapeuta` WHERE `codigo_funcionario` = " + reg);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nutri = new Fisioterapeuta();
                nutri.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                nutri.setCREFITO(rs.getInt("CREFITO"));
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return nutri;
    }

    public List<Fisioterapeuta> buscaTodos() {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Fisioterapeuta> nutris = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `fisioterapeuta`");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Fisioterapeuta nutri = new Fisioterapeuta();
                nutri = new Fisioterapeuta();
                nutri.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                nutri.setCREFITO(rs.getInt("CREFITO"));
                nutris.add(nutri);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando fisioterapeutas: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return nutris;
    }

    public int deletaFisioterapeuta(int reg) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `fisioterapeuta` WHERE `codigo_funcionario` = " + reg);
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

        Fisioterapeuta nutri = new Fisioterapeuta();
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
            String cfn = JOptionPane.showInputDialog("Digite o CREFITO do(a) fisioterapeuta");
            if (cfn == null || cfn.length() == 0) {
                quitRequested = true;
            } else {
                nutri.setCREFITO(Integer.parseInt(cfn));
            }
        }

        if (!quitRequested) {
            insereFisioterapeuta(nutri);
        }
    }

    public void MenuDeleta() {

        String reg = JOptionPane.showInputDialog("Digite o registro do funcionário a deletar");
        if (reg != null && reg.length() > 0) {
            deletaFisioterapeuta(Integer.parseInt(reg));
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
                        Fisioterapeuta f = buscaRegistro(registro);

                        if (f != null) {
                            String format = "%-15s%-15s\n";
                            System.out.printf(format, "Registro", "CREFITO");
                            f.Display();
                        } else {
                            System.out.println("Nenhum fisioterapeuta encontrado");
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
                    List<Fisioterapeuta> resultado = buscaTodos();
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-15s%-15s\n";
                        System.out.printf(format, "Registro", "CREFITO");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhum fisioterapeuta encontrado");
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
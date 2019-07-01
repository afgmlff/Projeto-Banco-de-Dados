package model.DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import model.bean.Reparo;

/**
 * ReparoDAO
 */
public class ReparoDAO {

    public void insereReparo(Reparo rep) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "INSERT INTO `reparo`(`codigo_funcionario`, `codigo_equipamento`, `descricao_reparo`, `data_reparo`) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, rep.getCodigo_funcionario());
            stmt.setInt(2, rep.getCodigo_equipamento());
            stmt.setString(3, rep.getDescricao_reparo());
            stmt.setTimestamp(4, rep.getData_reparo());
            stmt.executeUpdate();
            System.out.println("Reparo inserido com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de reparo: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Reparo> buscaFuncionario(int codigo_funcionario) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Reparo> reparos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `reparo` WHERE `codigo_funcionario` = " + codigo_funcionario);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Reparo rep = new Reparo();
                rep.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                rep.setCodigo_equipamento(rs.getInt("codigo_equipamento"));
                rep.setDescricao_reparo(rs.getString("descricao_reparo"));
                rep.setData_reparo(rs.getTimestamp("data_reparo"));
                reparos.add(rep);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando por código de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return reparos;
    }

    public List<Reparo> buscaEquipamento(int codigo_equipamento) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Reparo> reparos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `reparo` WHERE `codigo_equipamento` = " + codigo_equipamento);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Reparo rep = new Reparo();
                rep.setCodigo_funcionario(rs.getInt("codigo_funcionario"));
                rep.setCodigo_equipamento(rs.getInt("codigo_equipamento"));
                rep.setDescricao_reparo(rs.getString("descricao_reparo"));
                rep.setData_reparo(rs.getTimestamp("data_reparo"));
                reparos.add(rep);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando por código de equipamento: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return reparos;
    }

    public int deletaReparo(int codigo_funcionario, int codigo_equipamento, Date data_reparo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `reparo` WHERE `codigo_funcionario` = " + codigo_funcionario
                    + " AND `codigo_equipamento` = " + codigo_equipamento + " AND DATE(`data_reparo`) LIKE '"
                    + data_reparo.toString() + "%'");
            success = stmt.executeUpdate();
            System.out.println(success + " reparo(s) deletados!");
        } catch (SQLException ex) {
            System.out.println("Erro deletando reparo: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {

        Reparo r = new Reparo();
        boolean quitRequested = false;

        if (!quitRequested) {
            String cod = JOptionPane.showInputDialog("Digite o código do funconário que fez o reparo");
            if (cod != null && cod.length() > 0) {
                r.setCodigo_funcionario(Integer.parseInt(cod));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String cod = JOptionPane.showInputDialog("Digite o código do equipamento reparado");
            if (cod != null && cod.length() > 0) {
                r.setCodigo_equipamento(Integer.parseInt(cod));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String desc = JOptionPane.showInputDialog("Digite a descrição do reparo");
            if (desc != null && desc.length() > 0) {
                r.setDescricao_reparo(desc);
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            String data = JOptionPane.showInputDialog("Digite a data e hora do reparo (yyyy-mm-dd hh:mm:ss)");
            if (data != null && data.length() > 0) {
                r.setData_reparo(Timestamp.valueOf(data));
            } else {
                quitRequested = true;
            }
        }

        if (!quitRequested) {
            insereReparo(r);
        }
    }

    public void MenuDeleta() {

        String cod_func = JOptionPane.showInputDialog("Digite o código de funcionário do reparo a deletar");
        if (cod_func != null && cod_func.length() > 0) {
            String cod_eq = JOptionPane.showInputDialog("Digite o código do equipament do reparo a deletar");
            if (cod_eq != null && cod_eq.length() > 0) {
                String datahora = JOptionPane.showInputDialog("Digite a data do reparo (yyyy-mm-dd)");
                if (datahora != null && datahora.length() > 0) {
                    deletaReparo(Integer.parseInt(cod_func), Integer.parseInt(cod_eq), Date.valueOf(datahora));
                }
            }
        }
    }

    public void MenuConsulta() {
        JFrame frame = new JFrame("Escolha o tipo de consulta");
        frame.setBounds(300, 300, 200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b1 = new JButton("Código de funcionário");
        b1.setActionCommand("cod");
        b1.setMnemonic('F');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("cod")) {
                    String cod = JOptionPane.showInputDialog("Digite o código de funcionário a consultar");
                    if (cod != null && cod.length() > 0) {
                        int codigo_func = Integer.parseInt(cod);
                        List<Reparo> reps = buscaFuncionario(codigo_func);

                        if (reps != null && reps.size() > 0) {
                            String format = "%-15s%-15s%-40s%-20s\n";
                            System.out.printf(format, "Funcionário", "Equipamento", "Descrição", "Data-Hora");
                            for (int i = 0; i < reps.size(); i++) {
                                reps.get(i).Display();
                            }
                        } else {
                            System.out.println("Nenhum reparo encontrado");
                        }
                    }
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        JButton b2 = new JButton("Código de Equipamento");
        b2.setActionCommand("equi");
        b2.setMnemonic('E');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("equi")) {
                    String cod = JOptionPane.showInputDialog("Digite o código de equipamento a consultar");
                    List<Reparo> reparos = buscaEquipamento(Integer.parseInt(cod));
                    if (reparos != null && reparos.size() > 0) {
                        String format = "%-15s%-15s%-40s%-20s\n";
                        System.out.printf(format, "Funcionário", "Equipamento", "Descrição", "Data-Hora");
                        for (int i = 0; i < reparos.size(); i++) {
                            reparos.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhum reparo encontrado");
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
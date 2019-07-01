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
import model.bean.Funcionario;

public class FuncionarioDAO {

    public void insereFuncionario(Funcionario fun) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO `funcionario`(`nome`, `endereco`) VALUES(?, ?)");
            stmt.setString(1, fun.getNome());
            stmt.setString(2, fun.getEndereco());
            stmt.executeUpdate();
            System.out.println("Funcionario inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Funcionario buscaRegistro(int reg) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Funcionario func = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM `funcionario` WHERE `registro_funcionario` = " + reg);
            rs = stmt.executeQuery();

            if (rs.next()) {
                func = new Funcionario();
                func.setRegistro_funcionario(rs.getInt("registro_funcionario"));
                func.setEndereco(rs.getString("endereco"));
                func.setNome(rs.getString("nome"));
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando registro de funcionário: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return func;
    }

    public List<Funcionario> buscaNome(String nome) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM `funcionario` WHERE `nome` LIKE '%" + nome + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario func = new Funcionario();
                func.setRegistro_funcionario(rs.getInt("registro_funcionario"));
                func.setEndereco(rs.getString("endereco"));
                func.setNome(rs.getString("nome"));
                funcionarios.add(func);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando nome: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return funcionarios;
    }

    public int deletaFuncionario(int reg) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `funcionario` WHERE `registro_funcionario` = " + reg);
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

        Funcionario fun = new Funcionario();
        boolean quitRequested = false;

        if (!quitRequested) {
            String nome = JOptionPane.showInputDialog("Digite o nome do funcionário");
            if (nome == null || nome.length() == 0) {
                quitRequested = true;
            } else {
                fun.setNome(nome);
            }
        }

        if (!quitRequested) {
            String endereco = JOptionPane.showInputDialog("Digite o endereço do funcionário");
            if (endereco == null || endereco.length() == 0) {
                quitRequested = true;
            } else {
                fun.setEndereco(endereco);
            }
        }

        if (!quitRequested) {
            insereFuncionario(fun);
        }
    }

    public void MenuDeleta() {

        String reg = JOptionPane.showInputDialog("Digite o registro do funcionário a deletar");
        if (reg != null && reg.length() > 0) {
            deletaFuncionario(Integer.parseInt(reg));
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
                        Funcionario f = buscaRegistro(registro);

                        if (f != null) {
                            String format = "%-10s%-30s%-30s\n";
                            System.out.printf(format, "Registro", "Nome", "Endereço");
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
        JButton b2 = new JButton("Nome");
        b2.setActionCommand("nome");
        b2.setMnemonic('N');
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("nome")) {
                    String nome = JOptionPane.showInputDialog("Digite o nome a buscar");
                    List<Funcionario> resultado = buscaNome(nome);
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-10s%-30s%-30s\n";
                        System.out.printf(format, "Registro", "Nome", "Endereço");
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
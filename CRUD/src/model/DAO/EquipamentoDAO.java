package model.DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import model.bean.Equipamento;
import model.bean.MenuVideo;

/**
 * EquipamentoDAO
 */
public class EquipamentoDAO {

    String s = null;
    private byte[] vidbytes = null;

    public void insereEquipamento(Equipamento equi) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SET GLOBAL max_allowed_packet=10000000");
            stmt.executeUpdate();
            stmt.close();
            stmt = con.prepareStatement("INSERT INTO `equipamento` (`nome`, `video_funcionamento`) VALUES(?, ?)");
            stmt.setString(1, equi.getNome());
            stmt.setBlob(2, equi.getVideo());
            stmt.executeUpdate();
            stmt.close();
            stmt = con.prepareStatement("SET GLOBAL max_allowed_packet=1048576");
            stmt.executeUpdate();
            System.out.println("Equipamento inserido com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro na inserção de equipamento: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Equipamento buscaCodigo(int cod) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Equipamento equi = null;

        try {
            stmt = con.prepareStatement("SET GLOBAL max_allowed_packet=10000000");
            stmt.executeUpdate();
            stmt.close();
            stmt = con.prepareStatement("SELECT `codigo_quipamento`, `nome` FROM `equipamento` WHERE `codigo_equipamento` = " + cod);
            rs = stmt.executeQuery();
            if (rs.next()) {
                equi = new Equipamento();
                equi.setCodigo_equipamento(rs.getInt("codigo_equipamento"));
                equi.setNome(rs.getString("nome"));
                equi.setVideo(null);
            }
        } catch (SQLException ex) {
            System.out.println("Erro buscando código: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        try {
            con = ConnectionFactory.getConnection();
            stmt = con.prepareStatement("SET GLOBAL max_allowed_packet=1048576");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equi;
    }

    public List<Equipamento> buscaNome(String nome) {
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        List<Equipamento> equipamentos = new ArrayList<>();

        try {
            stmt = con.prepareStatement(
                    "SELECT `codigo_equipamento`, `nome` FROM `equipamento` WHERE `nome` LIKE '%" + nome + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Equipamento equi = new Equipamento();
                equi.setCodigo_equipamento(rs.getInt("codigo_equipamento"));
                equi.setNome(rs.getString("nome"));
                equipamentos.add(equi);
            }

        } catch (SQLException ex) {
            System.out.println("Erro buscando nome: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return equipamentos;
    }

    public int deletaEquipamento(int codigo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int success = 0;

        try {
            stmt = con.prepareStatement("DELETE FROM `equipamento` WHERE `codigo_equipamento` = " + codigo);
            stmt.executeUpdate();
            success = 1;
            System.out.println("Equipamento deletado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar equipamento: " + ex.getMessage());
            success = 0;
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

        return success;
    }

    public void MenuInsere() {
        Equipamento equi = new Equipamento();
        boolean quitRequested = false;

        if (!quitRequested) {
            String nome = JOptionPane.showInputDialog("Insira o nome do equipamento");
            equi.setNome(nome);
            if (nome == null || nome.length() == 0) {
                quitRequested = true;
            }
        }

        if (!quitRequested) {

            s = MenuVideo.CriaJanela();
            if (s != null && s.length() > 0) {
                vidbytes = VideoParaBytes(s);
                try {
                    equi.setVideo(new SerialBlob(vidbytes));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                equi.setVideo(null);
            }
        }

        if (!quitRequested) {
            insereEquipamento(equi);
        }
    }

    private byte[] VideoParaBytes(String path) {
        File f = null;
        FileInputStream is = null;
        f = new File(path);
        byte[] bytes = new byte[(int) f.length()];
        try {
            is = new FileInputStream(f);
            is.read(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return bytes;
    }

    public void MenuDeleta() {
        String codigo = JOptionPane.showInputDialog("Digite o código do equipamento a deletar");
        if (codigo != null && codigo.length() > 0) {
            deletaEquipamento(Integer.parseInt(codigo));
        }
    }

    public void MenuConsulta() {
        JFrame frame = new JFrame("Escolha o tipo de consulta");
        frame.setBounds(300, 300, 200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton b1 = new JButton("Código");
        b1.setActionCommand("cod");
        b1.setMnemonic('C');
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("cod")) {
                    String cod = JOptionPane.showInputDialog("Digite o código a consultar");
                    if (cod != null && cod.length() > 0) {
                        int codigo = Integer.parseInt(cod);
                        Equipamento equi = buscaCodigo(codigo);

                        if (equi != null) {
                            String format = "%-10s%-40s\n";
                            System.out.printf(format, "Código", "Nome");
                            equi.Display();
                            if (equi.getVideo() != null) {
                                FileOutputStream os = null;
                                String path = null;
                                try {
                                    if (equi.getNome().length() < 20) {
                                        path = (equi.getNome() + "_funcionamento.mp4");
                                    } else {
                                        path = (equi.getNome().substring(0, 20) + "_funcionamento.mp4");
                                    }
                                    os = new FileOutputStream(path);
                                    os.write(vidbytes);
                                    System.out.println("Video salvo: " + path);
                                } catch (Exception ex) {
                                    System.out.println("Erro salvando vídeo: " + ex.getMessage());
                                } finally {
                                    try {
                                        os.close();
                                    } catch (IOException e1) {
                                        System.out.println("Erro fechando arquivo: " + e1.getMessage());
                                    }
                                }
                            } else {
                                System.out.println("Nenhum equipamento encontrado");
                            }
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
                    List<Equipamento> resultado = buscaNome(nome);
                    if (resultado != null && resultado.size() > 0) {
                        String format = "%-10s%-40s\n";
                        System.out.printf(format, "Código", "Nome");
                        for (int i = 0; i < resultado.size(); i++) {
                            resultado.get(i).Display();
                        }
                    } else {
                        System.out.println("Nenhum equipamento encontrado");
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
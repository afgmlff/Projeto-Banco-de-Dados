package model.DAO;

import model.bean.Usuario;
import connection.ConnectionFactory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class UsuarioDAO {
	String s = null;

	public void insereUsuario(Usuario u) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement(
					"INSERT INTO `usuario`(`nome`, `endereco`, `data_nascimento`, `data_inicio`, `foto`) VALUES (?, ?, ?, ?, ?)");
			stmt.setString(1, u.getNome());
			stmt.setString(2, u.getEndereco());
			stmt.setDate(3, u.getData_nascimento());
			stmt.setDate(4, u.getData_inicio());
			stmt.setBlob(5, u.getFoto());
			stmt.executeUpdate();
			System.out.println("Usuário inserido com sucesso!");
		} catch (SQLException ex) {
			System.out.println("Erro na inserçãoo de usuário: " + ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public Usuario buscaMatricula(int matricula) {
		Connection con = ConnectionFactory.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Usuario u = null;

		try {
			stmt = con.prepareStatement("SELECT * FROM `usuario` WHERE `matricula` = " + matricula);
			rs = stmt.executeQuery();
			if (rs.next()) {
				u = new Usuario();
				u.setMatricula(rs.getInt("matricula"));
				u.setNome(rs.getString("nome"));
				u.setEndereco(rs.getString("endereco"));
				u.setData_nascimento(rs.getDate("data_nascimento"));
				u.setData_inicio(rs.getDate("data_inicio"));
				u.setFoto(rs.getBlob("foto"));
			}
		} catch (SQLException ex) {
			System.out.println("Erro buscando matricula: " + ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(con, stmt, rs);
		}

		return u;
	}

	public List<Usuario> buscaNome(String nome) {
		Connection con = ConnectionFactory.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		List<Usuario> usuarios = new ArrayList<>();

		try {
			stmt = con.prepareStatement("SELECT * FROM `usuario` WHERE `nome` LIKE '%" + nome + "%'");
			rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario u = new Usuario();
				u.setMatricula(rs.getInt("matricula"));
				u.setNome(rs.getString("nome"));
				u.setEndereco(rs.getString("endereco"));
				u.setData_nascimento(rs.getDate("data_nascimento"));
				u.setData_inicio(rs.getDate("data_inicio"));
				u.setFoto(rs.getBlob("foto"));
				usuarios.add(u);
			}

		} catch (SQLException ex) {
			System.out.println("Erro buscando matricula: " + ex.getMessage());
		} finally {
			ConnectionFactory.closeConnection(con, stmt, rs);
		}

		return usuarios;
	}

	public int deletaUsuario(int matricula) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		boolean success = false;

		try {
			stmt = con.prepareStatement("DELETE FROM `usuario` WHERE `matricula` = " + matricula);
			stmt.executeUpdate();
			success = true;
		} catch (SQLException ex) {
			System.out.println("Erro ao deletar usuario: " + ex.getMessage());
			success = false;
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}

		return (success ? 1 : 0);
	}

	public void MenuInsere() {

		Usuario u = new Usuario();
		boolean quitRequested = false;

		if (!quitRequested) {
			String nome = JOptionPane.showInputDialog("Insira o nome do usuário");
			u.setNome(nome);
			if (nome == null || nome.length() == 0) {
				quitRequested = true;
			}
		}

		if (!quitRequested) {
			String data_nascimento = JOptionPane.showInputDialog("Insira a data de nascimento (yyyy-mm-dd)");
			Date data = null;
			if (data_nascimento != null && data_nascimento.length() > 0) {
				data = Date.valueOf(data_nascimento);
				u.setData_nascimento(data);
			} else {
				quitRequested = true;
			}
		}

		if (!quitRequested) {
			String data_inicio = JOptionPane.showInputDialog("Insira a data de ingresso na academia (yyyy-mm-dd)");
			Date data = null;
			if (data_inicio != null && data_inicio.length() > 0) {
				data = Date.valueOf(data_inicio);
				u.setData_inicio(data);
			} else {
				quitRequested = true;
			}
		}

		if (!quitRequested) {
			String endereco = JOptionPane.showInputDialog("Insira o endereço do usuário");
			u.setEndereco(endereco);
			if (endereco == null || endereco.length() == 0) {
				quitRequested = true;
			}
		}

		if (!quitRequested) {
			s = MenuDAO.criaJanela();

			if (s != null && s.length() > 0) {
				try {
					u.setFoto(new SerialBlob(arquivoParaBytes(s)));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				quitRequested = true;
			}
		}

		if (!quitRequested) {
			insereUsuario(u);
		}

	}

	public byte[] arquivoParaBytes(String pathArq) {

		File f = new File(pathArq);

		byte[] blob = new byte[(int) f.length()];

		FileInputStream is = null;

		try {
			is = new FileInputStream(f);
			is.read(blob);
		} catch (IOException e) {
			System.out.println("Não foi possível converter para blob: " + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		return blob;
	}

	public void MenuDeleta() {

		String mat = JOptionPane.showInputDialog("Digite a matrícula a deletar");
		if (mat != null && mat.length() > 0) {
			deletaUsuario(Integer.parseInt(mat));
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
						Usuario u = buscaMatricula(matricula);

						if (u != null) {
							String format = "%-10s%-30s%-30s%-15s%-15s\n";
							System.out.printf(format, "Matr", "Nome", "Endereço", "Nascimento", "Início");
							u.Display();
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
				String nome = JOptionPane.showInputDialog("Digite o nome a buscar");
				List<Usuario> resultado = buscaNome(nome);
				String format = "%-10s%-30s%-30s%-15s%-15s\n";
				System.out.printf(format, "Matr", "Nome", "Endereço", "Nascimento", "Inicio");
				for (int i = 0; i < resultado.size(); i++) {
					resultado.get(i).Display();
				}
				frame.setVisible(false);
				frame.dispose();
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

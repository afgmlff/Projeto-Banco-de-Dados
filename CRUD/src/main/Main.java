package main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import connection.ConnectionFactory;
import main.MainMenu;

public class Main {

	public static void main(String[] args) {

		Connection con = null;
		boolean successCon = false;

		while (!successCon) {
			String user = JOptionPane.showInputDialog("Informe o usuário de acesso ao BD");

			String senha = JOptionPane.showInputDialog("Informe a senha do usuário");

			ConnectionFactory.setUser(user);
			ConnectionFactory.setPassword(senha);

			con = ConnectionFactory.getConnection();

			try {
				if (con == null || con.isClosed()) {
					System.out.println("Erro ao criar conexão com o banco!");
				} else {
					System.out.println("Conexão bem sucedida!");
					successCon = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainMenu.criaJanela();
			}
		});

	}
}

package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.DAO.UsuarioDAO;

public class MainMenu extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton b1, b2, b3;
	protected UsuarioDAO udao = new UsuarioDAO();

	public MainMenu() {
		b1 = new JButton("Cadastrar");
		b1.setActionCommand("cadastro");
		b2 = new JButton("Consultar");
		b2.setActionCommand("consulta");
		b3 = new JButton("Deletar");
		b3.setActionCommand("delecao");

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		add(b1);
		add(b2);
		add(b3);
	}

	public void actionPerformed(ActionEvent e) {
		if ("cadastro".equals(e.getActionCommand())) {
			udao.MenuInsere();
		} else if ("consulta".equals(e.getActionCommand())) {
			udao.MenuConsulta();
		} else if ("delecao".equals(e.getActionCommand())) {
			udao.MenuDeleta();
		}
	}

	public static void criaJanela() {
		JFrame frame = new JFrame("CRUD - Academia");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainMenu menu = new MainMenu();
		menu.setOpaque(true);
		frame.setContentPane(menu);
		frame.pack();
		frame.setVisible(true);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dim.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);

	}
}

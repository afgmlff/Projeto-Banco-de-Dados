package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.DAO.MenuDAO;

public class MainMenu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final char CADASTRO = 'k', CONSULTA = 'c', DELECAO = 'd', ATUALIZACAO = 'a';
	protected JButton b1, b2, b3, b4;
	protected char op;

	public MainMenu() {
		b1 = new JButton("Cadastrar");
		b1.setActionCommand("cadastro");
		b1.setMnemonic(KeyEvent.VK_C);
		b2 = new JButton("Consultar");
		b2.setActionCommand("consulta");
		b2.setMnemonic(KeyEvent.VK_O);
		b3 = new JButton("Deletar");
		b3.setActionCommand("delecao");
		b3.setMnemonic(KeyEvent.VK_D);
		b4 = new JButton("Atualizar");
		b4.setActionCommand("atualizacao");
		b4.setMnemonic(KeyEvent.VK_A);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		add(b1);
		add(b2);
		add(b3);
		add(b4);
	}

	public void actionPerformed(ActionEvent e) {
		if ("cadastro".equals(e.getActionCommand())) {
			MenuDAO.criaJanela(CADASTRO);
		} else if ("consulta".equals(e.getActionCommand())) {
			MenuDAO.criaJanela(CONSULTA);
		} else if ("delecao".equals(e.getActionCommand())) {
			MenuDAO.criaJanela(DELECAO);
		} else if("atualizacao".equals(e.getActionCommand())) {
			MenuDAO.criaJanela(ATUALIZACAO);
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

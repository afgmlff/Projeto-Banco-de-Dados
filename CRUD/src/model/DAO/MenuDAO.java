package model.DAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.MainMenu;

/**
 * MenuDAO
 */
public class MenuDAO extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> lista_tabelas;
    private JButton b1;
    private char op;
    private UsuarioDAO udao = new UsuarioDAO();
    private Usuario_TelefoneDAO utdao = new Usuario_TelefoneDAO();

    public MenuDAO(char op) {

        this.op = op;

        String[] tabelas = { "Administração", "Consulta Fisiologista", "Consulta Nutricionista",
                "Elaboração de Treinos", "Equipamentos", "Estagiários", "Supervisão de Estagiários", "Fisioterapeutas",
                "Funcionários", "Telefones de funcionários", "Inscrição em modalidades", "Modalidades ministradas",
                "Nutricionistas", "Professores", "Especialidades de professores", "Reparo de máquinas", "Usuários",
                "Telefones de usuários" };

        lista_tabelas = new JComboBox<String>(tabelas);
        lista_tabelas.setActionCommand("selecao");
        lista_tabelas.addActionListener(this);

        b1 = new JButton("OK");
        b1.setActionCommand("ok");
        b1.setMnemonic('O');
        b1.addActionListener(this);

        add(lista_tabelas, BorderLayout.PAGE_START);
        add(b1, BorderLayout.PAGE_END);
    }

    public void actionPerformed(ActionEvent e) {
        String tb = (String) lista_tabelas.getSelectedItem();

        if ("ok".equals(e.getActionCommand())) {

            if (tb.equals("Administração")) {
                //
            } else if (tb.equals("Consulta Fisiologista")) {
                //
            } else if (tb.equals("Consulta Nutricionista")) {
                //
            } else if (tb.equals("Elaboração de Treinos")) {
                //
            } else if (tb.equals("Equipamentos")) {
                //
            } else if (tb.equals("Estagiários")) {
                //
            } else if (tb.equals("Supervisão de Estagiários")) {
                //
            } else if (tb.equals("Fisioterapeutas")) {
                //
            } else if (tb.equals("Funcionários")) {
                //
            } else if (tb.equals("Telefones de funcionários")) {
                //
            } else if (tb.equals("Inscrição em modalidades")) {
                //
            } else if (tb.equals("Modalidades ministradas")) {
                //
            } else if (tb.equals("Nutricionistas")) {
                //
            } else if (tb.equals("Professores")) {
                //
            } else if (tb.equals("Especialidades de professores")) {
                //
            } else if (tb.equals("Reparo de máquinas")) {
                //
            } else if (tb.equals("Usuários")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    udao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    udao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    udao.MenuDeleta();
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Telefones de usuários")) {
                switch (op) {
                    case MainMenu.CADASTRO:
                        utdao.MenuInsere();
                        break;
    
                    case MainMenu.CONSULTA:
                        utdao.MenuConsulta();
                        break;
    
                    case MainMenu.DELECAO:
                        utdao.MenuDeleta();
                        break;
    
                    default:
                        break;
                    }
            }
        }
    }

    public static void criaJanela(char oper){
        JFrame frame;

        switch(oper){
            case MainMenu.CADASTRO:
                frame = new JFrame("Selecione a tabela onde deseja cadastrar");
                break;

            case MainMenu.CONSULTA:
                frame = new JFrame("Selecione a tabela que deseja consultar");
                break;

            case MainMenu.DELECAO:
                frame = new JFrame("Selecione a tabela de onde deletar");
                break;

            default:
                frame = new JFrame();
                break;
        }

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JComponent comp = new MenuDAO(oper);
        comp.setOpaque(true);
        frame.setContentPane(comp);

        frame.setVisible(true);
        frame.pack();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dim.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
    }
}
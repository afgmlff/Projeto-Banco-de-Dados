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
import javax.swing.JRootPane;

import main.MainMenu;

/**
 * MenuDAO
 */
public class MenuDAO extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> lista_tabelas;
    private static JButton b1;
    private char op;
    private UsuarioDAO udao = new UsuarioDAO();
    private Usuario_TelefoneDAO utdao = new Usuario_TelefoneDAO();
    private FuncionarioDAO fdao = new FuncionarioDAO();
    private Funcionario_TelefoneDAO ftdao = new Funcionario_TelefoneDAO();
    private ManutencaoDAO mdao = new ManutencaoDAO();
    private EstagiarioDAO edao = new EstagiarioDAO();
    private ProfessorDAO pdao = new ProfessorDAO();
    private Professor_EspecialidadeDAO pfdao = new Professor_EspecialidadeDAO();
    private NutricionistaDAO ndao = new NutricionistaDAO();
    private FisioterapeutaDAO fisdao = new FisioterapeutaDAO();
    private AdministracaoDAO adao = new AdministracaoDAO();
    private EquipamentoDAO eqdao = new EquipamentoDAO();
    private ReparoDAO rdao = new ReparoDAO();
    private ModalidadeDAO moddao = new ModalidadeDAO();
    private Modalidades_MinistradasDAO mmdao = new Modalidades_MinistradasDAO();
    private Estagiario_Eh_SupervisionadoDAO eesdao = new Estagiario_Eh_SupervisionadoDAO();
    private InscricaoDAO idao = new InscricaoDAO();
    private Consulta_NutriDAO cndao = new Consulta_NutriDAO();
    private Consulta_FisioDAO cfdao = new Consulta_FisioDAO();
    private Elabora_TreinoDAO etdao = new Elabora_TreinoDAO();

    public MenuDAO(char op) {

        this.op = op;

        String[] tabelas = { "Administração", "Consulta Fisioterapeuta", "Consulta Nutricionista",
                "Elaboração de Treinos", "Equipamentos", "Estagiários", "Supervisão de Estagiários", "Fisioterapeutas",
                "Funcionários", "Telefones de funcionários", "Inscrição em modalidades", "Manutenção", "Modalidades",
                "Modalidades ministradas", "Nutricionistas", "Professores", "Especialidades de professores",
                "Reparo de máquinas", "Usuários", "Telefones de usuários" };

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
                switch (op) {
                case MainMenu.CADASTRO:
                    adao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    adao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    adao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Consulta Fisioterapeuta")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    cfdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    cfdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    cfdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Consulta Nutricionista")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    cndao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    cndao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    cndao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Elaboração de Treinos")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    etdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    etdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    etdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Equipamentos")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    eqdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    eqdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    eqdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Estagiários")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    edao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    edao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    edao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Supervisão de Estagiários")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    eesdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    eesdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    eesdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Fisioterapeutas")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    fisdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    fisdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    fisdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Funcionários")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    fdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    fdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    fdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    fdao.MenuAtualiza();
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Telefones de funcionários")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    ftdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    ftdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    ftdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Inscrição em modalidades")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    idao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    idao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    idao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Manutenção")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    mdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    mdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    mdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Modalidades")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    moddao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    moddao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    moddao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    moddao.MenuAtualiza();
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Modalidades ministradas")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    mmdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    mmdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    mmdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Nutricionistas")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    ndao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    ndao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    ndao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Professores")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    pdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    pdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    pdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Especialidades de professores")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    pfdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    pfdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    pfdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            } else if (tb.equals("Reparo de máquinas")) {
                switch (op) {
                case MainMenu.CADASTRO:
                    rdao.MenuInsere();
                    break;

                case MainMenu.CONSULTA:
                    rdao.MenuConsulta();
                    break;

                case MainMenu.DELECAO:
                    rdao.MenuDeleta();
                    break;

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
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

                case MainMenu.ATUALIZACAO:
                    udao.MenuAtualiza();
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

                case MainMenu.ATUALIZACAO:
                    System.out.println("Erro! Não é possível atualizar");
                    break;

                default:
                    break;
                }
            }
        }
    }

    public static void criaJanela(char oper) {
        JFrame frame;

        switch (oper) {
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

        JRootPane pane = frame.getRootPane();
        pane.setDefaultButton(b1);

        frame.setVisible(true);
        frame.pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dim.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
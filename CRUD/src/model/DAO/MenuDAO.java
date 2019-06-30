package model.DAO;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MenuDAO extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JFileChooser jfc;
	protected static String s = null;
	protected final static JFrame frame = new JFrame("CRUD - Academia");

	public MenuDAO() {
		jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens jpg", "jpg", "jpeg", "JPG", "JPEG");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("Selecione a foto do usuário");

		int retorno = jfc.showOpenDialog(null);

		if (retorno == JFileChooser.APPROVE_OPTION) {
			s = jfc.getSelectedFile().getAbsolutePath();
		}
	}

	public static String criaJanela() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		MenuDAO menu = new MenuDAO();
		menu.setOpaque(true);
		frame.setContentPane(menu);

		frame.setVisible(true);
		frame.pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dim.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dim.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);

		if (s != null && s.length() > 0) {
			frame.setVisible(false);
			frame.dispose();
			return s;
		}
		return null;
	}
}

package model.bean;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * MenuVideo
 */
public class MenuVideo extends JPanel{

    private static final long serialVersionUID = 1L;
	protected JFileChooser jfc;
	protected static String s = null;
	protected final static JFrame frame = new JFrame("Escolha de vídeo");

    public MenuVideo() {
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("videos", "mp4", "MP4");
        jfc.setFileFilter(filter);
        jfc.setDialogTitle("Selecione o vídeo de funcionamento do equipamento");

        int retorno = jfc.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            s = jfc.getSelectedFile().getAbsolutePath();
        }
    }

    public static String CriaJanela(){

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		MenuVideo menu = new MenuVideo();
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
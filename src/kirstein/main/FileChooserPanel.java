package kirstein.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class FileChooserPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 5633081196628558754L;
	private JButton choose;
	private JFileChooser chooser;
	private JTextField path;
	
	public FileChooserPanel(FileFilter filter, String buttonText){
		choose = new JButton(buttonText);
		choose.addActionListener(this);
		chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		if(filter instanceof FolderFileFilter)
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		path = new JTextField(20);
		path.setText("No File Chosen");
		path.setEditable(false);
		setLayout(new FlowLayout());
		add(choose);
		add(path);
	}
	
	public File getSelectedFile(){
		return chooser.getSelectedFile();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		chooser.showDialog(getParent(), "Select");
		File f = chooser.getSelectedFile();
		if(f==null)
			path.setText("No File Chosen");
		else
			path.setText(chooser.getSelectedFile().getAbsolutePath());
	}
}

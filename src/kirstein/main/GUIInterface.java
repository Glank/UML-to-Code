package kirstein.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import kirstein.source.LanguageDescriptionCodeFactory;
import kirstein.source.NClassesPerFile;
import kirstein.source.java.JavaDescription;
import kirstein.source.php.PHPAllignment;
import kirstein.source.php.PHPDescription;
import kirstein.uml.UMLDocument;
import kirstein.umlet.UMLetDocument;

public class GUIInterface extends JFrame implements ActionListener{
	private static final long serialVersionUID = -792873786994437506L;
	
	private JButton generate;
	private FileChooserPanel file, folder;
	private JComboBox languages;
	
	private GUIInterface(){
		generate = new JButton("Generate Source");
		generate.addActionListener(this);
		file = new FileChooserPanel(new UMLetFileFilter(), "UMLet File");
		folder = new FileChooserPanel(new FolderFileFilter(), "Output Directory");
		languages = new JComboBox(new Object[]{"Java", "PHP"});
		setLayout(new GridLayout(4,1));
		add(file);
		add(folder);
		add(languages);
		add(generate);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		try{
			//creat factory by language
			LanguageDescriptionCodeFactory codeFactory = null;
			if(languages.getSelectedIndex()==0){
				codeFactory = new LanguageDescriptionCodeFactory("java", new JavaDescription());
			}
			else{
				codeFactory = new LanguageDescriptionCodeFactory("php", new PHPDescription());
				codeFactory.setSourceDistribution(new NClassesPerFile("php","File_",5));
				codeFactory.setOrdering(new PHPAllignment());
			}
			
			//parse umlet doc
			UMLetDocument umlet = new UMLetDocument(file.getSelectedFile());
			UMLDocument uml = new UMLDocument(umlet);
			
			//init rootFolder
			File rootFolder = folder.getSelectedFile();
			System.out.println();
			if(!rootFolder.exists())
				rootFolder.mkdirs();
			
			//produce code
			codeFactory.produceCode(uml, rootFolder);
		}catch(Throwable t){
			StackTraceElement[] elements = t.getStackTrace();
			String out = t.getMessage();
			//for(StackTraceElement e:elements)
			//	out+= "\n"+e.toString();
			ErrorDialog.show(out);
			System.exit(0);
		}
		JOptionPane.showMessageDialog(this, "Generation Complete");
		System.exit(0);
	}
	
	public static void main(String[] args){
		GUIInterface gui = new GUIInterface();
		gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gui.setVisible(true);
	}
}

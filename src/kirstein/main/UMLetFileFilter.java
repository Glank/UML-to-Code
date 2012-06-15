package kirstein.main;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class UMLetFileFilter extends FileFilter{

	@Override
	public boolean accept(File file) {
		if(file.isDirectory())
			return true;
		String path = file.getAbsolutePath();
		int extIndex = path.lastIndexOf('.');
		if(extIndex==-1)
			return false;
		return path.substring(extIndex+1).equalsIgnoreCase("uxf");
	}

	@Override
	public String getDescription() {
		return "UMLet Files";
	}
}

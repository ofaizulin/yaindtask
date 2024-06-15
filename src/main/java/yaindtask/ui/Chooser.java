package yaindtask.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Chooser extends JFrame {

	JFileChooser chooser;

	public Chooser() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(
				"doc"));
	}
	public String getFileToSaveCsv() {
		return getFileToSaveCsv(null);
	}
	
	public String getFileToSaveCsv(String suggestedFile) {
		File fileToSave = null;
		if (suggestedFile != null) {
			chooser.setSelectedFile(new File(suggestedFile));
		}
		int userSelection = chooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = chooser.getSelectedFile();
		}
		String fileFullName = fileToSave.getAbsolutePath();
		if(fileFullName!= null) {
			if(fileFullName.lastIndexOf(".")!=-1) {
				String extension = fileFullName.substring(fileFullName.lastIndexOf(".")+1);
				if(!"csv".equals(extension)){
					fileFullName = fileFullName.substring(0, fileFullName.lastIndexOf("."))+".csv";;
				}
			} else {
				fileFullName = fileFullName+".csv";
			}
		}
		return fileFullName;
	}

	public String getFilePath() {
		String fileName = new String();

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int r = chooser.showOpenDialog(new JFrame());
		if (r == JFileChooser.APPROVE_OPTION) {
			fileName = chooser.getSelectedFile().getPath();
		}
		return fileName;
	}
}
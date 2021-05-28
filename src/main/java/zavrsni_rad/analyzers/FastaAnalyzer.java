package zavrsni_rad.analyzers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import zavrsni_rad.utils.Utils;

public class FastaAnalyzer extends JFrame {
	
	
	public FastaAnalyzer(String[] data) throws IOException, InterruptedException {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocation(200, 200);
		setTitle("Result");
		
		if (getDataFromFile(data))
			initGUI();
		
	}
	
	private void initGUI() {
		
	}
	
	public boolean getDataFromFile(String[] data) throws IOException, InterruptedException {
		File outputFile = new File(data[3]);
		if (!outputFile.exists()) {
			JOptionPane.showMessageDialog(this, "Output file not found.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String quastPath = Utils.fetchQuastPath();
		if (quastPath.equals("")) {
			JOptionPane.showMessageDialog(this, "File quast.py not found, please check your quast.py path\n" + 
					"(Tools -> Raven)",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		ArrayList<String> commands = new ArrayList<>();
		commands.add(quastPath);
		commands.add(outputFile.toPath().toString());
		commands.add("-o");
		String[] outputFilePath = outputFile.toPath().toString().split("/");
		String outputFileName = outputFilePath[outputFilePath.length - 1];
		File resultDir = new File("quast_files/" + outputFileName);
		Files.createDirectories(resultDir.toPath());
		commands.add(resultDir.toPath().toString());
		ProcessBuilder pb = new ProcessBuilder(commands);
		Process process = pb.start();
		int status = process.waitFor();
		if (status != 0) {
			JOptionPane.showMessageDialog(this, "Analysis failed, for more information checkout:\n" +
					(resultDir.getAbsolutePath().toString() + "/quast.log"),
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		File resultFile = new File(resultDir + "/report.txt");
		
		
		return true;
	}
}

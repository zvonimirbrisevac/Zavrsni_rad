package zavrsni_rad.process_runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ProcessRunner {
	
	public enum ProcessStates {
		RUNNING, FAILED, FINNISHED;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println("Ovdje ne printa ništa čini se");
		
		String type = args[args.length - 1];
		System.out.println("Running " + type + "...");
		
		File allProcessLog = new File("all_process.log");
		allProcessLog.createNewFile();
		//System.exit(1);
		
		String refFile = args[args.length - 3];
		String querysFiles = args[args.length - 2];
		String ext = "";
		if (type.equals("MINIMAP2_MAPPING") || type.equals("RAM_MAPPING") || type.equals("RAVEN"))
			ext = ".paf";
		else if (type.equals("MINIMAP2_ALIGN"))
			ext = ".sam";
		
		
		int index = 1;
		String fileName = type + "_" + Integer.toString(index) + ext;
		File outputFile = new File(fileName);
		if (outputFile.exists()) {
			while(true) {
				index++;
				fileName = type + "_" + Integer.toString(index) + ext;
				outputFile = new File(fileName);
				if (!outputFile.exists()) {
					break;
				}
			}
		}
		outputFile.createNewFile();
		
		ArrayList<String> commands = new ArrayList<String>();
		for (int i = 0; i < args.length - 1; i++)
			commands.add(args[i]);
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		File errorFile = new File(fileName.substring(0, fileName.length() - 4) + "_stream.log");
		errorFile.createNewFile();
		pb.redirectError(errorFile);
		pb.redirectOutput(outputFile);
		Process process = pb.start();
		String timeStampStart = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(new Date());
		
		List<String> fileContent = new ArrayList<>(Files.readAllLines(allProcessLog.toPath()));
		int id = 1;
		if (!fileContent.isEmpty()) {
			String lastProcess = fileContent.get(fileContent.size() - 1);
			int lastId = Integer.parseInt(lastProcess.split(" : ")[0]);
			id = lastId + 1;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(id) + " : ");
		sb.append(refFile + " : ");
		sb.append(querysFiles + " : ");
		sb.append(timeStampStart + " : ");
		sb.append("- : ");
		sb.append(type + " : ");
		sb.append(ProcessStates.RUNNING.toString());
		fileContent.add(sb.toString());
		
		Files.write(allProcessLog.toPath(), fileContent);
		
		System.out.println("Doso je do ovdje");
		JOptionPane.showMessageDialog(new JFrame(), "Process running.", 
				"Dialog", JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Doso je do ovdje 2");
		int status = process.waitFor();
		
		System.out.println("Procces je zavrsio");
		String timeStampFinish = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(new Date());
		
		System.out.println("Output file path: " + outputFile.getAbsolutePath().toString());
		
		fileContent = new ArrayList<>(Files.readAllLines(allProcessLog.toPath()));
		for (int i = 0; i < fileContent.size(); i++) {
			String line = fileContent.get(i);
			String[] content = line.split(" : ");
			if (Integer.parseInt(content[0]) == id) {
				System.out.println("Pronaden id");
				content[4] = timeStampFinish;
				if (status == 0)
					content[6] = ProcessStates.FINNISHED.toString();
				else
					content[6] = ProcessStates.FAILED.toString();
				fileContent.set(i, String.join(" : ", content));
				break;
			}
		}
		
		Files.write(allProcessLog.toPath(), fileContent);			
		
		if (status == 0)
			JOptionPane.showMessageDialog(new JFrame(), "Process finished.", 
						"Dialog", JOptionPane.INFORMATION_MESSAGE);
		else 
			JOptionPane.showMessageDialog(new JFrame(), "Process failed.", 
					"Dialog", JOptionPane.ERROR_MESSAGE);
		
	}

}

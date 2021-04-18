package zavrsni_rad.process_runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ProcessRunner {
	
	public enum ProcessStates {
		RUNNING, FAILED, FINNISHED;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
				
		String type = args[args.length - 1];
		System.out.println("Running " + type + "...");
		
		Files.createDirectory(new File("all_processes").toPath());
		Files.createDirectory(new File("output_files").toPath());
		Files.createDirectory(new File("processes_streams").toPath());
		
		File allProcessLog = new File("all_processes/all_process.log");
		allProcessLog.createNewFile();
		//System.exit(1);
		
		String refFile = args[args.length - 3];
		String querysFiles = args[args.length - 2];
		String ext = "";
		if (type.equals("MINIMAP2_MAPPING") || type.equals("RAM_MAPPING"))
			ext = ".paf";
		else if (type.equals("MINIMAP2_ALIGN"))
			ext = ".sam";
		else if (type.equals("RAVEN"))
			ext = ".fasta";
		
		
		int index = 1;
		String fileName = type + "_" + Integer.toString(index) + ext;
		File outputFile = new File("output_files/" + fileName);
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
		File errorFile = new File("processes_streams/" + fileName.substring(0, fileName.length() - ext.length()) + "_stream.log");
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
		
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(new JFrame(), "Process running.", 
								"", JOptionPane.INFORMATION_MESSAGE);
		});
				
		System.out.println("Process runnig...");
		
		int status = process.waitFor();
		System.out.println("Procces finished");
		String timeStampFinish = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(new Date());
		
		System.out.println("Output file path: " + outputFile.getAbsolutePath().toString());
		
		fileContent = new ArrayList<>(Files.readAllLines(allProcessLog.toPath()));
		for (int i = 0; i < fileContent.size(); i++) {
			String line = fileContent.get(i);
			String[] content = line.split(" : ");
			if (Integer.parseInt(content[0]) == id) {
				System.out.println("Found id");
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
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(new JFrame(), "Process finished.", 
						"", JOptionPane.INFORMATION_MESSAGE);
			});
		else 
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(new JFrame(), "Process failed.", 
						"", JOptionPane.ERROR_MESSAGE);
			});
		
	}

}

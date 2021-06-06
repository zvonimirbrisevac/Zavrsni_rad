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

import zavrsni_rad.main_app.App;

public class ProcessRunner {
	
	public enum ProcessStates {
		RUNNING, FAILED, FINISHED;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
				
		String type = args[args.length - 1];
		System.out.println();
		System.out.println("Running " + type + "...");
		
		Files.createDirectories(new File("all_processes").toPath());
		//System.out.println(">>>>Stvorio je all_processes");
		Files.createDirectories(new File("output_files").toPath());
		Files.createDirectories(new File("processes_streams").toPath());
		
		File allProcessLog = new File("all_processes/all_process.log");
		allProcessLog.createNewFile();
		//System.exit(1);
		//System.out.println(">>>>>Stvorio je datoteke<<<<<<");
		String refFile = args[args.length - 3];
		String querysFiles = args[args.length - 2];
		String ext = "";
		if (type.equals("MINIMAP2_MAPPING") || type.equals("RAM_MAPPING"))
			ext = ".paf";
		else if (type.equals("MINIMAP2_ALIGN"))
			ext = ".sam";
		else if (type.equals("RAVEN"))
			ext = ".fasta";
		else if (type.equals("MINIMAP2_INDEXING"))
			ext = ".mmi";
		
		
		int index = 1;
		String fileName = type + "_" + Integer.toString(index) + ext;
		File outputFile = new File("output_files/" + fileName);
		if (outputFile.exists()) {
			while(true) {
				index++;
				fileName = type + "_" + Integer.toString(index) + ext;
				outputFile = new File("output_files/" + fileName);
				if (!outputFile.exists()) {
					break;
				}
			}
		}
		outputFile.createNewFile();
		
		//System.out.println(">>>>>>Stvorio je output file<<<<<<<");
		ArrayList<String> commands = new ArrayList<String>();
		for (int i = 0; i < args.length - 1; i++)
			commands.add(args[i]);
		
		
		///System.out.println(">>>>>>>>>>>Ide gradit processbuilder<<<<<<<<<");
		ProcessBuilder pb = new ProcessBuilder(commands);
		File errorFile = new File("processes_streams/" + fileName.substring(0, fileName.length() - ext.length()) + "_stream.log");
		errorFile.createNewFile();
		pb.redirectError(errorFile);
		pb.redirectOutput(outputFile);
		//System.out.println("krece start");
		Process process = pb.start();
		//System.out.println("zavrsio start");
		String timeStampStart = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss").format(new Date());
		List<String> fileContent = new ArrayList<>(Files.readAllLines(allProcessLog.toPath()));
		int id = 1;
		if (!fileContent.isEmpty()) {
			String lastProcess = fileContent.get(fileContent.size() - 1);
			int lastId = Integer.parseInt(lastProcess.split(" : ")[0]);
			id = lastId + 1;
		}
		final int finalId = id;
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(id) + " : ");
		if (!type.equals(App.PanelType.RAVEN.toString()) && !type.equals(App.PanelType.MINIMAP2_INDEXING.toString()))	
			sb.append(refFile + " : ");
		else
			sb.append("- : ");
		sb.append(querysFiles + " : ");
		sb.append(outputFile.getAbsolutePath() + " : ");
		sb.append(timeStampStart + " : ");
		sb.append("- : ");
		sb.append(type + " : ");
		sb.append(ProcessStates.RUNNING.toString());
		fileContent.add(sb.toString());
		
		Files.write(allProcessLog.toPath(), fileContent);
		
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(new JFrame(), "Process running. Id = " + Integer.toString(finalId), 
								"Process started", JOptionPane.INFORMATION_MESSAGE);
		});
				
		System.out.println("Process " + id +" running...");
		
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
				content[5] = timeStampFinish;
				if (status == 0)
					content[7] = ProcessStates.FINISHED.toString();
				else
					content[7] = ProcessStates.FAILED.toString();
				fileContent.set(i, String.join(" : ", content));
				break;
			}
		}
		
		Files.write(allProcessLog.toPath(), fileContent);			
		
		if (status == 0)
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(new JFrame(), "Process finished.\n Id = " + Integer.toString(finalId), 
						"Success", JOptionPane.INFORMATION_MESSAGE);
			});
		else 
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(new JFrame(), "Process failed. Id = " + Integer.toString(finalId) + "\n" +
						"More about why it failed:\n" + errorFile.getAbsolutePath(), 
						"Failure", JOptionPane.ERROR_MESSAGE);
			});
		
	}

}

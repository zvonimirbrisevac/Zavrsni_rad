package zavrsni_rad.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import zavrsni_rad.analyzers.FastaAnalyzer;
import zavrsni_rad.analyzers.PafAnalyzer;
import zavrsni_rad.analyzers.SamAnalyzer;
import zavrsni_rad.main_app.App;
import zavrsni_rad.main_app.App.PanelType;
import zavrsni_rad.process_runner.ProcessRunner;

public class Utils {
	
	public static String getToolPath(String tool) throws IOException {
		Files.createDirectories(new File("all_processes").toPath());
		File pathsFile = new File("all_processes/path_file.log");
		if (pathsFile.exists()) {
			List<String> paths = new ArrayList<>(Files.readAllLines(pathsFile.toPath()));
			for (String s : paths) {
				String[] data = s.split(" : ");
				if (data[0].equals(tool)) {
					return data[1];
				}
			}
			
		} else {
			pathsFile.createNewFile();
		}
		ProcessBuilder pb = new ProcessBuilder(("find /home -type f -name " + tool).split(" "));
		Process process = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String path = reader.readLine();
		List<String> paths = new ArrayList<>(Files.readAllLines(pathsFile.toPath()));
		path = (path == null ? "" : path);
		paths.add(tool + " : " + path);
		reader.close();
		Files.write(pathsFile.toPath(), paths);
		return path;
	}
	
	public static void writePath(String tool, String path) throws IOException {
		File pathsFile = new File("all_processes/path_file.log");
		List<String> paths = new ArrayList<>(Files.readAllLines(pathsFile.toPath()));
		for (int i = 0; i < paths.size(); i++) {
			String[] data = paths.get(i).split(" : ");
			if (data[0].equals(tool)) {
				paths.set(i, tool + " : " + path);
				break;
			}
		}
		Files.write(pathsFile.toPath(), paths);
		return;

	}
	
	public static String fetchMinimap2Path() throws IOException {
		return getToolPath("minimap2");
	}
	
	public static String fetchRamPath() throws IOException {
		return getToolPath("ram");
	}
	
	public static String fetchRavenPath() throws IOException {
		return getToolPath("raven");
	}
	
	public static String fetchQuastPath() throws IOException {
		return getToolPath("quast.py");	
	}
	
	public static void analyze(String id, App app) {
		File allProcesses = new File("all_processes/all_process.log");
		if (!allProcesses.exists())
			return;
		List<String> processesList = null;
		try {
			processesList = new ArrayList<>(Files.readAllLines(allProcesses.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String process : processesList) {
			String[] data = process.split(" : ");
			if (data[0].equals(id)) {
				if (!data[7].equals(ProcessRunner.ProcessStates.FINISHED.toString())) {
					JOptionPane.showMessageDialog(app, "Process can not be analyzed if it does not have status FINISHED.\n"
							+ "Current status: " + data[7], 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (data[6].equals(PanelType.MINIMAP2_INDEXING.toString())){
					JOptionPane.showMessageDialog(app, "Indexing can not be analyzed!", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (data[6].equals(PanelType.MINIMAP2_MAPPING.toString()) || data[6].equals(PanelType.RAM_MAPPING.toString())) {
					SwingUtilities.invokeLater(() -> {
						PafAnalyzer analyzer = null;
						try {
							analyzer = new PafAnalyzer(data);
							analyzer.setVisible(true);
							analyzer.pack();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} else if (data[6].equals(PanelType.MINIMAP2_ALIGN.toString())) {
					SamAnalyzer analyzer = null;
					try {
						analyzer = new SamAnalyzer(data);
						analyzer.setVisible(true);
						analyzer.pack();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else if (data[6].equals(PanelType.RAVEN.toString())) {
					FastaAnalyzer analyzer = null;
					try {
						analyzer = new FastaAnalyzer(data);
						analyzer.setVisible(true);
						analyzer.pack();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return;
			}
			
		}
		JOptionPane.showMessageDialog(app, "Id " + id + " not found.",
				"Error", JOptionPane.ERROR_MESSAGE);
	}

	public static String getToolVersion(String path) throws IOException {
		ProcessBuilder pb = new ProcessBuilder((path + " --version").split(" "));
		Process process = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String versionLine = reader.readLine();
		reader.close();
		return versionLine;
	}
	
	public static void printHelp() throws IOException {
		File aboutAppFile = new File("print_files/about_app.txt");
		List<String> aboutAppInfo = new ArrayList<>(Files.readAllLines(aboutAppFile.toPath()));
		for (String s : aboutAppInfo)
			System.out.println(s);
		System.out.println();
		System.out.println(App.version);
		System.out.println();
		File aboutAddArgsFile = new File("print_files/additional_arguments_info.txt");
		List<String> aboutAddArgs = new ArrayList<>(Files.readAllLines(aboutAddArgsFile.toPath()));
		for (String s : aboutAddArgs)
			System.out.println(s);
		System.out.println();
		
	}

}

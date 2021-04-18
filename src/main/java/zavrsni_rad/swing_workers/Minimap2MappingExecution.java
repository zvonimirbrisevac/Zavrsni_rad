package zavrsni_rad.swing_workers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import zavrsni_rad.main_app.App;
import zavrsni_rad.swing_components.Minimap2MappingPanel;

public class Minimap2MappingExecution extends SwingWorker<Void, Void>{
	
	private Minimap2MappingPanel panel;
	
	public Minimap2MappingExecution(Minimap2MappingPanel panel) {
		super();
		this.panel = panel;
		
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("java");
		commands.add("../process_runner/ProcessRunner.java");
		
		String minimap2Path = panel.getMinimap2Path();
		if (!minimap2Path.equals(""))
			commands.add(minimap2Path);
		else
			return null;
		
		String preset = panel.getPreset();
		if (!preset.equals("")) {
			commands.add("-x");
			commands.add(preset);
		}
		
		int threads = panel.getThreadsField();
		if (threads == -2) 
			return null;
		else if (threads >= 0) {
			commands.add("-t");
			commands.add(Integer.toString(threads));
		}
		
		
		String targetPath = panel.getTargetPath();
		if (!targetPath.equals(""))
			commands.add(targetPath);
		else
			return null;
		
		String seqPaths = panel.getSequencesPath();
		if (seqPaths != null)
			for(String s: seqPaths.split(";"))
				commands.add(s);
		else
			return null;
		
		commands.add(App.PanelType.MINIMAP2_ALIGN.toString());
		
		for (String s : commands)
			System.out.print(s + " ");
		System.out.println();
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		Process process = pb.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		
		System.out.println(process.waitFor());
		
		return null;
	}
}

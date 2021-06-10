package zavrsni_rad.swing_workers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import zavrsni_rad.main_app.App;
import zavrsni_rad.swing_components.Minimap2AlignPanel;

public class Minimap2AlignExecution extends SwingWorker<Integer, Integer> {
	
	private Minimap2AlignPanel panel;
	
	public Minimap2AlignExecution(Minimap2AlignPanel panel) {
		super();
		this.panel = panel;
		
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("java");
		commands.add("-cp");
		commands.add("target/classes");
		commands.add("zavrsni_rad.process_runner.ProcessRunner");
		
		String minimap2Path = panel.getMinimap2Path();
		if (!minimap2Path.equals(""))
			commands.add(minimap2Path);
		else
			return null;
		
		String preset = panel.getPreset();
		if (!preset.equals("")) {
			commands.add("-ax");
			commands.add(preset);
		} else {
			commands.add("-a");
		}
		
		String addOptions = panel.getAddOptions();
		if (!addOptions.equals(""))
			commands.add(addOptions);
		
		int threads = panel.getThreadsField();
		if (threads == -2) 
			return null;
		else if (threads >= 0) {
			commands.add("-t");
			commands.add(Integer.toString(threads));
		}
		
		boolean cigar = panel.getCigarCheck();
		if (cigar)
			commands.add("-c");
		
		String targetPath = panel.getTargetPath();
		if (!targetPath.equals(""))
			commands.add(targetPath);
		else
			return null;
		
		String seqPaths = panel.getSequencesPath();
		if (seqPaths != null)
			commands.add(seqPaths);
		else
			return null;
		
		commands.add(App.PanelType.MINIMAP2_ALIGN.toString());
		
		System.out.print("Executing command with ProcessRunner: ");
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
		if (process.waitFor() != 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Error occuered, process did nor start.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		//System.out.println(">>>>Rezultat executora: " + process.waitFor());
		
		return null;
	}
	
}

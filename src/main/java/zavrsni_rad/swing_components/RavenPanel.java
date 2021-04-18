package zavrsni_rad.swing_components;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class RavenPanel extends JPanel {
	
	private JPanel ravenPathPanel;
	private JPanel sequencesPanel;
	private JPanel threadsPanel;
	private JTextField ravenPath;
	private JTextField sequencesPath;
	private JFormattedTextField threadsField;
	
	public RavenPanel() throws IOException {
		super();
		
		this.setLayout(new SpringLayout());
		
		JLabel pleaseCheck = new JLabel("Path to your raven.exe:", SwingConstants.LEFT);
		this.add(pleaseCheck);

		ravenPath = new JTextField(16);
		ravenPath.setText(fetchRavenPath());

		JButton chooseRavenFileButton = new JButton("Choose file");
		chooseRavenFileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(fetchRavenPath());
				int returnValue = fileChooser.showOpenDialog(RavenPanel.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					ravenPath.setText(file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/*JPanel checkRaven = new JPanel();
		checkRaven.add(ravenPath);
		checkRaven.add(chooseRavenFileButton);

		this.add(checkRaven);*/
		
		this.add(ravenPath);
		this.add(chooseRavenFileButton);
		
		JLabel sequencesLabel = new JLabel("Sequences file(s):", SwingConstants.LEFT);
		this.add(sequencesLabel);

		sequencesPanel = new JPanel();
		sequencesPath = new JTextField(16);

		JButton chooseSequencesFiles = new JButton("Choose file");
		chooseSequencesFiles.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(RavenPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (sequencesPath.getText().equals(""))
					sequencesPath.setText(file.toPath().toString());
				else 
					sequencesPath.setText(sequencesPath.getText() + ";" + file.toPath().toString());
			}
		});

		/*sequencesPanel.add(sequencesPath);
		sequencesPanel.add(chooseSequencesFiles);

		this.add(sequencesPanel);*/
		
		this.add(sequencesPath);
		this.add(chooseSequencesFiles);
		
		JLabel threadsLabel = new JLabel("Number of threads (optional):", SwingConstants.LEFT);
		this.add(threadsLabel);
		
		threadsPanel = new JPanel();
		
		NumberFormat threadsFormat = NumberFormat.getInstance();
		NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
		threadsFormatter.setValueClass(Integer.class);
		threadsFormatter.setMinimum(0);
		threadsFormatter.setMaximum(Integer.MAX_VALUE);
		//threadsFormatter.setAllowsInvalid(false);
		// threadsFormatter.setCommitsOnValidEdit(true);
		threadsField = new JFormattedTextField(threadsFormatter);
		threadsField.setColumns(16);
		threadsField.resize(ravenPath.getSize());
		
		//this.add(threadsField);
		
		//this.add(new JPanel());
		
		//threadsPanel.setLayout(new GridLayout(1, 1));
		threadsPanel.add(threadsField);
		//threadsPanel.add(new JPanel());
		//JButton b = new JButton("bzvz");
		//b.setVisible(false);
		//b.disable();
		//threadsPanel.add(b);
		
		this.add(threadsField);
		
		this.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(this, 3, 3, 0, 0, 10, 10);
		
	}
	
	public String fetchRavenPath() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("find /home -type f -name raven".split(" "));
		Process process = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String path = reader.readLine();
		reader.close();
		return path == null ? "" : path;
	}
	
	
	
}

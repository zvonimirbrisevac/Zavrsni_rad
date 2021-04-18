package zavrsni_rad.swing_components;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class Minimap2MappingPanel extends JPanel{
	/*private JPanel minimap2PathPanel;
	private JPanel queryPanel;
	private JPanel threadsPanel;*/
	private JTextField minimap2Path;
	private JTextField refPath;
	private JTextField queryPath;
	private JFormattedTextField threadsField;
	private JComboBox<String> presetBox;
	
	public Minimap2MappingPanel() throws IOException {
		
		super();
		
		this.setLayout(new SpringLayout());
		
		JLabel pleaseCheck = new JLabel("Path to your minimap2.exe:", SwingConstants.LEFT);
		this.add(pleaseCheck);

		minimap2Path = new JTextField(16);
		minimap2Path.setText(fetchMinimap2Path());

		JButton chooseMinimap2FileButton = new JButton("Choose file");
		chooseMinimap2FileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(fetchMinimap2Path());
				int returnValue = fileChooser.showOpenDialog(Minimap2MappingPanel.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					minimap2Path.setText(file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		//JPanel checkMinimap2 = new JPanel();
		//checkMinimap2.add(minimap2Path);
		//checkMinimap2.add(chooseMinimap2FileButton);

		//this.add(checkMinimap2);
		
		this.add(minimap2Path);
		this.add(chooseMinimap2FileButton);
		
		JLabel refLabel = new JLabel("Reference file:", SwingConstants.LEFT);
		this.add(refLabel);

		refPath = new JTextField(16);

		JButton chooseRefButton = new JButton("Choose file");
		chooseRefButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(Minimap2MappingPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				refPath.setText(file.toPath().toString());
			}
		
		});

		this.add(refPath);
		this.add(chooseRefButton);
		
		JLabel queryLabel = new JLabel("Query file(s):", SwingConstants.LEFT);
		this.add(queryLabel);

		//queryPanel = new JPanel();
		queryPath = new JTextField(16);

		JButton chooseQueryFiles = new JButton("Choose file");
		chooseQueryFiles.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(Minimap2MappingPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (queryPath.getText().equals(""))
					queryPath.setText(file.toPath().toString());
				else 
					queryPath.setText(queryPath.getText() + ";" + file.toPath().toString());
			}
		});

		//queryPanel.add(queryPath);
		//queryPanel.add(chooseQueryFiles);

		//this.add(queryPanel);
		
		this.add(queryPath);
		this.add(chooseQueryFiles);
		
		JLabel threadsLabel = new JLabel("Number of threads (optional):", SwingConstants.LEFT);
		this.add(threadsLabel);
		
		//threadsPanel = new JPanel();
		
		NumberFormat threadsFormat = NumberFormat.getInstance();
		NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
		threadsFormatter.setValueClass(Integer.class);
		threadsFormatter.setMinimum(0);
		threadsFormatter.setMaximum(Integer.MAX_VALUE);
		//threadsFormatter.setAllowsInvalid(false);
		// threadsFormatter.setCommitsOnValidEdit(true);
		threadsField = new JFormattedTextField(threadsFormatter);
		threadsField.setColumns(16);
		
		
		this.add(threadsField);
		
		this.add(new JPanel());
		
		JLabel presetLabel = new JLabel("Choose preset (optional):");
		
		this.add(presetLabel);
		
		String[] presetOptions = {"", "Pac-Bio read overlap", "Nanopore read overlap"};
		presetBox = new JComboBox<String>(presetOptions);
		
		this.add(presetBox);
		
		this.add(new JPanel());
		
		
		SpringUtilities.makeCompactGrid(this, 5, 3, 0, 0, 10, 10);
		
	}
	
	public String fetchMinimap2Path() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("find /home -type f -name minimap2".split(" "));
		Process process = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String path = reader.readLine();
		reader.close();
		return path == null ? "" : path;
	}
	
	public String getMinimap2Path() {
		if (minimap2Path.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No Minimap2 path!", "Error", JOptionPane.ERROR_MESSAGE);

		}
		return minimap2Path.getText();
	}
	
	public String getTargetPath() {
		if (refPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No reference path!", "Error", JOptionPane.ERROR_MESSAGE);

		}
		return refPath.getText();
	}
	
	public String getSequencesPath() {
		if (queryPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No sequences paths!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return queryPath.getText();
	
	}
	
	public int getThreadsField() {
		return getIntValueFromField(threadsField, "threads");
	}
	
	
	public int getIntValueFromField(JFormattedTextField field, String dataType) {
		String s = field.getText();
		if (!s.equals("")) {
			try {
				int result = Integer.parseInt(s);
				return result;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Illegal " + dataType + " value!", 
						"Dialog", JOptionPane.ERROR_MESSAGE);
				return -2;
			}
			
		}
		return -1;
	}
	
	public void clearFields() {
		refPath.setText("");
		queryPath.setText("");
		threadsField.setText("");
		presetBox.setSelectedItem("");
	}

	public String getPreset() {		
		switch ((String)presetBox.getSelectedItem()) {
			case "PacBio read overlap": return "ava-pb";
			case "Nanopore read overlap": return "ava-ont";
		}
		
		return "";
	}
}

package zavrsni_rad.swing_components;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class Minimap2AlignPanel extends JPanel {
	
	private JPanel filesPanel;
	private JPanel refPanel;
	private JPanel queryPanel;
	private Minimap2IndexingOptPanel indexOptionalPanel;
	private JPanel samPanel;
	private Minimap2AlignOptPanel alignOptionalPanel;
	private JTextField minimap2Path;
	private JTextField refPath;
	private JTextField queryPath;
	private JTextField samPath;
	private JFormattedTextField threadsField;
	private JComboBox<String> presetBox;
	private JCheckBox cigarBox;
	
		
	public Minimap2AlignPanel() throws IOException {
		super();
		
		filesPanel = new JPanel(new SpringLayout());

		JLabel pleaseCheck = new JLabel("Path to your minimap2.exe:", SwingConstants.LEFT);

		minimap2Path = new JTextField(16);
		minimap2Path.setText(fetchMinimap2Path());

		JButton chooseMinimap2FileButton = new JButton("Choose file");
		chooseMinimap2FileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(fetchMinimap2Path());
				int returnValue = fileChooser.showOpenDialog(Minimap2AlignPanel.this);
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

		//filesPanel.add(checkMinimap2);
		filesPanel.add(pleaseCheck);
		filesPanel.add(minimap2Path);
		filesPanel.add(chooseMinimap2FileButton);
		
		
		JLabel refLabel = new JLabel("Reference file:", SwingConstants.LEFT);
		// filesPanel.add(refLabel);

		//refPanel = new JPanel();

		refPath = new JTextField(16);

		JButton chooseRefButton = new JButton("Choose file");
		chooseRefButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(Minimap2AlignPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				refPath.setText(file.toPath().toString());
			}
			/*if (refPath.getText().endsWith(".mmi"))
				disPanel.setEnabled(false);
			else
				disPanel.setEnabled(true);*/
		});

		//refPanel.add(refPath);
		//refPanel.add(chooseRefButton);

		filesPanel.add(refLabel);
		//filesPanel.add(refPanel);
		filesPanel.add(refPath);
		filesPanel.add(chooseRefButton);
		
		JLabel queryLabel = new JLabel("Query file(s):", SwingConstants.LEFT);

		//queryPanel = new JPanel();
		queryPath = new JTextField(16);

		JButton chooseQueryFiles = new JButton("Choose file");
		chooseQueryFiles.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(Minimap2AlignPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (queryPath.getText().equals(""))
					queryPath.setText(file.toPath().toString());
				else 
					queryPath.setText(queryPath.getText() + ";" + file.toPath().toString());
			}
		});
		
		filesPanel.add(queryLabel);
		filesPanel.add(queryPath);
		filesPanel.add(chooseQueryFiles);

		//filesPanel.add(queryPanel);

		
		JLabel threadsLabel = new JLabel("Number of threads (optional):", SwingConstants.LEFT);

		NumberFormat threadsFormat = NumberFormat.getInstance();
		NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
		threadsFormatter.setValueClass(Integer.class);
		threadsFormatter.setMinimum(0);
		threadsFormatter.setMaximum(Integer.MAX_VALUE);
		//threadsFormatter.setAllowsInvalid(false);
		// threadsFormatter.setCommitsOnValidEdit(true);
		threadsField = new JFormattedTextField(threadsFormatter);
		threadsField.setColumns(16);
		
		filesPanel.add(threadsLabel);
		
		filesPanel.add(threadsField);
		
		filesPanel.add(new JPanel());
		
		/*JLabel samLabel = new JLabel("SAM file:", SwingConstants.LEFT);

		filesPanel.add(samLabel);

		samPanel = new JPanel();
		samPath = new JTextField(16);

		JButton samFile = new JButton("Choose file");
		samFile.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(Minimap2AlignPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				samPath.setText(file.toPath().toString());
			}
		});

		samPanel.add(samPath);
		samPanel.add(samFile);

		filesPanel.add(samPanel);*/
		
		JLabel presetLabel = new JLabel("Choose preset option (optional):", SwingConstants.LEFT);
		
		String[] presetOptions = {"", "PacBio vs reference mapping", "Nanopore vs reference mapping", "asm-to-ref mapping (~0.1% sequence divergence)",
									"asm-to-ref mapping (~1% sequence divergence)", "asm-to-ref mapping (~5% sequence divergence)", "long-read splice alignment",
									"PacBio-CCS spliced alignment", "Genomic short-read mapping"};
		presetBox = new JComboBox<String>(presetOptions);
		
		filesPanel.add(presetLabel);
		filesPanel.add(presetBox);
		filesPanel.add(new JPanel());
		
		
		cigarBox = new JCheckBox("Generate cigar string");
		
		filesPanel.add(cigarBox);
		filesPanel.add(new JPanel());
		filesPanel.add(new JPanel());
		
		
		SpringUtilities.makeCompactGrid(filesPanel, 6, 3, 0, 0, 10, 10);
		add(filesPanel);
		
		/*indexOptionalPanel = new Minimap2AlignOptPanel();
		this.add(indexOptionalPanel);
		
		alignOptionalPanel =new Minimap2AlignOptPanel();
		this.add(alignOptionalPanel);*/
		

		
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
			JOptionPane.showMessageDialog(this, "No Minimap2 path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return minimap2Path.getText();
	}
	
	public String getTargetPath() {
		if (refPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No ref path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return refPath.getText();
	}
	
	public String[] getSequencesPath() {
		if (queryPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No sequences paths!", "Dialog", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		String[] files = queryPath.getText().split(";");
	
		return files;
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
		
	}
}

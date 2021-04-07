package zavrsni_rad.swing_components;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class DataPanelMinimap2Align extends JPanel {
	
	private JPanel filesPanel;
	private JPanel refPanel;
	private JPanel queryPanel;
	private JPanel indexOptionalPanel;
	private JPanel alignOptionalPanel;
	private JPanel samPanel;
	private JTextField minimap2Path;
	private JTextField refPath;
	private JTextField queryPath;
	private JTextField samPath;
	private JFormattedTextField kmerField;
	private JFormattedTextField windowField;
	private JFormattedTextField splitField;
	private JCheckBox homoKmer;
		
	public DataPanelMinimap2Align() throws IOException {
		super();
		
		filesPanel = new JPanel(new SpringLayout());

		JLabel pleaseCheck = new JLabel("Path to your minimap2.exe:", SwingConstants.LEFT);
		filesPanel.add(pleaseCheck);

		minimap2Path = new JTextField(16);
		minimap2Path.setText(fetchMinimap2Path());

		JButton chooseRamFileButton = new JButton("Choose file");
		chooseRamFileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(fetchMinimap2Path());
				int returnValue = fileChooser.showOpenDialog(DataPanelMinimap2Align.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					minimap2Path.setText(file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		JPanel checkRam = new JPanel();
		checkRam.add(minimap2Path);
		checkRam.add(chooseRamFileButton);

		filesPanel.add(checkRam);

		JLabel refLabel = new JLabel("Reference file:", SwingConstants.LEFT);
		// filesPanel.add(refLabel);

		refPanel = new JPanel();

		refPath = new JTextField(16);

		JButton chooseRefButton = new JButton("Choose file");
		chooseRefButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(DataPanelMinimap2Align.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				refPath.setText(file.toPath().toString());
			}
			if (refPath.getText().endsWith(".mmi"))
				indexOptionalPanel.disable();
		});

		refPanel.add(refPath);
		refPanel.add(chooseRefButton);

		filesPanel.add(refLabel);
		filesPanel.add(refPanel);

		JLabel queryLabel = new JLabel("Query file(s):", SwingConstants.LEFT);
		filesPanel.add(queryLabel);

		queryPanel = new JPanel();
		queryPath = new JTextField(16);

		JButton chooseQueryFiles = new JButton("Choose file");
		chooseQueryFiles.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(DataPanelMinimap2Align.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				if (queryPath.getText().equals(""))
					queryPath.setText(file.toPath().toString());
				else 
					queryPath.setText(queryPath.getText() + ";" + file.toPath().toString());
			}
		});

		queryPanel.add(queryPath);
		queryPanel.add(chooseQueryFiles);

		filesPanel.add(queryPanel);

		JLabel samLabel = new JLabel("SAM file:", SwingConstants.LEFT);

		filesPanel.add(samLabel);

		samPanel = new JPanel();
		samPath = new JTextField(16);

		JButton samFile = new JButton("Choose file");
		samFile.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(DataPanelMinimap2Align.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				samPath.setText(file.toPath().toString());
			}
		});

		samPanel.add(samPath);
		samPanel.add(samFile);

		filesPanel.add(samPanel);

		SpringUtilities.makeCompactGrid(filesPanel, 4, 2, 0, 0, 10, 10);
		add(filesPanel);
		
		indexOptionalPanel = new JPanel(new SpringLayout());
		indexOptionalPanel.setBorder(
				BorderFactory.createTitledBorder(null, "Indexing optional arguments", TitledBorder.LEADING, TitledBorder.TOP));
		indexOptionalPanel.setLayout(new SpringLayout());
		
		JLabel kmerLabel = new JLabel("Kmer length:", SwingConstants.LEFT);
		indexOptionalPanel.add(kmerLabel);

		NumberFormat kmerFormat = NumberFormat.getInstance();
		NumberFormatter kmerFormatter = new NumberFormatter(kmerFormat);
		kmerFormatter.setValueClass(Integer.class);
		kmerFormatter.setMinimum(0);
		kmerFormatter.setMaximum(Integer.MAX_VALUE);
		//kmerFormatter.setAllowsInvalid(false);
		// kmerFormatter.setCoitsOnValidEdit(true);
		kmerField = new JFormattedTextField(kmerFormatter);
		kmerField.setColumns(16);
		indexOptionalPanel.add(kmerField);

		JLabel windowLabel = new JLabel("Window length:", SwingConstants.LEFT);
		indexOptionalPanel.add(windowLabel);

		NumberFormat windowFormat = NumberFormat.getInstance();
		NumberFormatter windowFormatter = new NumberFormatter(windowFormat);
		windowFormatter.setValueClass(Integer.class);
		windowFormatter.setMinimum(0);
		windowFormatter.setMaximum(Integer.MAX_VALUE);
		//windowFormatter.setAllowsInvalid(false);
		// windowFormatter.setCoitsOnValidEdit(true);
		windowField = new JFormattedTextField(windowFormatter);
		windowField.setColumns(16);
		indexOptionalPanel.add(windowField);
		
		JLabel splitLabel = new JLabel("Split index:", SwingConstants.LEFT);
		indexOptionalPanel.add(splitLabel);
		
		NumberFormat splitFormat = NumberFormat.getInstance();
		NumberFormatter splitFormatter = new NumberFormatter(splitFormat);
		splitFormatter.setValueClass(Integer.class);
		splitFormatter.setMinimum(0);
		splitFormatter.setMaximum(Integer.MAX_VALUE);
		//splitFormatter.setAllowsInvalid(false);
		// windowFormatter.setCoitsOnValidEdit(true);
		splitField = new JFormattedTextField(splitFormatter);
		splitField.setColumns(16);
		indexOptionalPanel.add(splitField);
		
		homoKmer = new JCheckBox("Use homopolymer-compressed k-mer");
		indexOptionalPanel.add(homoKmer);
		
		indexOptionalPanel.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(indexOptionalPanel, 4, 2, 0, 0, 10, 10);
		add(indexOptionalPanel);
		
		
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
}

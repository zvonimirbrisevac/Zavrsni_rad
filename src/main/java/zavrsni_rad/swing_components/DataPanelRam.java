package zavrsni_rad.swing_components;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

public class DataPanelRam extends JPanel {
	
	private JPanel filesPanel;
	private JPanel targetPanel;
	private JPanel sequencesPanel;
	private JPanel optionalPanel;
	private JPanel pafPanel;
	private JTextField ramPath;
	private JTextField targetPath;
	private JTextField sequencesPath;
	private JTextField pafPath;
	private JFormattedTextField bandField;
	private JFormattedTextField chainField;
	private JFormattedTextField freqField;
	private JFormattedTextField gapField;
	private JFormattedTextField kmerField;
	private JFormattedTextField matchesField;
	private JFormattedTextField windowField;
	private JFormattedTextField threadsField;
	
	public DataPanelRam() throws IOException {
		
		filesPanel = new JPanel(new SpringLayout());
		
		JLabel pleaseCheck = new JLabel("Path to your ram.exe:", SwingConstants.LEFT);
		filesPanel.add(pleaseCheck);
		
		ramPath = new JTextField(16);
		ramPath.setText(getRamPath());
		
		JButton chooseRamFileButton = new JButton("Choose file");
		chooseRamFileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(getRamPath());
				int returnValue = fileChooser.showOpenDialog(DataPanelRam.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            ramPath.setText(file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		JPanel checkRam = new JPanel();
		checkRam.add(ramPath);
		checkRam.add(chooseRamFileButton);
		
		filesPanel.add(checkRam);
		
		JLabel targetLabel = new JLabel("Target file:", SwingConstants.LEFT);
		//filesPanel.add(targetLabel);
		
		targetPanel = new JPanel();
		
		targetPath = new JTextField(16);
		
		JButton chooseTargetButton = new JButton("Choose file");
		chooseTargetButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(getRamPath());
				int returnValue = fileChooser.showOpenDialog(DataPanelRam.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            targetPath.setText(file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		targetPanel.add(targetPath);
		targetPanel.add(chooseTargetButton);
		
		filesPanel.add(targetLabel);
		filesPanel.add(targetPanel);

		
		JLabel sequencesLabel = new JLabel("Sequences file(s):", SwingConstants.LEFT);
		filesPanel.add(sequencesLabel);
		
		sequencesPanel = new JPanel();
		sequencesPath = new JTextField(16);
		
		JButton chooseSequencesFiles = new JButton("Choose file");
		chooseSequencesFiles.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(getRamPath());
				int returnValue = fileChooser.showOpenDialog(DataPanelRam.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            sequencesPath.setText(sequencesPath.getText() + ";" + file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		sequencesPanel.add(sequencesPath);
		sequencesPanel.add(chooseSequencesFiles);
		
		filesPanel.add(sequencesPanel);
		
		JLabel pafLabel = new JLabel("PAF file:", SwingConstants.LEFT);
		
		filesPanel.add(pafLabel);
		
		pafPanel = new JPanel();
		pafPath = new JTextField(16);
		
		JButton pafFile = new JButton("Choose file");
		pafFile.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(getRamPath());
				int returnValue = fileChooser.showOpenDialog(DataPanelRam.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            sequencesPath.setText(sequencesPath.getText() + ";" + file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		pafPanel.add(pafPath);
		pafPanel.add(pafFile);
		
		filesPanel.add(pafPanel);
				
		SpringUtilities.makeCompactGrid(filesPanel, 4, 2, 0, 0, 10, 10);
		add(filesPanel);
		
		optionalPanel = new JPanel(new SpringLayout());
		optionalPanel.setBorder(BorderFactory.createTitledBorder(null, "Optional arguments", 
																	TitledBorder.LEADING, TitledBorder.TOP));
		optionalPanel.setLayout(new SpringLayout());
		
		JLabel kmerLabel = new JLabel("Kmer length:", SwingConstants.LEFT);
		optionalPanel.add(kmerLabel);
		
		NumberFormat kmerFormat = NumberFormat.getInstance();
	    NumberFormatter kmerFormatter = new NumberFormatter(kmerFormat);
	    kmerFormatter.setValueClass(Integer.class);
	    kmerFormatter.setMinimum(0);
	    kmerFormatter.setMaximum(Integer.MAX_VALUE);
	    kmerFormatter.setAllowsInvalid(false);
	   // kmerFormatter.setCommitsOnValidEdit(true);
	    kmerField = new JFormattedTextField(kmerFormatter);
	    kmerField.setColumns(16);
	    optionalPanel.add(kmerField);
	    

		JLabel windowLabel= new JLabel("Window length:", SwingConstants.LEFT);
		optionalPanel.add(windowLabel);
		
		NumberFormat windowFormat = NumberFormat.getInstance();
	    NumberFormatter windowFormatter = new NumberFormatter(windowFormat);
	    windowFormatter.setValueClass(Integer.class);
	    windowFormatter.setMinimum(0);
	    windowFormatter.setMaximum(Integer.MAX_VALUE);
	    windowFormatter.setAllowsInvalid(false);
	   // windowFormatter.setCommitsOnValidEdit(true);
	    windowField = new JFormattedTextField(windowFormatter);
	    windowField.setColumns(16);
	    optionalPanel.add(windowField);
	    
	    JLabel freqLabel= new JLabel("Frequency:", SwingConstants.LEFT);
		optionalPanel.add(freqLabel);
		
		NumberFormat freqFormat = NumberFormat.getInstance();
	    NumberFormatter freqFormatter = new NumberFormatter(freqFormat);
	    freqFormatter.setValueClass(Double.class);
	    freqFormatter.setMinimum(0);
	    freqFormatter.setMaximum(100);
	    freqFormatter.setAllowsInvalid(false);
	   // freqFormatter.setCommitsOnValidEdit(true);
	    freqField = new JFormattedTextField(freqFormatter);
	    freqField.setColumns(16);
	    optionalPanel.add(freqField);
	    
	    JLabel bandLabel= new JLabel("Bandwidth length:", SwingConstants.LEFT);
		optionalPanel.add(bandLabel);
		
		NumberFormat bandFormat = NumberFormat.getInstance();
	    NumberFormatter bandFormatter = new NumberFormatter(bandFormat);
	    bandFormatter.setValueClass(Integer.class);
	    bandFormatter.setMinimum(0);
	    bandFormatter.setMaximum(Integer.MAX_VALUE);
	    bandFormatter.setAllowsInvalid(false);
	   // windowFormatter.setCommitsOnValidEdit(true);
	    bandField = new JFormattedTextField(bandFormatter);
	    bandField.setColumns(16);
	    optionalPanel.add(bandField);
	    
	    JLabel chainLabel= new JLabel("Chain length:", SwingConstants.LEFT);
		optionalPanel.add(chainLabel);
		
		NumberFormat chainFormat = NumberFormat.getInstance();
	    NumberFormatter chainFormatter = new NumberFormatter(chainFormat);
	    chainFormatter.setValueClass(Integer.class);
	    chainFormatter.setMinimum(0);
	    chainFormatter.setMaximum(Integer.MAX_VALUE);
	    chainFormatter.setAllowsInvalid(false);
	   // windowFormatter.setCommitsOnValidEdit(true);
	    chainField = new JFormattedTextField(chainFormatter);
	    chainField.setColumns(16);
	    optionalPanel.add(chainField);
	    
	    JLabel matchesLabel= new JLabel("Matches:", SwingConstants.LEFT);
		optionalPanel.add(matchesLabel);
		
		NumberFormat matchesFormat = NumberFormat.getInstance();
	    NumberFormatter matchesFormatter = new NumberFormatter(matchesFormat);
	    matchesFormatter.setValueClass(Integer.class);
	    matchesFormatter.setMinimum(0);
	    matchesFormatter.setMaximum(Integer.MAX_VALUE);
	    matchesFormatter.setAllowsInvalid(false);
	   // matchesFormatter.setCommitsOnValidEdit(true);
	    matchesField = new JFormattedTextField(matchesFormatter);
	    matchesField.setColumns(16);
	    optionalPanel.add(matchesField);
	    
	    JLabel gapLabel= new JLabel("Gap:", SwingConstants.LEFT);
		optionalPanel.add(gapLabel);
		
		NumberFormat gapFormat = NumberFormat.getInstance();
	    NumberFormatter gapFormatter = new NumberFormatter(gapFormat);
	    gapFormatter.setValueClass(Integer.class);
	    gapFormatter.setMinimum(0);
	    gapFormatter.setMaximum(Integer.MAX_VALUE);
	    gapFormatter.setAllowsInvalid(false);
	   // gapFormatter.setCommitsOnValidEdit(true);
	    gapField = new JFormattedTextField(gapFormatter);
	    gapField.setColumns(16);
	    optionalPanel.add(gapField);
	    
	    JLabel threadsLabel= new JLabel("Threads:", SwingConstants.LEFT);
		optionalPanel.add(threadsLabel);
		
		NumberFormat threadsFormat = NumberFormat.getInstance();
	    NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
	    threadsFormatter.setValueClass(Integer.class);
	    threadsFormatter.setMinimum(0);
	    threadsFormatter.setMaximum(Integer.MAX_VALUE);
	    threadsFormatter.setAllowsInvalid(false);
	   // threadsFormatter.setCommitsOnValidEdit(true);
	    threadsField = new JFormattedTextField(threadsFormatter);
	    threadsField.setColumns(16);
	    optionalPanel.add(threadsField);
	    
	    SpringUtilities.makeCompactGrid(optionalPanel, 8, 2, 0, 0, 10, 10);
	    add(optionalPanel);
	}
	
	public String getRamPath() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("find /home -type f -name ram".split(" "));
		Process process = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String path = reader.readLine();
		reader.close();
		return path == null ? "" : path;
	}

	public String getTargetPath() {
		return targetPath.getText();
	}

	public void setTargetPath(JTextField targetPath) {
		this.targetPath = targetPath;
	}

	public String[] getSequencesPath() {
		String[] files = sequencesPath.getText().split(";");
		for (int i = 0; i < files.length; i++) 
			files[i] = "\"" + files[i] + "\"";
		return files;
	}

	public void setSequencesPath(JTextField sequencesPath) {
		this.sequencesPath = sequencesPath;
	}

	public String getPafPath() {
		return "\"" + pafPath.getText() + "\"";
	}

	public void setPafPath(JTextField pafPath) {
		this.pafPath = pafPath;
	}

	public int getBandField() {
		return Integer.parseInt(bandField.getText());
	}

	public void setBandField(JFormattedTextField bandField) {
		this.bandField = bandField;
	}

	public int getChainField() {
		return Integer.parseInt(chainField.getText());
	}

	public void setChainField(JFormattedTextField chainField) {
		this.chainField = chainField;
	}

	public double getFreqField() {
		return Double.parseDouble(freqField.getText());
	}

	public void setFreqField(JFormattedTextField freqField) {
		this.freqField = freqField;
	}

	public int getGapField() {
		return Integer.parseInt(gapField.getText());
	}

	public void setGapField(JFormattedTextField gapField) {
		this.gapField = gapField;
	}

	public int getKmerField() {
		return Integer.parseInt(kmerField.getText());
	}

	public void setKmerField(JFormattedTextField kmerField) {
		this.kmerField = kmerField;
	}

	public int getMatchesField() {
		return Integer.parseInt(matchesField.getText());
	}

	public void setMatchesField(JFormattedTextField matchesField) {
		this.matchesField = matchesField;
	}

	public int getWindowField() {
		return Integer.parseInt(windowField.getText());
	}

	public void setWindowField(JFormattedTextField windowField) {
		this.windowField = windowField;
	}

	public int getThreadsField() {
		return Integer.parseInt(threadsField.getText());
	}

	public void setThreadsField(JFormattedTextField threadsField) {
		this.threadsField = threadsField;
	}
	
	
}

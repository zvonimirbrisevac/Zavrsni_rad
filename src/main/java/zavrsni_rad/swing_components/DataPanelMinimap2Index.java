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

import zavrsni_rad.swing_layouts.SpringUtilities;

public class DataPanelMinimap2Index extends JPanel {
	
	private JPanel filesPanel;
	private JPanel targetPanel;
	private JPanel indexPanel;
	private JPanel optionalPanel;
	private JTextField targetPath;
	private JTextField mmPath;
	private JTextField indexPath;
	private JFormattedTextField kmerField;
	private JFormattedTextField windowField;
	private JFormattedTextField splitField;
	private JCheckBox homoKmer;
	
	public DataPanelMinimap2Index() throws IOException {
		
		super();
		
		filesPanel = new JPanel(new SpringLayout());

		JLabel pleaseCheck = new JLabel("Path to your minimap2.exe:", SwingConstants.LEFT);
		filesPanel.add(pleaseCheck);

		mmPath = new JTextField(16);
		mmPath.setText(fetchMinimap2Path());
		
		JButton chooseFileButton = new JButton("Choose file");
		chooseFileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(fetchMinimap2Path());
				int returnValue = fileChooser.showOpenDialog(DataPanelMinimap2Index.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					mmPath.setText(file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		JPanel check = new JPanel();
		check.add(mmPath);
		check.add(chooseFileButton);

		filesPanel.add(check);
		
		targetPanel = new JPanel();
		
		JLabel targetLabel = new JLabel("Target file:", SwingConstants.LEFT);

		targetPath = new JTextField(16);

		JButton chooseTargetButton = new JButton("Choose file");
		chooseTargetButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(DataPanelMinimap2Index.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				targetPath.setText(file.toPath().toString());
			}
		});

		targetPanel.add(targetPath);
		targetPanel.add(chooseTargetButton);

		filesPanel.add(targetLabel);
		filesPanel.add(targetPanel);
		
		indexPanel = new JPanel();
		JLabel indexLabel = new JLabel("Index file:", SwingConstants.LEFT);
		
		filesPanel.add(indexLabel);
		
		indexPath = new JTextField(16);

		JButton chooseIndexFiles = new JButton("Choose file");
		chooseIndexFiles.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(DataPanelMinimap2Index.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				indexPath.setText(file.toPath().toString());
			}
		});

		indexPanel.add(indexPath);
		indexPanel.add(chooseIndexFiles);

		filesPanel.add(indexPanel);
		
		SpringUtilities.makeCompactGrid(filesPanel, 3, 2, 0, 0, 10, 10);
		add(filesPanel);
		
		optionalPanel = new JPanel(new SpringLayout());
		optionalPanel.setBorder(
				BorderFactory.createTitledBorder(null, "Optional arguments", TitledBorder.LEADING, TitledBorder.TOP));
		optionalPanel.setLayout(new SpringLayout());
		
		JLabel kmerLabel = new JLabel("Kmer length:", SwingConstants.LEFT);
		optionalPanel.add(kmerLabel);

		NumberFormat kmerFormat = NumberFormat.getInstance();
		NumberFormatter kmerFormatter = new NumberFormatter(kmerFormat);
		kmerFormatter.setValueClass(Integer.class);
		kmerFormatter.setMinimum(0);
		kmerFormatter.setMaximum(Integer.MAX_VALUE);
		//kmerFormatter.setAllowsInvalid(false);
		// kmerFormatter.setCoitsOnValidEdit(true);
		kmerField = new JFormattedTextField(kmerFormatter);
		kmerField.setColumns(16);
		optionalPanel.add(kmerField);

		JLabel windowLabel = new JLabel("Window length:", SwingConstants.LEFT);
		optionalPanel.add(windowLabel);

		NumberFormat windowFormat = NumberFormat.getInstance();
		NumberFormatter windowFormatter = new NumberFormatter(windowFormat);
		windowFormatter.setValueClass(Integer.class);
		windowFormatter.setMinimum(0);
		windowFormatter.setMaximum(Integer.MAX_VALUE);
		//windowFormatter.setAllowsInvalid(false);
		// windowFormatter.setCoitsOnValidEdit(true);
		windowField = new JFormattedTextField(windowFormatter);
		windowField.setColumns(16);
		optionalPanel.add(windowField);
		
		JLabel splitLabel = new JLabel("Split index:", SwingConstants.LEFT);
		optionalPanel.add(splitLabel);
		
		NumberFormat splitFormat = NumberFormat.getInstance();
		NumberFormatter splitFormatter = new NumberFormatter(splitFormat);
		splitFormatter.setValueClass(Integer.class);
		splitFormatter.setMinimum(0);
		splitFormatter.setMaximum(Integer.MAX_VALUE);
		//splitFormatter.setAllowsInvalid(false);
		// windowFormatter.setCoitsOnValidEdit(true);
		splitField = new JFormattedTextField(splitFormatter);
		splitField.setColumns(16);
		optionalPanel.add(splitField);
		
		homoKmer = new JCheckBox("Use homopolymer-compressed k-mer");
		optionalPanel.add(homoKmer);
		
		optionalPanel.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(optionalPanel, 4, 2, 0, 0, 10, 10);
		add(optionalPanel);
		
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
		if (mmPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No Minimap2 path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return mmPath.getText();
	}
	
	public String getTargetPath() {
		if (targetPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No target path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return targetPath.getText();
	}
	
	public String getIndexPath() {
		if (indexPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No index path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return indexPath.getText();
	}
	
	public int getKmerField() {
		return getIntValueFromField(kmerField, "kmer length");
	}

	public int getWindowField() {
		return getIntValueFromField(windowField, "window length");
	}
	
	public int getSplitField() {
		return getIntValueFromField(splitField, "split index");
	}
	
	public boolean getHomoKmer() {
		return homoKmer.isSelected();
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
		targetPath.setText("");
		indexPath.setText("");
		splitField.setText("");
		kmerField.setText("");
		windowField.setText("");
		homoKmer.setSelected(false);
	}
}

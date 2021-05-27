package zavrsni_rad.swing_components;

import java.awt.GridLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import zavrsni_rad.swing_layouts.SpringUtilities;
import zavrsni_rad.utils.*;

public class RamPanel extends JPanel {

	private JPanel filesPanel;
	private JPanel targetPanel;
	private JPanel sequencesPanel;
	private RamOptPanel optionalPanel;
	private JPanel pafPanel;
	private JTextField ramPath;
	private JTextField targetPath;
	private JTextField sequencesPath;
	private JTextField pafPath;
	private JFormattedTextField threadsField;

	public RamPanel() throws IOException {
		
		super();
		
		filesPanel = new JPanel(new SpringLayout());

		JLabel pleaseCheck = new JLabel("Path to your ram.exe:", SwingConstants.LEFT);
		filesPanel.add(pleaseCheck);

		ramPath = new JTextField(16);
		ramPath.setText(Utils.fetchRamPath());

		JButton chooseRamFileButton = new JButton("Choose file");
		chooseRamFileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(Utils.fetchRamPath());
				int returnValue = fileChooser.showOpenDialog(RamPanel.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					ramPath.setText(file.toPath().toString());
					Utils.writePath("ram", file.toPath().toString());

				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		/*JPanel checkRam = new JPanel();
		checkRam.add(ramPath);
		checkRam.add(chooseRamFileButton);*/

		filesPanel.add(ramPath);
		filesPanel.add(chooseRamFileButton);

		JLabel targetLabel = new JLabel("Target file:", SwingConstants.LEFT);
		// filesPanel.add(targetLabel);

		targetPanel = new JPanel();

		targetPath = new JTextField(16);

		JButton chooseTargetButton = new JButton("Choose file");
		chooseTargetButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(RamPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				targetPath.setText(file.toPath().toString());
			}
		});

		//targetPanel.add(targetPath);
		//targetPanel.add(chooseTargetButton);

		filesPanel.add(targetLabel);
		//filesPanel.add(targetPanel);
		filesPanel.add(targetPath);
		filesPanel.add(chooseTargetButton);
		
		JLabel sequencesLabel = new JLabel("Sequences file(s):", SwingConstants.LEFT);
		filesPanel.add(sequencesLabel);

		sequencesPanel = new JPanel();
		sequencesPath = new JTextField(16);

		JButton chooseSequencesFiles = new JButton("Choose file");
		chooseSequencesFiles.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(RamPanel.this);
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

		filesPanel.add(sequencesPanel);*/
		
		filesPanel.add(sequencesPath);
		filesPanel.add(chooseSequencesFiles);

		JLabel threadsLabel = new JLabel("Number of threads (optional):", SwingConstants.LEFT);
		
		filesPanel.add(threadsLabel);
		
		NumberFormat threadsFormat = NumberFormat.getInstance();
		NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
		threadsFormatter.setValueClass(Integer.class);
		threadsFormatter.setMinimum(0);
		threadsFormatter.setMaximum(Integer.MAX_VALUE);
		//threadsFormatter.setAllowsInvalid(false);
		// threadsFormatter.setCommitsOnValidEdit(true);
		threadsField = new JFormattedTextField(threadsFormatter);
		threadsField.setColumns(16);
		
		filesPanel.add(threadsField);
		filesPanel.add(new JPanel());
		/*JLabel pafLabel = new JLabel("PAF file:", SwingConstants.LEFT);

		filesPanel.add(pafLabel);

		pafPanel = new JPanel();
		pafPath = new JTextField(16);

		JButton pafFile = new JButton("Choose file");
		pafFile.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(RamPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				pafPath.setText(file.toPath().toString());
			}
		});

		pafPanel.add(pafPath);
		pafPanel.add(pafFile);

		filesPanel.add(pafPanel);*/
		
		
		
		SpringUtilities.makeCompactGrid(filesPanel, 4, 3, 5, 5, 10, 10);
		add(filesPanel);

		//optionalPanel = new RamOptPanel(); 

		//add(optionalPanel);
	}


	public String getRamPath() {
		if (ramPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No Ram path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return ramPath.getText();
	}

	public String getTargetPath() {
		if (targetPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No target path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return targetPath.getText();
	}

	public String getSequencesPath() {
		if (sequencesPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No sequences paths!", "Dialog", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return sequencesPath.getText();
	}

	public String getPafPath() {
		if (pafPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No PAF path!", "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		return pafPath.getText();
	}
	
	public int getThreadsField() {
		return RamOptPanel.getIntValueFromField(threadsField, "threads");
	}

	public RamOptPanel getRamOptPanel() {
		return optionalPanel;
	}
	
	public void clearFields() {
		targetPath.setText("");
		sequencesPath.setText("");
		//pafPath.setText("");
	
		threadsField.setText("");
	}

	

}

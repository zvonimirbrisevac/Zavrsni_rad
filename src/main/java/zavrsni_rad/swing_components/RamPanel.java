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
	private JPanel pafPanel;
	private JTextField ramPath;
	private JTextField targetPath;
	private JTextField sequencesPath;
	private JTextField pafPath;
	private JFormattedTextField threadsField;
	private JTextField addOptionsField;

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

		filesPanel.add(ramPath);
		filesPanel.add(chooseRamFileButton);

		JLabel targetLabel = new JLabel("Reference file:", SwingConstants.LEFT);

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

		filesPanel.add(targetLabel);
		filesPanel.add(targetPath);
		filesPanel.add(chooseTargetButton);
		
		JLabel sequencesLabel = new JLabel("Query file(s):", SwingConstants.LEFT);
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

		filesPanel.add(sequencesPath);
		filesPanel.add(chooseSequencesFiles);

		JLabel threadsLabel = new JLabel("Number of threads (optional):", SwingConstants.LEFT);
		
		filesPanel.add(threadsLabel);
		
		NumberFormat threadsFormat = NumberFormat.getInstance();
		NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
		threadsFormatter.setValueClass(Integer.class);
		threadsFormatter.setMinimum(0);
		threadsFormatter.setMaximum(Integer.MAX_VALUE);
		threadsField = new JFormattedTextField(threadsFormatter);
		threadsField.setColumns(16);
		
		filesPanel.add(threadsField);
		filesPanel.add(new JPanel());
	
		JLabel addOptionsLabel = new JLabel("Other optional arguments: ", SwingConstants.LEFT);
		
		addOptionsField = new JTextField(16);
		
		filesPanel.add(addOptionsLabel);
		filesPanel.add(addOptionsField);
		filesPanel.add(new JPanel());
		
		
		SpringUtilities.makeCompactGrid(filesPanel, 5, 3, 5, 5, 10, 10);
		add(filesPanel);

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

	public String getAddOptions() {
		return addOptionsField.getText();
	}
	
	public int getThreadsField() {
		return getIntValueFromField(threadsField, "threads");
	}

	public void clearFields() {
		targetPath.setText("");
		sequencesPath.setText("");
		addOptionsField.setText("");
		threadsField.setText("");
	}

	public int getIntValueFromField(JFormattedTextField field, String dataType) {
		String s = field.getText();
		if (!s.equals("")) {
			try {
				int result = Integer.parseInt(s);
				return result;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Illegal " + dataType + " value!", 
						"Error", JOptionPane.ERROR_MESSAGE);
				return -2;
			}
			
		}
		return -1;
	}

}

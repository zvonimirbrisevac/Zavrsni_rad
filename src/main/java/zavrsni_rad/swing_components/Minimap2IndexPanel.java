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
import zavrsni_rad.utils.Utils;

public class Minimap2IndexPanel extends JPanel {
	
	private JPanel filesPanel;
	private Minimap2IndexingOptPanel optionalPanel;
	private JTextField targetPath;
	private JTextField mmPath;
	private JTextField indexPath;
	private JFormattedTextField threadsField;
	private JTextField addOptionsField;
	
	public Minimap2IndexPanel() throws IOException {
		
		super();
		
		filesPanel = new JPanel(new SpringLayout());

		JLabel pleaseCheck = new JLabel("Path to your minimap2.exe:", SwingConstants.LEFT);
		filesPanel.add(pleaseCheck);

		mmPath = new JTextField(16);
		mmPath.setText(Utils.fetchMinimap2Path());
		
		JButton chooseFileButton = new JButton("Choose file");
		chooseFileButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			try {
				fileChooser = new JFileChooser(Utils.fetchMinimap2Path());
				int returnValue = fileChooser.showOpenDialog(Minimap2IndexPanel.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					mmPath.setText(file.toPath().toString());
					Utils.writePath("minimap2", file.toPath().toString());
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		

		filesPanel.add(mmPath);
		filesPanel.add(chooseFileButton);
				
		JLabel targetLabel = new JLabel("Target file:", SwingConstants.LEFT);

		targetPath = new JTextField(16);

		JButton chooseTargetButton = new JButton("Choose file");
		chooseTargetButton.addActionListener((e) -> {
			JFileChooser fileChooser = null;
			fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(Minimap2IndexPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				targetPath.setText(file.toPath().toString());
			}
		});

		filesPanel.add(targetLabel);
		filesPanel.add(targetPath);
		filesPanel.add(chooseTargetButton);
		
		JLabel threadsLabel = new JLabel("Number of threads: ", SwingConstants.LEFT);
		
		NumberFormat threadsFormat = NumberFormat.getInstance();
		NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
		threadsFormatter.setValueClass(Integer.class);
		threadsFormatter.setMinimum(0);
		threadsFormatter.setMaximum(Integer.MAX_VALUE);
		//threadsFormatter.setAllowsInvalid(false);
		// threadsFormatter.setCommitsOnValidEdit(true);
		threadsField = new JFormattedTextField(threadsFormatter);
		threadsField.setColumns(16);
		//filesPanel.add(indexPanel);
		
		filesPanel.add(threadsLabel);
		filesPanel.add(threadsField);
		filesPanel.add(new JPanel());
		
		JLabel addOptionsLabel = new JLabel("Other optional arguments: ", SwingConstants.LEFT);
		
		addOptionsField = new JTextField(16);
		
		filesPanel.add(addOptionsLabel);
		filesPanel.add(addOptionsField);
		filesPanel.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(filesPanel, 4, 3, 5, 5, 10, 10);
		add(filesPanel);
		
		//optionalPanel = new Minimap2IndexingOptPanel();
		//add(optionalPanel);
		
	}
	
	
	public Minimap2IndexingOptPanel getIndOptPanel() {
		return optionalPanel; 
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
						"Error", JOptionPane.ERROR_MESSAGE);
				return -2;
			}
			
		}
		return -1;
	}
	
	public String getAddOptions() {
		return addOptionsField.getText();
	}
	
	
	
	public void clearFields() {
		targetPath.setText("");
		indexPath.setText("");
		addOptionsField.setText("");
		threadsField.setText("");
	}
}

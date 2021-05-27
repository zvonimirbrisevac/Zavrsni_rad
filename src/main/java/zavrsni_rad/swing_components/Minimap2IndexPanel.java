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
	private JPanel targetPanel;
	private JPanel indexPanel;
	private Minimap2IndexingOptPanel optionalPanel;
	private JTextField targetPath;
	private JTextField mmPath;
	private JTextField indexPath;
	
	
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
			int returnValue = fileChooser.showOpenDialog(Minimap2IndexPanel.this);
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
			int returnValue = fileChooser.showOpenDialog(Minimap2IndexPanel.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				indexPath.setText(file.toPath().toString());
			}
		});

		indexPanel.add(indexPath);
		indexPanel.add(chooseIndexFiles);

		filesPanel.add(indexPanel);
		
		SpringUtilities.makeCompactGrid(filesPanel, 3, 2, 5, 5, 10, 10);
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
	
	public String getIndexPath() {
		if (indexPath.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "No index path!", "Dialog", JOptionPane.ERROR_MESSAGE);

		}
		return indexPath.getText();
	}
	
	
	
	public void clearFields() {
		targetPath.setText("");
		indexPath.setText("");
		
	}
}

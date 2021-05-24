package zavrsni_rad.swing_components;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

import javax.swing.JButton;
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

public class RavenPanel extends JPanel {
	
	private JPanel filesPanel;
	private JPanel ravenPathPanel;
	private JPanel sequencesPanel;
	private JPanel threadsPanel;
	private JTextField ravenPath;
	private JTextField sequencesPath;
	private JFormattedTextField threadsField;
	
	public RavenPanel() throws IOException {
		super();
		
		filesPanel = new JPanel();
		filesPanel.setLayout(new SpringLayout());
		
		JLabel pleaseCheck = new JLabel("Path to your raven.exe:", SwingConstants.LEFT);
		filesPanel.add(pleaseCheck);

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

		filesPanel.add(checkRaven);*/
		
		filesPanel.add(ravenPath);
		filesPanel.add(chooseRavenFileButton);
		
		JLabel sequencesLabel = new JLabel("Sequences file(s):", SwingConstants.LEFT);
		filesPanel.add(sequencesLabel);

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

		filesPanel.add(sequencesPanel);*/
		
		filesPanel.add(sequencesPath);
		filesPanel.add(chooseSequencesFiles);
		
		JLabel threadsLabel = new JLabel("Number of threads (optional):", SwingConstants.LEFT);
		filesPanel.add(threadsLabel);
		
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
		
		//filesPanel.add(threadsField);
		
		//filesPanel.add(new JPanel());
		
		//threadsPanel.setLayout(new GridLayout(1, 1));
		threadsPanel.add(threadsField);
		//threadsPanel.add(new JPanel());
		//JButton b = new JButton("bzvz");
		//b.setVisible(false);
		//b.disable();
		//threadsPanel.add(b);
		
		filesPanel.add(threadsField);
		
		filesPanel.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(filesPanel, 3, 3, 5, 5, 10, 10);
		
		this.add(filesPanel);
		
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
	
	public String getSequencesPath() {
		if (sequencesPath.getText().equals("")) {
			JOptionPane.showMessageDialog(filesPanel, "No sequences paths!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return sequencesPath.getText();
	}
	
	public String getRavenPath() {
		if (ravenPath.getText().equals("")) {
			JOptionPane.showMessageDialog(filesPanel, "No Raven path!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return ravenPath.getText();
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
				JOptionPane.showMessageDialog(filesPanel, "Illegal " + dataType + " value!", 
						"Dialog", JOptionPane.ERROR_MESSAGE);
				return -2;
			}
			
		}
		return -1;
	}

	public void clearFields() {
		sequencesPath.setText("");
		threadsField.setText("");
	}
	
	
	
}

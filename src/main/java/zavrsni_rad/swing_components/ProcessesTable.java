package zavrsni_rad.swing_components;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class ProcessesTable  extends JFrame {
	
	private JPanel northPanel;
	private JPanel centralPanel;
	private JTable table;
	private JComboBox<String> typeBox;
	private JComboBox<String> statusBox;
	private JScrollPane scrollPanel;
	
	public ProcessesTable() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocation(200, 200);
		setTitle("Your processes");
		try {
			initGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	protected void initGUI() throws IOException {
		this.setLayout(new BorderLayout());
		
		File processesFile = new File("all_processes/all_process.log");
		if (!processesFile.exists()) {
			this.add(new JLabel("No processes so far."));
			return;
		}
		
		northPanel = new JPanel();
		northPanel.setLayout(new SpringLayout());
		
		JLabel selectProcessType = new JLabel("Select process type: ", SwingConstants.LEFT);
		northPanel.add(selectProcessType);
		
		String[] processTypes = {"", "Minimap2 alignment", "Minimap2 mapping", "Raven", "Ram mapping"};
		typeBox = new JComboBox<String>(processTypes);
		//typeBox.addActionListener();
		northPanel.add(typeBox);
		
		JLabel selectStatusType = new JLabel("Select status type: ", SwingConstants.LEFT);
		northPanel.add(selectStatusType);
		
		String[] statusTypes = {"", "Finished", "Running", "Failed"};
		statusBox = new JComboBox<String>(statusTypes);
		//statusBox.addActionListener();
		northPanel.add(statusBox);
		
		SpringUtilities.makeCompactGrid(northPanel, 2, 2, 10, 10, 10, 10);
		this.add(northPanel, BorderLayout.NORTH);
		
		String[] columnNames = {"Id", "Reference file", "Query file(s)", "Output file", "Start timestamp", "End timestamp", "Process type", "Status type"};
		List<String> fileContent = new ArrayList<>(Files.readAllLines(processesFile.toPath()));
		String[][] data = new String[fileContent.size()][8];
		int index = 0;
		for (String s : fileContent) {
			String[] processData = s.split(" : ");
			for (int i = 0; i < processData.length; i++)
				data[index][i] = processData[i];
			index++;
		}
		System.out.println(">>>>dodao je data u tablicu<<<<<");
		table = new JTable(data, columnNames);
		table.setFillsViewportHeight(true);
		
		scrollPanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		centralPanel = new JPanel();
		centralPanel.add(scrollPanel);
		this.add(centralPanel);
		
		
		
	}
	
}

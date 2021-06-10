package zavrsni_rad.swing_components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import zavrsni_rad.main_app.App;
import zavrsni_rad.process_runner.ProcessRunner;
import zavrsni_rad.swing_layouts.SpringUtilities;

public class ProcessesTable extends JFrame {

	private JPanel northPanel;
	private JPanel centralPanel;
	private JTable table;
	private JComboBox<String> typeBox;
	private JComboBox<String> statusBox;
	private JScrollPane scrollPanel;

	public ProcessesTable() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1500, 500);
		setLocation(200, 200);
		setTitle("Your processes");

		File processesFile = new File("all_processes/all_process.log");
		if (!processesFile.exists()) {
			this.dispose();
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(new JFrame(), "No processes to show.",
						"Failure", JOptionPane.ERROR_MESSAGE);
			});
		} else {
			try {
				initGUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	protected void initGUI() throws IOException {
		this.setLayout(new BorderLayout());

		File processesFile = new File("all_processes/all_process.log");

		northPanel = new JPanel();
		northPanel.setLayout(new SpringLayout());

		JLabel selectProcessType = new JLabel("Select process type: ", SwingConstants.LEFT);
		northPanel.add(selectProcessType);

		String[] processTypes = { "", "Minimap2 alignment", "Minimap2 mapping", "Raven", "Ram mapping" };
		typeBox = new JComboBox<String>(processTypes);
		// typeBox.addActionListener();
		northPanel.add(typeBox);

		northPanel.add(new JPanel());

		JLabel selectStatusType = new JLabel("Select status type: ", SwingConstants.LEFT);
		northPanel.add(selectStatusType);

		String[] statusTypes = { "", "Finished", "Running", "Failed" };
		statusBox = new JComboBox<String>(statusTypes);
		// statusBox.addActionListener();
		northPanel.add(statusBox);

		String[] columnNames = { "Id", "Reference file", "Query file(s)", "Output file", "Start timestamp",
				"End timestamp", "Process type", "Status type" };
		List<String> fileContent = new ArrayList<>(Files.readAllLines(processesFile.toPath()));
		String[][] data = new String[fileContent.size()][8];
		int index = 0;
		for (String s : fileContent) {
			String[] processData = s.split(" : ");
			for (int i = 0; i < processData.length; i++)
				data[index][i] = processData[i];
			index++;
		}
		// System.out.println(">>>>dodao je data u tablicu<<<<<");
		table = new JTable(data, columnNames);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		table.setFillsViewportHeight(true);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		for (int i = 1; i < 8; i++)
			columnModel.getColumn(i).setPreferredWidth(1500);

		// columnModel.getColumn(1).setPreferredWidth(100);
		// columnModel.getColumn(2).

		scrollPanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		// centralPanel = new JPanel();
		// centralPanel.add(scrollPanel);
		this.add(scrollPanel);

		JPanel buttonPanel = new JPanel(new SpringLayout());
		JButton filterButton = new JButton("Filter");
		filterButton.addActionListener((e) -> {
			String processType = getProcessEnum((String) typeBox.getSelectedItem());
			String statusType = getStatusEnum((String) statusBox.getSelectedItem());
			sorter.setRowFilter(null);
			if (!processType.equals(""))
				sorter.setRowFilter(RowFilter.regexFilter(processType));
			if (!statusType.equals(""))
				sorter.setRowFilter(RowFilter.regexFilter(statusType));
			/*
			 * List<String> fileContent = null; try { fileContent = new
			 * ArrayList<>(Files.readAllLines(processesFile.toPath())); } catch (IOException
			 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); } if
			 * (!processType.equals("")) fileContent = fileContent.stream().filter(s ->
			 * s.split(" : ")[6].equals(processType)).collect(Collectors.toList()); if
			 * (!statusType.equals("")) fileContent = fileContent.stream().filter(s ->
			 * s.split(" : ")[7].equals(statusType)).collect(Collectors.toList());
			 * 
			 * String[][] newData = new String[fileContent.size()][8]; int index = 0; for
			 * (String s : fileContent) { String[] processData = s.split(" : "); for (int i
			 * = 0; i < processData.length; i++) newData[index][i] = processData[i];
			 * index++; } table = new JTable(newData);
			 */
		});
		// filterButton.setPreferredSize(new Dimension(100, 25));
		// filterButton.setMaximumSize(filterButton.getPreferredSize());
		buttonPanel.add(filterButton, LEFT_ALIGNMENT);
		// filterButton.setPreferredSize(new Dimension(150, 30));

		northPanel.add(buttonPanel);

		SpringUtilities.makeCompactGrid(northPanel, 2, 3, 10, 10, 10, 10);
		this.add(northPanel, BorderLayout.NORTH);

	}

	public String getProcessEnum(String type) {
		// "", "Minimap2 alignment", "Minimap2 mapping", "Raven", "Ram mapping"}
		switch (type) {
		case "Minimap2 alignment":
			return App.PanelType.MINIMAP2_ALIGN.toString();
		case "Minimap2 mapping":
			return App.PanelType.MINIMAP2_MAPPING.toString();
		case "Raven":
			return App.PanelType.RAVEN.toString();
		case "Ram mapping":
			return App.PanelType.RAM_MAPPING.toString();
		default:
			return "";
		}
	}

	public String getStatusEnum(String type) {
		// {"", "Finished", "Running", "Failed"};
		switch (type) {
		case "Finished":
			return ProcessRunner.ProcessStates.FINISHED.toString();
		case "Running":
			return ProcessRunner.ProcessStates.RUNNING.toString();
		case "Failed":
			return ProcessRunner.ProcessStates.FAILED.toString();
		default:
			return "";
		}
	}

}

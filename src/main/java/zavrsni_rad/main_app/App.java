package zavrsni_rad.main_app;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import zavrsni_rad.analyzers.PafAnalyzer;
import zavrsni_rad.analyzers.SamAnalyzer;
import zavrsni_rad.process_runner.ProcessRunner;
import zavrsni_rad.swing_components.Minimap2AlignPanel;
import zavrsni_rad.swing_components.Minimap2IndexPanel;
import zavrsni_rad.swing_components.Minimap2MappingPanel;
import zavrsni_rad.swing_components.ProcessesTable;
import zavrsni_rad.swing_components.RamPanel;
import zavrsni_rad.swing_components.RavenPanel;
import zavrsni_rad.swing_workers.Minimap2AlignExecution;
import zavrsni_rad.swing_workers.Minimap2MappingExecution;
import zavrsni_rad.swing_workers.RamExecution;
import zavrsni_rad.swing_workers.RavenExecution;
import zavrsni_rad.utils.Utils;

/**
 * Hello world!
 *
 */
public class App extends JFrame {

	private JPanel centralPanel;
	private JPanel centralDataPanel;
	/*
	 * private RamPanel ramMappingPanel; private Minimap2IndexPanel
	 * minimap2IndexingPanel; private Minimap2AlignPanel minimap2AlignPanel; private
	 * Minimap2MappingPanel minimap2MappingPanel; private RavenPanel ravenPanel;
	 */
	private PanelType dataType;

	public enum PanelType {
		RAM_MAPPING, MINIMAP2_MAPPING, MINIMAP2_INDEXING, MINIMAP2_ALIGN, RAVEN;
	}

	public App() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocation(100, 100);
		setTitle("MapIt");
		//ImageIcon icon = new ImageIcon("icons/MapIt_icon.png");
		//setIconImage(icon);
		try {
			initGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void initGUI() throws IOException {
		System.out.println("Current directory: " + System.getProperty("user.dir"));
		System.out.println("Initializing GUI..");
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout);
		
		centralPanel = new JPanel(new BorderLayout());
		add(centralPanel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu toolsMenu = new JMenu("Tools");
		
		ButtonGroup tools = new ButtonGroup();
		
		JRadioButton minimap2IndRadio = new JRadioButton("Minimap2 indexing");
		minimap2IndRadio.addActionListener((e) -> {
			centralPanel.removeAll();
			try {
				centralDataPanel = new Minimap2IndexPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JPanel labelPanel = new JPanel(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 0, 0, 0);
			JLabel label = new JLabel("Minimap2 indexing");
			labelPanel.add(label, constraints);
			centralPanel.add(labelPanel, BorderLayout.NORTH);
			centralPanel.add(centralDataPanel, BorderLayout.CENTER);
			dataType = PanelType.MINIMAP2_INDEXING;
			centralPanel.revalidate();
			centralPanel.repaint();
			this.pack();
			
		});
		tools.add(minimap2IndRadio);
		
		JRadioButton minimap2AlignRadio = new JRadioButton("Minimap2 alignment");
		minimap2AlignRadio.addActionListener((e) -> {
			centralPanel.removeAll();
			try {
				centralDataPanel = new Minimap2AlignPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JPanel labelPanel = new JPanel(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 0, 0, 0);
			JLabel label = new JLabel("Minimap2 alignment");
			labelPanel.add(label, constraints);
			centralPanel.add(labelPanel, BorderLayout.NORTH);
			centralPanel.add(centralDataPanel, BorderLayout.CENTER);
			dataType = PanelType.MINIMAP2_ALIGN;
			centralPanel.revalidate();
			centralPanel.repaint();
			this.pack();
			
		});
		tools.add(minimap2AlignRadio);
		
		JRadioButton minimap2MappingRadio = new JRadioButton("Minimap2 mapping");
		minimap2MappingRadio.addActionListener((e) -> {
			centralPanel.removeAll();
			try {
				centralDataPanel = new Minimap2MappingPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JPanel labelPanel = new JPanel(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 0, 0, 0);
			JLabel label = new JLabel("Minimap2 mapping");
			labelPanel.add(label, constraints);
			centralPanel.add(labelPanel, BorderLayout.NORTH);
			centralPanel.add(centralDataPanel, BorderLayout.CENTER);
			dataType = PanelType.MINIMAP2_MAPPING;
			centralPanel.revalidate();
			centralPanel.repaint();
			this.pack();
			
		});
		tools.add(minimap2MappingRadio);
		
		JRadioButton ramRadio = new JRadioButton("Ram");
		ramRadio.addActionListener((e) -> {
			//BorderLayout layout_temp = (BorderLayout) centra.getLayout();
			centralPanel.removeAll();
			try {
				centralDataPanel = new RamPanel();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dataType = PanelType.RAM_MAPPING;
			centralPanel.add(centralDataPanel, BorderLayout.CENTER);
			JPanel labelPanel = new JPanel(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 0, 0, 0);
			JLabel label = new JLabel("Ram");
			labelPanel.add(label, constraints);
			centralPanel.add(labelPanel, BorderLayout.NORTH);
			centralPanel.repaint();
			centralPanel.revalidate();
			this.pack();
			
		});
		tools.add(ramRadio);
		
		JRadioButton ravenRadio = new JRadioButton("Raven");
		ravenRadio.addActionListener((e) -> {
			centralPanel.removeAll();
			try {
				centralDataPanel = new RavenPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dataType = PanelType.RAVEN;
			centralPanel.add(centralDataPanel, BorderLayout.CENTER);
			JPanel labelPanel = new JPanel(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 0, 0, 0);
			JLabel label = new JLabel("Raven");
			labelPanel.add(label, constraints);
			centralPanel.add(labelPanel, BorderLayout.NORTH);
			centralPanel.repaint();
			centralPanel.revalidate();
			this.pack();
			
		});
		tools.add(ravenRadio);
		
		
		toolsMenu.add(minimap2IndRadio);
		toolsMenu.add(minimap2AlignRadio);
		toolsMenu.add(minimap2MappingRadio);
		toolsMenu.add(new JSeparator(SwingConstants.HORIZONTAL));
		toolsMenu.add(ramRadio);
		toolsMenu.add(new JSeparator(SwingConstants.HORIZONTAL));
		toolsMenu.add(ravenRadio);
		
		minimap2AlignRadio.doClick();
		menuBar.add(toolsMenu);
		
		JMenu processesMenu = new JMenu("Processes");
		
		JMenuItem checkoutProcessesMenu = new JMenuItem("Checkout processes");
		checkoutProcessesMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(() -> {
		        	ProcessesTable table = new ProcessesTable();
		        	table.setVisible(true);
				}
				);
			}
		});
	
		processesMenu.add(checkoutProcessesMenu);
		
		JMenuItem analyzeProcessesMenu = new JMenuItem("Analyze process");
		analyzeProcessesMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = null;
				id = (String)JOptionPane.showInputDialog(
	                    App.this, "Please enter process id: ",
	                    "Enter id",
	                    JOptionPane.PLAIN_MESSAGE,
						null, null, "");
				if (id != null && !id.equals(""))
					Utils.analyze(id, App.this);
				
			}
		});
		processesMenu.add(analyzeProcessesMenu);
		
		menuBar.add(processesMenu);
		
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem aboutMinimap2 = new JMenuItem("About Minimap2");
		aboutMinimap2.addActionListener((e) -> {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    try {
					Desktop.getDesktop().browse(new URI("https://github.com/lh3/minimap2#table-of-contents"));
				} catch (IOException | URISyntaxException e1) {
					JOptionPane.showMessageDialog(App.this, "Something went wrong, failed to fetch:\n"
							+ "https://github.com/lh3/minimap2#table-of-contents",
							"Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		
		JMenuItem aboutRam = new JMenuItem("About Ram");
		aboutRam.addActionListener((e) -> {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    try {
					Desktop.getDesktop().browse(new URI("https://github.com/lbcb-sci/ram#ram"));
				} catch (IOException | URISyntaxException e1) {
					JOptionPane.showMessageDialog(App.this, "Something went wrong, failed to fetch:\n"
							+ "https://github.com/lbcb-sci/ram#ram",
							"Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		
		JMenuItem aboutRaven = new JMenuItem("About Raven");
		aboutRaven.addActionListener((e) -> {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    try {
					Desktop.getDesktop().browse(new URI("https://github.com/lbcb-sci/raven#raven"));
				} catch (IOException | URISyntaxException e1) {
					JOptionPane.showMessageDialog(App.this, "Something went wrong, failed to fetch:\n"
							+ "https://github.com/lbcb-sci/raven#raven ",
							"Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		
		helpMenu.add(aboutMinimap2);
		helpMenu.add(aboutRam);
		helpMenu.add(aboutRaven);
		
		helpMenu.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		JMenuItem minimap2Version = new JMenuItem("Minimap2 version");
		minimap2Version.addActionListener((e) -> {
			String path = null;
			try {
				path = Utils.fetchMinimap2Path();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!path.equals("")) {
				String version = null;
				try {
					version = Utils.getToolVersion(path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(App.this, "Your Minimap2 version:\n"
						+ version,
						"Your Minimap2 version", JOptionPane.INFORMATION_MESSAGE);
				
			} else {
				JOptionPane.showMessageDialog(App.this, "Minimap2 not found, please check path to your Minimap2 program\n" +
						"(Tools -> Minimap2 alignment/Minimapw mapping)",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JMenuItem ramVersion = new JMenuItem("Ram version");
		ramVersion.addActionListener((e) -> {
			String path = null;
			try {
				path = Utils.fetchRamPath();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!path.equals("")) {
				String version = null;
				try {
					version = Utils.getToolVersion(path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(App.this, "Your Ram version:\n"
						+ version,
						"Your Ram version", JOptionPane.INFORMATION_MESSAGE);
				
			}else {
				JOptionPane.showMessageDialog(App.this, "Ram not found, please check path to your Ram program\n" +
						"(Tools -> Ram)",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JMenuItem ravenVersion = new JMenuItem("Raven version");
		ravenVersion.addActionListener((e) -> {
			String path = null;
			try {
				path = Utils.fetchRavenPath();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!path.equals("")) {
				String version = null;
				try {
					version = Utils.getToolVersion(path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(App.this, "Your Raven version:\n"
						+ version,
						"Your Raven version", JOptionPane.INFORMATION_MESSAGE);
				
			} else {
				JOptionPane.showMessageDialog(App.this, "Raven not found, please check path to your Raven program\n" +
						"(Tools -> Raven)",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		helpMenu.add(minimap2Version);
		helpMenu.add(ramVersion);
		helpMenu.add(ravenVersion);
		
		menuBar.add(helpMenu);
		
		this.add(menuBar, BorderLayout.NORTH);
		
		JPanel buttonsPanel = new JPanel();

	    JPanel buttonsGridPanel = new JPanel();
	    buttonsGridPanel.setLayout(new GridLayout(1, 0, 10, 0));
	    buttonsPanel.add(buttonsGridPanel);

	    JButton buttonRun = new JButton("Run");
	    //buttonRun.setAction(new RunAction(dataType));
	    buttonRun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dataType == PanelType.RAM_MAPPING) 
					new RamExecution((RamPanel)centralDataPanel).execute();
				else if (dataType == PanelType.MINIMAP2_ALIGN)
					new Minimap2AlignExecution((Minimap2AlignPanel)centralDataPanel).execute();
				else if (dataType == PanelType.MINIMAP2_MAPPING)
					new Minimap2MappingExecution((Minimap2MappingPanel)centralDataPanel).execute();
				else if (dataType == PanelType.RAVEN)
					new RavenExecution((RavenPanel)centralDataPanel).execute();
			}
		});
	    buttonsGridPanel.add(buttonRun);

	    JButton buttonDelete = new JButton("Delete");
	    buttonDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dataType == PanelType.RAM_MAPPING) 
		    		((RamPanel) centralDataPanel).clearFields();
		    	else if (dataType == PanelType.MINIMAP2_ALIGN) 
		    		((Minimap2AlignPanel) centralDataPanel).clearFields();
		    	else if (dataType == PanelType.MINIMAP2_MAPPING)
		    		((Minimap2MappingPanel) centralDataPanel).clearFields();
		    	else if (dataType == PanelType.RAVEN)
		    		((RavenPanel) centralDataPanel).clearFields();
			}
	    });
	    buttonsGridPanel.add(buttonDelete);
	    
	    add(buttonsPanel, BorderLayout.SOUTH);

	}

	/*
	 * private class RunAction extends AbstractAction { private PanelType type;
	 * 
	 * public RunAction(PanelType type) { super(); this.type = type; }
	 * 
	 * @Override public void actionPerformed(ActionEvent arg0) { if (type ==
	 * PanelType.RAM_MAPPING) { RamMappingExecution ramMap = new
	 * RamMappingExecution(); } }
	 * 
	 * }
	 */


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			App mainApp = new App();
			mainApp.setVisible(true);
			mainApp.pack();

		});
	}
	
	
	
	
	
	
}

package zavrsni_rad.main_app;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import zavrsni_rad.swing_components.Minimap2AlignPanel;
import zavrsni_rad.swing_components.Minimap2IndexPanel;
import zavrsni_rad.swing_components.RamPanel;
import zavrsni_rad.swing_components.Minimap2MappingPanel;
import zavrsni_rad.swing_components.RavenPanel;
import zavrsni_rad.swing_workers.Minimap2AlignExecution;
import zavrsni_rad.swing_workers.Minimap2MappingExecution;
import zavrsni_rad.swing_workers.RamExecution;
import zavrsni_rad.swing_workers.RavenExecution;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
	
	private JPanel centralPanel;
	private JPanel centralDataPanel;
	/*private RamPanel ramMappingPanel;
	private Minimap2IndexPanel minimap2IndexingPanel;
	private Minimap2AlignPanel minimap2AlignPanel;
	private Minimap2MappingPanel minimap2MappingPanel;
	private RavenPanel ravenPanel;*/
	private PanelType dataType;
	
	
	public enum PanelType {
		RAM_MAPPING, MINIMAP2_MAPPING, MINIMAP2_INDEXING, MINIMAP2_ALIGN, RAVEN;
	}
	
	public App() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocation(100, 100);
		setTitle("Aplikacija");
		try {
			initGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void initGUI() throws IOException {
		System.out.println("Current directory: " + System.getProperty("user.dir"));
		System.out.println("Initialazing GUI..");
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout);
		
		centralPanel = new JPanel();
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
			centralPanel.add(centralDataPanel);
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
			centralPanel.add(centralDataPanel);
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
		
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem aboutMinimap2 = new JMenuItem("About Minimap2");
		
		JMenuItem aboutRam = new JMenuItem("About Ram");
		
		JMenuItem aboutRaven = new JMenuItem("About Raven");
		
		helpMenu.add(aboutMinimap2);
		helpMenu.add(aboutRam);
		helpMenu.add(aboutRaven);
		
		menuBar.add(helpMenu);
		
		this.add(menuBar, BorderLayout.NORTH);
		
		JPanel buttonsPanel = new JPanel();

	    JPanel buttonsGridPanel = new JPanel();
	    buttonsGridPanel.setLayout(new GridLayout(1, 0, 10, 0));
	    buttonsPanel.add(buttonsGridPanel);

	    JButton buttonRun = new JButton("Run");
	    //buttonRun.setAction(new RunAction(dataType));
	    buttonRun.addActionListener((e) -> {
	    	if (dataType == PanelType.RAM_MAPPING) 
				new RamExecution((RamPanel)centralDataPanel).execute();
			else if (dataType == PanelType.MINIMAP2_ALIGN)
				new Minimap2AlignExecution((Minimap2AlignPanel)centralDataPanel).execute();
			else if (dataType == PanelType.MINIMAP2_MAPPING)
				new Minimap2MappingExecution((Minimap2MappingPanel)centralDataPanel).execute();
			else if (dataType == PanelType.RAVEN)
				new RavenExecution((RavenPanel)centralDataPanel).execute();
	    });
	    buttonsGridPanel.add(buttonRun);

	    JButton buttonDelete = new JButton("Delete");
	    buttonDelete.addActionListener((e) -> {
	    	if (dataType == PanelType.RAM_MAPPING) 
	    		((RamPanel) centralDataPanel).clearFields();
	    	else if (dataType == PanelType.MINIMAP2_ALIGN) 
	    		((Minimap2AlignPanel) centralDataPanel).clearFields();
	    	else if (dataType == PanelType.MINIMAP2_MAPPING)
	    		((Minimap2MappingPanel) centralDataPanel).clearFields();
	    	else if (dataType == PanelType.RAVEN)
	    		((RavenPanel) centralDataPanel).clearFields();
	    });
	    buttonsGridPanel.add(buttonDelete);
	    
	    add(buttonsPanel, BorderLayout.SOUTH);

	}
	
	/*private class RunAction extends AbstractAction {
		private PanelType type;
		
		public RunAction(PanelType type) {
			super();
			this.type = type;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (type == PanelType.RAM_MAPPING) {
				RamMappingExecution ramMap = new RamMappingExecution();
			}
		}
		
	}*/
	
	
	
			
			
		
	/*private class MmIndexingExecution extends SwingWorker<Integer, Integer> {
			
		public MmIndexingExecution() {
			super();
			
			try {
				doInBackground();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		@Override
		protected Integer doInBackground() throws Exception {
			ArrayList<String> commands = new ArrayList<String>();
				
			String minimap2Path = minimap2IndexingPanel.getMinimap2Path();
			if (!minimap2Path.equals("")) {
				commands.add(minimap2Path);
			} else {
				return null;
			}
			
			commands.add("-d");
			
			String indexPath = minimap2IndexingPanel.getIndexPath();
			if (!indexPath.equals(""))
				commands.add(indexPath);
			else
				return null;
			
			String targetPath = minimap2IndexingPanel.getTargetPath();
			if (!targetPath.equals(""))
				commands.add(targetPath);
			
			int kmer = minimap2IndexingPanel.getKmerField();
			if (kmer == -2) 
				return null;
			else if (kmer >= 0) {
				commands.add("-k");
				commands.add(Integer.toString(kmer));
			}
			
			int split = minimap2IndexingPanel.getSplitField();
			if (split == -2) 
				return null;
			else if (split >= 0) {
				commands.add("-I");
				commands.add(Integer.toString(split));
			}
				
			int window = minimap2IndexingPanel.getWindowField();
			if (window == -2) 
				return null;
			else if (window >= 0) {
				commands.add("-w");
				commands.add(Integer.toString(window));
			}
			
			if (minimap2IndexingPanel.getHomoKmer())
				commands.add("-H");
			
			System.out.print("Running cominimap2and: ");
			for (String s : commands)
				System.out.print(s + " ");
			System.out.println();
			
			ProcessBuilder pb = new ProcessBuilder(commands);
			File errorFile = new File(indexPath.substring(0, indexPath.length() - 4) + "_stream.log");
			errorFile.createNewFile();
			pb.redirectError(errorFile);
			pb.redirectOutput(new File(indexPath));
			Process process = pb.start();
			JOptionPane.showMessageDialog(new JFrame(), "Indexing running.", 
					"Dialog", JOptionPane.INFORMATION_MESSAGE);
			
			return null;

		
		}
	}*/ 
	
	
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(() -> {
        	App mainApp = new App();
        	mainApp.setVisible(true);
        	mainApp.pack();
        	
        });
    }
}

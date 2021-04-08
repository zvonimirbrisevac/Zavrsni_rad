package zavrsni_rad.main_app;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import zavrsni_rad.swing_components.DataPanelMinimap2Align;
import zavrsni_rad.swing_components.DataPanelMinimap2Index;
import zavrsni_rad.swing_components.DataPanelRam;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
	
	private JPanel centralPanel;
	private DataPanelRam ramMappingPanel;
	private DataPanelMinimap2Index minimap2IndexingPanel;
	private DataPanelMinimap2Align minimap2AlignPanel;
	private PanelType dataType;
	
	
	enum PanelType {
		RAM_MAPPING, MINIMAP2_MAPPING, MINIMAP2_INDEXING, MINIMAP2_ALIGN;
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
		System.out.println("Initialazing GUI..");
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout);
		
		centralPanel = new JPanel();
		add(centralPanel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu toolsMenu = new JMenu("Tools");
		
		ButtonGroup tools = new ButtonGroup();
		
		JRadioButton minimap2IndRadio = new JRadioButton("Minmap2 indexing");
		minimap2IndRadio.addActionListener((e) -> {
			//BorderLayout layout_temp = (BorderLayout) centra.getLayout();
			centralPanel.removeAll();
			try {
				minimap2IndexingPanel = new DataPanelMinimap2Index();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dataType = PanelType.MINIMAP2_INDEXING;
			centralPanel.add(minimap2IndexingPanel);
			centralPanel.repaint();
			centralPanel.revalidate();
			this.pack();
			
		});
		tools.add(minimap2IndRadio);
		
		JRadioButton minimap2AlignRadio = new JRadioButton("Minmap2 alignment");
		minimap2AlignRadio.addActionListener((e) -> {
			//BorderLayout layout_temp = (BorderLayout) centra.getLayout();
			centralPanel.removeAll();
			try {
				minimap2AlignPanel = new DataPanelMinimap2Align();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dataType = PanelType.MINIMAP2_ALIGN;
			centralPanel.add(minimap2AlignPanel);
			centralPanel.repaint();
			centralPanel.revalidate();
			this.pack();
			
		});
		tools.add(minimap2AlignRadio);
		
		JRadioButton ramRadio = new JRadioButton("Ram");
		ramRadio.addActionListener((e) -> {
			//BorderLayout layout_temp = (BorderLayout) centra.getLayout();
			centralPanel.removeAll();
			try {
				ramMappingPanel = new DataPanelRam();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dataType = PanelType.RAM_MAPPING;
			centralPanel.add(ramMappingPanel);
			centralPanel.repaint();
			centralPanel.revalidate();
			this.pack();
			
		});
		tools.add(ramRadio);
		ramRadio.doClick();
		
		toolsMenu.add(minimap2IndRadio);
		toolsMenu.add(minimap2AlignRadio);
		toolsMenu.add(ramRadio);
		
		menuBar.add(toolsMenu);
		
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem aboutMinimap2 = new JMenuItem("About Minimap2");
		
		JMenuItem aboutRam = new JMenuItem("About Ram");
		
		helpMenu.add(aboutMinimap2);
		helpMenu.add(aboutRam);
		
		menuBar.add(helpMenu);
		
		this.add(menuBar, BorderLayout.NORTH);
		
		JPanel buttonsPanel = new JPanel();

	    JPanel buttonsGridPanel = new JPanel();
	    buttonsGridPanel.setLayout(new GridLayout(1, 0, 10, 0));
	    buttonsPanel.add(buttonsGridPanel);

	    JButton buttonRun = new JButton("Run");
	    //buttonRun.setAction(new RunAction(dataType));
	    buttonRun.addActionListener((e) -> {
	    	if (dataType == PanelType.RAM_MAPPING) {
				RamMappingExecution ramMap = new RamMappingExecution();
			} else if (dataType == PanelType.MINIMAP2_INDEXING) {
				MmIndexingExecution minimap2Indexing = new MmIndexingExecution();
			}
	    });
	    buttonsGridPanel.add(buttonRun);

	    JButton buttonDelete = new JButton("Delete");
	    buttonDelete.addActionListener((e) -> {
	    	if (dataType == PanelType.RAM_MAPPING) {
	    		ramMappingPanel.clearFields();
	    	} else if (dataType == PanelType.MINIMAP2_INDEXING) {
	    		minimap2IndexingPanel.clearFields();
	    	}
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
	
	
	private class RamMappingExecution extends SwingWorker<Integer, Integer> {
		
		public RamMappingExecution() {
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
			
			String ramPath = ramMappingPanel.getRamPath();
			if (!ramPath.equals(""))
				commands.add(ramPath);
			else
				return null;
			
			String targetPath = ramMappingPanel.getTargetPath();
			if (!targetPath.equals(""))
				commands.add(targetPath);
			else
				return null;
			
			String[] seqPaths = ramMappingPanel.getSequencesPath();
			if (seqPaths != null)
				for(String s: seqPaths)
					commands.add(s);
			else
				return null;
			
			int bandWidth = ramMappingPanel.getBandField();
			if (bandWidth == -2) 
				return null;
			else if (bandWidth >= 0) { 
				commands.add("--bandwidth");
				commands.add(Integer.toString(bandWidth));
			}
			
			int chain = ramMappingPanel.getChainField();
			if (chain == -2) 
				return null;
			else if (chain >= 0) {
				commands.add("--chain");
				commands.add(Integer.toString(chain));
			}
			
			double freq = ramMappingPanel.getFreqField();
			if (freq == -2.0) 
				return null;
			else if (freq >= 0) {
				commands.add("-f");
				commands.add(Double.toString(freq));
			}
				
			int gap = ramMappingPanel.getGapField();
			if (gap == -2) 
				return null;
			else if (gap >= 0) { 
				commands.add("--gap");
				commands.add(Integer.toString(gap));
			}
			
			int kmer = ramMappingPanel.getKmerField();
			if (kmer == -2) 
				return null;
			else if (kmer >= 0) {
				commands.add("-k");
				commands.add(Integer.toString(kmer));
			}
			
			int matches = ramMappingPanel.getMatchesField();
			if (matches == -2) 
				return null;
			else if (matches >= 0) {
				commands.add("--matches");
				commands.add(Integer.toString(matches));
			}
				
			int window = ramMappingPanel.getWindowField();
			if (window == -2) 
				return null;
			else if (window >= 0) {
				commands.add("-w");
				commands.add(Integer.toString(window));
			}
				
			int threads = ramMappingPanel.getThreadsField();
			if (threads == -2) 
				return null;
			else if (threads >= 0) {
				commands.add("-t");
				commands.add(Integer.toString(threads));
			}
			
			if (ramMappingPanel.getMinhash())
				commands.add("--minhash");
			
			String pafPath = ramMappingPanel.getPafPath();
			if (pafPath.equals(""))
				return null;
			
			System.out.print("Running cominimap2and: ");
			for (String s : commands)
				System.out.print(s + " ");
			
			
			ProcessBuilder pb = new ProcessBuilder(commands);
			File errorFile = new File(pafPath.substring(0, pafPath.length() - 4) + "_stream.log");
			errorFile.createNewFile();
			pb.redirectError(errorFile);
			pb.redirectOutput(new File(pafPath));
			Process process = pb.start();
			JOptionPane.showMessageDialog(new JFrame(), "Mapping running.", 
					"Dialog", JOptionPane.INFORMATION_MESSAGE);
			/*int status = process.waitFor();
			if (status == 0) {
				JOptionPane.showMessageDialog(new JFrame(), "Mapping done.", 
						"Dialog", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Mapping failed.", 
						"Dialog", JOptionPane.ERROR_MESSAGE);
			}*/
			
			
			return null;
		}
	}
		
	private class MmIndexingExecution extends SwingWorker<Integer, Integer> {
			
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
	}
	
	
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(() -> {
        	App mainApp = new App();
        	mainApp.setVisible(true);
        	mainApp.pack();
        	
        });
    }
}

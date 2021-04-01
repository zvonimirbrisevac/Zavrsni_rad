package zavrsni_rad.main_app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import zavrsni_rad.swing_components.DataPanelRam;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
	private JPanel centralPanel;
	private PanelType dataType;
	
	
	enum PanelType {
		RAM_MAPPING, MINIMAP2_MAPPING;
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
		
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu toolsMenu = new JMenu("Tools");
		
		ButtonGroup tools = new ButtonGroup();
		
		JRadioButton minimap2Radio = new JRadioButton("Minmap2");
		tools.add(minimap2Radio);
		
		JRadioButton ramRadio = new JRadioButton("Ram");
		tools.add(ramRadio);
		ramRadio.setSelected(true);
		
		toolsMenu.add(minimap2Radio);
		toolsMenu.add(ramRadio);
		
		menuBar.add(toolsMenu);
		
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem aboutMinimap2 = new JMenuItem("About Minimap2");
		
		JMenuItem aboutRam = new JMenuItem("About Ram");
		
		helpMenu.add(aboutMinimap2);
		helpMenu.add(aboutRam);
		
		menuBar.add(helpMenu);
		
		this.add(menuBar, BorderLayout.NORTH);
		
		centralPanel = null;
		if (ramRadio.isSelected()) {
			centralPanel = new DataPanelRam();
		}
		
		this.add(centralPanel, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();

	    JPanel buttonsGridPanel = new JPanel();
	    buttonsGridPanel.setLayout(new GridLayout(1, 0, 10, 0));
	    buttonsPanel.add(buttonsGridPanel);

	    JButton buttonRun= new JButton("Run");
	    buttonRun.setAction(new RunAction());
	    buttonsGridPanel.add(buttonRun);

	    JButton buttonDelete = new JButton("Delete");
	    buttonsGridPanel.add(buttonDelete);
	    
	    add(buttonsPanel, BorderLayout.SOUTH);

	}
	
	private class RunAction extends AbstractAction {
		private JPanel dataPanel;
		
		public RunAction(JPanel dataPanel) {
			super();
			this.dataPanel = dataPanel;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
		}
		
	}
	
	
	private class RamMappingExecution extends SwingWorker<Integer, Integer> {

		@Override
		protected Integer doInBackground() throws Exception {
			// TODO Auto-generated method stub
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

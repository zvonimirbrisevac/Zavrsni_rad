package zavrsni_rad.swing_components;

import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;

public class ProcessesTable  extends JFrame {
	
	private JTable table;
	private JComboBox<String> box;
	
	public ProcessesTable() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	

	protected void initGUI() {
		
	}
	
}

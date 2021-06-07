package zavrsni_rad.swing_components;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AdditionalArgumentsWindow extends JFrame {
	
	private JPanel centralPanel;
	private JPanel southPanel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	
	public AdditionalArgumentsWindow() {
		
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 700);
		setLocation(300, 300);
		setTitle("Additional arguments info");
		
		try {
			initGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initGUI() throws IOException {
		
		this.setLayout(new BorderLayout());
		//this.setBounds(10, 10, 10, 10);
		
		textArea = new JTextArea();
		
		File argumentsInfoFile = new File("print_files/additional_arguments_info.txt");
		List<String> argumenstInfo = new ArrayList<>(Files.readAllLines(argumentsInfoFile.toPath()));
		for (String s : argumenstInfo) {
			textArea.append(s);
			textArea.append("\n");
		}
		textArea.setEditable(false);
		
		scrollPane = new JScrollPane(textArea);
		
		/*centralPanel = new JPanel();
		centralPanel.setAutoscrolls(true);
		centralPanel.add(scrollPane);*/
		
		this.add(scrollPane);
		
		//this.add(centralPanel);
		
		southPanel = new JPanel();
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			this.dispose();
		});
		southPanel.add(okButton);
		
		this.add(southPanel, BorderLayout.SOUTH);
		
	}
}

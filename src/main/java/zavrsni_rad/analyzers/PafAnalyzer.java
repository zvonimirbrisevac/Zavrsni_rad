package zavrsni_rad.analyzers;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class PafAnalyzer  extends JFrame{
	
	private JPanel centralPanel;
	private JPanel southPanel;
	
	private long positiveStrand = 0;
	private long queryTotalLength = 0;
	private long targetTotalLength = 0;
	private long totalResidueMatches = 0;
	private long totalAlignmentBlockLength = 0;
	private long totalMappingQuality = 0;
	private long fileLength = 0;
	

	public PafAnalyzer(String[] data) throws IOException {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocation(200, 200);
		setTitle("Result");
		
		getDataFromFile(data);
		
		initGUI();
		
	}
	
	private void initGUI() {
		
		DecimalFormat df = new DecimalFormat("#.###");
		
		DecimalFormat lf = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = lf.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		lf.setDecimalFormatSymbols(symbols);
		
		this.setLayout(new BorderLayout());
		
		centralPanel = new JPanel(new SpringLayout());
		
		centralPanel.add(new JLabel("Query total length:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(queryTotalLength), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Target total length:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(targetTotalLength), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Percentage of original relative strands:\t", SwingConstants.LEFT));
		
		double percOriginalStrands = ((double)positiveStrand) / fileLength;
		centralPanel.add(new JLabel(df.format(percOriginalStrands), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Total residue matches:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(totalResidueMatches), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Average length of alignment block:\t", SwingConstants.LEFT));
		
		double averageBlockLength = ((double) totalAlignmentBlockLength) / fileLength;
		centralPanel.add(new JLabel(df.format(averageBlockLength), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Average mapping quality:\t", SwingConstants.LEFT));
		
		double averageMapQuality = ((double) totalMappingQuality) / fileLength;
		centralPanel.add(new JLabel(df.format(averageMapQuality), SwingConstants.LEFT));
		
		SpringUtilities.makeCompactGrid(centralPanel, 6, 2, 20, 20, 10, 10);
		
		this.add(centralPanel, BorderLayout.CENTER);
		
		southPanel = new JPanel();
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			this.dispose();
		});
		southPanel.add(okButton);
		
		this.add(southPanel, BorderLayout.SOUTH);
		
		
	}
	
	public void getDataFromFile(String[] data) throws IOException {
		File outputFile = new File(data[3]);
		if (!outputFile.exists()) {
			JOptionPane.showMessageDialog(this, "Output file not found.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		List<String> content = new ArrayList<>(Files.readAllLines(outputFile.toPath()));
		fileLength = content.size();
		for (String line : content) {
			String[] pafData = line.split("\t");
			/*System.out.println(">>>> data size: " + pafData.length);
			for (int i = 0; i < pafData.length; i++) {
				System.out.println("data " + i + " : " + pafData[i]);
			}*/
			queryTotalLength += Long.parseLong(pafData[1]);
			if (pafData[4].equals("+"))
				positiveStrand++;
			targetTotalLength += Long.parseLong(pafData[6]);
			totalResidueMatches += Long.parseLong(pafData[9]);
			totalAlignmentBlockLength += Long.parseLong(pafData[10]);
			totalMappingQuality += Long.parseLong(pafData[11]);
		}
	}
}

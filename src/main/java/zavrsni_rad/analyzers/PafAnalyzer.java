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

public class PafAnalyzer extends JFrame{
	
	private JPanel centralPanel;
	private JPanel southPanel;
	
	private long positiveStrand = 0;
	private long queryTotalLength = 0;
	private long queryTotalStartToEnd = 0;
	private double averageQueryCoverage = 0;
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
		
		if (getDataFromFile(data))
			initGUI();
		else
			this.dispose();
		
	}
	
	private void initGUI() {
		
		DecimalFormat df = new DecimalFormat("#######.###");
		
		DecimalFormat lf = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = lf.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		lf.setDecimalFormatSymbols(symbols);
		
		this.setLayout(new BorderLayout());
		
		centralPanel = new JPanel(new SpringLayout());
		
		centralPanel.add(new JLabel("Average query coverage in alignment blocks:\t", SwingConstants.LEFT));
		
		double queryCoverage = averageQueryCoverage / fileLength;
		centralPanel.add(new JLabel(df.format(queryCoverage * 100) + " %", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Percentage of original relative strands:\t", SwingConstants.LEFT));
		
		double percOriginalStrands = ((double)positiveStrand) / fileLength;
		centralPanel.add(new JLabel(df.format(percOriginalStrands * 100) + " %", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Average percentage of matches in alignment block:\t", SwingConstants.LEFT));
		
		double averageResidueMatches = ((double) totalResidueMatches) / totalAlignmentBlockLength;
		centralPanel.add(new JLabel(df.format(averageResidueMatches * 100) + " %", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Average length of alignment block:\t", SwingConstants.LEFT));
		
		double averageBlockLength = ((double) totalAlignmentBlockLength) / fileLength;
		centralPanel.add(new JLabel(df.format(averageBlockLength), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Average mapping quality (0-255):\t", SwingConstants.LEFT));
		
		double averageMapQuality = ((double) totalMappingQuality) / fileLength;
		centralPanel.add(new JLabel(df.format(averageMapQuality), SwingConstants.LEFT));
		
		SpringUtilities.makeCompactGrid(centralPanel, 5, 2, 20, 20, 10, 10);
		
		this.add(centralPanel, BorderLayout.CENTER);
		
		southPanel = new JPanel();
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			this.dispose();
		});
		southPanel.add(okButton);
		
		this.add(southPanel, BorderLayout.SOUTH);
		
		
	}
	
	public boolean getDataFromFile(String[] data) throws IOException {
		File outputFile = new File(data[3]);
		if (!outputFile.exists()) {
			JOptionPane.showMessageDialog(this, "Output file not found.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		List<String> content = new ArrayList<>(Files.readAllLines(outputFile.toPath()));
		fileLength = content.size();
		for (String line : content) {
			String[] pafData = line.split("\t");
			
			queryTotalLength += Long.parseLong(pafData[1]);
			if (pafData[4].equals("+"))
				positiveStrand++;
			averageQueryCoverage += ((double)Long.parseLong(pafData[3]) - Long.parseLong(pafData[2])) / Long.parseLong(pafData[1]);
			totalResidueMatches += Long.parseLong(pafData[9]);
			totalAlignmentBlockLength += Long.parseLong(pafData[10]);
			totalMappingQuality += Long.parseLong(pafData[11]);
		}
		return true;
	}
}

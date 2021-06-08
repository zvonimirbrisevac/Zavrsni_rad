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
import zavrsni_rad.utils.Utils;

public class FastaAnalyzer extends JFrame {
	
	private JPanel centralPanel;
	private JPanel southPanel;
	
	private long totalLength = 0;
	private long largestContig = 0;
	private double GCpercentage = 0;
	private long N50length = 0;
	private long N75length = 0;
	private long L50num = 0;
	private long L75num = 0;
	
			
	public FastaAnalyzer(String[] data) throws IOException, InterruptedException {
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
		DecimalFormat df = new DecimalFormat("##.###");
		
		DecimalFormat lf = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		DecimalFormatSymbols symbols = lf.getDecimalFormatSymbols();
		symbols.setGroupingSeparator(' ');
		lf.setDecimalFormatSymbols(symbols);
		
		this.setLayout(new BorderLayout());
		
		centralPanel = new JPanel(new SpringLayout());
		
		centralPanel.add(new JLabel("Total number of bases in the assembly:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(totalLength), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Length of the longest contig in the assembly:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(largestContig), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("Percentege of G and C nucleotides in the assembly:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(df.format(GCpercentage) + " %", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("N50 length:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(N50length), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("N75 length:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(N75length), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("The number of contigs equal to or longer than N50:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(L50num), SwingConstants.LEFT));
		
		centralPanel.add(new JLabel("The number of contigs equal to or longer than N75:\t", SwingConstants.LEFT));
		
		centralPanel.add(new JLabel(lf.format(L75num), SwingConstants.LEFT));
		
		SpringUtilities.makeCompactGrid(centralPanel, 7, 2, 20, 20, 10, 10);
		
		this.add(centralPanel, BorderLayout.CENTER);
		
		southPanel = new JPanel();
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			this.dispose();
		});
		southPanel.add(okButton);
		
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	public boolean getDataFromFile(String[] data) throws IOException, InterruptedException {
		File outputFile = new File(data[3]);
		if (!outputFile.exists()) {
			JOptionPane.showMessageDialog(this, "Output file not found.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String quastPath = Utils.fetchQuastPath();
		if (quastPath.equals("")) {
			JOptionPane.showMessageDialog(this, "File quast.py not found, please check your quast.py path\n" + 
					"(Tools -> Raven)",
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		ArrayList<String> commands = new ArrayList<>();
		commands.add(quastPath);
		commands.add(outputFile.toPath().toString());
		commands.add("-o");
		String[] outputFilePath = outputFile.toPath().toString().split("/");
		String outputFileName = outputFilePath[outputFilePath.length - 1];
		File resultDir = new File("quast_files/" + outputFileName);
		Files.createDirectories(resultDir.toPath());
		commands.add(resultDir.toPath().toString());
		ProcessBuilder pb = new ProcessBuilder(commands);
		Process process = pb.start();
		int status = process.waitFor();
		if (status != 0) {
			JOptionPane.showMessageDialog(this, "Analysis failed, for more information checkout:\n" +
					(resultDir.getAbsolutePath().toString() + "/quast.log"),
					"Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		File resultFile = new File(resultDir + "/report.tsv");
		List<String> resultLines = new ArrayList<>(Files.readAllLines(resultFile.toPath()));
		/*int i = 1;
		for (String s : resultLines) {
			System.out.print("Line " + (i++) + " : ");
			String[] lineData = s.split("\t");
			for (String s1 : lineData) {
				System.out.print(s1 + ", ");
			}
			System.out.println();
		}*/
		for (String s : resultLines) {
			String[] lineData = s.split("\t");
			if (lineData.length < 2)
				continue;
			switch (lineData[0]) {
				case "Total length": totalLength = Long.parseLong(lineData[1]);
										break;
				case "Largest contig": largestContig = Long.parseLong(lineData[1]);
										break;
				case "GC (%)": GCpercentage = Double.parseDouble(lineData[1]);
										break;
				case "N50": N50length = Long.parseLong(lineData[1]);
								break;
				case "N75": N75length = Long.parseLong(lineData[1]);
								break;
				case "L50": L50num = Long.parseLong(lineData[1]);
								break;
				case "L75": L75num = Long.parseLong(lineData[1]);
								break;
			}
		}
		

		
		return true;
	}
}

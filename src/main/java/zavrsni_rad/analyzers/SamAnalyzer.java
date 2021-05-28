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
import javax.swing.SwingContainer;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class SamAnalyzer extends JFrame {

	private JPanel centralPanel;
	private JPanel southPanel;

	private long dataSize = 0;
	private long totalMapQuality = 0;
	private long unmappedSegments = 0;
	private long originalSegments = 0;
	private long suplementaryAligns = 0;
	private long secondaryAligns = 0;
	private long properAligns = 0;

	public SamAnalyzer(String[] data) throws IOException {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocation(200, 200);
		setTitle("Result");
		System.out.println("Data za sam anal:");
		for (int i = 0; i < data.length; i++)
			System.out.println(data[i]);

		if (getDataFromFile(data))
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

		/*
		 * private long dataSize = 0; private long totalMapQuality = 0; private long
		 * unmappedSegments = 0; private long originalSegments = 0; private long
		 * suplementaryAligns = 0; private long secondaryAligns = 0; private long
		 * properAligns = 0;
		 */
		centralPanel.add(new JLabel("Percentage of properly aligned alignments:\t", SwingConstants.LEFT));

		double properPerc = ((double) properAligns) / dataSize;
		centralPanel.add(new JLabel(df.format(properPerc), SwingConstants.LEFT));

		centralPanel.add(new JLabel("Percantage of alignments on original strand:\t", SwingConstants.LEFT));

		double origPerc = ((double) originalSegments) / dataSize;
		centralPanel.add(new JLabel(df.format(origPerc), SwingConstants.LEFT));

		centralPanel.add(new JLabel("Percentage of alignments with unmapped segment(s):\t", SwingConstants.LEFT));

		double unmappedPerc = ((double) unmappedSegments) / dataSize;
		centralPanel.add(new JLabel(df.format(unmappedPerc), SwingConstants.LEFT));

		centralPanel.add(new JLabel("Percentage of secondary alignments:\t", SwingConstants.LEFT));

		double secondPerc = ((double) secondaryAligns) / dataSize;
		centralPanel.add(new JLabel(df.format(secondPerc), SwingConstants.LEFT));

		centralPanel.add(new JLabel("Percentage of suplementary alignments:\t", SwingConstants.LEFT));

		double suplementPerc = ((double) suplementaryAligns) / dataSize;
		centralPanel.add(new JLabel(df.format(suplementPerc), SwingConstants.LEFT));

		centralPanel.add(new JLabel("Average mapping quality of alignments:\t", SwingConstants.LEFT));

		double averageMapq = ((double) totalMapQuality) / dataSize;
		centralPanel.add(new JLabel(df.format(averageMapq), SwingConstants.LEFT));

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

	public boolean getDataFromFile(String[] data) throws IOException {
		File outputFile = new File(data[3]);
		if (!outputFile.exists()) {
			JOptionPane.showMessageDialog(this, "Output file not found.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		List<String> content = new ArrayList<>(Files.readAllLines(outputFile.toPath()));
		/*for (int i = 0; i < content.size(); i++) {
			if (content.get(i).startsWith("@")) {
				System.out.println("Removat ce se: " + content.get(i));
				content.remove(i);
			} else {
				System.out.println("Breakat ce se: " + content.get(i));
				break;
			}
		}*/
		//dataSize = content.size();
		for (String line : content) {
			if (line.startsWith("@"))
				continue;
			dataSize++;
			//System.out.println("Line: " + line);
			String[] samData = line.split("\t");
			//System.out.println("Mapping quality: " + samData[4]);
			totalMapQuality += Integer.parseInt(samData[4]);
			int flag = Integer.parseInt(samData[1]);
			boolean properly = (flag & (1 << 1)) != 0;
			if (properly)
				properAligns++;

			boolean unmapped = (flag & (1 << 2)) != 0;
			if (unmapped)
				unmappedSegments++;

			boolean reversed = (flag & (1 << 4)) != 0;
			if (!reversed)
				originalSegments++;

			boolean suplemen = (flag & (1 << 11)) != 0;
			if (suplemen)
				suplementaryAligns++;

			boolean secondary = (flag & (1 << 8)) != 0;
			if (secondary)
				secondaryAligns++;

		}
		return true;
	}
}

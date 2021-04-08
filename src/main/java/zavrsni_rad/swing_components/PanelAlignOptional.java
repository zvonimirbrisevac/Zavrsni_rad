package zavrsni_rad.swing_components;

import java.awt.Color;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class PanelAlignOptional extends JPanel {
	
	private JFormattedTextField zDropField;
	private JFormattedTextField zDropInvField;
	private JFormattedTextField gapExtField;
	private JFormattedTextField gapField;
	private JFormattedTextField matchField;
	private JFormattedTextField minPeakField;
	private JFormattedTextField mismatchField;
	private JCheckBox cigar;
	private JComboBox<String> wayFindGTAG;
	
	public PanelAlignOptional() {
		this.setBorder(
				BorderFactory.createTitledBorder(null, "Alignment optional arguments", TitledBorder.LEADING, TitledBorder.TOP));
		this.setLayout(new SpringLayout());

		JLabel matchLabel = new JLabel("Match score:", SwingConstants.LEFT);
		this.add(matchLabel);

		NumberFormat matchFormat = NumberFormat.getInstance();
		NumberFormatter matchFormatter = new NumberFormatter(matchFormat);
		matchFormatter.setValueClass(Integer.class);
		matchFormatter.setMinimum(0);
		matchFormatter.setMaximum(Integer.MAX_VALUE);
		//matchFormatter.setAllowsInvalid(false);
		// matchFormatter.setCommitsOnValidEdit(true);
		matchField = new JFormattedTextField(matchFormatter);
		matchField.setColumns(16);
		this.add(matchField);

		JLabel mismatchLabel = new JLabel("Mismatch penalty:", SwingConstants.LEFT);
		this.add(mismatchLabel);

		NumberFormat mismatchFormat = NumberFormat.getInstance();
		NumberFormatter mismatchFormatter = new NumberFormatter(mismatchFormat);
		mismatchFormatter.setValueClass(Integer.class);
		mismatchFormatter.setMinimum(0);
		mismatchFormatter.setMaximum(Integer.MAX_VALUE);
		//mismatchFormatter.setAllowsInvalid(false);
		// mismatchFormatter.setCommitsOnValidEdit(true);
		mismatchField = new JFormattedTextField(mismatchFormatter);
		mismatchField.setColumns(16);
		this.add(mismatchField);
		
		JLabel gapLabel = new JLabel("Gap open penalty:", SwingConstants.LEFT);
		this.add(gapLabel);

		NumberFormat gapFormat = NumberFormat.getInstance();
		NumberFormatter gapFormatter = new NumberFormatter(gapFormat);
		gapFormatter.setValueClass(Integer.class);
		gapFormatter.setMinimum(0);
		gapFormatter.setMaximum(Integer.MAX_VALUE);
		//gapFormatter.setAllowsInvalid(false);
		// gapFormatter.setCommitsOnValidEdit(true);
		gapField = new JFormattedTextField(gapFormatter);
		gapField.setColumns(16);
		this.add(gapField);

		JLabel gapExtLabel = new JLabel("Gap extension penalty:", SwingConstants.LEFT);
		this.add(gapExtLabel);

		NumberFormat gapExtFormat = NumberFormat.getInstance();
		NumberFormatter gapExtFormatter = new NumberFormatter(gapExtFormat);
		gapExtFormatter.setValueClass(Integer.class);
		gapExtFormatter.setMinimum(0);
		gapExtFormatter.setMaximum(100);
		//gapExtFormatter.setAllowsInvalid(false);
		// gapExtFormatter.setCommitsOnValidEdit(true);
		gapExtField = new JFormattedTextField(gapExtFormatter);
		gapExtField.setColumns(16);
		this.add(gapExtField);

		JLabel zDropLabel = new JLabel("Z-drop score:", SwingConstants.LEFT);
		this.add(zDropLabel);

		NumberFormat zDropFormat = NumberFormat.getInstance();
		NumberFormatter zDropFormatter = new NumberFormatter(zDropFormat);
		zDropFormatter.setValueClass(Integer.class);
		zDropFormatter.setMinimum(0);
		zDropFormatter.setMaximum(Integer.MAX_VALUE);
		//zDropFormatter.setAllowsInvalid(false);
		// mismatchFormatter.setCommitsOnValidEdit(true);
		zDropField = new JFormattedTextField(zDropFormatter);
		zDropField.setColumns(16);
		this.add(zDropField);

		JLabel zDropInvLabel = new JLabel("Inversion Z-drop score:", SwingConstants.LEFT);
		this.add(zDropInvLabel);

		NumberFormat zDropInvFormat = NumberFormat.getInstance();
		NumberFormatter zDropInvFormatter = new NumberFormatter(zDropInvFormat);
		zDropInvFormatter.setValueClass(Integer.class);
		zDropInvFormatter.setMinimum(0);
		zDropInvFormatter.setMaximum(Integer.MAX_VALUE);
		//zDropInvFormatter.setAllowsInvalid(false);
		// mismatchFormatter.setCommitsOnValidEdit(true);
		zDropInvField = new JFormattedTextField(zDropInvFormatter);
		zDropInvField.setColumns(16);
		this.add(zDropInvField);

		JLabel minPeakLabel = new JLabel("Minimal peak DP alignment score:", SwingConstants.LEFT);
		this.add(minPeakLabel);

		NumberFormat minPeakFormat = NumberFormat.getInstance();
		NumberFormatter minPeakFormatter = new NumberFormatter(minPeakFormat);
		minPeakFormatter.setValueClass(Integer.class);
		minPeakFormatter.setMinimum(0);
		minPeakFormatter.setMaximum(Integer.MAX_VALUE);
		//minPeakFormatter.setAllowsInvalid(false);
		// minPeakFormatter.setCommitsOnValidEdit(true);
		minPeakField = new JFormattedTextField(minPeakFormatter);
		minPeakField.setColumns(16);
		this.add(minPeakField);
		
		JLabel wayFindLabel = new JLabel("How to find GT-AG");
		this.add(wayFindLabel);
		
		String[] ways = {"", "Transcript strand", "Both strands", "Don't match GT-AG"};
		wayFindGTAG = new JComboBox<String>(ways);
		this.add(wayFindGTAG);
		
		cigar = new JCheckBox("Generate cigar string");
		this.add(cigar);
		
		this.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(this, 9, 2, 0, 0, 10, 10);
		
		
	}
}

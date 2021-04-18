package zavrsni_rad.swing_components;

import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class RamOptPanel extends JPanel{
	
	private JFormattedTextField bandField;
	private JFormattedTextField chainField;
	private JFormattedTextField freqField;
	private JFormattedTextField gapField;
	private JFormattedTextField kmerField;
	private JFormattedTextField matchesField;
	private JFormattedTextField windowField;
	private JFormattedTextField threadsField;
	private JCheckBox minhash;
	
	public RamOptPanel() {

		this.setBorder(
				BorderFactory.createTitledBorder(null, "Optional arguments", TitledBorder.LEADING, TitledBorder.TOP));
		this.setLayout(new SpringLayout());

		JLabel kmerLabel = new JLabel("Kmer length:", SwingConstants.LEFT);
		this.add(kmerLabel);

		NumberFormat kmerFormat = NumberFormat.getInstance();
		NumberFormatter kmerFormatter = new NumberFormatter(kmerFormat);
		kmerFormatter.setValueClass(Integer.class);
		kmerFormatter.setMinimum(0);
		kmerFormatter.setMaximum(Integer.MAX_VALUE);
		//kmerFormatter.setAllowsInvalid(false);
		// kmerFormatter.setCommitsOnValidEdit(true);
		kmerField = new JFormattedTextField(kmerFormatter);
		kmerField.setColumns(16);
		this.add(kmerField);

		JLabel windowLabel = new JLabel("Window length:", SwingConstants.LEFT);
		this.add(windowLabel);

		NumberFormat windowFormat = NumberFormat.getInstance();
		NumberFormatter windowFormatter = new NumberFormatter(windowFormat);
		windowFormatter.setValueClass(Integer.class);
		windowFormatter.setMinimum(0);
		windowFormatter.setMaximum(Integer.MAX_VALUE);
		//windowFormatter.setAllowsInvalid(false);
		// windowFormatter.setCommitsOnValidEdit(true);
		windowField = new JFormattedTextField(windowFormatter);
		windowField.setColumns(16);
		this.add(windowField);

		JLabel freqLabel = new JLabel("Frequency:", SwingConstants.LEFT);
		this.add(freqLabel);

		NumberFormat freqFormat = NumberFormat.getInstance();
		NumberFormatter freqFormatter = new NumberFormatter(freqFormat);
		freqFormatter.setValueClass(Integer.class);
		freqFormatter.setMinimum(0);
		freqFormatter.setMaximum(100);
		//freqFormatter.setAllowsInvalid(false);
		// freqFormatter.setCommitsOnValidEdit(true);
		freqField = new JFormattedTextField(freqFormatter);
		freqField.setColumns(16);
		this.add(freqField);

		JLabel bandLabel = new JLabel("Bandwidth length:", SwingConstants.LEFT);
		this.add(bandLabel);

		NumberFormat bandFormat = NumberFormat.getInstance();
		NumberFormatter bandFormatter = new NumberFormatter(bandFormat);
		bandFormatter.setValueClass(Integer.class);
		bandFormatter.setMinimum(0);
		bandFormatter.setMaximum(Integer.MAX_VALUE);
		//bandFormatter.setAllowsInvalid(false);
		// windowFormatter.setCommitsOnValidEdit(true);
		bandField = new JFormattedTextField(bandFormatter);
		bandField.setColumns(16);
		this.add(bandField);

		JLabel chainLabel = new JLabel("Chain length:", SwingConstants.LEFT);
		this.add(chainLabel);

		NumberFormat chainFormat = NumberFormat.getInstance();
		NumberFormatter chainFormatter = new NumberFormatter(chainFormat);
		chainFormatter.setValueClass(Integer.class);
		chainFormatter.setMinimum(0);
		chainFormatter.setMaximum(Integer.MAX_VALUE);
		//chainFormatter.setAllowsInvalid(false);
		// windowFormatter.setCommitsOnValidEdit(true);
		chainField = new JFormattedTextField(chainFormatter);
		chainField.setColumns(16);
		this.add(chainField);

		JLabel matchesLabel = new JLabel("Matches:", SwingConstants.LEFT);
		this.add(matchesLabel);

		NumberFormat matchesFormat = NumberFormat.getInstance();
		NumberFormatter matchesFormatter = new NumberFormatter(matchesFormat);
		matchesFormatter.setValueClass(Integer.class);
		matchesFormatter.setMinimum(0);
		matchesFormatter.setMaximum(Integer.MAX_VALUE);
		//matchesFormatter.setAllowsInvalid(false);
		// matchesFormatter.setCommitsOnValidEdit(true);
		matchesField = new JFormattedTextField(matchesFormatter);
		matchesField.setColumns(16);
		this.add(matchesField);

		JLabel gapLabel = new JLabel("Gap:", SwingConstants.LEFT);
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

		JLabel threadsLabel = new JLabel("Threads:", SwingConstants.LEFT);
		this.add(threadsLabel);

		NumberFormat threadsFormat = NumberFormat.getInstance();
		NumberFormatter threadsFormatter = new NumberFormatter(threadsFormat);
		threadsFormatter.setValueClass(Integer.class);
		threadsFormatter.setMinimum(0);
		threadsFormatter.setMaximum(Integer.MAX_VALUE);
		//threadsFormatter.setAllowsInvalid(false);
		// threadsFormatter.setCommitsOnValidEdit(true);
		threadsField = new JFormattedTextField(threadsFormatter);
		threadsField.setColumns(16);
		this.add(threadsField);
		
		minhash = new JCheckBox("Use only a portion of all minimizers");
		
		this.add(minhash);
		
		this.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(this, 9, 2, 0, 0, 10, 10);
	}
	
	public int getBandField() {
		return getIntValueFromField(bandField, "bandwidth length");
	}

	public int getChainField() {
		return getIntValueFromField(chainField, "chain");
	}
	

	public double getFreqField() {
		String freq = freqField.getText();
		if (!freq.equals("")) {
			try {
				double result = Double.parseDouble(freq);
				return result;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Illegal freq value!", 
						"Dialog", JOptionPane.ERROR_MESSAGE);
				return -2.0;
			}
			
		}
		return -1.0;
	}


	public int getGapField() {
		return getIntValueFromField(gapField, "gap");
	}

	public int getKmerField() {
		return getIntValueFromField(kmerField, "kmer length");
	}


	public int getMatchesField() {
		return getIntValueFromField(matchesField, "matches");
	}


	public int getWindowField() {
		return getIntValueFromField(windowField, "window length");
	}


	public int getThreadsField() {
		return getIntValueFromField(threadsField, "threads");
	}
	
	public boolean getMinhash() {
		return minhash.isSelected();
	}
	
	public static int getIntValueFromField(JFormattedTextField field, String dataType) {
		String s = field.getText();
		if (!s.equals("")) {
			try {
				int result = Integer.parseInt(s);
				return result;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Illegal " + dataType + " value!", 
						"Dialog", JOptionPane.ERROR_MESSAGE);
				return -2;
			}
			
		}
		return -1;
	}
}

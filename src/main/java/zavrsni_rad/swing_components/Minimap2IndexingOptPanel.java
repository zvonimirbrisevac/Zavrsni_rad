package zavrsni_rad.swing_components;

import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import zavrsni_rad.swing_layouts.SpringUtilities;

public class Minimap2IndexingOptPanel extends JPanel{
	
	private JFormattedTextField kmerField;
	private JFormattedTextField windowField;
	private JFormattedTextField splitField;
	private JCheckBox homoKmer;
	
	public Minimap2IndexingOptPanel() {
		
		super();
		
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
		// kmerFormatter.setCoitsOnValidEdit(true);
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
		// windowFormatter.setCoitsOnValidEdit(true);
		windowField = new JFormattedTextField(windowFormatter);
		windowField.setColumns(16);
		this.add(windowField);
		
		JLabel splitLabel = new JLabel("Split index:", SwingConstants.LEFT);
		this.add(splitLabel);
		
		NumberFormat splitFormat = NumberFormat.getInstance();
		NumberFormatter splitFormatter = new NumberFormatter(splitFormat);
		splitFormatter.setValueClass(Integer.class);
		splitFormatter.setMinimum(0);
		splitFormatter.setMaximum(Integer.MAX_VALUE);
		//splitFormatter.setAllowsInvalid(false);
		// windowFormatter.setCoitsOnValidEdit(true);
		splitField = new JFormattedTextField(splitFormatter);
		splitField.setColumns(16);
		this.add(splitField);
		
		homoKmer = new JCheckBox("Use homopolymer-compressed k-mer");
		this.add(homoKmer);
		
		this.add(new JPanel());
		
		SpringUtilities.makeCompactGrid(this, 4, 2, 0, 0, 10, 10);
	}
	
	public int getKmerField() {
		return getIntValueFromField(kmerField, "kmer length");
	}

	public int getWindowField() {
		return getIntValueFromField(windowField, "window length");
	}
	
	public int getSplitField() {
		return getIntValueFromField(splitField, "split index");
	}
	
	public boolean getHomoKmer() {
		return homoKmer.isSelected();
	}
	
	public int getIntValueFromField(JFormattedTextField field, String dataType) {
		String s = field.getText();
		if (!s.equals("")) {
			try {
				int result = Integer.parseInt(s);
				return result;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Illegal " + dataType + " value!", 
						"Dialog", JOptionPane.ERROR_MESSAGE);
				return -2;
			}
			
		}
		return -1;
	}
}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


public class FontSelector {
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FontSelectorFrame frame = new FontSelectorFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}


@SuppressWarnings("serial")
class FontSelectorFrame extends JFrame {
	
	private static final int MIN_FONT_SIZE = 4;
	private static final int MAX_FONT_SIZE = 500;
	private static final int DEFAULT_FONT_SIZE = 50;
	
	private JPanel rootPanel;
	private JPanel paramsPanel;
	private JLabel textLabel;
	private JComboBox<String> fontNameComboBox;
	private JSlider fontSizeSlider;
	private JCheckBox boldCheckBox;
	private JCheckBox italicCheckBox;
	private JButton resetButton;
	
	public FontSelectorFrame() {
		
		setTitle("Font Selector");

		// Create components
		
		textLabel = new JLabel("To be, or not to be");
		
		fontNameComboBox = new JComboBox<String>();
		fontNameComboBox.setEditable(false);
		for (String fontName : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
			fontNameComboBox.addItem(fontName);
		}
		fontNameComboBox.setSelectedIndex(0);
		fontNameComboBox.addActionListener(fontNameActionListener);
		
		fontSizeSlider = new JSlider(MIN_FONT_SIZE, MAX_FONT_SIZE, DEFAULT_FONT_SIZE);
		fontSizeSlider.addChangeListener(fontChangeListener);
		
		boldCheckBox = new JCheckBox("Bold");
		boldCheckBox.addChangeListener(fontChangeListener);
		
		italicCheckBox = new JCheckBox("Italic");
		italicCheckBox.addChangeListener(fontChangeListener);
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(resetActionListener);
		
		// Layout components
		
		paramsPanel = new JPanel();
		paramsPanel.setLayout(new FlowLayout());
		
		paramsPanel.add(fontNameComboBox);
		paramsPanel.add(fontSizeSlider);
		paramsPanel.add(boldCheckBox);
		paramsPanel.add(italicCheckBox);
		paramsPanel.add(resetButton);
		
		rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());
		
		rootPanel.add(paramsPanel, BorderLayout.NORTH);
		rootPanel.add(textLabel, BorderLayout.CENTER);

		this.add(rootPanel);
		
		updateText();

		this.setSize(1000, 500);
	}
	
	private void updateText() {
		String fontName = (String)fontNameComboBox.getSelectedItem();
		
		int fontSize = fontSizeSlider.getValue();
		
		int fontStyle = 0;
		if (boldCheckBox.isSelected()) {
			fontStyle |= Font.BOLD;
		}
		if (italicCheckBox.isSelected()) {
			fontStyle |= Font.ITALIC;
		}

		Font font = new Font(fontName, fontStyle, fontSize);
		
		textLabel.setFont(font);
	}
	
	private ActionListener fontNameActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateText();
		}	
	};
	
	private ChangeListener fontChangeListener = new ChangeListener() {

		@Override
		public void stateChanged(ChangeEvent e) {
			updateText();
		}	
	};
	
	private ActionListener resetActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			fontSizeSlider.setValue(DEFAULT_FONT_SIZE);
		}	
	};
	
}

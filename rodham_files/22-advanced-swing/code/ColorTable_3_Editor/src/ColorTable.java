import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;


@SuppressWarnings("serial")
public class ColorTable extends JFrame {

	private ArrayList<ColorScheme> colorSchemes;
	private ColorTableModel tableModel;
	private JTable table;

	public ColorTable(String title) throws HeadlessException {
		super(title);

		colorSchemes = generateSchemes();

		tableModel = new ColorTableModel(colorSchemes);

		table = new JTable(tableModel);
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);

		TableColumnModel columnModel = table.getColumnModel();		
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(150);
		}		
		for (int i = 1; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(new ColorCellRenderer());
			column.setCellEditor(new ColorCellEditor());
		}
		

		JPanel rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(table.getTableHeader(), BorderLayout.NORTH);
		rootPanel.add(table, BorderLayout.CENTER);

		this.add(rootPanel);
	}
	
	private ArrayList<ColorScheme> generateSchemes() {
		
		final int NUM_SCHEMES = 20;
		
		ArrayList<ColorScheme> result = new ArrayList<ColorScheme>();
		Random rand = new Random();
		
		for (int i = 1; i <= NUM_SCHEMES; ++i) {
			
			ColorScheme scheme = new ColorScheme("Scheme " + i,
													generateColor(rand),
													generateColor(rand),
													generateColor(rand),
													generateColor(rand));
			result.add(scheme);
		}
		
		return result;
	}
	
	private Color generateColor(Random rand) {
		
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		
		return new Color(r, g, b);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ColorTable frame = new ColorTable("Color Table");
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}


@SuppressWarnings("serial")
class ColorCellRenderer extends JLabel implements TableCellRenderer {

	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

	public ColorCellRenderer() {
		
		setOpaque(true);
		setFont(getFont().deriveFont(16.0f));
	}

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {

		Color c = ColorUtils.fromString((String)value);
		this.setBackground(c);
		
		if (isSelected) {
			this.setBorder(selectedBorder);
		}
		else {
			this.setBorder(unselectedBorder);
		}
		
		this.setText((String)value);
		
		return this;
	}

}


@SuppressWarnings("serial")
class ColorCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	private JComboBox<String> comboBox;
	private String currentValue;
	
	private Map<String, Color> colorMap;
	
	public ColorCellEditor() {
		
		colorMap = new TreeMap<String, Color>();
		colorMap.put("Black", Color.BLACK);
		colorMap.put("Blue", Color.BLUE);
		colorMap.put("Cyan", Color.CYAN);
		colorMap.put("Dark Gray", Color.DARK_GRAY);
		colorMap.put("Gray", Color.GRAY);
		colorMap.put("Green", Color.GREEN);
		colorMap.put("Yellow", Color.YELLOW);
		colorMap.put("Light Gray", Color.LIGHT_GRAY);
		colorMap.put("Magenta", Color.MAGENTA);
		colorMap.put("Orange", Color.ORANGE);
		colorMap.put("Pink", Color.PINK);
		colorMap.put("Red", Color.RED);
		colorMap.put("White", Color.WHITE);
			
		comboBox = new JComboBox<String>();
		comboBox.setEditable(true);
		comboBox.addActionListener(actionListener);
		for (String colorName : colorMap.keySet()) {
			comboBox.addItem(colorName);
		}
	}

	@Override
	public Object getCellEditorValue() {
		return currentValue;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		currentValue = (String)value;
		
		comboBox.setSelectedItem(currentValue);
		
		return comboBox;
	}
	
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String comboValue = (String)comboBox.getSelectedItem();
			
			Color colorValue = ColorUtils.fromString(comboValue);
			
			if (colorValue == null) {
				if (colorMap.containsKey(comboValue)) {
					colorValue = colorMap.get(comboValue);
				}
			}
			
			if (colorValue != null) {
				currentValue = ColorUtils.toString(colorValue);
			}
			
			fireEditingStopped();
		}	
	};
	
}

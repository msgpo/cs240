package client;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.*;


@SuppressWarnings("serial")
public class IndexingTable extends JPanel {

	private IndexingTableModel tableModel;
	private JTable table;
//	private BatchState bs;

	public IndexingTable(BatchState bs) {

		tableModel = new IndexingTableModel(bs);

		table = new JTable(tableModel);
		table.setRowHeight(15);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(mouseAdapter);

		TableColumnModel columnModel = table.getColumnModel();		
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(75);
		}		
		for (int i = 1; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(new iCellRenderer());
			column.setCellEditor(new iCellEditor());
		}
		

		
		JPanel rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(table.getTableHeader(), BorderLayout.NORTH);
		rootPanel.add(table, BorderLayout.CENTER);
		rootPanel.setMinimumSize(new Dimension(512, 256));

		this.add(rootPanel);
		this.setMinimumSize(new Dimension(512, 256));
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {

			if (e.isPopupTrigger()) {
				
				final int row = table.rowAtPoint(e.getPoint());
				final int column = table.columnAtPoint(e.getPoint());
				
				if (row >= 0 && row < tableModel.getRowCount() &&
						column >= 1 && column < tableModel.getColumnCount()) {
										
					// move highlight to here
				}
			}
		}
		
	};

	public void changeModel(BatchState bs){
		table.setModel(new IndexingTableModel(bs));
	}
	
		


}


@SuppressWarnings("serial")
class iCellRenderer extends JLabel implements TableCellRenderer {

	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);

	public iCellRenderer() {
		
		setOpaque(true);
		setFont(getFont().deriveFont(16.0f));
	}

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {

		//  check if wrong by iTrie, then color it red
		//Color c = ColorUtils.fromString((String)value);
		//this.setBackground(c);
		
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
class iCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	private JTextField textField;
	private String currentValue;
	
	
	public iCellEditor() {
					
		textField = new JTextField();
		textField.setEditable(true);
		textField.addActionListener(actionListener);
		// 		fill with values.
	//	for (String colorName : colorMap.keySet()) {
	//		comboBox.addItem(colorName);
	//	}
	}

	@Override
	public Object getCellEditorValue() {
		return currentValue;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		currentValue = (String)value;
		
		textField.setText(currentValue);
		
		return textField;
	}
	
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			currentValue = textField.getText();
			
		//	String comboValue = (String)comboBox.getSelectedItem();

			//  spellcheck here.
			
		//	Color colorValue = ColorUtils.fromString(comboValue);
		//	
		//	if (colorValue == null) {
		//		if (colorMap.containsKey(comboValue)) {
		//			colorValue = colorMap.get(comboValue);
		//		}
		//	}
		//	
		//	if (colorValue != null) {
		//		currentValue = ColorUtils.toString(colorValue);
		//	}
			
			fireEditingStopped();
		}	
	};
	
}


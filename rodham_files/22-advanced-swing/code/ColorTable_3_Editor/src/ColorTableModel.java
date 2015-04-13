
import java.awt.*;
import javax.swing.table.*;


@SuppressWarnings("serial")
public class ColorTableModel extends AbstractTableModel {

	private java.util.List<ColorScheme> colorSchemes;

	public ColorTableModel(java.util.List<ColorScheme> colorSchemes) {
		this.colorSchemes = colorSchemes;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int column) {

		String result = null;

		if (column >= 0 && column < getColumnCount()) {

			switch (column) {
			case 0:
				result = "Scheme Name";
				break;
			case 1:
				result = "Foreground";
				break;
			case 2:
				result = "Background";
				break;
			case 3:
				result = "Highlight";
				break;
			case 4:
				result = "Shadow";
				break;
			default:
				assert false;
				break;
			}
		} else {
			throw new IndexOutOfBoundsException();
		}

		return result;
	}

	@Override
	public int getRowCount() {
		return colorSchemes.size();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

	@Override
	public Object getValueAt(int row, int column) {

		Object result = null;

		if (row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) {

			ColorScheme cs = colorSchemes.get(row);

			switch (column) {
			case 0:
				result = cs.getName();
				break;
			case 1:
				result = ColorUtils.toString(cs.getForeground());
				break;
			case 2:
				result = ColorUtils.toString(cs.getBackground());
				break;
			case 3:
				result = ColorUtils.toString(cs.getHighlight());
				break;
			case 4:
				result = ColorUtils.toString(cs.getShadow());
				break;
			default:
				assert false;
				break;
			}
		} else {
			throw new IndexOutOfBoundsException();
		}

		return result;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		
		if (row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) {

			ColorScheme cs = colorSchemes.get(row);
			
			Color c = null;
			if (column > 0) {
				c = ColorUtils.fromString((String)value);
				if (c == null) {
					return;
				}
			}

			switch (column) {
			case 0:
				cs.setName((String)value);
				break;
			case 1:
				cs.setForeground(c);
				break;
			case 2:
				cs.setBackground(c);
				break;
			case 3:
				cs.setHighlight(c);
				break;
			case 4:
				cs.setShadow(c);
				break;
			default:
				assert false;
				break;
			}
			
			this.fireTableCellUpdated(row, column);
			
		} else {
			throw new IndexOutOfBoundsException();
		}		
	}

}

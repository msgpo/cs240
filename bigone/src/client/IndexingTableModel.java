package client;

import java.awt.*;
import javax.swing.table.*;
import java.util.*;

import shared.model.record;
import shared.model.field;


@SuppressWarnings("serial")
public class IndexingTableModel extends AbstractTableModel {
	
	private BatchState bs;
	
	public IndexingTableModel(BatchState bState) {
		bs = bState;
	}

	@Override
	public int getColumnCount() {
		return bs.fields.size() + 1;
	}

	@Override
	public String getColumnName(int column) {

		String result = null;
		
		if(column == 0){
			result = "Record Number";
		}
		else if(column >= 0 && column < getColumnCount()){
			result = bs.fields.get(column - 1).getTitle();
		}
		else {
			throw new IndexOutOfBoundsException();
		}

		return result;
	}

	@Override
	public int getRowCount() {
		return bs.rows;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if(column != 0){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Object getValueAt(int row, int column) {

		Object result = null;


		if(column == 0){
			// return record number: i.e. row
			result = row + 1;
		}
		else if(row >= 0 && row < getRowCount() && 
				column > 0 && 
				column < getColumnCount()){
			for(record r : bs.records){
				if(r.getNumber() == row + 1 &&
						r.getFieldNumber() == column){
					// remember FNumber starts at one,
					// so counting from the 1st column
					// makes sense.
					result = r.getValue();
				}
			}
		}
		else {
			throw new IndexOutOfBoundsException();
		}

		return result;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {

		if(column == 0){
			// ha ha can't happen.
			this.fireTableCellUpdated(row, column);
		}
		else if(row >= 0 && row < getRowCount() && column > 0
				&& column < getColumnCount()){
			for(record r : bs.records){
				if(r.getNumber() == row + 1 &&
						r.getFieldNumber() == column){
					// remember FNumber starts at one,
					// so counting from the 1st column
					// makes sense.
					r.setValue((String) value);
				}
			}			
			this.fireTableCellUpdated(row, column);	
		} 
		else {
			throw new IndexOutOfBoundsException();
		}		
	}

}


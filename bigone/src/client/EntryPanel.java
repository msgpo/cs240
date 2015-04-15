package client;

import java.awt.*;
import javax.swing.*;

public class EntryPanel extends JPanel{
	
		JScrollPane tablePane;
		JPanel formPanel;
		IndexingTable iTable;
	
	public EntryPanel(BatchState bs){
	 	new JPanel();
		JTabbedPane tabs = new JTabbedPane();

	//	JScrollPane tablePane = new JScrollPane();
		iTable = new IndexingTable(bs);
		iTable.setMinimumSize(new Dimension(512, 256));
	//	tablePane.add(iTable);
	//	tablePane.setMinimumSize(new Dimension(512, 256));
		JPanel formPanel = new JPanel();
		formPanel.setMinimumSize(new Dimension(512, 256));

		tabs.addTab("Table Entry", iTable);
		tabs.addTab("Form Entry", formPanel);
		this.add(tabs);
		this.setMinimumSize(new Dimension(512, 256));
		this.setPreferredSize(new Dimension(512, 256));

	}

	public void init(BatchState bs){
		iTable.changeModel(bs);
	}



		
}


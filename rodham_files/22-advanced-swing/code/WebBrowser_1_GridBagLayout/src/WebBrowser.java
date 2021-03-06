
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;


@SuppressWarnings("serial")
public class WebBrowser extends JFrame {

    private JMenuItem backMenuItem;
    private JMenuItem forwardMenuItem;
    private JMenuItem addMenuItem;
    private JMenuItem removeMenuItem;
    private JMenuItem exitMenuItem;
    private JTextField urlField;
    private JButton addButton;
    private JButton removeButton;
    private JButton backButton;
    private JButton forwardButton;
    private JList<String> favList;
    private JPopupMenu favPopupMenu;
    private JMenuItem favAddMenuItem;
    private JMenuItem favRemoveMenuItem;
    private JEditorPane htmlViewer;
    private JPopupMenu htmlPopupMenu;
    private JMenuItem htmlBackMenuItem;
    private JMenuItem htmlForwardMenuItem;

    private ArrayList<String> history;
    private int historyPosition;

    public WebBrowser(String title) {
        super(title);
        
        history = new ArrayList<String>();
        historyPosition = -1;
        
        createComponents();
    }

    private void createComponents() {
    	
        addWindowListener(windowAdapter);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Commands");
        menu.setMnemonic('c');
        menuBar.add(menu);

        backMenuItem = new JMenuItem("Back", KeyEvent.VK_B);
        backMenuItem.addActionListener(actionListener);
        menu.add(backMenuItem);

        forwardMenuItem = new JMenuItem("Forward", KeyEvent.VK_F);
        forwardMenuItem.addActionListener(actionListener);
        menu.add(forwardMenuItem);

        addMenuItem = new JMenuItem("Add Favorite", KeyEvent.VK_A);
        addMenuItem.addActionListener(actionListener);
        menu.add(addMenuItem);

        removeMenuItem = new JMenuItem("Remove Favorite", KeyEvent.VK_R);
        removeMenuItem.addActionListener(actionListener);
        menu.add(removeMenuItem);

        exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(actionListener);
        menu.add(exitMenuItem);
        
        JLabel urlLabel = new JLabel("URL:");
        
        urlField = new JTextField();
        urlField.setOpaque(true);
        urlField.setBackground(Color.white);
        urlField.setPreferredSize(new Dimension(750, 30));
        urlField.addActionListener(actionListener);

        addButton = new JButton("Add");
        addButton.addActionListener(actionListener);
        
        removeButton = new JButton("Remove");
        removeButton.addActionListener(actionListener);
        
        JToolBar favToolBar = new JToolBar();
        favToolBar.add(addButton);
        favToolBar.add(removeButton);
        
        favList = new JList<String>(new String[]{ "FAVORITES WILL GO HERE" });
        favList.addMouseListener(mouseAdapter);
        
        JScrollPane favScrollPane = new JScrollPane(favList);
        favScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        favScrollPane.setPreferredSize(new Dimension(250, 600));
        
        favAddMenuItem = new JMenuItem("Add");
        favAddMenuItem.addActionListener(actionListener);
        
        favRemoveMenuItem = new JMenuItem("Remove");
        favRemoveMenuItem.addActionListener(actionListener);
        
        favPopupMenu = new JPopupMenu();
        favPopupMenu.add(favAddMenuItem);
        favPopupMenu.add(favRemoveMenuItem);

        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        
        forwardButton = new JButton("Forward");
        forwardButton.addActionListener(actionListener);
        
        JToolBar htmlToolBar = new JToolBar();
        htmlToolBar.add(backButton);
        htmlToolBar.add(forwardButton);

        htmlViewer = new JEditorPane();
        htmlViewer.setOpaque(true);
        htmlViewer.setBackground(Color.white);
        htmlViewer.setPreferredSize(new Dimension(500, 600));
        htmlViewer.setEditable(false);
        htmlViewer.addHyperlinkListener(hyperlinkListener);
        htmlViewer.addMouseListener(mouseAdapter);

        JScrollPane htmlScrollPane = new JScrollPane(htmlViewer);
        htmlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        htmlScrollPane.setPreferredSize(new Dimension(500, 600));
        
        htmlBackMenuItem = new JMenuItem("Back");
        htmlBackMenuItem.addActionListener(actionListener);
        
        htmlForwardMenuItem = new JMenuItem("Forward");
        htmlForwardMenuItem.addActionListener(actionListener);
        
        htmlPopupMenu = new JPopupMenu();
        htmlPopupMenu.add(htmlBackMenuItem);
        htmlPopupMenu.add(htmlForwardMenuItem);

        JPanel rootPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setGbc(gbc, 0, 0, 1, 1);
        rootPanel.add(urlLabel, gbc);
        
        setGbc(gbc, 1, 0, 11, 1);
        setGbcWeight(gbc, 100, 0);
        setGbcFillAnchor(gbc, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
        rootPanel.add(urlField, gbc);
        
        setGbc(gbc, 0, 1, 2, 1);
        rootPanel.add(favToolBar, gbc);
        
        setGbc(gbc, 4, 1, 2, 1);
        rootPanel.add(htmlToolBar, gbc);
        
        setGbc(gbc, 0, 2, 4, 1);
        setGbcWeight(gbc, 0, 100);
        setGbcFillAnchor(gbc, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        rootPanel.add(favScrollPane, gbc);
        
        setGbc(gbc, 4, 2, 8, 1);
        setGbcWeight(gbc, 100, 100);
        setGbcFillAnchor(gbc, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        rootPanel.add(htmlScrollPane, gbc);
        
        this.add(rootPanel);
    }
    
    private static GridBagConstraints _gbc = new GridBagConstraints();
    
    private void initGbc(GridBagConstraints gbc) {
    	gbc.gridx = _gbc.gridx;
    	gbc.gridy = _gbc.gridy;
    	gbc.gridwidth = _gbc.gridwidth;
    	gbc.gridheight = _gbc.gridheight;
    	gbc.weightx = _gbc.weightx;
    	gbc.weighty = _gbc.weighty;
    	gbc.anchor = _gbc.anchor;
    	gbc.fill = _gbc.fill;
    	gbc.insets = _gbc.insets;
    	gbc.ipadx = _gbc.ipadx;
    	gbc.ipady = _gbc.ipady;
    }
    
    private void setGbc(GridBagConstraints gbc, int gridx, int gridy,
    						int gridwidth, int gridheight) {
    	initGbc(gbc);
    	gbc.gridx = gridx;
    	gbc.gridy = gridy;
    	gbc.gridwidth = gridwidth;
    	gbc.gridheight = gridheight;
    }
    
    private void setGbcWeight(GridBagConstraints gbc, int weightx, int weighty) {
    	gbc.weightx = weightx;
    	gbc.weighty = weighty;
    }
    
    private void setGbcFillAnchor(GridBagConstraints gbc, int fill, int anchor) {
    	gbc.fill = fill;
    	gbc.anchor = anchor;
    }
    
    private void setGbcPadding(GridBagConstraints gbc, int top, int left, 
    							int bottom, int right, int ipadx, int ipady) {
    	gbc.insets = new Insets(top, left, bottom, right);
    	gbc.ipadx = ipadx;
    	gbc.ipady = ipady;
    }
    
    private WindowAdapter windowAdapter = new WindowAdapter() {
    	
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    };

    private ActionListener actionListener = new ActionListener() {
    	
	    public void actionPerformed(ActionEvent e) {
	    	
	        if (e.getSource() == urlField) {
	        	
	            historyLoadPage(urlField.getText());
	        }
	        else if (e.getSource() == backMenuItem || 
	        			e.getSource() == backButton ||
	        			e.getSource() == htmlBackMenuItem) {
	        	
	            System.out.println("go back");
	            
	            if (historyPosition > 0) {
	            	--historyPosition;
	                loadPage(history.get(historyPosition));
	            }
	        }
	        else if (e.getSource() == forwardMenuItem || 
	        			e.getSource() == forwardButton ||
	        			e.getSource() == htmlForwardMenuItem) {
	        	
	            System.out.println("go forward");
	            
	            if (historyPosition < (history.size() - 1)) {
	            	++historyPosition;
	                loadPage(history.get(historyPosition));
	            }
	        }
	        else if (e.getSource() == addMenuItem || 
	        			e.getSource() == addButton ||
	        			e.getSource() == favAddMenuItem) {
	        	
	        	System.out.println("add favorite");
	        }
	        else if (e.getSource() == removeMenuItem ||
	        			e.getSource() == removeButton ||
	        			e.getSource() == favRemoveMenuItem) {
	        	
	        	System.out.println("remove favorite");
	        }
	        else if (e.getSource() == exitMenuItem) {
	            System.exit(0);
	        }
	    }
    };

    private HyperlinkListener hyperlinkListener = new HyperlinkListener() {
    	
	    public void hyperlinkUpdate(HyperlinkEvent e) {
	    	
	        System.out.println("hyperlink");
	        
	        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
	            historyLoadPage(e.getURL().toString());
	        }
	    }
    };
    
    private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if (e.getSource() == favList) {
					favPopupMenu.show(e.getComponent(),
		                       			e.getX(), e.getY());
				}
				else if (e.getSource() == htmlViewer) {
					htmlPopupMenu.show(e.getComponent(),
                   						e.getX(), e.getY());
				}
			}
		} 	
    };
    
    private void loadPage(String url) {
    	
        System.out.println("loadPage: " + url);

        urlField.setText(url);

        try {
            htmlViewer.setPage(url);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void historyLoadPage(String url) {
    	
    	// Remove URLs after the current one in the history
    	while (history.size() > (historyPosition + 1))
    		history.remove(historyPosition + 1);
 
    	// Append the new URL to the history
    	history.add(url);
    	
    	// Update the history position
    	++historyPosition;
    	assert (historyPosition == history.size() - 1);
    	
    	// Load the new URL
        loadPage(url);
    }

    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
               WebBrowser frame = new WebBrowser("Web Browser");
               frame.pack();
               frame.setVisible(true);
           }
        });
    }

}

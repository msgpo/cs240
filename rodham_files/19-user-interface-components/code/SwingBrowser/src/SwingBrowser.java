
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.net.*;


public class SwingBrowser extends JFrame 
    implements ActionListener, HyperlinkListener, TreeSelectionListener {

    private class LinkInfo {
        public String text;
        public String url;

        public LinkInfo(String t, String u) {
            text = t;
            url = u;
        }

        public String toString() {
            return text;
        }
    }

    JMenuBar menuBar;
    JMenuItem backMenuItem;
    JMenuItem exitMenuItem;
    JTextField urlField;
    JEditorPane htmlViewer;
    JScrollPane htmlScrollPane;
    JTree favTree;
    DefaultTreeModel favTreeModel;
    JScrollPane favScrollPane;
    JButton addFavButton;
    JButton remFavButton;

    Stack history = new Stack();
    String curURL = "";

    public SwingBrowser(String title) {
        super(title);
        createComponents();
    }

    private void createComponents() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Commands");
        menu.setMnemonic('c');
        menuBar.add(menu);

        backMenuItem = new JMenuItem("Back", KeyEvent.VK_B);
        backMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        backMenuItem.addActionListener(this);
        menu.add(backMenuItem);

        exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(this);
        menu.add(exitMenuItem);

        urlField = new JTextField(50);
        urlField.setOpaque(true);
        urlField.setBackground(Color.white);
        urlField.setPreferredSize(new Dimension(750, 30));
        urlField.addActionListener(this);

        htmlViewer = new JEditorPane();
        htmlViewer.setOpaque(true);
        htmlViewer.setBackground(Color.white);
        htmlViewer.setPreferredSize(new Dimension(500, 600));
        htmlViewer.setEditable(false);
        htmlViewer.addHyperlinkListener(this);

        htmlScrollPane = new JScrollPane(htmlViewer);
        htmlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        htmlScrollPane.setPreferredSize(new Dimension(500, 600));

        favTreeModel = new DefaultTreeModel(createNodes());
        favTree = new JTree(favTreeModel);
        favTree.setOpaque(true);
        favTree.setBackground(Color.white);
        favTree.setPreferredSize(new Dimension(250, 600));
        favTree.setEditable(false);
        favTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        favTree.setShowsRootHandles(false);
        favTree.addTreeSelectionListener(this);

        favScrollPane = new JScrollPane(favTree);
        favScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        favScrollPane.setPreferredSize(new Dimension(250, 600));

        addFavButton = new JButton("Add");
        addFavButton.setOpaque(true);
        addFavButton.addActionListener(this);

        remFavButton = new JButton("Remove");
        remFavButton.setOpaque(true);
        remFavButton.addActionListener(this);

        JPanel favBtnPanel = new JPanel();
        favBtnPanel.add(addFavButton);
        favBtnPanel.add(remFavButton);

        JPanel favPanel = new JPanel(new BorderLayout());
        favPanel.add(favBtnPanel, BorderLayout.NORTH);
        favPanel.add(favScrollPane, BorderLayout.CENTER);

        JPanel browsePanel = new JPanel(new BorderLayout());
        browsePanel.add(favPanel, BorderLayout.WEST);
        browsePanel.add(htmlScrollPane, BorderLayout.CENTER);

        getContentPane().add(urlField, BorderLayout.NORTH);
        getContentPane().add(browsePanel, BorderLayout.CENTER);
    }

    DefaultMutableTreeNode createNodes() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Favorites");

        DefaultMutableTreeNode byu = new DefaultMutableTreeNode("BYU");
        byu.add(new DefaultMutableTreeNode(new LinkInfo("Computer Science",
                                                        "http://www.cs.byu.edu")));
//                                                        "file:/public_html/index.html")));

        DefaultMutableTreeNode news = new DefaultMutableTreeNode("News");
        news.add(new DefaultMutableTreeNode(new LinkInfo("CNN",
                                                        "http://www.cnn.com")));
//                                                        "file:/public_html/cs240/index.html")));

        DefaultMutableTreeNode sports = new DefaultMutableTreeNode("Sports");
        sports.add(new DefaultMutableTreeNode(new LinkInfo("Sports Illustrated",
                                                        "http://sportsillustrated.cnn.com")));
//                                                        "file:/public_html/cs240/crawler/index.html")));

        DefaultMutableTreeNode weather = new DefaultMutableTreeNode("Weather");
        weather.add(new DefaultMutableTreeNode(new LinkInfo("Weather Channel",
                                                        "http://www.weather.com")));
//                                                        "file:/public_html/cs240/chess/index.html")));

        root.add(byu);
        root.add(news);
        root.add(sports);
        root.add(weather);

        return root;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == urlField) {
            historyLoadPage(urlField.getText());
        }
        else if (e.getSource() == backMenuItem) {
            System.out.println("go back");
            if (!history.isEmpty()) {
                loadPage((String)history.pop());
            }
        }
        else if (e.getSource() == exitMenuItem) {
            System.exit(0);
        }
        else if (e.getSource() == addFavButton) {
            DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)favTree.getLastSelectedPathComponent();
            if (node == null || node.isLeaf()) {
                JOptionPane.showMessageDialog(this, "Please select a favorites folder before clicking Add");
            }
            else {
                String text = JOptionPane.showInputDialog("Title for this page");
                if (text != null) {
                    favTreeModel.insertNodeInto(
                        new DefaultMutableTreeNode(new LinkInfo(text, curURL)),
                        node, node.getChildCount());
                } 
            }
        }
        else if (e.getSource() == remFavButton) {
            DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)favTree.getLastSelectedPathComponent();
            if (node == null || !node.isLeaf()) {
                JOptionPane.showMessageDialog(this, "Please select a favorites link before clicking Remove");
            }
            else {
                favTreeModel.removeNodeFromParent(node);
            }
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        System.out.println("hyperlink");
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            historyLoadPage(e.getURL().toString());
        }
    }

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = 
            (DefaultMutableTreeNode)favTree.getLastSelectedPathComponent();
        if (node != null) {
            if (node.isLeaf()) {
                LinkInfo linkInfo = (LinkInfo)node.getUserObject();
                historyLoadPage(linkInfo.url);
            }
        }
    }

    private void loadPage(String url) {
        System.out.println("loadPage: " + url);
        urlField.setText(url);
        curURL = url;
        try {
            htmlViewer.setPage(url);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void historyLoadPage(String url) {
        if (!curURL.equals("")) {
            history.push(curURL);
        }

        loadPage(url);
    }

    public static void main(String[] args) {
        SwingBrowser frame = new SwingBrowser("Swing Web Browser");
        frame.pack();
        frame.setVisible(true);
    }

}

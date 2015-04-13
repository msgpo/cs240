import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class FavPanel extends JPanel {

	public interface Context {

		public String getCurrentUrl();

		public void onFavSelected(String url);
	}

	private Context context;
	private JButton addButton;
	private JButton removeButton;
	private JList<FavInfo> favList;
	private FavListModel favListModel;
	private JPopupMenu favPopupMenu;
	private JMenuItem favAddMenuItem;
	private JMenuItem favRemoveMenuItem;

	public FavPanel(Context c) {

		super();

		context = c;

		addButton = new JButton("Add");
		addButton.addActionListener(actionListener);

		removeButton = new JButton("Remove");
		removeButton.addActionListener(actionListener);

		JToolBar favToolBar = new JToolBar();
		favToolBar.add(addButton);
		favToolBar.add(removeButton);

		favListModel = new FavListModel();
		favListModel.add(new FavInfo("BYU CS", "http://www.cs.byu.edu/"));
		favListModel.add(new FavInfo("CNN", "http://www.cnn.com/"));
		favListModel.add(new FavInfo("ESPN", "http://www.espn.com/"));
		favListModel.add(new FavInfo("Weather", "http://www.weather.com/"));

		favList = new JList<FavInfo>(favListModel);
		favList.addMouseListener(mouseAdapter);

		JScrollPane favScrollPane = new JScrollPane(favList);
		favScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		favScrollPane.setPreferredSize(new Dimension(250, 600));

		favAddMenuItem = new JMenuItem("Add");
		favAddMenuItem.addActionListener(actionListener);

		favRemoveMenuItem = new JMenuItem("Remove");
		favRemoveMenuItem.addActionListener(actionListener);

		favPopupMenu = new JPopupMenu();
		favPopupMenu.add(favAddMenuItem);
		favPopupMenu.add(favRemoveMenuItem);

		this.setLayout(new BorderLayout());
		this.add(favToolBar, BorderLayout.NORTH);
		this.add(favScrollPane, BorderLayout.CENTER);
	}

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == addButton || e.getSource() == favAddMenuItem) {
				addFav();
			} else if (e.getSource() == removeButton
					|| e.getSource() == favRemoveMenuItem) {
				removeFav();
			}
		}
	};

	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {

			if (e.isPopupTrigger()) {
				if (e.getSource() == favList) {
					favPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if (e.getButton() == MouseEvent.BUTTON1) {
				
				int index = favList.locationToIndex(e.getPoint());
				if (index >= 0 && index < favListModel.getSize()) {

					FavInfo selectedFav = favListModel.getElementAt(index);
					if (context != null) {
						context.onFavSelected(selectedFav.getUrl());
					}
				}
			}
		}
	};

	public void addFav() {

		System.out.println("add favorite");

		if (context.getCurrentUrl().equals("")) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
					"You must first load a web page", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			FavDialog favDialog = new FavDialog(
					(JFrame) this.getTopLevelAncestor(), true);
			favDialog.setLocationRelativeTo(this.getTopLevelAncestor());
			favDialog.setVisible(true);

			if (favDialog.getStatus()) {
				String title = favDialog.getTitle();
				favListModel.add(new FavInfo(title, context.getCurrentUrl()));
			}
		}
	}

	public void removeFav() {

		System.out.println("remove favorite");

		FavInfo selectedFav = favList.getSelectedValue();
		if (selectedFav != null) {
			favListModel.remove(selectedFav);
		}
		else {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
					"You must select a favorite to remove", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}

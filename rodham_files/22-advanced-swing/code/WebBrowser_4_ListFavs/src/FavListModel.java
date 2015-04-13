
import javax.swing.*;
import java.util.*;


@SuppressWarnings("serial")
public class FavListModel extends AbstractListModel<FavInfo> {

	private List<FavInfo> favorites;
	
	public FavListModel() {
		favorites = new ArrayList<FavInfo>();
	}
	
	@Override
	public FavInfo getElementAt(int index) {
		return favorites.get(index);
	}

	@Override
	public int getSize() {
		return favorites.size();
	}
	
	public void add(FavInfo fav) {
		favorites.add(fav);
		this.fireContentsChanged(this, favorites.size() - 1, favorites.size() - 1);
	}
	
	public void remove(FavInfo fav) {

		int index = favorites.indexOf(fav);
		if (index >= 0) {
			favorites.remove(index);
			this.fireContentsChanged(this, index, index);
		}
	}

}

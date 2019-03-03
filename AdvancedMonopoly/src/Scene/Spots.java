package scene;

import module.DataManager;
import MTool.MCellLabel;
import MTool.MPanel;
// 显示游戏物件的层：地块，各种地点等。由一个个小的label组成。
public class Spots extends MPanel {
	
	private DataManager data = new DataManager();
	
	private MCellLabel[] cellLabels = new MCellLabel[data.map().length()];
	
	public Spots() {
		for (int i = 0; i < data.map().length(); i++) {
			cellLabels[i] = new MCellLabel(data.map().getCellAt(i));
			add(cellLabels[i]);
		}
			
	}
	
	public MCellLabel getCellLabel(int index) {
		return cellLabels[index];
	}

}

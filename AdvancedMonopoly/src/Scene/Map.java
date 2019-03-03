package scene;

import MTool.MLabel;
import MTool.MPanel;

public class Map extends MPanel {

	private MLabel mapl = new MLabel("System/map.png");
	
	public Map() {
		super();
		mapl.setBounds(0, 0, 22*32, 16*32);
		add(mapl);
	}
	

}
